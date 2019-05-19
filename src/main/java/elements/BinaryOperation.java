package main.java.elements;

import main.java.exceptions.EvaluatingException;

import java.util.Map;
import java.util.function.BinaryOperator;

public class BinaryOperation {
	
	private final String operation;
	private static final Map<String, BinaryOperator<Integer>> opIdentifierToOperation = Map.of(
			"+", Integer::sum,
			"-", (a, b) -> a - b,
			"*", (a, b) -> a * b,
			"/", (a, b) -> a / b,
			">", (a, b) -> (a > b ? 1 : 0),
			"<", (a, b) -> (a < b ? 1 : 0),
			"=", (a, b) -> (a.equals(b) ? 1 : 0)
	);
	
	
	public BinaryOperation(String operation) {
		this.operation = operation;
	}
	
	int apply(int a, int b) throws EvaluatingException {
		try {
			return opIdentifierToOperation.get(operation).apply(a, b);
		} catch (ArithmeticException e) {
			throw new EvaluatingException("RUNTIME ERROR");
		}
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
