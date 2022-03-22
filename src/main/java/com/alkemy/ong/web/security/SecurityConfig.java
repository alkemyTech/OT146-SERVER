package com.alkemy.ong.web.security;

import com.alkemy.ong.web.security.jwt.CustomAuthenticationFilter;
import com.alkemy.ong.web.security.jwt.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);


        http.authorizeRequests().antMatchers(
                        "/testimonials/{page}",
                        "/news/{page}",
                        "/members", "/members/{page}",
                        "/comments", "/comments/{id}", "/comments/post/{newsId}").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/organization/public/{id}", "/activities/{id}").hasRole("USER")
                .antMatchers(HttpMethod.PUT,"/members/{id}").hasRole("USER")

                .antMatchers(
                "/users", "/users/{id}",
                        "/testimonials", "/testimonials/{id}",
                        "/slides/**",
                        "/news","/news/{id}",
                        "/members/all",
                        "/image",
                        "/sendemail",
                        "/contacts/{id}",
                        "/comments/details",
                        "/ong/categories/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/organization/public/{id}", "/activities/{id}", "/members/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/members/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/contacts", "/organization/public/{id}", "/activities/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/contacts").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll()
                .and().httpBasic();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

}
