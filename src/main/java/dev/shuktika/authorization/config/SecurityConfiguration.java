package dev.shuktika.authorization.config;
import dev.shuktika.authorization.filter.AuthenticationFilter;
import dev.shuktika.authorization.filter.AuthorizationFilter;
import dev.shuktika.authorization.filter.ExceptionHandlerFilter;
import dev.shuktika.authorization.service.PensionerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_WHITELIST = {
            "/authorization/api/login",
    };
    private static final String LOGIN_URL = "/authorization/api/login";
    private final PensionerService pensionerService;
    private final PasswordEncoder passwordEncoder;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final PropertyValuesConfiguration propertyValuesConfiguration;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pensionerService)
                .passwordEncoder(passwordEncoder);
    }

    private AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean(), propertyValuesConfiguration);
        authenticationFilter.setFilterProcessesUrl(LOGIN_URL);
        return authenticationFilter;
    }

    private AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(propertyValuesConfiguration);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(authenticationFilter())
                .addFilterBefore(exceptionHandlerFilter, LogoutFilter.class)
                .addFilterBefore(authorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
