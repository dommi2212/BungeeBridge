package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to get count of players on a server.
 * 
 * Returned: int onlinecount
 */
@SuppressWarnings("serial")
public class PacketGetOnlineCountServer extends BungeePacket implements Serializable {
	
	private String server;
	
	/**
	 * Instantiates a new PacketGetOnlineCountServer.
	 *
	 * @param server server to get playercount from
	 */
	public PacketGetOnlineCountServer(String server) {
		super(BungeePacketType.GETONLINECOUNTSERVER, true);
		this.server = server;
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
