package com.wws.myrpc.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {

    public static Method[] getAllInstanceMethods(Class clazz){
        if(clazz == null || !Modifier.isInterface(clazz.getModifiers())){
            return null;
        }
        List<Method> list = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if(!Modifier.isStatic(method.getModifiers())){
                list.add(method);
            }
        }
        return list.toArray(new Method[0]);
    }

}
