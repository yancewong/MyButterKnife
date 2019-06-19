package com.yw.library

import android.app.Activity
import android.view.View
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 *
 * @author wangyanpeng
 */
object InjectManager {
    fun inject(activity: Activity){
        injectLayout(activity)
        injectViews(activity)
        injectEvent(activity)
    }

    //注入布局
    private fun injectLayout(activity: Activity) {
        val clazz = activity.javaClass
        val contentView = clazz.getAnnotation(ContentView::class.java)
        if(null != contentView){
            val layoutId = contentView.value
            try{
                val method = clazz.getMethod("setContentView",Int::class.java)
                method.invoke(activity, layoutId)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

    }

    //注入控件
    private fun injectViews(activity: Activity) {
        val clazz = activity.javaClass
        val fields = clazz.declaredFields
        try {
            for (field in fields) {
                val annotation = field.getAnnotation(BindView::class.java)
                if (null != annotation) {
                    //此field是需要绑定view的控件
                    val method = clazz.getMethod("findViewById", Int::class.java)
                    val viewId = annotation.value
                    val v = method.invoke(activity, viewId)
                    field.isAccessible = true
                    field.set(activity,v)
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    //注入事件监听器
    private fun injectEvent(activity: Activity) {
//
//        val clazz = activity.javaClass
//        val methods = clazz.declaredMethods
//        for (method: Method in methods) {
//            val annotations = method.annotations
//            for (annotation in annotations) {
//                val annotationType = annotation.annotationType()
//                if (null != annotationType) {
//                    val eventBase = annotationType!!.getAnnotation(EventBase::class.java)
//                    //事件三个重要成员
//                    val callBackListener = eventBase!!.callBackListener()
//                    val listenerSetter = eventBase!!.listenerSetter()
//                    val listenerType = eventBase!!.listenerType()
//                    try {
//                        val valueMethod = annotationType!!.getDeclaredMethod("value")
//                        val viewIds = valueMethod.invoke(annotation) as IntArray
//                        //                      拦截方法，我们自定义的方法
//                        val handler = ListenerInvocationHandler(activity)
//                        handler.addMethod(callBackListener, method)
//                        val proxyInstance =
//                            Proxy.newProxyInstance(listenerType.getClassLoader(), arrayOf(listenerType), handler)
//                        for (viewId in viewIds) {
//                            //获取当前的view
//                            val view = activity.findViewById<View>(viewId)
//                            val setter = view.javaClass.getMethod(listenerSetter, listenerType)
//                            setter.invoke(view, callBackListener)
//                        }
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//
//                }
//            }
//        }


    }
}