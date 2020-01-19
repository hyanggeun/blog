package kr.co.blog;

//import kr.co.blog.property.FileUploadProperties;

import kr.co.blog.property.FileUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileUploadProperties.class)
public class Application  {
//    private final String name = "SONG";
//    private final String password = "1234";
//    private final String email = "songsogu@gmail.com";
//    private final String phone = "010-2224-6965";
//
//    private final String name2 = "BYUNG";
//    private final String password2 = "4311";
//    private final String email2 = "byungwoon@gmail.com";
//    private final String phone2 = "010-1344-6965";
//
//    private final String board_name = "게시글 1";
//    private final String board_cotent = "게시글 내영1";
//
//    private final String board_name2 = "게시글 2";
//    private final String board_cotent2 = "게시글 내영2";
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


//    @Bean
//    public CommandLineRunner runner(BoardRepository boardRepository, UserRepository userRepository){
//    return args ->{
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        User user =  userRepository.save(User.builder().name(name).password(passwordEncoder.encode(password)).phone(phone).email(email).roles(Collections.singletonList("ROLE_USER")).build());
//        User user2 =  userRepository.save(User.builder().name(name2).password(passwordEncoder.encode(password2)).phone(phone2).email(email2).roles(Collections.singletonList("ROLE_USER")).build());
//        boardRepository.save(Board.builder().name(board_name).content(board_cotent).user(user).build());
//        boardRepository.save(Board.builder().name(board_name2).content(board_cotent2).user(user2).build());
//    };
//    }
}
