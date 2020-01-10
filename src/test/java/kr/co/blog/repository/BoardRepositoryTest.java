package kr.co.blog.repository;

import kr.co.blog.entitiy.Board;
import kr.co.blog.entitiy.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    private final String name = "SONG";
    private final String password = "1234";
    private final String email = "songsogu@gmail.com";
    private final String phone = "010-2224-6965";

    private final String name2 = "BYUNG";
    private final String password2 = "4311";
    private final String email2 = "byungwoon@gmail.com";
    private final String phone2 = "010-1344-6965";

    private final String board_name = "게시글 1";
    private final String board_cotent = "게시글 내영1";

    private final String board_name2 = "게시글 2";
    private final String board_cotent2 = "게시글 내영2";
    @Before
    public void setUp(){

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user =  userRepository.save(User.builder().name(name).password(passwordEncoder.encode(password)).phone(phone).email(email).roles(Collections.singletonList("ROLE_USER")).build());
        User user2 =  userRepository.save(User.builder().name(name2).password(passwordEncoder.encode(password2)).phone(phone2).email(email2).roles(Collections.singletonList("ROLE_USER")).build());

        boardRepository.save(Board.builder().name(board_name).content(board_cotent).user(user).build());
        boardRepository.save(Board.builder().name(board_name2).content(board_cotent2).user(user2).build());

//        System.out.println(user.toString());
    }


    @Test
    public void user_생성_test(){
        User ret = userRepository.findByEmail(email).get();
        assertThat(ret.getName(),is(name));
    }

    @Test
    public void board_조회_테스트(){
        User ret_user = userRepository.findByEmail(email).get();
        List<Board> ret = boardRepository.findAllByUser(ret_user);
//        ret.stream().map(b -> b.toString()).forEach(System.out::println);
    }

    @Test
    public void board_전체_조회(){
        List<Board> ret = boardRepository.findAll();
        ret.stream().map(b->b.toString()).forEach(System.out::println);
        assertThat(ret.get(0).getUser().getName(),is(name));
        assertThat(ret.get(1).getUser().getName(),is(name2));
    }


}

