package main.java.element;

import main.java.EvaluatingException;

import java.util.*;

public class Variable extends Expression {
	
	private final String name;
	
	public Variable(String name) {
		this.required = List.of(name);
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
	public int evaluate(List<Integer> args, Map<String, Function> functions) throws EvaluatingException {
		if (args.size() == 1) {
			return args.get(0);
		}
		throw new EvaluatingException("PARAMETER NOT FOUND", name);
	}
}
