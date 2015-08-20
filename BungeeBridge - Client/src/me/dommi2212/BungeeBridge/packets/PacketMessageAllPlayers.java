package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to send a message to all players on the network.
 */
@SuppressWarnings("serial")
public class PacketMessageAllPlayers extends BungeePacket implements Serializable {
	
	private String message;
	
	/**
	 * Instantiates a new PacketMessageAllPlayers.
	 *
	 * @param message message
	 */
	public PacketMessageAllPlayers(String message) {
		super(BungeePacketType.MESSAGEALLPLAYERS, false);
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
