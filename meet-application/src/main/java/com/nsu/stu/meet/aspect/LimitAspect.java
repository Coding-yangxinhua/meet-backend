package com.nsu.stu.meet.aspect;

import com.nsu.stu.meet.annotation.Limit;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.base.LimitBaseService;
import com.nsu.stu.meet.service.RelationLimitService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LimitAspect {

    @Autowired
    RelationLimitService relationLimitService;

    /** 以自定义 @WebLog 注解为切点 */
    @Pointcut("@annotation(com.nsu.stu.meet.annotation.Limit)")
    public void limit() {}

    /**
     * 在切点之前织入
     * @param joinPoint
     * @throws Throwable
     */
    @Before("limit()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Long queryId = (Long) joinPoint.getArgs()[0];
        Long userId = null;
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method :
                methods) {
            Limit annotation = method.getAnnotation(Limit.class);
            Class clazz = annotation.clazz();
            LimitBaseService limitBaseService = (LimitBaseService) clazz.newInstance();
            userId = limitBaseService.getUserId(queryId);
        }

        if (userId != null) {
            Long tokenUserId = JwtStorage.userId();
            relationLimitService.getUserRelationLimit(tokenUserId, userId);
        }

    }

}