package kr.co.blog.controller;

import kr.co.blog.entitiy.Comment;
import kr.co.blog.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("board/{id}/comment")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }


    @PostMapping("")
    public ResponseEntity<?> createComment(@PathVariable Long id, @RequestBody Comment comment){
        return ResponseEntity.ok(commentService.createComment(id,comment.getComment()));
    }


    @PostMapping("/{commentId}/reply")
    public ResponseEntity<?> createReplys(@PathVariable Long id, @PathVariable Long commentId, @RequestBody Comment comment){
        return ResponseEntity.ok(commentService.createReComment(id, commentId, comment));
    }


    @GetMapping("/like")
    public ResponseEntity<?> listByLike(@PathVariable Long id){
        return ResponseEntity.ok(commentService.listByLike(id));
    }

    @GetMapping("/createdate")
    public ResponseEntity<?> listByCreateDate(@PathVariable Long id){
        return ResponseEntity.ok(commentService.listByCreateDate(id));
    }

    @GetMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestParam Long commentId){
        return ResponseEntity.ok(commentService.deleteComment(commentId));
    }

    @GetMapping("{commentId}/reply")
    public ResponseEntity<?> listReplys(@PathVariable Long commentId){
        return ResponseEntity.ok(commentService.listReply(commentId));
    }

}
