package com.nsu.stu.meet.aspect;

import com.alibaba.fastjson.JSON;
import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.JwtInfo;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.BaseModel;
import com.nsu.stu.meet.model.CheckModel;
import com.nsu.stu.meet.model.vo.LimitVo;
import com.nsu.stu.meet.service.CheckService;
import com.nsu.stu.meet.service.RelationLimitService;
import com.nsu.stu.meet.service.UserRelationService;
import com.nsu.stu.meet.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class LimitAspect {

    @Autowired
    UserRelationService relationService;

    @Autowired
    RelationLimitService relationLimitService;

    /** 以自定义 @WebLog 注解为切点 */
    @Pointcut("@annotation(com.nsu.stu.meet.annotation.Limit)")
    public void limit() {}

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserService userService;

    @Autowired
    StringRedisTemplate redisTemplate;



    /**
     * 在切点之前织入
     * @param joinPoint
     * @throws Throwable
     */
    @Around("limit()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String targetName = joinPoint.getTarget().getClass().getName();
        // 获得待查询id
        Long queryId = getQueryId(joinPoint.getArgs()[0]);
        // 自身userId
        Long tokenUserId = JwtStorage.userId();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        Class<?> clazz = null;
        boolean half = false;
        for (Method method :
                methods) {
            Limit annotation = method.getAnnotation(Limit.class);
            if (annotation != null) {
                clazz = annotation.clazz();
                half = annotation.half();
                break;
            }
        }
        if (clazz == null) return joinPoint.proceed();
        // 获得权限实体类
        LimitVo limitVo = getLimitVo(clazz, queryId);
        // 实体类不存在
        if (limitVo == null) {
            return ResponseEntity.checkError(SystemConstants.UNKNOWN_ERROR);
        }
        // 待查询用户及其所需权限
        Long queryUserId = limitVo.getUserId();
        Integer limitId = limitVo.getLimitId();
        // 若查询用户不存在：阻止
        if (queryUserId == null || !userService.checkExists(queryUserId)) {
            return ResponseEntity.checkError(SystemConstants.INFO_NOT_EXISTS);
        }
        // 若查询用户与登录用户id相等，放行
        if (queryUserId.equals(tokenUserId)) {
            JwtStorage.set(true);
            return joinPoint.proceed();
        }
        JwtStorage.set(false);
        // 判断是否在黑名单内
        if (half) {
            List<Long> blockedUser = relationService.getBlockedUser(tokenUserId);
            if (blockedUser.contains(queryUserId)) {
                return ResponseEntity.checkError(SystemConstants.BLOCK);
            }
            return joinPoint.proceed();
        }
        boolean blockedEach = relationService.isBlockedEach(tokenUserId, limitVo.getUserId());
        if (blockedEach) {
            return ResponseEntity.checkError(SystemConstants.BLOCK);
        }
        // 判断是否有权限
        boolean limitPass = relationLimitService.isLimitPass(tokenUserId, queryUserId, limitId);
        if (limitPass) {
            return joinPoint.proceed();
        }
        return ResponseEntity.checkError(SystemConstants.NO_RIGHTS_VISIT);
    }

    /**
     * 获得待查询id
     * @param obj
     * @return
     */
    private Long getQueryId (Object obj) {
        Long queryId = null;
        if (obj.getClass().equals(Long.class)) {
            queryId = (Long) obj;

        } else {
            queryId = ((CheckModel) obj).getQueryId();
        }
        return queryId;
    }
    private LimitVo getLimitVo (Class<?> clazz, Long queryId) {
        LimitVo limitVo = null;
        CheckService checkService = (CheckService) applicationContext.getBean(clazz);
        String key = OwnUtil.getRedisKey("LIMIT", clazz.getSimpleName(), queryId);
        String limitVoString = redisTemplate.opsForValue().get(key);
        if (limitVoString != null) {
            limitVo = JSON.parseObject(limitVoString, LimitVo.class);
        } else {
            limitVo = checkService.getLimitVo(queryId);
            redisTemplate.opsForValue().set(key, JSON.toJSONString(limitVo), 1, TimeUnit.HOURS);
        }
        return limitVo;
    }


}