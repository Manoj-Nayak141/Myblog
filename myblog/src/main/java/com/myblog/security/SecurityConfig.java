package com.myblog.security;

import jdk.internal.dynalink.support.NameCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder getEncodedPassword(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers("/api/auth/signup").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

    }
        @Override
        @Bean
        protected UserDetailsService userDetailsService() {
            UserDetails manoj = User.builder().username("manoj").password(getEncodedPassword().encode("Mantu@141")).roles("USER").build();
            UserDetails admin =  User.builder().username("admin").password(getEncodedPassword().encode("Admin")).roles("ADMIN").build();
            return new InMemoryUserDetailsManager(manoj,admin);
    }
}