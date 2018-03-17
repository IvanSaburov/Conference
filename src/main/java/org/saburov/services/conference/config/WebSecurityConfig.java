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
        http
                .authorizeRequests().antMatchers("/css/**").permitAll() // Enable css when logged out
                .and()
                .authorizeRequests()
//                .antMatchers("/", "add", "delete/{id}", "save", "students", "/addStudentCourse/**").hasAnyRole("ROLE_TEST")
                .antMatchers("/students").hasAnyRole("TEST")
                .antMatchers("/addStudentCourse/**").hasAnyRole("TEST1")
                .antMatchers("/registration").permitAll()
                .antMatchers("/presentations").permitAll()
                .antMatchers("/addUser").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/presentations")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .and()
                .exceptionHandling().accessDeniedPage("/login")
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
