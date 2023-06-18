package com.proj.togedutch;

import com.google.api.client.util.Value;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class ConfigEnvironmentTest {

    @Value("${spring.config.activate.on-profile}")
    String environmentValue;

    @Test
    @DisplayName("Local.yml 부여 확인")
    public void configEnvironmentTest(){
        String local = "local";
        Assertions.assertThat(environmentValue).isEqualTo(local);
    }
}
