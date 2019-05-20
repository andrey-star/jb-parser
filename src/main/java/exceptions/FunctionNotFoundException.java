package main.java.exceptions;

public class FunctionNotFoundException extends EvaluatingException {
	
	public FunctionNotFoundException(String name, int line) {
		super("FUNCTION NOT FOUND", name, line);
	}
	
}
