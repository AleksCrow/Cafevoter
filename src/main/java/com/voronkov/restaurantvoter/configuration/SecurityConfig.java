package com.voronkov.restaurantvoter.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //для данных из бд
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public SecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                    .authorizeRequests()
                    .antMatchers("/**/admin/**").hasRole("ADMIN")
                    .antMatchers("/users/profile/register").anonymous()
                    .anyRequest().authenticated()
                .and().csrf().disable();

        http.formLogin().permitAll().defaultSuccessUrl("/restaurants")
                    .permitAll()
                .and()
                    .logout().logoutSuccessUrl("/login");

    }

    //для связи юзеров из бд, пароли оставил незашифрованными
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService)
                            .passwordEncoder(NoOpPasswordEncoder.getInstance());

//                .userDetailsService(userService)    //чтобы менеджер входил в бд и искал данные
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
//                .usersByUsernameQuery("select email, password, enabled from users where email=?")
//                //позволяет получить юзеров с их ролями
//                .authoritiesByUsernameQuery("select u.email, ur.role from users u inner join user_roles ur on u.id=ur.user_id where u.email=?");
    }
}
