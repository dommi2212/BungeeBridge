package me.dommi2212.BungeeBridge;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.packets.PacketCustom;

/**
 *Abstract base-class for all packets. You may <b>not</b> extend this class as you have to set a type in the constructor. Use {@link PacketCustom} instead.
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
	 * @return The type of the packet.
	 */
	public BungeePacketType getType() {
		return type;
	}
	
	/**
	 * Checks, whether the packet should answer or not.
	 *
	 * @return Whether the packet answers or not.
	 */
	public boolean shouldAnswer() {
		return shouldanswer;
	}
	
	/**
	 * Gets the password if SECMODE is set to {@link SecurityMode#PASS}. 
	 *
	 * @return The password or {@code null}, if no password has been set.
	 */
	public String getPassword() {
		return pass;
	}
} 