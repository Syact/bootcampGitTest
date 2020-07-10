package com.exmaple.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configurable
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected UserDetailsService userDetailsService() { // informacija par lietotaju
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // statiski veidoti lietotaji
        manager.createUser(User.withDefaultPasswordEncoder().username("kate").password("123").roles("ADMIN").build());
        manager.createUser(User.withDefaultPasswordEncoder().username("janis").password("321").roles("USER").build());

        return manager;
    }

    @Bean // izpilda pirms config darbibam
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // kurai lapai lauj pieklut attiecigajam lietotajam
        http.authorizeRequests()
                .antMatchers("/customer/register").permitAll()
                .antMatchers("/product/showAllProducts").permitAll()
                .antMatchers("/product/**").hasRole("ADMIN")
                .antMatchers("/customer/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();


        http.csrf().disable();
        http.headers().frameOptions().disable();

    }
}
