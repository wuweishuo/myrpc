package com.wws.myrpc.client.instance.constance;

import com.wws.myrpc.client.instance.callback.CallbackContextMap;
import com.wws.myrpc.util.IdGenerator;
import io.netty.util.AttributeKey;

/**
 * AttributeKeyConst
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2020-12-26
 */
public abstract class AttributeKeyConst {

    /**
     * 请求回调上下文
     */
    public static final AttributeKey<CallbackContextMap> CALLBACK_CONTEXT_MAP_ATTRIBUTE_KEY = AttributeKey.valueOf("callback.context");

    /**
     * flow生成器
     */
    public static final AttributeKey<IdGenerator> ID_GENERATOR_ATTRIBUTE_KEY = AttributeKey.valueOf("idGenerator");

}
