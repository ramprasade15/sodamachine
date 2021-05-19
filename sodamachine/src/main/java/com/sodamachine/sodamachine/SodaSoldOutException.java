package com.sodamachine.sodamachine;

public class SodaSoldOutException extends Exception{
    public SodaSoldOutException(){
        super();
    }
    public SodaSoldOutException(final String message){
        super(message);
    }
}
