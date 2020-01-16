package main.java.element;

import main.java.exception.EvaluatingException;
import main.java.exception.RuntimeException;

import java.util.Map;

/**
 * The {@code BinaryExpression} class represents a binary expression.
 */
public class BinaryExpression extends Expression {
	private final Expression left, right;
	private final BinaryOperation operation;
	private final int line;
	
	/**
	 * Constructs an {@code IfExpression} object, using the given arguments
	 * @param left the left side of th binary expression
	 * @param right the right side of th binary expression
	 * @param operation the operation of the binary expression
	 * @param line is the line, at which the expression takes place
	 */
	public BinaryExpression(Expression left, Expression right, BinaryOperation operation, int line) {
		this.left = left;
		this.right = right;
		this.operation = operation;
		this.line = line;
	}
	
	@Override
	public int evaluate(Map<String, Integer> scope, Map<String, Function> functions) throws EvaluatingException {
		int a = left.evaluate(scope, functions);
		int b = right.evaluate(scope, functions);
		try {
			return operation.apply(a, b);
		} catch (ArithmeticException e) {
			throw new RuntimeException(toString(), line);
		}
	}
	
	@Override
	public String toString() {
		return "(" + left.toString() + operation.toString() + right.toString() + ")";
	}
}
