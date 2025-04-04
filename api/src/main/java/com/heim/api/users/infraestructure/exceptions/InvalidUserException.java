package com.heim.api.users.infraestructure.exceptions;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(){
        super();
    }

    public InvalidUserException(String  message){
        super(message);
    }

    public InvalidUserException(String message, Throwable cause){
        super(message, cause);
    }

    public InvalidUserException(Throwable cause){
        super(cause);
    }
}
