package com.mcforsas.game.engine.exceptions;

/**
 * @author MCForsas @since 3/23/2019
 * Thrown when user tries to access asset of unknown type.
 */
public class UnknownAssetTypeException extends Exception{
    public UnknownAssetTypeException(){
        super();
    }

    public UnknownAssetTypeException(String message) {
        super(message);
    }
}
