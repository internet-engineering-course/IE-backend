package utilities;


import exceptions.DeserializeException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class Deserializer {
    private static ObjectMapper mapper = new ObjectMapper();
    public static <T> T deserialize(String json , Class<T> type) throws DeserializeException {
        T t;
        try {
            t = mapper.readValue(json, type);
            return t;
        } catch (IOException e) {
            throw new DeserializeException();
        }
    }

    public static <T> List<T> deserializeList(String json , Class<T> type) throws DeserializeException {
        List<T> objects;
        try {
            objects = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, type));
            return objects;
        } catch (IOException e) {
            throw new DeserializeException();
        }
    }
}
