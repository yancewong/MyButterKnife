package com.yw.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangyanpeng
 */

@Target(ElementType.ANNOTATION_TYPE) //元注解，作用在注解之上
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    //事件的三个重要成员
//    1.setOnClickListener 方法名
    String listenerSetter();
//    2.监听的对象 View.OnXXXListener
    Class<?> listenerType();
//    3回调方法 onXXXClick
    String callBackListener();


}
