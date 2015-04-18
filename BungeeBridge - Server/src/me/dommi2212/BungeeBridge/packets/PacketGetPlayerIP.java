package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;
import java.util.UUID;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to get a player's IP.
 * 
 * Returned: InetSocketAddress address.
 */
public class PacketGetPlayerIP extends BungeePacket implements Serializable {
	
	private UUID uuid;
	
	/**
	 * Instantiates a PacketGetPlayerIP.
	 *
	 * @param uuid the player's uuid
	 */
	public PacketGetPlayerIP(UUID uuid) {
		this.uuid = uuid;
		this.type = BungeePacketType.GETPLAYERIP;
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
