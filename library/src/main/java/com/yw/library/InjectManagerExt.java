package com.yw.library;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wangyanpeng
 */
public class InjectManagerExt {
    public static void inject(Activity activity){
        injectLayout(activity);
        injectViews(activity);
        injectEvent(activity);
    }

    private static void injectLayout(Activity activity){
        Class<? extends Activity> clz = activity.getClass();
        ContentView annotation = clz.getAnnotation(ContentView.class);
        if(null != annotation){
            int layoutId = annotation.value();
            try {
                Method setContentView = clz.getMethod("setContentView", int.class);
                setContentView.invoke(activity,layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectViews(Activity activity){
//        val clazz = activity.javaClass
//        val fields = clazz.declaredFields
//        try {
//            for (field in fields) {
//                val annotation = field.getAnnotation(BindView::class.java)
//                if (null != annotation) {
//                    //此field是需要绑定view的控件
//                    val method = clazz.getMethod("findViewById", Int::class.java)
//                    val viewId = annotation.value
//                    val v = method.invoke(activity, viewId)
//                    field.isAccessible = true
//                    field.set(activity,v)
//                }
//            }
//        }catch (e: Exception){
//            e.printStackTrace()
//        }
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try{
            for (Field field : fields) {
                BindView annotation = field.getAnnotation(BindView.class);
                if(null != annotation){
                    Method method = clazz.getMethod("findViewById", int.class);
                    int viewId = annotation.value();
                    View v = (View) method.invoke(activity,viewId);
                    field.setAccessible(true);
                    field.set(activity,v);

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void injectEvent(Activity activity){
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if(null != annotationType){
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    //事件三个重要成员
                    String callBackListener = eventBase.callBackListener();
                    String listenerSetter = eventBase.listenerSetter();
                    Class<?> listenerType = eventBase.listenerType();
                    try{
                        Method valueMethod = annotationType.getDeclaredMethod("value");
                        int[] viewIds = (int[]) valueMethod.invoke(annotation);
//                      拦截方法，我们自定义的方法
                        ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                        handler.addMethod(callBackListener,method);
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);
                        for(int viewId : viewIds){
                            //获取当前的view
                            View view = activity.findViewById(viewId);
                            Method  setter = view.getClass().getMethod(listenerSetter, listenerType);
                            setter.invoke(view,listener);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
