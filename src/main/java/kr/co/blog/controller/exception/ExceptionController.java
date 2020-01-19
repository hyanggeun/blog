package kr.co.blog.controller.exception;


import kr.co.blog.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class ExceptionController{

    @ExceptionHandler(value = EmailSigninFailedException.class)
    public ResponseEntity EmailSigninFailed(EmailSigninFailedException e){
        Map<String, Object> m  = new HashMap<>();
        m.put("code",HttpStatus.BAD_REQUEST.value());
        m.put("msg",e.getMessage());
        return ResponseEntity.badRequest().body(m);
    }

    @ExceptionHandler(value = PasswordSigninFailedException.class)
    public ResponseEntity<Map<String, Object>> PasswordSigninFailed(PasswordSigninFailedException e){
        Map<String, Object> m  = new HashMap<>();
        m.put("code",HttpStatus.BAD_REQUEST.value());
        m.put("msg",e.getMessage());
        return ResponseEntity.badRequest().body(m);
    }

    @ExceptionHandler(value = BoardExistFailedException.class)
    public ResponseEntity<Map<String,Object>> BoardExistFailed(BoardExistFailedException e){
        Map<String, Object> m = new HashMap<>();
        m.put("code",HttpStatus.BAD_REQUEST.value());
        m.put("msg",e.getMessage());
        return ResponseEntity.badRequest().body(m);
    }


    @ExceptionHandler(value = UserExistException.class)
    public ResponseEntity<Map<String,Object>> UserExistException(UserExistException e){
        Map<String, Object> m = new HashMap<>();
        m.put("code",HttpStatus.BAD_REQUEST.value());
        m.put("msg",e.getMessage());
        return ResponseEntity.badRequest().body(m);
    }

    @ExceptionHandler(value = BoardNotExistException.class)
    public ResponseEntity<Map<String,Object>> BoardNotExistException(BoardNotExistException e){
        Map<String, Object> m = new HashMap<>();
        m.put("code",HttpStatus.BAD_REQUEST.value());
        m.put("msg",e.getMessage());
        return ResponseEntity.badRequest().body(m);
    }
    @ExceptionHandler(value = NoCommentException.class)
    public ResponseEntity<Map<String, Object>> NoCommentException(NoCommentException e){
        Map<String,Object> m = new HashMap<>();
        m.put("code",HttpStatus.BAD_REQUEST.value());
        m.put("msg",e.getMessage());
        return ResponseEntity.badRequest().body(m);
    }
}