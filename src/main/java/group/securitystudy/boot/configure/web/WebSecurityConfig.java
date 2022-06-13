package group.securitystudy.boot.configure.web;

//Spring Security에서 관련된 설정을 하기 위해 필요한 config 파일
//TO-DO: https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter참고하여 코드 리팩토링

import group.securitystudy.feature.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.AuthenticationManagerBeanDefinitionParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security를 활성화한다는 의미
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { //WebSecurityConfigurerAdapter는 Spring Security의 설정파일로서의 역할을 하기 위해 상속해야 하는 클래스

    private final UserService userService; //유저에 대한 서비스

    @Override
    //인증(인증 과정 및 인증 필요성)을 무시할 경로들을 설정하기 위한 configure 메서드
    public void configure(WebSecurity web){
        //static 하위 폴더 (css, js, img)는 무조건 접근이 가능해야하기 때문에 인증을 무시
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }


    @Override
    // http 관련 인증 설정을 위한 configure 메서드
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/signup", "/user").permitAll() // "/login", "/signup", "/user" 경로에는 누구나 접근 허용
                .antMatchers("/").hasRole("USER") // "/"에는 USER, ADMIN 권한을 가진 사용자만 접근 가능
                .antMatchers("/admin").hasRole("ADMIN") // "/admin"경로에는 ADMIN을 가진 사용자만 접근 가능
                .anyRequest().authenticated() ////anyRequest는 anyMatchers에서 설정하지 않은 나머지 경로를 의미, 나머지 요청들은 권한이 있어야(인증을 받았어야) 접근 가능-USER든 ADMIN이든 상관 없음
                .and()
                .formLogin() //로그인에 관한 설정
                .loginPage("/login") // 로그인 페이지 링크 설정
                .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트할 주소 - 즉 여기서는 로그인 성공 후 "/"경로로 돌아감
                .and()
                .logout() //로그아웃에 대한 설정
                .logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트할 주소
                .invalidateHttpSession(true) // 로그아웃 이후 세션 전체 삭제 여부
        ;
    }

//    @Override
//    //유저가 로그인 할 때, 필요한 정보를 가져오는 메서드
//    public void configure(AuthenticationManagerBuilder auth) throws Exception{
//
//        // userService에서는 UserDetailsService를 implements해서 loadUserByUsername() 구현해야함 (서비스 참고) -
//        //그래야 유저 로그인 시 유저 네임(로그인 아이디)에 맞는 정보를 loadUserByUserName을 통해 가져올 수 있음
//        //!!!!!!!!! 즉 userSerive의 loadUserByUserName은 로그인 요청 시의 유저 네임에 맞는 유저 정보를 가져오는 서비스
//        auth.userDetailsService(userService)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }

}
