package main.java.element;

import main.java.EvaluatingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BinaryExpression extends Expression {
	private final Expression left, right;
	private final BinaryOperation operation;
	
	public BinaryExpression(Expression left, Expression right, BinaryOperation operation) {
		List<String> res = new ArrayList<>();
		for (String param : left.required) {
			if (!res.contains(param)) {
				res.add(param);
			}
		}
		for (String param : right.required) {
			if (!res.contains(param)) {
				res.add(param);
			}
		}
		this.required = res;
		this.left = left;
		this.right = right;
		this.operation = operation;
	}
	
	@Override
	public int evaluate(List<Integer> argValues, Map<String, Function> functions) throws EvaluatingException {
		List<Integer> leftArgs = generateArguments(argValues, left, required);
		List<Integer> rightArgs = generateArguments(argValues, right, required);
		return operation.apply(left.evaluate(leftArgs, functions), right.evaluate(rightArgs, functions));
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
		return "(" + left.toString() + operation.toString() + right.toString() + ")";
	}
}
