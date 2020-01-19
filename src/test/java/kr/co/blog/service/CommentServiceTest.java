package kr.co.blog.service;

import kr.co.blog.dto.CommentListResponse;
import kr.co.blog.entitiy.Board;
import kr.co.blog.entitiy.Comment;
import kr.co.blog.entitiy.User;
import kr.co.blog.exception.NoCommentException;
import kr.co.blog.repository.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=local")
public class CommentServiceTest {


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

    User user;
    Board b;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;



    @Before
    public void setUp(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user = new User();
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhone(phone);
        user.setEmail(email);
        user.setRoles(Collections.singletonList("ROLE_USER"));

        b = new Board();
        b.setName(board_name);
        b.setContent(board_cotent);
        b.setUser(user);
    }
    @Test
    public void listByLikeTest(){
        List<CommentListResponse> arr= new ArrayList<>();
        LongStream.rangeClosed(0,100).mapToObj((id)-> new CommentListResponse("comment"+id, "user01",id,LocalDateTime.now(),false,0L)).forEach(arr::add);
        given(commentRepository.findAllByCommentLike(any())).willReturn(arr);
        commentRepository.findAllByCommentLike(1L);
        List<CommentListResponse> res = commentService.listByLike(1L);
        assertThat(res.get(0).getComment(), is("comment0"));
    }
    @Test
    public void listByLikeWithNoContentsTest(){
        given(commentRepository.findAllByCommentLike(any())).willReturn(new ArrayList<>());
        commentRepository.findAllByCommentLike(1L);
        List<CommentListResponse> res = commentService.listByLike(1L);
        assertThat(res.size(),is(0));

    }

    @Test
    public void deleteCommentTest(){

        Comment c = new Comment();
        c.setComment("Hello");
        c.AddBoard(b);
        c.AddUser(user);

        given(commentRepository.findById(1L)).willReturn(Optional.of(c));

        String ret = commentService.deleteComment(1L);
        assertThat(ret,is("Deleted OK"));

    }

    @Test(expected = NoCommentException.class)
    public void deleteCommentErrorTest(){

        Comment c = new Comment();
        c.setComment("Hello");
        c.AddBoard(b);
        c.AddUser(user);


        given(commentRepository.findById(1L)).willReturn(Optional.of(c));

        String ret = commentService.deleteComment(2L);
        assertThat(ret,is("Deleted OK"));
    }

}