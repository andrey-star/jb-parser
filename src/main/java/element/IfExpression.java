package main.java.element;

import main.java.EvaluatingException;

import java.util.List;
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
		return false;
	}
	
	@Override
	public String toString() {
		return "[" + rule + "]?{" + ifTrue + "}:{" + ifFalse + "}";
	}
	
	@Override
	public int evaluate(Map<String, Integer> args) throws EvaluatingException {
		return rule.evaluate(args) != 0 ? ifTrue.evaluate(args) : ifFalse.evaluate(args);
	}
}
