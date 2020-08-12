package cn.hiboot.framework.research.spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof ComplexStart) {
            ComplexStart singleResearchSpring = (ComplexStart) bean;
            singleResearchSpring.setHome("BeanPostProcessor:postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof ComplexStart) {
            ComplexStart singleResearchSpring = (ComplexStart) bean;
            singleResearchSpring.setHome("BeanPostProcessor:postProcessAfterInitialization");
        }
        return bean;
    }

}