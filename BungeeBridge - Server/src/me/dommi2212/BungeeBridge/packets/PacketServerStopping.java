package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to indicate that a server is stopping.
 * ONLY USE THIS PACKET IF YOU KNOW WHAT YOU DO!
 * (Not mentioned on the page)
 */
public class PacketServerStopping extends BungeePacket implements Serializable {

	private String bungeename;
	
	/**
	 * Instantiates a new PacketServerStopping.
	 */
	public PacketServerStopping(String bungeename) {
		this.bungeename = bungeename;
		this.type = BungeePacketType.SERVERSTOPPING;
		this.shouldanswer = false;
	}
	
	/**
	 * Gets the bungeename.
	 *
	 * @return bungeename
	 */
	public String getBungeename() {
		return bungeename;
	}

}
