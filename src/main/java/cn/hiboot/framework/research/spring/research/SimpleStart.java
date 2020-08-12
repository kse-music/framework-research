package cn.hiboot.framework.research.spring.research;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2019/10/9 10:36
 */
@Slf4j
@ComponentScan(basePackageClasses = SimpleStart.class)
public class SimpleStart {

    public SimpleStart() {
        log.info("execute constructor");
    }

    public String test(){
        return "test method return value";
    }
}
