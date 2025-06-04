package com.atm.security.config;

import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

public class ThymeleafConfig {

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

}
