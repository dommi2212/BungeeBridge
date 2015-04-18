package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Kicks all Players on the network.
 */
public class PacketKickAllPlayers extends BungeePacket implements Serializable {
	
	private String message;
	
	/**
	 * Instantiates a new PacketKickAllPlayers.
	 *
	 * @param message message
	 */
	public PacketKickAllPlayers(String message) {
		this.message = message;
		this.type = BungeePacketType.KICKALLPLAYERS;
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
