package main.java.exception;

public class ArgumentNumberMismatchException extends EvaluatingException {
	
	public ArgumentNumberMismatchException(String name, int line) {
		super("ARGUMENT NUMBER MISMATCH", name, line);
	}
	
}
