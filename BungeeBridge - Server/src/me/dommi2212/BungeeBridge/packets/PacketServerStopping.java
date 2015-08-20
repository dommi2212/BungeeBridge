package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to indicate that a server is stopping.
 * ONLY USE THIS PACKET IF YOU KNOW WHAT YOU DO!
 * (Not mentioned on the page)
 */
@SuppressWarnings("serial")
public class PacketServerStopping extends BungeePacket implements Serializable {

	private String bungeename;
	
	/**
	 * Instantiates a new PacketServerStopping.
	 * 
	 * @param bungeename the bungeename of the server.
	 */
	public PacketServerStopping(String bungeename) {
		super(BungeePacketType.SERVERSTOPPING, false);
		this.bungeename = bungeename;
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
