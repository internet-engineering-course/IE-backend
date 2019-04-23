package ir.ac.ut.joboonja.utilities;


import ir.ac.ut.joboonja.exceptions.SerializeException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;


public class Deserializer {
    private static ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <T> T deserialize(String json , Class<T> type) throws SerializeException {
        T t;
        try {
            t = mapper.readValue(json, type);
            return t;
        } catch (IOException e) {
            throw new SerializeException();
        }
    }

    public static <T> List<T> deserializeList(String json , Class<T> type) throws SerializeException {
        List<T> objects;
        try {
            objects = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, type));
            return objects;
        } catch (IOException e) {
            throw new SerializeException();
        }
    }
}
