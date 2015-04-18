package me.dommi2212.BungeeBridge;

/**
 * Is thrown if Packetsend fails.
 */
public class PacketFailSendException extends RuntimeException {
	
	public PacketFailSendException() {
		super();
	}
	
	public PacketFailSendException(String message) {
		super(message);
	}
}