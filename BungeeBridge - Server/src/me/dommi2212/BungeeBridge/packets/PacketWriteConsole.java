package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;
import java.util.logging.Level;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to write a message to the console.
 */
@SuppressWarnings("serial")
public class PacketWriteConsole extends BungeePacket implements Serializable {
	
	private String message;
	private Level level;
	
	/**
	 * Instantiates a new PacketWriteConsole.
	 *
	 * @param message message
	 */
	public PacketWriteConsole(String message) {
		super(BungeePacketType.WRITECONSOLE, false);
		this.message = message;
		this.level = Level.INFO;
	}
	
	/**
	 * Instantiates a new PacketWriteConsole.
	 *
	 * @param message message
	 * @param level level
	 */
	public PacketWriteConsole(String message, Level level) {
		super(BungeePacketType.WRITECONSOLE, false);
		this.message = message;
		this.level = level;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Gets the level.
	 *
	 * @return level
	 */
	public Level getLevel() {
		return level;
	}
}
