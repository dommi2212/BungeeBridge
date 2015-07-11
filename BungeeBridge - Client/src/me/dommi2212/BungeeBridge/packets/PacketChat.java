package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;
import java.util.UUID;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to chat a message or send a command as a player.
 * 
 * Returned: -
 */
@SuppressWarnings("serial")
public class PacketChat extends BungeePacket implements Serializable {
	
	private UUID uuid;
	private String message;
	
	/**
	 * Instantiates a new PacketChat.
	 *
	 * @param uuid the player's uuid.
	 * @param message the message to send.
	 */
	public PacketChat(UUID uuid, String message) {
		this.uuid = uuid;
		this.message = message;
		this.type = BungeePacketType.CHAT;
		this.shouldanswer = false;
	}
	
	/**
	 * Gets the uuid.
	 *
	 * @return uuid
	 */
	public UUID getUUID() {
		return uuid;
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
