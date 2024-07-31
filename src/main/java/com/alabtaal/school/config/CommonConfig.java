package com.alabtaal.school.config;

import java.util.ResourceBundle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

  @Bean
  public ResourceBundle resourceBundle() {
    return ResourceBundle.getBundle("Bundle");
  }

}
