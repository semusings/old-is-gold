package io.github.bhuwanupadhyay.ordering.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Json {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Map<String, Object> body = new LinkedHashMap<>();

    public static Json create() {
        return new Json();
    }

    public Json setCustomerId(String customerId) {
        body.put("customerId", customerId);
        return this;
    }

    @SneakyThrows
    String toJsonString() {
        return MAPPER.writeValueAsString(body);
    }


    @SneakyThrows
    byte[] toBytes() {
        return MAPPER.writeValueAsBytes(body);
    }
}
