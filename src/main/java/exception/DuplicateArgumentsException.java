package main.java.exception;

public class DuplicateArgumentsException extends EvaluatingException {
	
	public DuplicateArgumentsException(String name, int line) {
		super("DUPLICATE ARGUMENTS FOUND", name, line);
	}
	
}
