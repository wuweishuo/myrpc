package com.wws.myrpc.spi;

import java.lang.annotation.*;

/**
 * SPI
 * 标示这是一个spi接口
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SPI {

    /**
     * 默认实现
     *
     * @return
     */
    String value() default "";

}
