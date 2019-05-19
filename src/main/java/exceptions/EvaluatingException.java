package main.java.exceptions;

public class EvaluatingException extends Exception {
    
    private final String error;
    private String name;
    private int line;
    
    public EvaluatingException(String error) {
        super(error);
        this.error = error;
    }
    
    public EvaluatingException(String error, String name) {
        super(String.format("%s %s", error, name));
        this.error = error;
        this.name = name;
    }
    
    public EvaluatingException(String error, String name, int line) {
        super(String.format("%s %s:%d", error, name, line + 1));
        this.error = error;
        this.name = name;
        this.line = line;
    }
    
    public String getError() {
        return error;
    }
    
    public String getName() {
        return name;
    }
    
    public int getLine() {
        return line;
    }
}
