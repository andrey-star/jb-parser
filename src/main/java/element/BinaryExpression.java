package main.java.element;

import main.java.EvaluatingException;

import java.util.Map;

public class BinaryExpression extends Expression {
	private final Expression left, right;
	private final BinaryOperation operation;
	
	public BinaryExpression(Expression left, Expression right, BinaryOperation operation) {
		this.left = left;
		this.right = right;
		this.operation = operation;
	}
	
	@Override
	public int evaluate(Map<String, Integer> args) throws EvaluatingException {
		return operation.apply(left.evaluate(args), right.evaluate(args));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BinaryExpression) {
			return left.equals(((BinaryExpression) obj).left)
					&& operation.equals(((BinaryExpression) obj).operation)
					&& right.equals(((BinaryExpression) obj).right);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + left.toString()+ operation.toString() + right.toString() + ")";
	}
}
