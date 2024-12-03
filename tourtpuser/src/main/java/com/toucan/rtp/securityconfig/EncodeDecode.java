package com.toucan.rtp.securityconfig;

import java.util.Base64;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class EncodeDecode<T> implements RedisSerializer<T> {
	private final RedisSerializer<T> serializer;

    public EncodeDecode(RedisSerializer<T> serializer) {
        this.serializer = serializer;
    }



    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        byte[] serializedData = serializer.serialize(t);
        byte[] encodedData = Base64.getEncoder().encode(serializedData);
        System.out.println("Serialized data: " + new String(encodedData));
        return encodedData;
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        byte[] decodedData = Base64.getDecoder().decode(bytes);
        System.out.println("Deserialized data: " + new String(decodedData));
        return serializer.deserialize(decodedData);
    }

}
