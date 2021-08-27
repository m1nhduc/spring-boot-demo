package dmd.test.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private BCryptPasswordEncoder encoder;
    private CustomAuthenticationSuccessHandler authenSuccessHandler;
    private CustomUserDetailService userDetailService;

    @Autowired
    public SecurityConfig(BCryptPasswordEncoder encoder, CustomAuthenticationSuccessHandler authenSuccessHandler, CustomUserDetailService userDetailService) {
        this.encoder = encoder;
        this.authenSuccessHandler = authenSuccessHandler;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/admin-login").permitAll()
            .antMatchers("/admin-signup").permitAll()
            .antMatchers("/admin-dashboard/**").hasAuthority("ADMIN")
            .anyRequest()
            .authenticated()
        .and()
            .formLogin()
            .loginPage("/admin-login")
            .permitAll()
            .failureUrl("/admin-login?error=true")
            .usernameParameter("email")
            .passwordParameter("password")
            .successHandler(authenSuccessHandler)
        .and()
            .logout()
            .permitAll()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessHandler(new CustomLogoutSuccessHandler())
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/admin-login")
        .and()
            .exceptionHandling();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
}
