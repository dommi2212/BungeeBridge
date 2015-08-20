package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to get count of players on the network.
 * 
 * Returned: int onlinecount
 */
@SuppressWarnings("serial")
public class PacketGetOnlineCountGlobal extends BungeePacket implements Serializable {
	
	/**
	 * Instantiates a new PacketGetOnlineCountGlobal.
	 */
	public PacketGetOnlineCountGlobal() {
		super(BungeePacketType.GETONLINECOUNTGLOBAL, true);
	}
	
}
