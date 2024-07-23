package com.vincent.stanleyhotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class StanleyHotelApplication {

    public static void main(String[] args) {

        SpringApplication.run(StanleyHotelApplication.class, args);
        System.out.println("(ㅅ˘˘)(^人^)(-人-)(T人T)(-∧-)(￣人￣)(-∧-；)(人>U<)(-人-〃)(人-ω-)");
        System.out.println("May the bug never appear");
    }

}
