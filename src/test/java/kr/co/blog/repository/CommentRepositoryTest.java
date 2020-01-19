package kr.co.blog.repository;

import kr.co.blog.dto.CommentListResponse;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
@DataJpaTest
@RunWith(SpringRunner.class)
public class CommentRepositoryTest {


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

    private User user;
    private Board b;

    @Before
    public void setUp(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user = new User();
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhone(phone);
        user.setEmail(email);
        user.setRoles(Collections.singletonList("ROLE_USER"));
        userRepository.save(user);
        b = new Board();
        b.setName(board_name);
        b.setContent(board_cotent);
        b.setUser(user);
        boardRepository.save(b);
    }


    @Test
    public void 좋아요순댓글_Test(){
        for(long i=0;i<100;i++){
            Comment c = new Comment();
            c.setComment("Hello"+i);
            c.AddBoard(b);
            c.AddUser(user);
            c.setLike(i);
            c.setParentId(0L);
            c.setDeleted(false);
            commentRepository.save(c);
        }
        List<CommentListResponse> cr = commentRepository.findAllByCommentLike(b.getId());
        assertThat(cr.get(0).getComment(),is("Hello99"));
    }
    @Test
    public void 댓글없음_좋아요순댓글_Test(){
        List<CommentListResponse> cr = commentRepository.findAllByCommentLike(b.getId());
        assertThat(cr.size(), is(0));
    }
    @Test
    public void 최신글순댓글_Test() throws InterruptedException {
        List<Comment> arr = new ArrayList<>();
        for(long i=0;i<100;i++){
            Comment c = new Comment();
            c.setComment("Hello"+i);
            c.AddBoard(b);
            c.AddUser(user);
            c.setLike(i);
            c.setParentId(0L);
            c.setDeleted(false);
            arr.add(c);
            Thread.sleep(10);
            commentRepository.save(c);
        }
        Board b = boardRepository.findAll().get(0);
        List<CommentListResponse> cr = commentRepository.findAllByCreateDate(b.getId());
        assertThat(cr.get(0).getCreateDate(),is(arr.get(99).getCreateDate()));
    }


    @Test
    public void 최신글_생성후_삭제_조회_test(){
        List<Comment> arr = new ArrayList<>();
        for(long i=0;i<100;i++){
            Comment c = new Comment();
            c.setComment("Hello"+i);
            c.AddBoard(b);
            c.AddUser(user);
            c.setLike(i);
            c.setParentId(0L);
            c.setDeleted(false);
            arr.add(c);
            commentRepository.save(c);
        }
        Board b = boardRepository.findAll().get(0);
        List<CommentListResponse> cr = commentRepository.findAllByCreateDate(b.getId());
        assertThat(cr.get(0).getComment(),is(arr.get(99).getComment()));
        arr.get(99).setDeleted(true);
        List<CommentListResponse> cr_deleted = commentRepository.findAllByCreateDate(b.getId());
        assertThat(cr_deleted.get(0).getComment(),is(arr.get(98).getComment()));
    }

    @Test
    public void 답글리스트_Test(){
        Comment c = new Comment();
        c.setComment("Hello");
        c.AddBoard(b);
        c.AddUser(user);
        c.setLike(1L);
        c.setParentId(0L);
        c.setDeleted(false);
        commentRepository.save(c);
        List<Comment> arr = new ArrayList<>();
        for(long i=0;i<100;i++){
            Comment c1 = new Comment();
            c1.setComment("Reply"+i);
            c1.AddBoard(b);
            c1.AddUser(user);
            c1.setLike(i);
            c1.setParentId(c.getId());
            c1.setDeleted(false);
            arr.add(c1);
            commentRepository.save(c1);
        }
        assertThat(commentRepository.findAllReply(c.getId()).get(0).getComment(),is("Reply0"));
    }


}