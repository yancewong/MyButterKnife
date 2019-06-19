package com.yw.library;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangyanpeng
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerType = View.OnLongClickListener.class,listenerSetter = "setOnLongClickListener", callBackListener = "onLongClick")
public @interface OnLongClick {
    int[] value();
}
