package me.dommi2212.BungeeBridge.events.wrapped;

import java.util.UUID;

import me.dommi2212.BungeeBridge.WrappedEvent;

/**
 * A Wrapper-Class representing an PlayerCommandPreprocessEvent.
 */
public class WrappedPlayerCommandPreprocessEvent extends WrappedEvent {
	private static final long serialVersionUID = -5509012987435412826L;
	
	private UUID uuid;
	private String message;
	private String bungeename;
	
	public WrappedPlayerCommandPreprocessEvent(UUID uuid, String message, String bungeename) {
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
