package com.teste.banco.exception;

public class TransacaoNotFoundException extends RuntimeException {

    public TransacaoNotFoundException (String message) {
        super(message);
    }
    
}
