package org.example.springbooturl.domain.standard.util.ut;

import lombok.SneakyThrows;
import org.example.springbooturl.global.app.AppConfig;

import java.util.Base64;

public class Ut {
    public static class json {

        @SneakyThrows
        public static String toString(Object obj) {
            return AppConfig.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }

        @SneakyThrows
        public static <T> T toObj(String str, Class<T> cls) {
            return AppConfig.getObjectMapper().readValue(str, cls);
        }
    }

    public static String base64Decode(String str) {
        byte[] decodedBytes = Base64.getDecoder().decode(str);
        return new String(decodedBytes);
    }
}