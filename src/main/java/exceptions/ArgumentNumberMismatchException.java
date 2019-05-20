package main.java.exceptions;

public class ArgumentNumberMismatchException extends EvaluatingException {
	
	public ArgumentNumberMismatchException(String name, int line) {
		super("ARGUMENT NUMBER MISMATCH", name, line);
	}
	
}
