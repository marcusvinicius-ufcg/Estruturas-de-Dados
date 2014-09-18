package adt.hashtable;

@SuppressWarnings("serial")
public class HashtableOverflowException extends RuntimeException {

	public HashtableOverflowException() {
		super("Hashtable overflow!");
	}
}
