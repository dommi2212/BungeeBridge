package me.dommi2212.BungeeBridge.events.wrapped;

import java.util.UUID;

import me.dommi2212.BungeeBridge.WrappedEvent;
import me.dommi2212.BungeeBridge.events.unwrapped.AsyncPlayerChatEvent;
import net.md_5.bungee.api.plugin.Event;

/**
 * A Wrapper-Class representing an AsyncPlayerChatEvent.
 */
public class WrappedAsyncPlayerChatEvent extends WrappedEvent {
	
	private static final long serialVersionUID = -6973004689464370567L;
	private UUID uuid;
	private String message;
	private String format;
	private String bungeename;
	
	/**
	 * Instantiates a new WrappedAsyncPlayerChatEvent.
	 *
	 * @param uuid the uuid
	 * @param message the message
	 * @param format the format of the message
	 * @param bungeename the bungeename of the server
	 */
	public WrappedAsyncPlayerChatEvent(UUID uuid, String message, String format, String bungeename) {
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

	@Override
	public Event toEvent() {
		return new AsyncPlayerChatEvent(uuid, message, format, bungeename);
	}

}
