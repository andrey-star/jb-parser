package main.java.element;

import main.java.EvaluatingException;

import java.util.Map;

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
	public int evaluate(Map<String, Integer> args) throws EvaluatingException {
		if (args.containsKey(name)) {
			return args.get(name);
		}
		throw new EvaluatingException("No argument value for variable '" + name + "'");
	}
}
