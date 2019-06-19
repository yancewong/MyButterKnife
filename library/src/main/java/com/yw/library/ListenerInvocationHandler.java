package com.yw.library;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author wangyanpeng
 * 需要拦截
 */
public class ListenerInvocationHandler implements InvocationHandler {
    private Object target;
    private HashMap<String,Method> methodHashMap = new HashMap<>();

    public ListenerInvocationHandler(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(null != target) {
            String name = method.getName();//假如是onClick
            //重新赋值，并且拦截了onclick方法
            method = methodHashMap.get(name);//如果找到了，执行该自定义的click方法
            if(null != method){

                return method.invoke(target,args);
            }
        }
        return null;
    }

    /**
     *
     * @param methodName 本应该执行的方法 onclick 拦截
     * @param method 执行自定义的方法
     */
    public void addMethod(String methodName,Method method){
        methodHashMap.put(methodName,method);
    }



}
