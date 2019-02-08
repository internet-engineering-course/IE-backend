package utilities;

import exceptions.DeserializeException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class Deserializer {
    private static ObjectMapper mapper = new ObjectMapper();
    public static <T> T deserialize(String json , Class<T> type) throws DeserializeException {
        T t = null;
        try {
            t = mapper.readValue(json, type);
            return t;
        } catch (IOException e) {
            throw new DeserializeException();
        }
    }
}
