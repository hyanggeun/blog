package kr.co.blog.exception;

public class BoardExistFailedException extends RuntimeException {
    public BoardExistFailedException(String msg){
        super(msg);
    }
}
