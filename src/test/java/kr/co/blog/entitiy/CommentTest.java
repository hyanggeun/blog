//package kr.co.blog.entitiy;
//
//import kr.co.blog.repository.UserRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//
//@SpringBootTest
//@TestPropertySource(properties = "spring.profiles.active=dev")
//@RunWith(SpringRunner.class)
//public class CommentTest {
//
//
//    @MockBean
//    private UserRepository userRepository;
//
//
//    @MockBean
//    private Comment comment;
//
//    @Before
//    public void setUp(){
//
////        given(userRepository.save(any())).willReturn(User.builder().email("songsogu@gmail.com").name("SONG").password("1234").phone("010-2224-6965").build());
//        given(userRepository.findByEmail(any())).willReturn(Optional.of(User.builder().email("songsogu@gmail.com").name("SONG").password("1234").phone("010-2224-6965").build()));
//    }
//
//    @Test
//    public void test(){
//        User test_user = userRepository.findByEmail("s").get();
//        assertThat(test_user.getName(),is("SONG"));
//    }
//}