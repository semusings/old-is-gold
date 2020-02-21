package pathsala.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class Utils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static <T> T read(String file, Class<T> clazz) {
        return MAPPER.readValue(Utils.class.getClassLoader().getResourceAsStream(file), clazz);
    }

}
