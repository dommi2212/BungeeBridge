package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to indicate a server is still alive and update some data.
 * ONLY USE THIS PACKET IF YOU KNOW WHAT YOU DO!
 * (Not mentioned on the page)
 */
@SuppressWarnings("serial")
public class PacketKeepAlive extends BungeePacket implements Serializable {
	
	private String bungeename;
	private boolean auto;
	private String motd;
	
	/**
	 * Instantiates a new PacketKeepAlive.
	 * 
	 * @param bungeename the bungeename
	 * @param auto whether the packet was send automatically
	 * @param motd the motd
	 */
	public PacketKeepAlive(String bungeename, boolean auto, String motd) {
		this.bungeename = bungeename;
		this.auto = auto;
		this.motd = motd;
		this.type = BungeePacketType.KEEPALIVE;
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
	
	/**
	 * Checks if is automatic.
	 *
	 * @return true, if is automatic
	 */
	public boolean isAutomatic() {
		return auto;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return message
	 */
	public String getMOTD() {
		return motd;
	}
}
