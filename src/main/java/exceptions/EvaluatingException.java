package main.java.exceptions;

public class EvaluatingException extends Exception {
    
    public EvaluatingException(String error, String name, int line) {
        super(String.format("%s %s:%d", error, name, line + 1));
    }
    
}
