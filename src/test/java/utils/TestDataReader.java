package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TestDataReader {
    public static List<User> getRegisterUsers() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(
                    new File("src/test/resources/testdata/registerData.json"),
                    User[].class
            ));
        } catch (Exception e) {
            throw new RuntimeException("registerData.json okunamadı", e);
        }
    }
}
