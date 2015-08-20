package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;
import java.util.UUID;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;
import me.dommi2212.BungeeBridge.PackedTitle;

/**
 * Packet used to send a title to a player.
 */
@SuppressWarnings("serial")
public class PacketSendTitle extends BungeePacket implements Serializable {

	private UUID uuid;
	private PackedTitle title;
	
	/**
	 * Instantiates a new PacketSendTitle.
	 *
	 * @param uuid uuid
	 * @param title title
	 */
	public PacketSendTitle(UUID uuid, PackedTitle title) {
		super(BungeePacketType.SENDTITLE, false);
		this.uuid = uuid;
		this.title = title;
	}
	
	/**
	 * Gets the uuid.
	 *
	 * @return uuid
	 */
	public UUID getUUID() {
		return uuid;
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
