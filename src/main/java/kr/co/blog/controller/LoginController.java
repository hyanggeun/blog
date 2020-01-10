package kr.co.blog.controller;

import kr.co.blog.config.JwtTokenProvider;
import kr.co.blog.entitiy.User;
import kr.co.blog.service.LoginService;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@Log
public class LoginController {

    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;
    public LoginController(LoginService loginService, JwtTokenProvider jwtTokenProvider){
        this.loginService = loginService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<?> signin(HttpServletResponse res,  @RequestBody User req){
        User user =  loginService.login(req.getEmail(), req.getPassword());
        String cookieValue = jwtTokenProvider.createToken(user.getEmail(),user.getRoles());
        Cookie myCookie = new Cookie("cookieName",cookieValue);
        myCookie.setMaxAge(1000 * 60 * 60 * 24 * 7 );
        myCookie.setPath("/");
        res.addCookie(myCookie);
        return ResponseEntity.ok("OK");
    }

    @GetMapping(value="/signout")
    public ResponseEntity<?> signout(HttpServletResponse res){
        Cookie c = new Cookie("cookieName",null);
        c.setMaxAge(0);
        c.setPath("/");
        res.addCookie(c);
        return ResponseEntity.ok("logout ok");
    }

    @PostMapping(value="/emailcheck")
    public ResponseEntity<?> emailcheck(@RequestBody User req){
        loginService.emailCheck(req);
        return ResponseEntity.ok("ok");
    }

    @PostMapping(value="/signup")
    public ResponseEntity<?> signup(@RequestBody User req) throws URISyntaxException {
       User user =  loginService.signin(req.getName(), req.getPassword(), req.getEmail(), req.getPhone());
        return ResponseEntity.created(new URI("/signin")).body(user);
    }

}
