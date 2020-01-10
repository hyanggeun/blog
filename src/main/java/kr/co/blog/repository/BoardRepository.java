package kr.co.blog.repository;

import kr.co.blog.dto.BoardListResponse;
import kr.co.blog.entitiy.Board;
import kr.co.blog.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board>  findAllByUser(User user);

    @Query( value = "SELECT new kr.co.blog.dto.BoardListResponse(b.id, b.name, b.content, b.create_time, b.update_time , b.user.name) FROM Board b ORDER BY b.create_time DESC")
    List<BoardListResponse> findAllDesc();
}
