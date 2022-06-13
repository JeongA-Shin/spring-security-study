package group.securitystudy.webapi.controller;

import group.securitystudy.feature.service.UserService;
import group.securitystudy.webapi.form.UserForm;
import group.securitystudy.webapi.mapper.UserFormMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService service;
    private final UserFormMapper mapper;

    @PostMapping("/user")
    public String signUp(UserForm.Input.Add in){
        service.save(mapper.toUser(in)); //회원 정보를 저장한 후
        return "redirect:/login"; //로그인 페이지로 가게 해// 줌
    }

    @GetMapping(value="/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        // 스프링 시큐리티에서 기본으로 제공해주는 SecurityContextLogoutHandler()의 logout()을 사용해서 로그아웃 처리
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

}
