package pathsala.serverless.uaa.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Map;

public class TestUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static <T> T read(String file, Class<T> clazz) {
        return MAPPER.readValue(TestUtils.class.getClassLoader().getResourceAsStream(file), clazz);
    }

    @SneakyThrows
    public static String readString(String file) {
        try (InputStream input = TestUtils.class.getClassLoader().getResourceAsStream(file);
             StringWriter writer = new StringWriter()) {
            IOUtils.copy(input, writer, Charset.forName("UTF-8"));
            return writer.toString();
        }
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static void setEnv(String key, String value) {
        Map<String, String> env = System.getenv();
        Class<?> cl = env.getClass();
        Field field = cl.getDeclaredField("m");
        field.setAccessible(true);
        Map<String, String> writableEnv = (Map<String, String>) field.get(env);
        writableEnv.put(key, value);
    }

}
