package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;
import java.util.UUID;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to get count of players on a server.
 * 
 * Returned: int onlinecount
 */
public class PacketGetOnlineCountServer extends BungeePacket implements Serializable {
	
	private String server;
	
	/**
	 * Instantiates a new PacketGetOnlineCountServer.
	 *
	 * @param server server to get playercount from
	 */
	public PacketGetOnlineCountServer(String server) {
		this.server = server;
		this.type = BungeePacketType.GETONLINECOUNTSERVER;
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
