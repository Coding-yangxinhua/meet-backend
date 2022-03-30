package com.nsu.stu.meet.aspect;

import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import com.nsu.stu.meet.model.vo.LimitVo;
import com.nsu.stu.meet.service.CheckService;
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
        Long queryId = (Long) joinPoint.getArgs()[0];
        LimitVo limitVo = null;
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method :
                methods) {
            Limit annotation = method.getAnnotation(Limit.class);
            if (annotation != null) {
                Class clazz = annotation.clazz();
                CheckService checkService = (CheckService) applicationContext.getBean(clazz);
                limitVo = checkService.getLimitVo(queryId);
            }
        }
        // 如果用户不存在或权限不存在
        if (limitVo == null || limitVo.getUserId() != null || limitVo.getLimitId() != null) {
            return ResponseEntity.checkError(SystemConstants.UNKNOWN_ERROR);
        }
        Long tokenUserId = JwtStorage.userId();
        boolean blockedEach = relationService.isBlockedEach(tokenUserId, limitVo.getUserId());
        if (blockedEach) {
            return ResponseEntity.checkError(SystemConstants.BLOCK);
        }
        return joinPoint.proceed();

    }

}