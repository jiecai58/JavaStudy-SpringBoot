package com.study.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 *  cache aspect
 * @author Jie.cai58@gmail.com
 * @date 2019/9/19 14:23
 */
@Slf4j
@Component
@Aspect
public class InfoAspect {

    @Around("@annotation(dst)")
    public Object doInfo(ProceedingJoinPoint pjp, Info dst) throws Throwable {
        Object ret;
        System.out.println("进入切面");
        try {
            ret = pjp.proceed();
        }catch (Throwable e){

            throw e;
        }
        return ret;
    }


}
