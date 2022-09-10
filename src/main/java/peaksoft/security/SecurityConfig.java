package peaksoft.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import peaksoft.service.UserDetailServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService service() {
        return new UserDetailServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(service());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/companies").hasAnyAuthority(
                        "ADMIN", "INSTRUCTOR", "STUDENT")
                .antMatchers("/api/**").hasAuthority("ADMIN")
                .antMatchers("/save/**").hasAnyAuthority("ADMIN", "INSTRUCTOR")
                .antMatchers("/delete/**").hasAuthority("ADMIN")
                .antMatchers("/students/delete/**").hasAuthority("INSTRUCTOR")
                .antMatchers("/saveAssign/**").hasAnyAuthority("ADMIN", "INSTRUCTOR")
                .antMatchers("/students/all/**").hasAuthority("INSTRUCTOR")
                .antMatchers("/students/**/delete").hasAuthority("INSTRUCTOR")
                .antMatchers("/lessons/**/delete").hasAuthority("INSTRUCTOR")
                .antMatchers("/lessons/update/**").hasAuthority("INSTRUCTOR")
                .antMatchers("/lessons/**/new").hasAuthority("INSTRUCTOR")
                .antMatchers("/tasks/all/**").hasAnyAuthority("INSTRUCTOR", "STUDENT")
                .antMatchers("/task/new").hasAuthority("INSTRUCTOR")
                .antMatchers("/tasks/update/**").hasAnyAuthority("STUDENT", "INSTRUCTOR")
                .antMatchers("/tasks/**/delete").hasAnyAuthority("INSTRUCTOR")
                .antMatchers("/videos/all/**").hasAnyAuthority("INSTRUCTOR", "STUDENT", "ADMIN")
                .antMatchers("/videos/**/new/**").hasAuthority("INSTRUCTOR")
                .antMatchers("/videos/update/**").hasAuthority("INSTRUCTOR")
                .antMatchers("/videos/**/delete").hasAuthority("INSTRUCTOR")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout().permitAll();
    }
}
