package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to get the IP of a server.
 * 
 * Returned: InetSocketAddress address
 */
@SuppressWarnings("serial")
public class PacketGetServerIP extends BungeePacket implements Serializable {
	
	private String server;
	
	/**
	 * Instantiates a PacketGetServerIP.
	 *
	 * @param server server
	 */
	public PacketGetServerIP(String server) {
		super(BungeePacketType.GETSERVERIP, true);
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
