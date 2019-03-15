package utilities;


import exceptions.SerializeException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class Serializer {
    private static ObjectMapper mapper = new ObjectMapper();
    public static String serialize(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Serialize Error!";
    }
}
