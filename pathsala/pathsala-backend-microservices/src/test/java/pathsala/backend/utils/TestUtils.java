package pathsala.backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.io.InputStreamReader;

public class TestUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static <T> T read(String file, Class<T> clazz) {
        return MAPPER.readValue(TestUtils.class.getClassLoader().getResourceAsStream(file), clazz);
    }

    @SneakyThrows
    public static String readString(String file) {
        try (InputStream input = TestUtils.class.getClassLoader().getResourceAsStream(file)) {
            return FileCopyUtils.copyToString(new InputStreamReader(input));
        }
    }
}
