package me.dommi2212.BungeeBridge.events.unwrapped;

import java.util.UUID;

import net.md_5.bungee.api.plugin.Event;

/**
 * An event representing Spigot's AsyncPlayerChatEvent.
 */
public class AsyncPlayerChatEvent extends Event {
	
	private UUID uuid;
	private String message;
	private String format;
	private String bungeename;
	
	/**
	 * Instantiates a new AsyncPlayerChatEvent.
	 *
	 * @param uuid the uuid
	 * @param message the message
	 * @param format the format of the message
	 * @param bungeename the bungeename of the server
	 */
	public AsyncPlayerChatEvent(UUID uuid, String message, String format, String bungeename) {
		this.uuid = uuid;
		this.message = message;
		this.format = format;
		this.bungeename = bungeename;
	}
	
	/**
	 * Gets the player's uuid.
	 *
	 * @return the uuid
	 */
	public UUID getUUID() {
		return uuid;
	}
	
	/**
	 * Gets the message of the event.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Gets the format of the message.
	 *
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	
	/**
	 * Gets the bungeename of the server.
	 *
	 * @return the bungeename
	 */
	public String getBungeename() {
		return bungeename;
	}

}
