package ru.tvey.cloudserverapp.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import ru.tvey.cloudserverapp.security.filter.AuthenticationFilter;
import ru.tvey.cloudserverapp.security.filter.ExceprionHandlerFilter;
import ru.tvey.cloudserverapp.security.filter.JWTAuthorizationFilter;
import ru.tvey.cloudserverapp.security.manager.CustomAuthenticationManager;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationManager authenticationManager;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                .requestMatchers("main/login").permitAll()
                .requestMatchers("main/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceprionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(new AuthenticationFilter(authenticationManager))
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();

    }

}
