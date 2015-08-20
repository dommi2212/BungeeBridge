package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;
import me.dommi2212.BungeeBridge.PackedTitle;

/**
 * Packet used to send a title to all players.
 */
@SuppressWarnings("serial")
public class PacketSendAllTitle extends BungeePacket implements Serializable {
	
	private PackedTitle title;
	
	/**
	 * Instantiates a new PacketSendAllTitle.
	 *
	 * @param title title
	 */
	public PacketSendAllTitle(PackedTitle title) {
		super(BungeePacketType.SENDALLTITLE, false);
		this.title = title;
	}
	
	/**
	 * Gets the title.
	 *
	 * @return title
	 */
	public PackedTitle getTitle() {
		return title;
	}
}
