package main.java.element;

import java.util.Map;
import java.util.function.BinaryOperator;

public class BinaryOperation {
	
	private static final Map<String, BinaryOperator<Integer>> opIdentifierToOperation = Map.of(
			"+", Integer::sum,
			"-", (a, b) -> a - b,
			"*", (a, b) -> a * b,
			"/", (a, b) -> a / b
	);
	
	private String operation;
	
	public BinaryOperation(String operation) {
		this.operation = operation;
	}
	
	public int apply(int a, int b) {
		return opIdentifierToOperation.get(operation).apply(a, b);
	}
	
	@Override
	public String toString() {
		return operation;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BinaryOperation) {
			return operation.equals(((BinaryOperation) obj).operation);
		}
		return false;
	}
}
