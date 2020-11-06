package com.example.chat.exception;

public class CreateRoomFailException extends RuntimeException{
    private String code;
    private String msg;

    public CreateRoomFailException(){

    }

    public CreateRoomFailException(String message) {
        super(message);
        this.msg = message;
    }

    public CreateRoomFailException(String code, String msg){
        super();
        this.code = code;
        this.msg = msg;
    }

    public String getCode(){
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
