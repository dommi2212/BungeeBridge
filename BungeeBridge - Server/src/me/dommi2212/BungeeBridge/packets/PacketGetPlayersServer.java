package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to resolve the uuid of all players on a server.
 * 
 * Returned: List<UUID> uuids
 */
@SuppressWarnings("serial")
public class PacketGetPlayersServer extends BungeePacket implements Serializable {
	
	/** The server. */
	private String server;
	
	/**
	 * Instantiates a new PacketGetPlayersServer.
	 *
	 * @param server server
	 */
	public PacketGetPlayersServer(String server) {
		this.server = server;
		this.type = BungeePacketType.GETPLAYERSSERVER;
		this.shouldanswer = true;
	}
	
	/**
	 * Gets the server.
	 *
	 * @return server
	 */
	public String getServer() {
		return server;
	}
	
}
