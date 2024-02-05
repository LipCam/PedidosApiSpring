package com.lipcam.PedidosApiSpring.exceptions;

public class RegistroInexistenteException extends RuntimeException{

    public RegistroInexistenteException(){
        super("Registro inexistente");
    }
}
