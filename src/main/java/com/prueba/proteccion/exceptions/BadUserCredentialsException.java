package com.prueba.proteccion.exceptions;

public class BadUserCredentialsException extends RuntimeException{

    public BadUserCredentialsException(String message){
        super(message);
    }

}
