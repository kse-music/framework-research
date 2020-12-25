package cn.hiboot.framework.research.spring.processor;

import org.aspectj.lang.annotation.Around;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor,EnvironmentAware {

    private Environment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("aopConfig");
        try {
            Method[] declaredMethods = null;
            if(beanDefinition instanceof AbstractBeanDefinition){
                AbstractBeanDefinition abstractBeanDefinition = (AbstractBeanDefinition) beanDefinition;
                Class<?> beanClass = null;
                if(beanDefinition instanceof ScannedGenericBeanDefinition){
                    beanClass = abstractBeanDefinition.resolveBeanClass(null);
                }else if(beanDefinition instanceof AnnotatedGenericBeanDefinition){
                    beanClass = abstractBeanDefinition.getBeanClass();
                }
                if(beanClass != null){
                    declaredMethods = beanClass.getDeclaredMethods();
                }
            }
            if(declaredMethods == null){
                return;
            }
            for (Method declaredMethod : declaredMethods) {
                Around around = declaredMethod.getAnnotation(Around.class);
                InvocationHandler invocationHandler = Proxy.getInvocationHandler(around);
                Field declaredField = invocationHandler.getClass().getDeclaredField("memberValues");
                declaredField.setAccessible(true);
                Map memberValues = (Map) declaredField.get(invocationHandler);
                memberValues.put("value", environment.resolvePlaceholders(around.value()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("BeanFactoryPostProcessor : postProcessBeanFactory invoke");
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.environment = environment;
    }
}