package pl.com.kocielapki.cattery.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.com.kocielapki.cattery.auth.ShaPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new ShaPasswordEncoder(256);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select login, password_hash, is_enabled from users where login = ?;")
                .authoritiesByUsernameQuery("select (select login from users where id = user_id) as username, authority from user_authorities where user_id = (select id from users where login = ?)");
    }


    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .anyRequest().permitAll()

                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/kukuruku")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout().logoutUrl("/perform/logout").logoutSuccessUrl("/kukuruku?logout").permitAll().deleteCookies("JSESSIONID").invalidateHttpSession(false)
                .and()
                .cors()
                .and()
                .csrf().disable();
    }

}
