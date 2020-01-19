package kr.co.blog.service;

import kr.co.blog.dto.CommentListResponse;
import kr.co.blog.entitiy.Comment;
import kr.co.blog.entitiy.User;
import kr.co.blog.exception.BoardExistFailedException;
import kr.co.blog.exception.BoardNotExistException;
import kr.co.blog.exception.NoCommentException;
import kr.co.blog.repository.BoardRepository;
import kr.co.blog.repository.CommentRepository;
import kr.co.blog.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    //댓글 생성
    public Comment createComment(Long BoardId, String comment){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(user.getName());
        log.info(user.getRoles().toString());
        User ret_user = userRepository.findById(user.getId()).get();
        Comment newComment = new Comment();
        newComment.setComment(comment);
        newComment.setDeleted(false);
        newComment.setParentId(0L);
        newComment.setLike(0L);
        newComment.AddUser(ret_user);
        newComment.AddBoard(boardRepository.findById(BoardId).orElseThrow(()->new BoardExistFailedException("생성된 board가 없습니다.")));
        return commentRepository.save(newComment);
    }
    //대댓글 생
    public Comment createReComment(Long BoardId, Long commentId, Comment comment ){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User ret_user = userRepository.findById(user.getId()).get();
        Comment newRecomment = new Comment();
        newRecomment.setComment(comment.getComment());
        newRecomment.setDeleted(false);
        newRecomment.setParentId(commentId);
        newRecomment.setLike(0L);
        newRecomment.AddUser(ret_user);
        newRecomment.AddBoard(boardRepository.findById(BoardId).orElseThrow(()->new BoardNotExistException("생성된 board가 없습니다.")));
        return commentRepository.save(newRecomment);
    }
    //댓글 조회
    public List<CommentListResponse> listByLike(Long BoardId){
        List<CommentListResponse> commentList = commentRepository.findAllByCommentLike(BoardId);
        if(commentList.size()==0){
            return  new ArrayList<>();
        }
        return commentList;
    }
    //댓글 조회
    public List<CommentListResponse> listByCreateDate(Long BoardId){
        List<CommentListResponse> commentList = commentRepository.findAllByCreateDate(BoardId);
        if(commentList.size()==0){
            return  new ArrayList<>();
        }
        return commentList;
    }

    //1. 댓글 삭제
    public String deleteComment(Long commentId){
       Optional<Comment> c =  commentRepository.findById(commentId);
       Comment creturn = c.orElseThrow(()-> new NoCommentException("삭제할 댓글이 없습니다."));
       creturn.setDeleted(true);
       return "Deleted OK";
    }
    //2. 대댓글 보기
    public List<CommentListResponse> listReply(Long commentId){
        List<CommentListResponse> replyList = commentRepository.findAllReply(commentId);
        if(replyList==null){
            return new ArrayList<>();
        }
        return replyList;
    }

    //TODO 좋아요 +1

    //TODO 대댓글 삭제

}
