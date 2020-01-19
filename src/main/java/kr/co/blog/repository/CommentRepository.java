package kr.co.blog.repository;

import kr.co.blog.dto.CommentListResponse;
import kr.co.blog.entitiy.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select new kr.co.blog.dto.CommentListResponse(c.comment, c.user.name, c.like, c.createDate, c.deleted, c.parentId) from Comment c where c.parentId=0  and c.board.id = :boardId and c.deleted = false order by c.like desc")
    List<CommentListResponse> findAllByCommentLike(Long boardId);

    @Query(value = "select new kr.co.blog.dto.CommentListResponse(c.comment, c.user.name, c.like, c.createDate, c.deleted, c.parentId) from Comment c where c.parentId=0  and c.board.id = :boardId  and c.deleted = false order by c.createDate desc")
    List<CommentListResponse> findAllByCreateDate(Long boardId);

    @Query(value = "select new kr.co.blog.dto.CommentListResponse(c.comment, c.user.name, c.like, c.createDate, c.deleted, c.parentId) from Comment c where c.parentId= :commentId and c.deleted = false order by c.createDate")
    List<CommentListResponse> findAllReply(Long commentId);




}
