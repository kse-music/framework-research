package cn.hiboot.framework.research.proxy;

import cn.hiboot.framework.research.proxy.cglib.HelloConcrete;
import cn.hiboot.framework.research.proxy.cglib.MyMethodInterceptor;
import cn.hiboot.framework.research.proxy.jdk.JDKProxy;
import cn.hiboot.framework.research.proxy.jdk.LogInvocationHandler;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * jdk And Cglib Proxy
 *
 * CGLiB所创建的动态代理对象要比JDK所创建的动态代理对象的性能要高出10倍，但CGLiB创建动态代理所花费的时间却比JDK动态代理多8倍。
 *
 * 对于singleton的代理对象或具有实例池的代理，因为无需频繁的创建代理对象，所以适合使用CGLiB代理，繁殖则使用JDK动态代理。
 *
 * 由于CGLIB动态代理采用动态创建子类的方式生成代理对象，所以不能对目标类中 的final方法进行代理
 *
 * @author DingHao
 * @since 2019/4/15 17:09
 */
public class ProxyDemo {

    public static void main(String[] args) {
        jdkProxy();
        cglibProxy();
    }

    public static void jdkProxy(){
        //-Dsun.misc.ProxyGenerator.saveGeneratedFiles=true
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        UserManager hello = (UserManager) Proxy.newProxyInstance(
                ProxyDemo.class.getClassLoader(), // 1. 类加载器
                new Class<?>[] {UserManager.class}, // 2. 代理需要实现的接口，可以有多个
                new LogInvocationHandler(new UserManagerImpl()));// 3. 方法调用的实际处理者
        hello.addUser("1","I love you!");

        JDKProxy jdkProxy = new JDKProxy();
        UserManager userManagerJDK = (UserManager) jdkProxy.newProxy(new UserManagerImpl());
        userManagerJDK.addUser("tom", "root");

    }

    public static void cglibProxy(){
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "F:\\tmp\\class");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloConcrete.class);
        enhancer.setCallback(new MyMethodInterceptor());
        HelloConcrete hello = (HelloConcrete)enhancer.create();
        System.out.println(hello.sayHello("I love you!"));

//        UserManager userManager = (UserManager) new CGLibProxy().createProxyObject(new UserManagerImpl());
//        userManager.addUser("tom", "root");
    }

}
