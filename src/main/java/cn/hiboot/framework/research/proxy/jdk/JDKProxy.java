package cn.hiboot.framework.research.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * JDK动态代理类
 *
 *
 */
public class JDKProxy implements InvocationHandler {

    private Object targetObject;//需要代理的目标对象

    public Object newProxy(Object targetObject) {//将目标对象传入进行代理
        this.targetObject = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(), this);//返回代理对象
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        checkPermission();//一般我们进行逻辑处理的函数比如这个地方是模拟检查权限
        Object ret  = method.invoke(targetObject, args);       //调用invoke方法，ret存储该方法的返回值
        return ret;
    }

    private void checkPermission() {//模拟检查权限的例子
        System.out.println("检查权限  checkPermission()!");
    }
}
