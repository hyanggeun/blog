package kr.co.blog.exception;

import org.springframework.http.HttpStatus;

public class EmailSigninFailedException extends RuntimeException {
    public EmailSigninFailedException(String msg){
        super(msg);
    }
}
