package com.examportal.Configuration;


import com.examportal.services.implementations.UserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorisedHandler;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }


////    only for testing purpose
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new  BCryptPasswordEncoder();
            }

//    this method is used to get user from database for authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsServiceImplementation).passwordEncoder(passwordEncoder());
    }

//    this method decides which url should be accessible to user or admin
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //disable cross site request forjury
                .cors().disable() // disable cors
                .authorizeRequests() //below requests are authorised.
                .antMatchers("/generate-token", "/user/", "/current-user","/category/","/quiz/","/quiz/{qId}","/question/quiz/all/{qId}","/question/","/question/{queId}/","/quiz/category/{cid}","/quiz/category/active/{cid}","/quiz/active","/question/quiz/{qId}","/question/eval-quiz", "/user/test").permitAll() //these request does not need authentication
                .antMatchers("Http.OPTIONS").permitAll()  //these request does not need authentication
                .anyRequest().authenticated()//except above requests any other request need to be authenticated
                .and()
                .exceptionHandling().authenticationEntryPoint((AuthenticationEntryPoint) unauthorisedHandler)// this handles exception if occurred
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        for filtering the requests
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
