package com.example.zerobaseproject03;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


// Security 설정을 하는 클래스
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers("/",
                    "/member/register",
                    "/member/email-auth") // 메인,회원가입,이메일 인증은 접근 가능
            .permitAll();

        http.formLogin()
            .loginPage("/member/login")
            .failureHandler(null)
            .permitAll();


        super.configure(http);
    }

}
