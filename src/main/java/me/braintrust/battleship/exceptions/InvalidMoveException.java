package me.braintrust.battleship.exceptions;

public class InvalidMoveException extends RuntimeException{

    public InvalidMoveException(String message){
        super(message);
    }
}
