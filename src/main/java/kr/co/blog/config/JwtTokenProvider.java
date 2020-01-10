package kr.co.blog.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.blog.service.CustomUserDetailService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;


@Component
public class JwtTokenProvider {

    private String secretKey = "1234";

    private Long tokenValidMs = 1000L* 60 * 60;

    private final CustomUserDetailService userDetailsService;

    public JwtTokenProvider(CustomUserDetailService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String userId, List<String> roles){
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles",roles);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserId(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req){
       Cookie[] cookies = req.getCookies();
       if(cookies==null){
           return null;
       }
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("cookieName")){
                return cookie.getValue();
            }
        }
        return null;
    }

    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }

//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//        User user = (User) userDetailsService.loadUserByUsername(token.getName());
//        if(user ==null){
//            throw new EmailSigninFailedException("email is wrong");
//        }
//        if(!matchesPassword(user.getPassword(), token)){
//            throw new BadCredentialsException("password wrong");
//        }
//        List<GrantedAuthority> authorities =  (List<GrantedAuthority>)user.getAuthorities();
//        return new UsernamePasswordAuthenticationToken(user, "", authorities);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return false;
//    }
//
//    boolean matchesPassword(String password, Authentication token){
//        return passwordEncoder.encode(password).equals(token.getCredentials());
//    }
}
