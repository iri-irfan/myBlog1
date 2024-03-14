package com.myblog.blog1.congif;

import com.myblog.blog1.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {




    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
   public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/posts/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/posts/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();


    }

//    @Override
//    protected UserDetailsService userDetailsService() {
//        UserDetails user1 = User.builder()
//                .username("IRFAN")
//                .password(passwordEncoder().encode("iri"))
//                .roles()
//                .build();
//        UserDetails user2 = User.builder()
//                .username("Irshad")
//                .password(passwordEncoder().encode("irshu"))
//                .roles().build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
