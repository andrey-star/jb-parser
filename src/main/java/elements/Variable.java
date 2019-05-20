package main.java.elements;

import main.java.exceptions.EvaluatingException;

import java.util.*;

public class Variable extends Expression {
	
	private final String name;
	
	public Variable(String name) {
		this.name = name;
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
	public int evaluate(Map<String, Integer> scope, Map<String, Function> functions) throws EvaluatingException {
		if (scope.containsKey(name)) {
			return scope.get(name);
		}
		throw new EvaluatingException("PARAMETER NOT FOUND", name);
	}
}
