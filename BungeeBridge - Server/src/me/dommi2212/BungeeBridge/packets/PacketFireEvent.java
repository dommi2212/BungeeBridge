package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;
import me.dommi2212.BungeeBridge.WrappedEvent;

/**
 * Packet used to fire an event on BungeeCord.
 */
@SuppressWarnings("serial")
public class PacketFireEvent extends BungeePacket implements Serializable {
	
	private WrappedEvent event;
	
	/**
	 * Instantiates a new PacketFireEvent.
	 *.
	 * @param event the event to fire.
	 */
	public PacketFireEvent(WrappedEvent event) {
		super(BungeePacketType.FIREEVENT, false);
		this.event = event;
	}
	
	/**
	 * Gets the event.
	 *
	 * @return the event.
	 */
	public WrappedEvent getEvent() {
		return event;
	}
	
}
