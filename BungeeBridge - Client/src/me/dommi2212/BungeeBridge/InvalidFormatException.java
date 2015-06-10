package me.dommi2212.BungeeBridge;

/**
 * Is thrown if a unknown Packet is recieved.
 */
@SuppressWarnings("serial")
public class InvalidFormatException extends RuntimeException {
	
	public InvalidFormatException(String message) {
		super(message);
	}

}
