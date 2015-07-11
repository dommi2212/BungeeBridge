package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to get the names of all servers.
 * 
 * Returned: List (String) servers
 */
@SuppressWarnings("serial")
public class PacketGetServers extends BungeePacket implements Serializable {
	
	/**
	 * Instantiates a new PacketGetServers.
	 */
	public PacketGetServers() {
		this.type = BungeePacketType.GETSERVERS;
		this.shouldanswer = true;
	}
	
}
