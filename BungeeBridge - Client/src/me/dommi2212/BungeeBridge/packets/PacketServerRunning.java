package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used to indicate that a server started up. Is only sent on Server start/reload.
 * ONLY USE THIS PACKET IF YOU KNOW WHAT YOU DO!
 * (Not mentioned on the page)
 */
@SuppressWarnings("serial")
public class PacketServerRunning extends BungeePacket implements Serializable {

	private String name;
	private String motd;
	private int port;
	private int updateinterval;
	private int version;
	private int slots;
	
	/**
	 * Instantiates a new PacketServerRunning.
	 *
	 * @param name the name of the server
	 * @param motd the motd of the server
	 * @param port the port of the server
	 * @param updateinterval the updateinterval
	 * @param version the version of BungeeBridgeC
	 * @param slots the slots of the server
	 */
	public PacketServerRunning(String name, String motd, int port, int updateinterval, int version, int slots) {
		super(BungeePacketType.SERVERRUNNING, true);
		this.name = name;
		this.motd = motd;
		this.port = port;
		this.updateinterval = updateinterval;
		this.version = version;
		this.slots = slots;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the MOTD.
	 *
	 * @return motd
	 */
	public String getMOTD() {
		return motd;
	}
	
	/**
	 * Gets the port.
	 *
	 * @return port
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * Gets the UpdateInterval.
	 *
	 * @return updateinterval
	 * @deprecated as of version 1.6.0! Reason: Misspelled method-name. Use {@link #getUpdateInterval()} instead.
	 */
	@Deprecated
	public int getUpdateIntervall() {
		return updateinterval;
	}
	
	/**
	 * Gets the UpdateInterval.
	 *
	 * @return updateinterval
	 */
	public int getUpdateInterval() {
		return updateinterval;
	}
	
	/**
	 * Gets the version.
	 *
	 * @return version
	 */
	public int getVersion() {
		return version;
	}
	
	/**
	 * Gets the slots.
	 *
	 * @return slots
	 */
	public int getSlots() {
		return slots;
	}


}
