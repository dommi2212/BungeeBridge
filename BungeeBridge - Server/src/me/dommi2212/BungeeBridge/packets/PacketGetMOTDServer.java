package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to get the MOTD of a server.
 * 
 * Returned: String MOTD
 */
@SuppressWarnings("serial")
public class PacketGetMOTDServer extends BungeePacket implements Serializable {
	
	private String bungeename;
	
	/**
	 * Instantiates a new PacketGetMOTDServer.
	 *
	 * @param bungeename the bungeename
	 */
	public PacketGetMOTDServer(String bungeename) {
		this.bungeename = bungeename;
		this.type = BungeePacketType.GETMOTDSERVER;
		this.shouldanswer = true;
	}
	
	/**
	 * Gets the server.
	 *
	 * @return server
	 */
	public String getBungeename() {
		return bungeename;
	}
	
}
