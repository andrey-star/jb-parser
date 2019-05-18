package main.java.element;

import java.util.List;
import java.util.Map;

public class Constant extends Expression {
	
	private final int value;
	
	public Constant(int value) {
		this.value = value;
	}
	
	@Override
	public int evaluate(Map<String, Integer> args) {
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