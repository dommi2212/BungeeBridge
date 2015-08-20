package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to get the slots of a server.
 * 
 * Returned: int slots
 */
@SuppressWarnings("serial")
public class PacketGetSlotsServer extends BungeePacket implements Serializable {
	
	private String bungeename;
	
	/**
	 * Instantiates a new PacketGetSlotsServer.
	 *
	 * @param bungeename the bungeename
	 */
	public PacketGetSlotsServer(String bungeename) {
		super(BungeePacketType.GETSLOTSSERVER, true);
		this.bungeename = bungeename;
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
