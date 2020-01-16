package main.java.element;

import java.util.function.BinaryOperator;

public class BinaryOperation {
	
	private final BinaryOperator<Integer> operation;
	private final String opIdentifier;
	
	public BinaryOperation(BinaryOperator<Integer> operation, String opIdentifier) {
		this.operation = operation;
		this.opIdentifier = opIdentifier;
	}
	
	int apply(int a, int b) {
		return operation.apply(a, b);
	}
	
	@Override
	public String toString() {
		return opIdentifier;
	}
	
}
