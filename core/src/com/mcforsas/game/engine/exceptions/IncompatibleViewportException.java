package com.mcforsas.game.engine.exceptions;

/**
 * Created by mcforsas on 19.4.22
 * Thrown when viewports are incompatible. Ie: tried to use method which only can be used with other viewport
 */
public class IncompatibleViewportException extends Exception{
    public IncompatibleViewportException(){
        super();
    }

    public IncompatibleViewportException(String message){
        super(message);
    }
}
