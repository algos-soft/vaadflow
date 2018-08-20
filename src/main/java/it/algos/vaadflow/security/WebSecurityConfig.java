package it.algos.vaadflow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(debug = false)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGOUT_SUCCESS_URL = "/role";
    private static final String LOGIN_PROCESSING_URL = "/login";

//    @Override
//    public void configure(WebSecurity web) throws Exception {
////        web.debug(true);
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("gac").password(passwordEncoder().encode("fulvia"))
                .authorities("admin");

        auth.inMemoryAuthentication().withUser("admin")
                .password("admin")
                .roles("USER", "ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests().antMatchers("/**").hasRole("USER").and().formLogin();

//        http
//                .authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();

//        http
//                .csrf().disable()
//                .authorizeRequests().requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .and().httpBasic()
//        ;

        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated();
        http.authorizeRequests().requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll();
        http.httpBasic();


//        // Not using Spring CSRF here to be able to use plain HTML for the login page
//        http.csrf().disable()
//
////                // Register our CustomRequestCache, that saves unauthorized access attempts, so
////                // the user is redirected after login.
//                .requestCache().requestCache(new CustomRequestCache())
//
//                // Restrict access to our application.
//                .and().authorizeRequests()
//
//                // Allow all flow internal requests.
//                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
//
//                // Allow all requests by logged in users.
//                .anyRequest().hasAnyAuthority(Role.getAllRoles())
//
//                .and().formLogin().loginPage(LOGIN_URL).permitAll()
//
//                // Configure the login page.
////                .and().formLogin().loginPage(LOGIN_URL).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL)
////                .failureUrl(LOGIN_FAILURE_URL)
//
//                // Register the success handler that redirects users to the page they last tried
//                // to access
////                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
//
//                // Configure logout
//                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);

    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new DbUserDetailsManager();
    }

}
