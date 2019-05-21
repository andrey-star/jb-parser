package main.java.elements;

import java.util.Map;

/**
 * the {@code Constant} class represents an Integer value.
 */
public class Constant extends Expression {
	
	private final int value;
	
	/**
	 * Constructs a {@code Constant} object, given int Integer value.
	 * @param value the value of the constant
	 */
	public Constant(int value) {
		this.value = value;
	}
	
	@Override
	public int evaluate(Map<String, Integer> scope, Map<String, Function> functions) {
		return value;
	}
	
	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
