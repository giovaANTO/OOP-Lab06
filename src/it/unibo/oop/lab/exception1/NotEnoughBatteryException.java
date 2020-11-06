package it.unibo.oop.lab.exception1;

/**
 * This class extends the IllegalStateException because a method that require some energy from the battery
 * has been invoked at an illegal time where the battery level capacity isn't enough to make an action.
 * The state where the battery level is low represent an inappropriate moment in the java application where 
 */
public class NotEnoughBatteryException extends IllegalStateException {

	private static final long serialVersionUID = -288979685441144222L;

	public String toString() {
		return "The battery level of the robot isn't enough to make the operation required";
	}
	
	public String getMessage() {
        return this.toString();
    }

}
