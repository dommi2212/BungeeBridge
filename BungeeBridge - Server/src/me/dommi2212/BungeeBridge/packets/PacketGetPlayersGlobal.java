package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to resolve the uuid of all players.
 * 
 * Returned: List (UUID) uuids
 */
@SuppressWarnings("serial")
public class PacketGetPlayersGlobal extends BungeePacket implements Serializable {
	
	/**
	 * Instantiates a PacketGetPlayersGlobal.
	 */
	public PacketGetPlayersGlobal() {
		this.type = BungeePacketType.GETPLAYERSGLOBAL;
		this.shouldanswer = true;
	}
	
}
