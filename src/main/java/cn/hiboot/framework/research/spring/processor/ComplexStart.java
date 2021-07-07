package cn.hiboot.framework.research.spring.processor;

import cn.hiboot.framework.research.dozer.DozerDemo;
import cn.hiboot.framework.research.spring.basic.BeanLife;
import cn.hiboot.framework.research.spring.basic.ExampleBean;
import cn.hiboot.framework.research.spring.basic.ExampleBean2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import javax.annotation.PostConstruct;

@ComponentScan(value = {"cn.hiboot.frame.spring.processor","cn.hiboot.frame.dozer"},
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {MyBeanPostProcessor.class, DozerDemo.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM, value = MyTypeFilter.class)})
public class ComplexStart implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(ComplexStart.class);


    @Value("${user.home2}")
    private String home;

//    @Autowired
    private ExampleBean exampleBean;

    @Autowired
    private ExampleBean2 exampleBean2;

    public void setHome(String home) {
        this.home = home;
    }


//    public ComplexStart(){
//        logger.info("Constructor invoke");
//    }

    /**
     * 10、推荐使用构造函数注入
     *
     * 这一条实践来自Phil Webb（Spring Boot的项目负责人, @phillip_webb）。
     *
     * 保持业务逻辑免受Spring Boot代码侵入的一种方法是使用构造函数注入。
     * 不仅是因为@Autowired注解在构造函数上是可选的，而且还可以在没有Spring的情况下轻松实例化bean。
     */
    public ComplexStart(ExampleBean exampleBean) {
        this.exampleBean = exampleBean;
    }

    @PostConstruct
    private void init(){
        logger.info("@PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("afterPropertiesSet");
    }

    public String test(){
        logger.info("home = "+ home);
        innerInvoke();
        return "test method return value";
    }

    /**
     * 内部调用不会走切面
     */
    public void innerInvoke(){
        logger.info("{} \n{}",exampleBean,exampleBean2);
    }

//    @Component//不会为其生成CGLIB代理Class ，相当于@Configuration(proxyBeanMethods = false)
    @Configuration
    static class InnerService{

        private final Logger logger = LoggerFactory.getLogger(InnerService.class);

        @Bean
        public ExampleBean exampleBean(){
            logger.info("only one?");
            return new ExampleBean();
        }

        @Bean
        public ExampleBean2 exampleBean2(){
            ExampleBean2 exampleBean2 = new ExampleBean2();
            exampleBean2.setExampleBean(exampleBean());
            return exampleBean2;
        }

        @Bean(initMethod="beanInitMethod",destroyMethod="beanDestroyMethod")
        public BeanLife beanLife(){
            return new BeanLife();
        }

    }
}