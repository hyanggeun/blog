package kr.co.blog.exception;

public class PasswordSigninFailedException extends RuntimeException {
    private  String msg = "패스워드가 다릅니다";
    public PasswordSigninFailedException(){
        super();
    }
    public PasswordSigninFailedException(String msg){
        super(msg);
    }
}
