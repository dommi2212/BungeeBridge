package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Kicks all Players on the network.
 */
@SuppressWarnings("serial")
public class PacketKickAllPlayers extends BungeePacket implements Serializable {
	
	private String message;
	
	/**
	 * Instantiates a new PacketKickAllPlayers.
	 *
	 * @param message message
	 */
	public PacketKickAllPlayers(String message) {
		super(BungeePacketType.KICKALLPLAYERS, false);
		this.message = message;
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
