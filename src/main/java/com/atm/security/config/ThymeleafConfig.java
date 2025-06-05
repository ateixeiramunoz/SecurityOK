package com.atm.security.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

public class ThymeleafConfig {

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }


    //activar layout dialect
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }



}
