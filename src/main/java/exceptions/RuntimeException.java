package main.java.exceptions;

public class RuntimeException extends EvaluatingException {
	
	public RuntimeException(String name, int line) {
		super("RUNTIME ERROR", name, line);
	}
	
}
