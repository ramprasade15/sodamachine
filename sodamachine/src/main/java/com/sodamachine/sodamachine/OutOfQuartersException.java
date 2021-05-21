package com.sodamachine.sodamachine;

public class OutOfQuartersException extends Exception{
    public  OutOfQuartersException(){
        super();
    }
    public OutOfQuartersException(String message) {
        super(message);
    }
}
