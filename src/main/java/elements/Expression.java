package main.java.elements;

/**
 * The {@code Expression} class represents an expression, that
 * can be evaluated.
 */
public abstract class Expression implements Evaluatable {
	
	@Override
	abstract public boolean equals(Object obj);
	
	@Override
	abstract public String toString();
}
