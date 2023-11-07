package kr.movements.vertical.config;

import kr.movements.vertical.common.filter.JwtExceptionFilter;
import kr.movements.vertical.common.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Optional;

/**
 * @file name : SecurityConfiguration.java
 * @project : spring-boot-init
 * @date : Feb 20, 2023
 * @author : ckr
 * @history:
 * @program comment : spring security configuration
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration implements AuditorAware<String> {

    private final JwtFilter jwtFilter;

    private final JwtExceptionFilter jwtExceptionFilter;

    private static final String[] AUTH_LIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger/**",
            "/guest/**",
            "/auth/login"
    };

    /*
    @Bean
    public RoleHierarchyImpl roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("1010 > 1020 > 1030"); // 권한 별 계층 관계 객체 생성
        return roleHierarchy;
    }

    @Bean
    public AccessDecisionVoter<?> roleVoter(){
        return new RoleHierarchyVoter(roleHierarchy()); // 권한 계층 심사 객체에 적용
    }

     */


    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/app/**").hasAuthority("USER_ROLE")
                .antMatchers("/common/**").hasAuthority("USER_ROLE")
                .antMatchers("/web/**").hasAuthority("USER_ROLE")
                .antMatchers(AUTH_LIST).permitAll(); // 해당 url 들은 모두 허용

        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // jwt 필터를 등록
                .addFilterBefore(jwtExceptionFilter, JwtFilter.class);

        return http.build();
    }
    */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource()) // cors 설정 등록
                .and()
                .csrf().disable()
                .anonymous().disable()

                .exceptionHandling()
                // 401 unauthorized exception handler
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpStatus.UNAUTHORIZED.value(),"인증 처리에 실패하였습니다. 잠시후 다시 시도해주세요."))
                // 403 forbidden exception handler
                .accessDeniedHandler((request, response, accessDeniedException) -> response.sendError(HttpStatus.FORBIDDEN.value(),"허용되지 않은 권한입니다. 다시 확인해주세요."))

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()

                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // cors 정책 적용


                .antMatchers("/app/**").hasAuthority("USER_ROLE")
                //.antMatchers("/common/**").hasAuthority("USER_ROLE")
                .antMatchers("/web/**").hasAuthority("USER_ROLE")

                .antMatchers(AUTH_LIST).permitAll() // 해당 url 들은 모두 허용

                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // jwt 필터를 등록

        return http.build();
    }




    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //.antMatchers("/auth/login").permitAll() // 로그인 API 허용
                //.antMatchers("/app/**").hasRole("USER_ROLE")

                .anyRequest().authenticated() // 다른 요청은 인증 필요
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Spring Security 에 의한 filter 를 거치지 않고 전부 허용함. (주로 정적파일을 등록해야함)
        return (web) -> web.ignoring().antMatchers(AUTH_LIST);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        config.setMaxAge(3600L); // 1시간동안 pre-flight request(cors 예비요청)를 저장하고 캐싱
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

     */

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        config.setMaxAge(3600L); // 1시간동안 pre-flight request(cors 예비요청)를 저장하고 캐싱
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication == null || !authentication.isAuthenticated() ? Optional.empty() : Optional.of(authentication.getName());
    }
}
