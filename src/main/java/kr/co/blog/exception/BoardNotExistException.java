package kr.co.blog.exception;

public class BoardNotExistException extends RuntimeException {
    public BoardNotExistException(String msg){
        super(msg);
    }
}
