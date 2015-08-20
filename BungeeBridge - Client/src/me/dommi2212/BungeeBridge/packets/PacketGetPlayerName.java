package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;
import java.util.UUID;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to get a player's name.
 * 
 * Returned: String name
 */
@SuppressWarnings("serial")
public class PacketGetPlayerName extends BungeePacket implements Serializable {
	
	private UUID uuid;
	
	/**
	 * Instantiates a new PacketGetPlayerName.
	 *
	 * @param uuid the player's uuid
	 */
	public PacketGetPlayerName(UUID uuid) {
		super(BungeePacketType.GETPLAYERNAME, true);
		this.uuid = uuid;
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
