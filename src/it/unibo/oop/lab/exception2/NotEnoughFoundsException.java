package it.unibo.oop.lab.exception2;

public class NotEnoughFoundsException extends IllegalStateException {

	
	public String toString() {
		return "[NotEnoughFoundsException] : The bank account doesn't have enough founds to complete the operation";
	}
	
	public String getMessage() {
		return this.toString();
	}
	
	
}
