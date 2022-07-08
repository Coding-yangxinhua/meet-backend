package com.nsu.stu.meet.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xinhua X Yang
 */
@Slf4j
@UtilityClass
public class MySqlParserUtils {
    public <T> EqualsTo equalsTo(String column, T data) {
        return new EqualsTo(new Column(column), new StringValue(String.valueOf(data)));
    }

    public <T> Expression inExpression(String column, List<T> dataList) {
        if (dataList.isEmpty()) {
            return new EqualsTo(new StringValue("1"), new StringValue("1"));
        }
        List<Expression> collect = dataList.stream().map(data -> new StringValue(String.valueOf(data))).collect(Collectors.toList());
        ItemsList losList = new ExpressionList(collect);
        return new InExpression(new Column(column), losList);
    }

    public <T> Expression notInExpression(String column, List<T> dataList) {
        if (dataList.isEmpty()) {
            return new EqualsTo(new StringValue("1"), new StringValue("1"));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(column).append(" ").append("not in").append(" ").append("(");
        for (int i = 0; i < dataList.size() - 1; i++) {
            sb.append(dataList.get(i)).append(",");
        }
        sb.append(dataList.get(dataList.size() - 1)).append(")");
        Expression expression = new AndExpression();
        try {
             expression = CCJSqlParserUtil.parseCondExpression(sb.toString());
        } catch (JSQLParserException e) {
            log.error("sql转换失败");
        }
        return expression;
    }

    /**
    将不同条件以 and 拼接，同时以 （ ）包裹
     **/
    public Expression andExpression(Expression where, Expression... others) {
        if (others.length == 0) {
            return where;
        }
        Expression andExpression;
        if (where != null) {
            andExpression = where;
        } else {
            andExpression = new AndExpression();
        }
        for (int i = 0; i < others.length; i ++) {
            if (others[i] == null) {
                continue;
            }
            andExpression = new AndExpression(andExpression, others[i]);
        }
        return new Parenthesis(andExpression);
    }

    public Expression orExpression(Expression where, Expression... others) {
        OrExpression orExpression = new OrExpression(where, others[0]);
        for (int i = 1; i < others.length; i ++) {
            orExpression = new OrExpression(orExpression, others[i]);
        }
        return orExpression;
    }

    /**
    将不同条件以 or 拼接，同时以 （ ）包裹
    **/
    public Expression orExpression(List<Expression> others) {
        if (others.isEmpty()) {
            return null;
        }
        if (others.size() == 1) {
            return others.get(0);
        }
        OrExpression orExpression = new OrExpression(others.get(0), others.get(1));
        for (int i = 2; i < others.size(); i ++) {
            orExpression = new OrExpression(orExpression, others.get(i));
        }
        return new Parenthesis(orExpression);
    }
}
