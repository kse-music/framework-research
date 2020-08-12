package cn.hiboot.framework.research.spring.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;

/**
 * @author DingHao
 * @since 2018/12/10 10:43
 */
//@Configuration
@ComponentScan("cn.hiboot.frame.spring.basic")
@ImportResource("classpath:spring-config.xml")
public class MainConfiguration {

    @Bean
    @Primary
    public ExampleBean exampleBean(){
        return new ExampleBean();
    }

    @Bean(name="testBean",initMethod="start",destroyMethod="cleanUp")
    public TestBean testBean() {
        return new TestBean();
    }

}
