package cn.hiboot.framework.research.dom4j;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE})
@ZxfResource(name = "z")
public @interface TestAnnotation {
    String value() default "";
}
