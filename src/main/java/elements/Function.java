package main.java.elements;

import main.java.exceptions.EvaluatingException;

import java.util.*;

public class Function {

	private final String name;
	private final List<String> params;
	private final Expression body;
	private final int line;
	
	public Function(String name, List<String> params, Expression body, int line) {
		this.name = name;
		this.params = params;
		this.body = body;
		this.line = line;
	}
	
	int call(List<Integer> argValues, Map<String, Function> functions) throws EvaluatingException {
		Map<String, Integer> scope = new HashMap<>();
		for (int i = 0; i < argValues.size(); i++) {
			scope.put(params.get(i), argValues.get(i));
		}
		if (scope.size() != params.size()) {
			throw new EvaluatingException("DUPLICATE ARGUMENTS FOUND", name, line);
		}
		try {
			return body.evaluate(scope, functions);
		} catch (EvaluatingException e) {
			throw new EvaluatingException(e.getError(), e.getName(), line);
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	List<String> getParams() {
		return params;
	}
	
	public Expression getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		return name + "(" + String.join(",", params) + ")={" + body + "}";
	}
}
