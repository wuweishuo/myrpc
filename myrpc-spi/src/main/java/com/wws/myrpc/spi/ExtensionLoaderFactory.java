package com.wws.myrpc.spi;

/**
 * ExtenderLoaderFactory
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-17
 */
public class ExtensionLoaderFactory {

    public static <T> T load(Class<T> clazz) {
        return ExtensionLoader.getExtensionLoader(clazz).load(getClassLoader());
    }

    public static <T> T load(Class<T> clazz, String name) {
        return load(clazz, name, getClassLoader());
    }

    public static <T> T load(Class<T> clazz, String name, ClassLoader classLoader) {
        return ExtensionLoader.getExtensionLoader(clazz).load(name, classLoader);
    }

    public static <T> T load(Class<T> clazz, String name, SPIProperties properties) {
        return ExtensionLoader.getExtensionLoader(clazz).load(name, getClassLoader(), properties);
    }

    private static ClassLoader getClassLoader() {
        return ExtensionLoaderFactory.class.getClassLoader();
    }

}
