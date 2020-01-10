package kr.co.blog.service;


import kr.co.blog.dto.BoardListResponse;
import kr.co.blog.entitiy.Board;
import kr.co.blog.entitiy.User;
import kr.co.blog.exception.BoardExistFailedException;
import kr.co.blog.exception.BoardNoTextException;
import kr.co.blog.exception.BoardNotExistException;
import kr.co.blog.repository.BoardRepository;
import lombok.extern.java.Log;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public List<BoardListResponse> boardList(UserDetails userDetails) {
        User s = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("msg:     "+s);
        List<BoardListResponse> ret = boardRepository.findAllDesc();
        if (ret.isEmpty()) {
            throw new BoardExistFailedException("게시물이 없습니다.");
        }
        return ret;
    }

    public Board update(Long id, Board board) {
        Board b = boardRepository.findById(id).orElseThrow(()-> new BoardNoTextException("수정할 글이 없습니다."));
        b.setName(board.getName());
        b.setContent(board.getContent());
        b.setUpdate_time(LocalDateTime.now());
        return boardRepository.save(b);
    }

    public void delete(Long id) {
        Board b = boardRepository.findById(id).orElseThrow(()-> new BoardNotExistException("삭제할 글이 없습니다."));
        boardRepository.delete(b);

    }

    public Board create(Board board) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        board.setUser(user);
        Board b = boardRepository.save(board);
        return b;
    }
}
