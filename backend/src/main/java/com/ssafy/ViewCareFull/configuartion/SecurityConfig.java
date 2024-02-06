package com.ssafy.ViewCareFull.configuartion;

import com.ssafy.ViewCareFull.domain.users.repository.UsersRepository;
import com.ssafy.ViewCareFull.domain.users.security.ViewCareFullAuthenticationProvider;
import com.ssafy.ViewCareFull.domain.users.security.jwt.JwtAuthenticationFilter;
import com.ssafy.ViewCareFull.domain.users.security.jwt.JwtTokenProvider;
import com.ssafy.ViewCareFull.domain.users.security.jwt.ViewCareFullUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final ViewCareFullUserDetailsService userDetailsService;
  private final JwtTokenProvider jwtTokenProvider;
  private final UsersRepository usersRepository;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
//        .cors(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/users/**").permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/docs/**").permitAll()
            .requestMatchers("/swagger-resources/**").permitAll()
            .requestMatchers("/**").permitAll()
            .anyRequest().authenticated())
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, usersRepository),
            UsernamePasswordAuthenticationFilter.class)
    ;
    return http.build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    return new ViewCareFullAuthenticationProvider(userDetailsService, passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
