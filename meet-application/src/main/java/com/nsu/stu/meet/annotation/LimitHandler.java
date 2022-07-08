package com.nsu.stu.meet.annotation;

import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.nsu.stu.meet.common.base.DataScopeHolder;
import com.nsu.stu.meet.common.base.DataScopeParam;
import com.nsu.stu.meet.common.base.JwtStorage;
import com.nsu.stu.meet.common.util.MySqlParserUtils;
import com.nsu.stu.meet.model.enums.LimitEnums;
import com.nsu.stu.meet.service.UserRelationService;
import com.nsu.stu.meet.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Xinhua X Yang
 */
@Aspect
@Slf4j
@Component
public class LimitHandler implements DataPermissionHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRelationService relationService;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.nsu.stu.meet.annotation.LimitQuery)")
    public void limitQuery() {
        // 织入点
    }

    @Before("limitQuery()")
    public void doBefore(JoinPoint point) {
        // 初始化Data Scope
        DataScopeHolder.remove();
        DataScopeParam dataScopeParam = new DataScopeParam();
        // 从注解中获得字段
        LimitQuery annotationLog = getAnnotationLog(point);
        // 赋值
        if (annotationLog != null) {
            dataScopeParam.setUserColumn(annotationLog.userColumn());
            dataScopeParam.setLimitColumn(annotationLog.limitColumn());
        }
        // 获得当前用户id
        Long userId = JwtStorage.userId();
        log.info("前置条件");
        // 查询用户关注和拉黑名单
        List<Long> userFollowIds = relationService.getUserFollowIds(userId);
        List<Long> blockedEach = relationService.getBlockedEach(userId);
        dataScopeParam.setBlockedEachIds(blockedEach);
        dataScopeParam.setUserFollowIds(userFollowIds);
        // 获取用户权限
        // 存入LocalThread
        DataScopeHolder.set(dataScopeParam);
    }

    /**
     * 获得注解
     */
    private LimitQuery getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(LimitQuery.class);
        }
        return null;
    }
    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        DataScopeParam dataScopeParam = DataScopeHolder.get();
        if (dataScopeParam == null) {
            return where;
        }
        log.info("开始拼接sql");
        // 获得当前用户id
        Long userId = JwtStorage.userId();
        // 查询用户关注和拉黑名单
        List<Long> userFollowIds = relationService.getUserFollowIds(userId);
        List<Long> blockedEach = relationService.getBlockedEach(userId);

        // 用户可看关注的人
        Expression follow = MySqlParserUtils.inExpression(dataScopeParam.getUserColumn(), userFollowIds);
        Expression followLimit = MySqlParserUtils.equalsTo(dataScopeParam.getLimitColumn(), LimitEnums.FOLLOW.value());
        // 用户不能看任一拉黑人
        Expression block = MySqlParserUtils.notInExpression(dataScopeParam.getUserColumn(), blockedEach);
        // 用户对自己无限制
        Expression self = MySqlParserUtils.equalsTo(dataScopeParam.getUserColumn(), userId);
        // 用户对公开权限无限制
        Expression publicLimit = MySqlParserUtils.equalsTo(dataScopeParam.getLimitColumn(), LimitEnums.PUBLIC.value());
        followLimit = MySqlParserUtils.andExpression(follow, followLimit);
        Expression orExpression = MySqlParserUtils.orExpression(Arrays.asList(followLimit, publicLimit));
        orExpression = MySqlParserUtils.andExpression(orExpression, block);
        orExpression = MySqlParserUtils.orExpression(Arrays.asList(orExpression, self));
        log.info("拼接完成：" + orExpression.toString());
        if (where == null) {
            return orExpression;
        } else {
            return new AndExpression(where, orExpression);
        }

    }



}
