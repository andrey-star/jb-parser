package main.java.elements;

public abstract class Expression implements Evaluatable {
	
	@Override
	abstract public boolean equals(Object obj);
	
	@Override
	abstract public String toString();
}
