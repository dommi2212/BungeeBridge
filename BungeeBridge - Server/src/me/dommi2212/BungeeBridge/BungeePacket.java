package me.dommi2212.BungeeBridge;

import java.io.Serializable;

/**
 * Core of all packets.
 */
public abstract class BungeePacket implements Serializable {
	
	private static final long serialVersionUID = 3728278382368494804L;
	private BungeePacketType type;
	private boolean shouldanswer;
	private String pass = null;
	
	public BungeePacket(BungeePacketType type, boolean shouldanswer) {
		this.type = type;
		this.shouldanswer = shouldanswer;
	}
	
	/**
	 * Gets the type of a packet.
	 *
	 * @return type
	 */
	public BungeePacketType getType() {
		return type;
	}
	
	/**
	 * Controls if a packet should answer or not.
	 *
	 * @return true, if successful
	 */
	public boolean shouldAnswer() {
		return shouldanswer;
	}
	
	/**
	 * Displays password if SECMODE is set to SecurityMode.PASS.
	 *
	 * @return pass
	 */
	public String getPassword() {
		return pass;
	}
} 