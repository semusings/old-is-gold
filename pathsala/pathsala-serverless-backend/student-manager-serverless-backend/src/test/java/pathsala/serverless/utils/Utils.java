package pathsala.serverless.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;

public class Utils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Random RANDOM = new Random(1000);

    @SneakyThrows
    public static <T> T read(String file, Class<T> clazz) {
        return MAPPER.readValue(Utils.class.getClassLoader().getResourceAsStream(file), clazz);
    }

    @SneakyThrows
    public static String readToString(String file) {
        try (InputStream stream = Utils.class.getClassLoader().getResourceAsStream(file)) {
            StringWriter writer = new StringWriter();
            IOUtils.copy(stream, writer, Charset.forName("UTF-8"));
            return writer.toString();
        }
    }

    public static String id() {
        return UUID.randomUUID().toString();
    }

    public static String endpointUri() {
        return "";
    }

}
