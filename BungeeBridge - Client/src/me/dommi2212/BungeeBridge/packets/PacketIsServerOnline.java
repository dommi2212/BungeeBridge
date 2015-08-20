package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to check if a server is online/responds.
 * 
 * Returned: boolean result
 */
@SuppressWarnings("serial")
public class PacketIsServerOnline extends BungeePacket implements Serializable {
	
	private String bungeename;
	
	/**
	 * Instantiates a new PacketIsServerOnline.
	 *
	 * @param bungeename bungeename
	 */
	public PacketIsServerOnline(String bungeename) {
		super(BungeePacketType.ISSERVERONLINE, true);
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
