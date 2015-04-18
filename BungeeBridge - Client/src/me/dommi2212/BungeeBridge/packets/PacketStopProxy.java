package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to stop the proxy.
 */
public class PacketStopProxy extends BungeePacket implements Serializable {
	
	private String message = null;
	
	/**
	 * Instantiates a new PacketStopProxy.
	 */
	public PacketStopProxy() {
		this.type = BungeePacketType.STOPPROXY;
		this.shouldanswer = false;
	}
	
	/**
	 * Instantiates a new PacketStopProxy.
	 *
	 * @param message message
	 */
	public PacketStopProxy(String message) {
		this.message = message;
		this.type = BungeePacketType.STOPPROXY;
		this.shouldanswer = false;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
}
