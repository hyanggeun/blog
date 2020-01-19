package kr.co.blog.exception;

public class NoCommentException extends RuntimeException {
    public NoCommentException(String msg){
        super(msg);
    }
}
