package main.java.element;

import main.java.EvaluatingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IfExpression extends Expression {
	
	private final Expression rule;
	private final Expression ifTrue, ifFalse;
	
	public IfExpression(Expression rule, Expression ifTrue, Expression ifFalse) {
		List<String> res = new ArrayList<>();
		for (String param : rule.required) {
			if (!res.contains(param)) {
				res.add(param);
			}
		}
		for (String param : ifTrue.required) {
			if (!res.contains(param)) {
				res.add(param);
			}
		}
		for (String param : ifFalse.required) {
			if (!res.contains(param)) {
				res.add(param);
			}
		}
		this.required = res;
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
	public int evaluate(List<Integer> argValues, Map<String, Function> functions) throws EvaluatingException {
		List<Integer> ruleArgs = generateArguments(argValues, rule, required);
		List<Integer> ifTrueArgs = generateArguments(argValues, ifTrue, required);
		List<Integer> ifFalseArgs = generateArguments(argValues, ifFalse, required);
		return rule.evaluate(ruleArgs, functions) != 0 ?
				ifTrue.evaluate(ifTrueArgs, functions) :
				ifFalse.evaluate(ifFalseArgs, functions);
	}
}
