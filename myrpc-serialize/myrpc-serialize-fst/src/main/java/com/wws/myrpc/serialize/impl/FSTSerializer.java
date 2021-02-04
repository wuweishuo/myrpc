package com.wws.myrpc.serialize.impl;

import com.wws.myrpc.serialize.SerializerProperties;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * FSTSerializer
 *
 * @author wuweishuo
 * @version 1.0.0
 * @date 2021-01-28
 */
public class FSTSerializer extends AbstractSerializer {

    private static final FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    public FSTSerializer(SerializerProperties properties) {
        super(properties);
    }

    @Override
    public void serialize(OutputStream out, Object obj) throws IOException {
        FSTObjectOutput fstObjectOutput = conf.getObjectOutput(out);
        fstObjectOutput.writeObject(obj);
        fstObjectOutput.flush();
    }

    @Override
    public <T> T deserialize(InputStream in, Class<T> clazz) throws IOException {
        FSTObjectInput objectInput = conf.getObjectInput(in);
        try {
            return (T) objectInput.readObject(clazz);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

}
