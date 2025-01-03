package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public ConfigurableServletWebServerFactory webserverFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/index.html"));
        return factory;
    }
}
