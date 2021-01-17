package com.wws.myrpc.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * ReflectionUtil
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public class ReflectionUtil {

    /**
     * 获取所有实例public方法
     *
     * @param clazz
     * @return
     */
    public static Method[] getAllInstanceMethods(Class<?> clazz) {
        if (clazz == null || !Modifier.isInterface(clazz.getModifiers())) {
            return new Method[0];
        }
        List<Method> list = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (!Modifier.isStatic(method.getModifiers())) {
                list.add(method);
            }
        }
        return list.toArray(new Method[0]);
    }

}
