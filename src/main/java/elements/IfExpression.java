package main.java.elements;

import main.java.exceptions.EvaluatingException;

import java.util.Map;

public class IfExpression extends Expression {
	
	private final Expression rule;
	private final Expression ifTrue, ifFalse;
	
	public IfExpression(Expression rule, Expression ifTrue, Expression ifFalse) {
		this.rule = rule;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IfExpression) {
			return rule.equals(((IfExpression) obj).rule)
					&& ifTrue.equals(((IfExpression) obj).ifTrue)
					&& ifFalse.equals(((IfExpression) obj).ifFalse);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "[" + rule + "]?{" + ifTrue + "}:{" + ifFalse + "}";
	}
	
	@Override
	public int evaluate(Map<String, Integer> scope, Map<String, Function> functions) throws EvaluatingException {
		return rule.evaluate(scope, functions) != 0 ?
				ifTrue.evaluate(scope, functions) :
				ifFalse.evaluate(scope, functions);
	}
}
