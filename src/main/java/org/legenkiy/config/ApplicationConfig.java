package org.legenkiy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Scanner;

@Configuration
@ComponentScan("org.legenkiy")
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    public static final Scanner scanner = new Scanner(System.in);

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
