package cn.hiboot.framework.research.spring.basic;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/10/9 10:36
 */
public class BeanLife implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware ,ApplicationContextAware, InitializingBean,
        ApplicationListener<ApplicationEvent>, DisposableBean,SmartInitializingSingleton {

    private ExampleBean exampleBean;

    public BeanLife() {
        System.out.println("1:执行构造器");
    }

    @Autowired
    public void setExampleBean(ExampleBean exampleBean) {
        this.exampleBean = exampleBean;
        System.out.println("2:设置属性");
    }

    @PostConstruct
    private void init(){
        System.out.println("4.2:执行后置处理器(Bean初始化前) CommonAnnotationBeanPostProcessor(处理注解@Resource @PostConstruct @PreDestroy)");
    }

    @PreDestroy
    private void preDestroy(){
        System.out.println("9:执行PreDestroy");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("3.2:设置ClassLoader");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("3.3:设置BeanFactory");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("3.1:设置BeanName");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("4.1:执行后置处理器(Bean初始化前) ApplicationContextAwareProcessor(处理bean实现EnvironmentAware EmbeddedValueResolverAware ResourceLoaderAware ApplicationEventPublisherAware MessageSourceAware ApplicationContextAware通知接口)");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("5:bean所有属性设置完毕通知");
    }

    private void beanInitMethod(){
        System.out.println("6:处理@Bean注解中指定的初始化方法");
        applicationListenerDetector();
    }

    private void applicationListenerDetector(){
        System.out.println("7:执行后置处理器(Bean初始化后) ApplicationListenerDetector(处理bean实现ApplicationListener接口)");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("收到事件：" + event);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("10:执行destroy");
    }

    private void beanDestroyMethod(){
        System.out.println("11:处理@Bean注解中指定的销毁方法");
    }

    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("8:执行afterSingletonsInstantiated,此时所有常规单例bean全部创建,也就是说可以正常使用该bean对象了");
    }
}
