package com.wws.myrpc.client.instance.constance;

import com.wws.myrpc.client.instance.callback.CallbackContextMap;
import com.wws.myrpc.util.IdGenerator;
import io.netty.util.AttributeKey;

public abstract class AttributeKeyConst {

    public static final AttributeKey<CallbackContextMap> CALLBACK_CONTEXT_MAP_ATTRIBUTE_KEY = AttributeKey.valueOf("callback.context");

    public static final AttributeKey<IdGenerator> ID_GENERATOR_ATTRIBUTE_KEY = AttributeKey.valueOf("idGenerator");

}
