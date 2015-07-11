package me.dommi2212.BungeeBridge.events.unwrapped;

import java.util.UUID;

import net.md_5.bungee.api.plugin.Event;

/**
 * An event representing Spigot's PlayerCommandPreprocessEvent.
 */
public class PlayerCommandPreprocessEvent extends Event {
	
	private UUID uuid;
	private String message;
	private String bungeename;
	
	public PlayerCommandPreprocessEvent(UUID uuid, String message, String bungeename) {
		this.uuid = uuid;
		this.message = message;
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
	 * Gets the bungeename of the server.
	 *
	 * @return the bungeename
	 */
	public String getBungeename() {
		return bungeename; 
	}

}
