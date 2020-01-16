package main.java.exception;

public class RuntimeException extends EvaluatingException {
	
	public RuntimeException(String name, int line) {
		super("RUNTIME ERROR", name, line);
	}
	
}
