package cn.hiboot.framework.research.proxy.cglib;


import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MyMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("执行前..." + Arrays.toString(args));
        Object object = methodProxy.invokeSuper(obj, args);
        System.out.println("执行后...");
        return object;
    }

}
