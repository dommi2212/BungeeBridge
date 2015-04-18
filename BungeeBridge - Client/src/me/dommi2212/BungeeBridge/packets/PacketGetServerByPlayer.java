package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;
import java.util.UUID;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to get name of a player's server.
 * 
 * Returned: String server
 */
public class PacketGetServerByPlayer extends BungeePacket implements Serializable {
	
	private UUID uuid;
	
	/**
	 * Instantiates a new PacketGetServerByPlayer.
	 *
	 * @param uuid the player's uuid
	 */
	public PacketGetServerByPlayer(UUID uuid) {
		this.uuid = uuid;
		this.type = BungeePacketType.GETSERVERBYPLAYER;
		this.shouldanswer = true;
	}
	
	/**
	 * Gets the uuid.
	 *
	 * @return uuid
	 */
	public UUID getUUID() {
		return uuid;
	}
	
}
