package cn.hiboot.framework.research.proxy.cglib;

public class HelloConcrete {
    public String sayHello(String str) {
        System.out.println("execute");
        return "HelloConcrete: " + str;
    }
}
