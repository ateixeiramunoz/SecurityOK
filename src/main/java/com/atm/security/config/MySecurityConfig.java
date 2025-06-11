package com.atm.security.config;

import com.atm.security.chat.entities.ChatMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

/**
 * The type My security config.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MySecurityConfig {



    /**
     * Cadena de filtros security filter chain.
     * Este método es el encargado de aplicar los filtros de seguridad en orden y por tanto controlar el acceso de los usuarios a los diferentes recursos.
     * También vale para configurar las funcionalidades de login, logout y csrf
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain cadenaDeFiltros(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .authorizeHttpRequests(authorize -> Optional.ofNullable(authorize
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/js/**").permitAll()
                                .requestMatchers("/foro/**").permitAll()
                                .requestMatchers("/canales/**").permitAll()
                                .requestMatchers("/hilo/**").permitAll()
                                .requestMatchers("/enviar/ciencia").permitAll()
                                .requestMatchers("/topic/ciencia").permitAll()
                                .requestMatchers("/mostrarChat").authenticated()
                                .requestMatchers("/gs-guide-websocket").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/logout").permitAll()
                                .requestMatchers("/ficheros").permitAll()
                                .requestMatchers("/ficheros/**").permitAll()
                                .requestMatchers("/prueba").authenticated()
                                .requestMatchers("/paginaUsuarios").authenticated()
                                .anyRequest().authenticated()
                ));

        return http.build();
    }


    /**
     * Password encoder b crypt password encoder.
     *
     * @return the b crypt password encoder
     */
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
