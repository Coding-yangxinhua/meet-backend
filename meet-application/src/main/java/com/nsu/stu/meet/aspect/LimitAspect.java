package com.nsu.stu.meet.aspect;

import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.model.BaseModel;
import com.nsu.stu.meet.model.vo.LimitVo;
import com.nsu.stu.meet.service.CheckService;
import com.nsu.stu.meet.service.RelationLimitService;
import com.nsu.stu.meet.service.UserRelationService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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

    /**
     * 在切点之前织入
     * @param joinPoint
     * @throws Throwable
     */
    @Around("limit()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String targetName = joinPoint.getTarget().getClass().getName();
        Object obj = joinPoint.getArgs()[0];
        Long queryId = null;

        if (obj.getClass().equals(Long.class)) {
            queryId = (Long) obj;
        } else {
            queryId = ((BaseModel) obj).getQueryId();
        }
        LimitVo limitVo = null;
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method :
                methods) {
            Limit annotation = method.getAnnotation(Limit.class);
            if (annotation != null) {
                Class<?> clazz = annotation.clazz();
                CheckService checkService = (CheckService) applicationContext.getBean(clazz);
                limitVo = checkService.getLimitVo(queryId);
            }
        }

        // 实体类不存在
        if (limitVo == null) {
            return ResponseEntity.checkError(SystemConstants.UNKNOWN_ERROR);
        }
        // 待查询用户及其所需权限
        Long queryUserId = limitVo.getUserId();
        Integer limitId = limitVo.getLimitId();
        if (queryUserId == null || limitId == null) {
            return ResponseEntity.checkError(SystemConstants.INFO_NOT_EXISTS);
        }
        // 判断是否在黑名单内
        Long tokenUserId = JwtStorage.userId();
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

}