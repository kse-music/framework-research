package cn.hiboot.framework.research.spring.basic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * DefaultAopProxyFactory:代理创建行为,即便是选择了cglib代理，但如果目标实现了接口，最终还是会选择jdk做代理
 */
@Aspect
@EnableAspectJAutoProxy
@Component
public class AopConfig {

    private final Logger logger = LoggerFactory.getLogger(AopConfig.class);

    /**
     * 拦截ComplexStart下所有方法并忽略有IgnoreCheck注解的方法
     * @param p
     * @return
     * @throws Throwable
     */
    @Around("execution(* cn.hiboot.framework.research.spring.processor.ComplexStart.*(..)) && !@annotation(cn.hiboot.framework.research.spring.basic.IgnoreCheck)")
    public Object around(ProceedingJoinPoint p) throws Throwable {
        logger.info("proceed 前");
        Object obj = p.proceed();
        logger.info("proceed 后");
        return obj;
    }

}
