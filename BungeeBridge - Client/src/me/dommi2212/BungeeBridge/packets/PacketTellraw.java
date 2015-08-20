package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;
import java.util.UUID;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

/**
 * Packet used to send a JSON- or "Tellraw"-Message to a player. A JSON-String can be obtained easily by using {@link ComponentSerializer#toString(BaseComponent)} of Spigot's ChatAPI.
 */
@SuppressWarnings("serial")
public class PacketTellraw extends BungeePacket implements Serializable {
	
	private UUID uuid;
	private String jsonString;
	
	/**
	 * Instantiates a new PacketTellraw.
	 *
	 * @param uuid the player's uuid.
	 * @param jsonString the JSON-String-Representation of the Message
	 */
	public PacketTellraw(UUID uuid, String jsonString) {
		super(BungeePacketType.TELLRAW, false);
		this.uuid = uuid;
		this.jsonString = jsonString;
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
	 * Gets the JSON-String.
	 *
	 * @return jsonString
	 */
	public String getJSONString() {
		return jsonString;
	}
	
}
