package main.java.elements;

import main.java.exceptions.ParameterNotFoundException;

import java.util.Map;

public class Variable extends Expression {
	
	private final String name;
	private final int line;
	
	public Variable(String name, int line) {
		this.name = name;
		this.line = line;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Variable) {
			return name.equals(((Variable) obj).name);
		}
		return false;
		
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int evaluate(Map<String, Integer> scope, Map<String, Function> functions) throws ParameterNotFoundException {
		if (scope.containsKey(name)) {
			return scope.get(name);
		}
		throw new ParameterNotFoundException(name, line);
	}
}
