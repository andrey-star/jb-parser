package main.java.exceptions;

public class ParameterNotFoundException extends EvaluatingException {
	
	public ParameterNotFoundException(String name, int line) {
		super("PARAMETER NOT FOUND", name, line);
	}
	
}
