package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to send custom Subjects & Data.
 * 
 * Returns: Answer of your Bungeecord-Plugin
 */
public class PacketCustom extends BungeePacket implements Serializable {
	
	private String channel;
	private Object subject;
	
	/**
	 * Instantiates a new PacketCustomCommand.
	 *
	 * @param channel channel
	 * @param subject subject
	 */
	public PacketCustom(String channel, Object subject) {
		this.channel = channel;
		this.subject = subject;
		this.type = BungeePacketType.CUSTOM;
		this.shouldanswer = true;
	}
	
	/**
	 * Gets the channel.
	 *
	 * @return channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * Gets the subject.
	 *
	 * @return subject
	 */
	public Object getSubject() {
		return subject;
	}
}
