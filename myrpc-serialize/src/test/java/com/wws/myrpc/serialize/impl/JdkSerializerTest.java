package com.wws.myrpc.serialize.impl;

import com.wws.myrpc.domain.User;
import com.wws.myrpc.serialize.Serializer;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JdkSerializerTest extends TestCase {

    private Serializer serializer = new JdkSerializer();

    public void testDeserialize() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        serializer.serialize(out, null);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        User obj = serializer.deserialize(in, User.class);
        System.out.println(obj);
    }
}