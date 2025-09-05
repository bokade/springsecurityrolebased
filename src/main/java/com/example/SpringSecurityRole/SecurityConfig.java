package com.example.SpringSecurityRole;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true) // @Secured enable karne ke liye
public class SecurityConfig {


    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails manager = User.withUsername("manager")
                .password(encoder.encode("manager123"))
                .roles("MANAGER")
                .build();

        UserDetails user = User.withUsername("user")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, manager, user);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // New style for Spring Security 6.1+
    //for simple login based on role
   /* @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> {}); // <- ab is tarah likhna hai

        return http.build();
    } */


@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/error", "/unauthorized").permitAll() // login page sab ke liye open
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")              // custom login page ka URL
                    .loginProcessingUrl("/doLogin")   // form submit hone ka action // login form ke <form action="..."> submit hoga yaha (POST request)
                    .defaultSuccessUrl("/success", true) // login success hone par successful redirect
                    .failureUrl("/login?error=true")  // login fail par redirect wapas login page
                    .permitAll()  // sabko login page access ki permission
            )
            .exceptionHandling(ex -> ex
                    .accessDeniedPage("/unauthorized") // agar role match nahi hota
            );

    return http.build();
}



}
