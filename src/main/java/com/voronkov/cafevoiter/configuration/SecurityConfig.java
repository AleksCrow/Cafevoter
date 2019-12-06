package com.voronkov.cafevoiter.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //для данных из бд
    private final DataSource dataSource;

    @Autowired
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/users/**").access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/cafes").authenticated()
                .and()
                    .csrf().disable()
                    .formLogin().defaultSuccessUrl("/cafes").permitAll()
                .and()
                    .logout().permitAll();

    }

    //для связи юзеров из бд
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)    //чтобы менеджер входил в бд и искал роли
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select email, password, enabled from users where email=?")
                //позволяет получить юзеров с их ролями
                .authoritiesByUsernameQuery("select u.email, ur.role from users u inner join user_roles ur on u.id=ur.user_id where u.email=?");
    }
}
