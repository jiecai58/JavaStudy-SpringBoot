package com.study.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Aspect
public class MyAspect{

    @Nullable
    private Boolean conditionPassing;

    public String condition;

    public static final ExpressionParser expressionParser = new SpelExpressionParser();
    public static final EvaluationContext evalContext = new StandardEvaluationContext();


    @Around("@annotation(dst)")
    public Object cache(ProceedingJoinPoint joinPoint,MyCache dst)throws Throwable{
        //获取方法签名
        MethodSignature  methodSignature = (MethodSignature) joinPoint.getSignature();
        //得到el表达式
        String el = dst.value();
        //解析el表达式，将#id等替换为参数值
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i <parameterNames.length ; i++) {
            evalContext.setVariable(parameterNames[i],args[i]);
        }
        Expression expression = expressionParser.parseExpression(dst.value());
        String key = expression.getValue(evalContext).toString();
        System.out.println(key);

        this.condition =  dst.condition();
        isConditionPassing();

        //根据key从缓存中拿数据，这里省略

        //如果缓存中没有则执行目标方法
        Object o =   joinPoint.proceed();
        //将结果放入缓存,这里省略

        return o;

    }

    protected boolean isConditionPassing() {
        if (this.conditionPassing == null) {
            if (StringUtils.hasText(this.condition)) {
                Expression expression = expressionParser.parseExpression(this.condition);
                this.conditionPassing = condition(expression, evalContext);
            } else {
                this.conditionPassing = true;
            }
        }
        return this.conditionPassing;
    }

    public boolean condition(Expression expression, EvaluationContext evalContext) {
        return (Boolean.TRUE.equals(expression.getValue(evalContext, Boolean.class)));
    }


}