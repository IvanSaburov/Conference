package org.saburov.services.conference.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String admin = "ADMIN";
        String presenter = "PRESENTER";
        String listener = "LISTENER";
        http
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/presentations").hasAnyRole(presenter, admin)
                .antMatchers("/schedule").hasAnyRole(presenter, admin, listener)
                .antMatchers("/users").hasAnyRole(admin)
                .antMatchers("/addUser").permitAll()
                .antMatchers("/apischedule").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/schedule")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .and()
                .exceptionHandling().accessDeniedPage("/schedule")
                .and()
                .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(basicAuthenticationProvider()).eraseCredentials(false);
    }


    @Bean
    public BasicAuthenticationProvider basicAuthenticationProvider() {
        return new BasicAuthenticationProvider();
    }

}
