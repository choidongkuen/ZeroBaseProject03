package com.example.zerobaseproject03.configuration;


import com.example.zerobaseproject03.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


// Security 설정을 하는 클래스
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final MemberService memberService;


    // Password Encoder 가져오는 클래스
    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // 로그인없이 접근 가능한 부분 설정(메인,회원가입,회원 정보)
        http.authorizeRequests()
            .antMatchers("/",
                    "/member/register",
                    "/member/email-auth",
                    "/member/find/password",
                    "/member/reset/password"
            )
            .permitAll();

        // 관리자 관련 설정
        http.authorizeRequests()
            .antMatchers("/admin/**")
            .hasAnyAuthority("ROLE_ADMIN");

        // 로그인 실패시 처리하는 설정
        http.formLogin()
            .loginPage("/member/login")
            .failureHandler(getFailureHandler())
            .permitAll();


        // 로그아웃 처리하는 설정
        http.logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
            .logoutSuccessUrl("/") // 로그아웃시 이동할 페이지
            .invalidateHttpSession(true); // 세션 초기화


        http.exceptionHandling()
            .accessDeniedPage("/error/denied");

        super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).
            passwordEncoder(getPasswordEncoder());


        super.configure(auth);
    }


}
