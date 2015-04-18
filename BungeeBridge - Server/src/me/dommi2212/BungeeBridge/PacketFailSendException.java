package me.dommi2212.BungeeBridge;

/**
 * Is thrown if Packetsend fails.
 */
@SuppressWarnings("serial")
public class PacketFailSendException extends RuntimeException {
	
	public PacketFailSendException() {
		super();
	}
	
	public PacketFailSendException(String message) {
		super(message);
	}
}