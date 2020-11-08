package it.unibo.oop.lab.exception2;

public class WrongAccountHolderException extends IllegalStateException {

	
	public String toString() {
		return "[WrongAccountHolderException] : The following action is taken by an unauthorized account";
	}
	
	public String getMessage() {
		return this.toString();
	}
	
	
}
