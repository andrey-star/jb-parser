package main.java.elements;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Constant extends Expression {
	
	private final int value;
	
	public Constant(int value) {
		this.required = Collections.emptyList();
		this.value = value;
	}
	
	@Override
	public int evaluate(List<Integer> args, Map<String, Function> functions) {
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Constant) {
			return value == (((Constant) obj).value);
		}
		return false;
		
	}
	
	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
