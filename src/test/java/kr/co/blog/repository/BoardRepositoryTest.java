package kr.co.blog.repository;

import kr.co.blog.entitiy.Board;
import kr.co.blog.entitiy.Comment;
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
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;


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

        User user = new User();
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhone(phone);
        user.setEmail(email);
        user.setRoles(Collections.singletonList("ROLE_USER"));

        userRepository.save(user);

        Board b = new Board();
        b.setName(board_name);
        b.setContent(board_cotent);
        b.setUser(user);

        boardRepository.save(b);

        Comment c = new Comment();
        c.setComment("Hello");
        c.AddBoard(b);
        c.AddUser(user);
        commentRepository.save(c);

        Comment c2 = new Comment();
        c2.setComment("Hello2");
        c2.AddBoard(b);
        c2.AddUser(user);
        commentRepository.save(c2);


    }

    @Test
    public void comment_Test(){
        List<Comment> commentList = commentRepository.findAll();
        commentList.forEach(i-> System.out.println(i.getBoard().getName()));
    }

    @Test
    public void 댓글_board_조회_test(){
        Board board = boardRepository.findAll().get(0);
        List<Comment> comments = board.getComments();
        comments.forEach(c-> System.out.println(c.getComment()+ c.getUser().getName()));
    }

    @Test
    public void user_생성_test(){
        User ret = userRepository.findByEmail(email).get();
        assertThat(ret.getName(),is(name));
    }

    @Test
    public void board_조회_테스트(){
        User ret_user = userRepository.findByEmail(email).get();
        List<Board> ret = boardRepository.findAllByUserAndAndDeletedIsFalse(ret_user);
//        ret.stream().map(b -> b.toString()).forEach(System.out::println);
    }

    @Test
    public void board_전체_조회(){
        List<Board> ret = boardRepository.findAll();
        ret.stream().map(b->b.toString()).forEach(System.out::println);
        assertThat(ret.get(0).getUser().getName(),is(name));
    }


}

