package me.dommi2212.BungeeBridge;

/**
 * Is thrown if a unknown Packet is recieved.
 */
@SuppressWarnings("serial")
public class UnknownPacketException extends RuntimeException {
	
	public UnknownPacketException() {
		super();
	}
	
	public UnknownPacketException(String message) {
		super(message);
	}

}
