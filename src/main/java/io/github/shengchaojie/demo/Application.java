package io.github.shengchaojie.demo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author shengchaojie
 * @date 2020-02-09
 **/
@SpringBootApplication
@EnableDubbo
@EnableWebMvc
@PropertySource({"classpath:mock.properties"})
public class Application {


    public static void main(String[] args) {

        SpringApplication.run(Application.class,args);
    }

}
