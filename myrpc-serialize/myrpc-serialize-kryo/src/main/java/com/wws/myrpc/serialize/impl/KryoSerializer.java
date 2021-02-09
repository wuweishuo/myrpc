package com.wws.myrpc.serialize.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;
import com.wws.myrpc.serialize.SerializerProperties;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * KryoSerializer
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-28
 */
public class KryoSerializer extends AbstractSerializer {

    private final Pool<Kryo> kryoPool;

    public KryoSerializer(SerializerProperties properties) {
        super(properties);
        kryoPool = new Pool<Kryo>(true, false, 8) {
            protected Kryo create() {
                Kryo kryo = new Kryo();
                kryo.setRegistrationRequired(false);
                return kryo;
            }
        };
    }

    @Override
    public void serialize(OutputStream out, Object obj) throws IOException {
        Kryo kryo = kryoPool.obtain();
        Output output = new Output(out);
        kryo.writeClassAndObject(output, obj);
        output.flush();
    }

    @Override
    public <T> T deserialize(InputStream in, Class<T> clazz) throws IOException, ClassNotFoundException {
        Kryo kryo = kryoPool.obtain();
        Input input = new Input(in);
        return (T) kryo.readClassAndObject(input);
    }
}
