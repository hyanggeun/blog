package kr.co.blog.controller;


import kr.co.blog.dto.BoardListResponse;
import kr.co.blog.entitiy.Board;
import kr.co.blog.service.BoardService;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@Log
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }
    @GetMapping(value ="")
    public ResponseEntity<?> boardList(@AuthenticationPrincipal UserDetails userDetails){

        List<BoardListResponse> b = boardService.boardList(userDetails);
        return ResponseEntity.ok(b);
    }

    @PostMapping("")
    public ResponseEntity<?> boardCreate(@RequestBody Board board){

        Board b = boardService.create(board);
        return ResponseEntity.ok().body(b);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> boardUpdate(@PathVariable Long id,@RequestBody Board board){
        Board  b = boardService.update(id, board);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> boardDelete(@PathVariable Long id){
        boardService.delete(id);
        return ResponseEntity.ok("deleted");
    }
}
