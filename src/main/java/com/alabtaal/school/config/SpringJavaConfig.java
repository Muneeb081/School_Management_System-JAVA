package com.alabtaal.school.config;

import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor
public class SpringJavaConfig {
    private final SpringFXMLLoader springFXMLLoader;

    @Bean
    @Lazy
    public StageManager stageManager(final Stage stage)
    {
        return new StageManager(springFXMLLoader,stage);
    }
}
