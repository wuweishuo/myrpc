package com.wws.myrpc.spi;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ExtenderLoader
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-17
 */
public class ExtensionLoader<T> {

    private static final Map<Class<?>, ExtensionLoader<?>> LOADERS = new ConcurrentHashMap<>();

    private static final String BASE_PATH = "META-INF/myrpc";

    private final Class<T> clazz;

    private final String defaultName;

    private volatile Map<String, String> classMap;

    private final Map<String, T> instanceMap = new ConcurrentHashMap<>();

    private ExtensionLoader(Class<T> clazz, String name) {
        this.clazz = clazz;
        this.defaultName = name;
    }

    protected T load(ClassLoader classLoader) {
        return load(defaultName, classLoader);
    }

    protected T load(String name, ClassLoader classLoader) {
        loadAllExtensionClass(classLoader);
        return getExtension(name);
    }

    private T getExtension(String name) {
        T instance = instanceMap.get(name);
        if (instance == null) {
            synchronized (instanceMap) {
                instance = (T) instanceMap.get(name);
                if (instance == null) {
                    try {
                        instance = initInstance(name);
                    } catch (Exception e) {
                        throw new IllegalStateException("extension class(" + name + ") could not be instantiated:" + e.getMessage(), e);
                    }
                    instanceMap.put(name, instance);
                }
            }
        }
        return instance;
    }

    private <T> T initInstance(String name) throws IllegalAccessException, InstantiationException {
        String className = classMap.get(name);
        if (className == null) {
            throw new IllegalArgumentException("extension class(" + name + ") don't exist!");
        }
        try {
            Class<?> clazz = Class.forName(className);
            return (T) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("clazz (" + className + ") don't exist!");
        }
    }

    private void loadAllExtensionClass(ClassLoader classLoader) {
        if (classMap == null) {
            synchronized (this) {
                if (classMap == null) {
                    Map<String, String> map = new HashMap<>();
                    loadDirectory(classLoader, map);
                    classMap = map;
                }
            }
        }
    }

    private void loadDirectory(ClassLoader classLoader, Map<String, String> classMap) {
        String path = BASE_PATH + "/" + clazz.getName();
        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                loadResource(url, classMap);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadResource(URL url, Map<String, String> classMap) {
        try {
            InputStream inputStream = url.openStream();
            Properties properties = new Properties();
            properties.load(inputStream);
            for (Object k : properties.keySet()) {
                String name = (String) k;
                String clazz = (String) properties.get(k);
                classMap.put(name, clazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static <T> ExtensionLoader<T> getExtensionLoader(Class<T> clazz) {
        SPI annotation = clazz.getAnnotation(SPI.class);
        if (annotation == null || !clazz.isInterface()) {
            throw new IllegalArgumentException("clazz (" + clazz + ") is not spi interface!");
        }
        String name = annotation.value();
        if ("".equals(name)) {
            throw new IllegalArgumentException("clazz (" + clazz + ") hasn't default name!");
        }
        ExtensionLoader<T> extensionLoader = (ExtensionLoader<T>) LOADERS.get(clazz);
        if (extensionLoader != null) {
            return extensionLoader;
        }
        LOADERS.putIfAbsent(clazz, new ExtensionLoader<>(clazz, name));
        return (ExtensionLoader<T>) LOADERS.get(clazz);
    }

}
