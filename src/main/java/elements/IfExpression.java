package main.java.elements;

import main.java.exceptions.EvaluatingException;

import java.util.Map;

/**
 * The {@code IfExpression} class represents the ternary operator.
 */
public class IfExpression extends Expression {
	
	private final Expression condition;
	private final Expression ifTrue, ifFalse;
	
	/**
	 * Constructs an {@code IfExpression} object, using the given arguments
	 * @param condition the condition of the ternary operator
	 * @param ifTrue the value, if the condition is {@code true}
	 * @param ifFalse the value, if the condition is {@code false}
	 */
	public IfExpression(Expression condition, Expression ifTrue, Expression ifFalse) {
		this.condition = condition;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IfExpression) {
			return condition.equals(((IfExpression) obj).condition)
					&& ifTrue.equals(((IfExpression) obj).ifTrue)
					&& ifFalse.equals(((IfExpression) obj).ifFalse);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "[" + condition + "]?{" + ifTrue + "}:{" + ifFalse + "}";
	}
	
	@Override
	public int evaluate(Map<String, Integer> scope, Map<String, Function> functions) throws EvaluatingException {
		return condition.evaluate(scope, functions) != 0 ?
				ifTrue.evaluate(scope, functions) :
				ifFalse.evaluate(scope, functions);
	}
}
