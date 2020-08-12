package cn.hiboot.framework.research.spring;

import cn.hiboot.framework.research.spring.annotation.TestAnnotation;
import cn.hiboot.framework.research.spring.annotation.ZxfResource;
import cn.hiboot.framework.research.spring.basic.*;
import cn.hiboot.framework.research.spring.hierarchy.ChildContext;
import cn.hiboot.framework.research.spring.hierarchy.ParentContext;
import cn.hiboot.framework.research.spring.processor.ComplexStart;
import cn.hiboot.framework.research.spring.research.SimpleStart;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpringDemo {

    /**
     * Spring 源码探底
     */
    @Test
    public void research(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SimpleStart.class);
        SimpleStart bean = context.getBean(SimpleStart.class);
        println(bean.test());
    }

    @Test
    public void complex(){
        ApplicationContext context = new AnnotationConfigApplicationContext(ComplexStart.class);
        ComplexStart bean = context.getBean(ComplexStart.class);
        println(bean.test());
    }


    /**
     * 包含  replace-method 示例
     */
    @Test
    public void runFromXml(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");//从spring-context.xml加载
        ReplaceMethodDemo replaceMethodDemo = context.getBean(ReplaceMethodDemo.class);
        println(replaceMethodDemo.getName());
    }

    /**
     * 包含以下示例：
     *      循环依赖注入、FactoryBean、@Bean、ObjectFactory、ObjectProvider、@ImportResource、@Lookup、@Primary、aop
     *
     * Spring事务传播属性定义：是当一个事务方法碰到另一个事务方法时的处理行为
     * <table border="1">
     * <tr>
     *     <td>传播性</td>
     *     <td>值</td>
     *     <td>描述</td>
     * </tr>
     * <tr><td>PROPAGATION_REQUIRED</td><td>0</td><td>支持当前事务，如果没有就新建事务</td></tr>
     * <tr><td>PROPAGATION_SUPPORTS</td><td>1</td><td>支持当前事务，如果没有就不以事务的方式运行</td></tr>
     * <tr><td>PROPAGATION_MANDATORY</td><td>2</td><td>支持当前事务，如果当前没事务就抛异常</td></tr>
     * <tr><td>PROPAGATION_REQUIRES_NEW</td><td>3</td><td>无论当前是否有事务，都会新起一个事务</td></tr>
     * <tr><td>PROPAGATION_NOT_SUPPORTED</td><td>4</td><td>不支持事务，如果当前存在事务，就将此事务挂起不以事务方式运行</td></tr>
     * <tr><td>PROPAGATION_NEVER</td><td>5</td><td>不支持事务，如果有事务就抛异常</td></tr>
     * <tr><td>PROPAGATION_NESTED</td><td>6</td><td>如果当前存在事务，在当前事务中再新起一个事务</td></tr>
     * </table>
     *
     */
    @Test
    public void testBasic(){

        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);

        TestBean tb = context.getBean(TestBean.class);
        println(tb.getName());

        DemoBean demoBean = context.getBean(DemoBean.class);
        demoBean.test();

        LookupDemo look = context.getBean(LookupDemo.class);
        look.look();

    }

    @Test
    public void testHierarchy(){
        ApplicationContext parent = new AnnotationConfigApplicationContext(ParentContext.class);
        ConfigurableApplicationContext child = new AnnotationConfigApplicationContext(ChildContext.class);
        Object obj = child.getBean("parentContext");
        println(obj);

        child.setParent(parent);
        Object obj2 = child.getBean("parentContext");
        println(obj2);

    }

    private void println(Object obj){
        System.out.println(obj);
    }


    @Test
    public void parseAnnotation() {
        AnnotatedTypeMetadata metadata = AnnotationMetadata.introspect(BeanDefine.class);
        println(((StandardAnnotationMetadata) metadata).hasAnnotation(ZxfResource.class.getName()));//false
        println(metadata.isAnnotated(ZxfResource.class.getName()));//true
        Map<String, Object> attributes = metadata.getAnnotationAttributes(TestAnnotation.class.getName());
        MultiValueMap<String, Object> multiValueMap = metadata.getAllAnnotationAttributes(TestAnnotation.class.getName());
        List<AnnotationAttributes> annotationAttributes = annotationAttributesFromMultiValueMap(multiValueMap);
        assert attributes != null;
        println(attributes.get("value"));
    }

    List<AnnotationAttributes> annotationAttributesFromMultiValueMap(MultiValueMap<String, Object> multiValueMap) {
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Map.Entry<String, List<Object>> entry : multiValueMap.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                Map<String, Object> map;
                if (i < maps.size()) {
                    map = maps.get(i);
                }else {
                    map = new HashMap<>();
                    maps.add(map);
                }
                map.put(entry.getKey(), entry.getValue().get(i));
            }
        }
        List<AnnotationAttributes> annotationAttributes = new ArrayList<>(maps.size());
        for (Map<String, Object> map : maps) {
            annotationAttributes.add(AnnotationAttributes.fromMap(map));
        }
        return annotationAttributes;
    }

}