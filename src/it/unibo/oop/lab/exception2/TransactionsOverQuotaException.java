package it.unibo.oop.lab.exception2;

public class TransactionsOverQuotaException extends IllegalStateException {

	
	public String toString() {
		return "[TransactionsOverQuotaException] : The account reached the limit of allowed operations";
	}
	
	public String getMessage() {
		return this.toString();
	}
	
	
}
