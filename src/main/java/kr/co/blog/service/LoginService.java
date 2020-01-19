package kr.co.blog.service;

import kr.co.blog.config.JwtTokenProvider;
import kr.co.blog.entitiy.User;
import kr.co.blog.exception.EmailSigninFailedException;
import kr.co.blog.exception.PasswordSigninFailedException;
import kr.co.blog.exception.UserExistException;
import kr.co.blog.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    public User login(String id, String password) {
        User user = userRepository.findByEmail(id).orElseThrow(()->new EmailSigninFailedException("이메일이 없습니다."));
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new PasswordSigninFailedException("패스워드가 다릅니다.");
        }
        return user;
    }

    public User signin(String name, String password, String email, String phone){
        boolean chk = userRepository.findByEmail(email).isPresent();
        if(chk){
            throw new UserExistException("이미 회원가입이 되었습니다");
        }

        User user = new User();
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhone(phone);
        user.setEmail(email);
        user.setRoles(Collections.singletonList("ROLE_USER"));
        return userRepository.save(user);
    }

    public void emailCheck(User req) {
        boolean chk = userRepository.findByEmail(req.getEmail()).isPresent();
        if(chk){
            throw new UserExistException("이미 회원가입이 되었습니다");
        }
    }
}
