package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;
import java.util.UUID;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to send a actionbar to a player.
 */
@SuppressWarnings("serial")
public class PacketSendActionbar extends BungeePacket implements Serializable {

	private UUID uuid;
	private String actionbar;
	
	/**
	 * Instantiates a new PacketSendActionbar.
	 *
	 * @param uuid uuid
	 * @param actionbar actionbar
	 */
	public PacketSendActionbar(UUID uuid, String actionbar) {
		this.uuid = uuid;
		this.actionbar = actionbar;
		this.type = BungeePacketType.SENDACTIONBAR;
		this.shouldanswer = false;
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
	 * Gets the actionbar.
	 *
	 * @return actionbar
	 */
	public String getActionbar() {
		return actionbar;
	}
}
