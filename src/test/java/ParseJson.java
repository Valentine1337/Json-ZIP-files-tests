import domain.Man;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ParseJson {

    @Test
    void jsonHardTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Man valentin = objectMapper.readValue(Paths.get("src/test/resources/files/sample.json").toFile(),
                Man.class);
        assertThat(valentin.gender).isEqualTo("male");
        assertThat(valentin.firstName).isEqualTo("Valentin");
        assertThat(valentin.address.city).isEqualTo("San Diego");
        assertThat(valentin.phoneNumbers).isNotNull();
    }
}
