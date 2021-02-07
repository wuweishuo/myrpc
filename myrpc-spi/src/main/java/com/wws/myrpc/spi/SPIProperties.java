package com.wws.myrpc.spi;

import java.util.*;
import java.util.function.Consumer;

/**
 * SPIProperties
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-02-04
 */
public class SPIProperties implements Iterable<Map.Entry<String, String>> {

    private final String prefix;

    private static final String JOINER = "_";

    private static final String NAME = "name";

    public SPIProperties(String prefix) {
        this.prefix = prefix;
    }

    private final Map<String, String> map = new HashMap<>();

    public String getName(){
        return getProperty(NAME);
    }

    public void setName(String name){
        setProperty(NAME, name);
    }

    public String getProperty(String key){
        return map.get(prefix+ JOINER + key);
    }

    public void setProperty(String key, String value){
        map.put(prefix+ JOINER + key, value);
    }

    /**
     * 将map中的key加上前缀存入
     * @param map
     */
    public void setProperties(Map<String, String> map){
        if(map == null || map.isEmpty()){
            return;
        }
        for (String key : map.keySet()) {
            setProperty(key, map.get(key));
        }
    }

    /**
     * 从map从复制符合前缀的值
     * @param map
     */
    public void clone(Map<String, String> map){
        if(map == null || map.isEmpty()){
            return;
        }
        for (String key : map.keySet()) {
            if(key.startsWith(prefix + JOINER)){
                this.map.put(key, map.get(key));
            }
        }
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return map.entrySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<String, String>> action) {
        map.entrySet().forEach(action);
    }

    @Override
    public Spliterator<Map.Entry<String, String>> spliterator() {
        return map.entrySet().spliterator();
    }
}
