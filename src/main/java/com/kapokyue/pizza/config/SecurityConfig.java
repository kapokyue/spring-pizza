package com.kapokyue.pizza.config;

import com.kapokyue.pizza.model.User;
import com.kapokyue.pizza.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Configuration @EnableGlobalMethodSecurity( prePostEnabled = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
  @Autowired
  UserRepository userRepo;

  @Override
  protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
    auth.userDetailsService( account -> {
      Optional<User> u = userRepo.findByAccount( account );
      return u.orElseThrow( () ->
          new UsernameNotFoundException( "No this user: " + account ) );
    } );
  }

  @Override
  protected void configure( HttpSecurity http ) throws Exception {
    http.authorizeRequests().anyRequest().permitAll()

        .and()
        .exceptionHandling()
        .defaultAuthenticationEntryPointFor(
            loginUrlAuthenticationEntryPoint(),
            AnyRequestMatcher.INSTANCE )

        .and()
        .httpBasic();
  }

  @Bean
  public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
    LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint( "/login" )
    {
      @Override
      public void commence( HttpServletRequest request,
                            HttpServletResponse response,
                            AuthenticationException authException )
          throws IOException, ServletException
      {
        request.setAttribute( "error", "This page require login" );
        super.commence( request, response, authException );
      }
    };
    entryPoint.setUseForward( true );
    return entryPoint;
  }
}
