package me.dommi2212.BungeeBridge;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Spigot-Server.
 */
public class BungeeServer {

	private String name;
	private String motd;
	private String bungeename;
	private int port;
	private int updateintervall;
	private InetSocketAddress address;
	private int slots;
	protected static List<BungeeServer> servers = new ArrayList<BungeeServer>();
	
	/**
	 * Instantiates a new BungeeServer.
	 *
	 * @param name name
	 * @param motd motd
	 * @param bungeename bungeename
	 * @param port port
	 * @param updateintervall updateintervall
	 * @param address address
	 * @param slots slots
	 */
	public BungeeServer(String name, String motd, String bungeename, int port, int updateintervall, InetSocketAddress address, int slots) {
		this.name = name;
		this.motd = motd;
		this.bungeename = bungeename;
		this.port = port;
		this.updateintervall = updateintervall;
		this.address = address;
		this.slots = slots;
		servers.add(this);
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
	 * Gets the motd.
	 *
	 * @return motd
	 */
	public String getMOTD() {
		return motd;
	}

	/**
	 * Gets the bungeename.
	 *
	 * @return bungeename
	 */
	public String getBungeename() {
		return bungeename;
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
	 * Gets the updateintervall.
	 *
	 * @return updateintervall
	 */
	public int getUpdateIntervall() {
		return updateintervall;
	}
	
	/**
	 * Gets the address.
	 *
	 * @return address
	 */
	public InetSocketAddress getAddress() {
		return address;
	}	
	
	/**
	 * Gets the slots.
	 *
	 * @return slots
	 */
	public int getSlots() {
		return slots;
	}
	
	/**
	 * Sets the motd.
	 *
	 * @param motd
	 * @deprecated Deprecated since version 1.5.1! Use {@link BungeeServer#updateData(String)} or {@link BungeeServer#updateData(String, String, int, int, InetSocketAddress, int)} instead!
	 */
	@Deprecated
	public void setMOTD(String motd) {
		this.motd = motd;
	}

	/**
	 * Sets the updateintervall.
	 *
	 * @param updateintervall
	 * @deprecated Deprecated since version 1.5.1! Use {@link BungeeServer#updateData(String)} or {@link BungeeServer#updateData(String, String, int, int, InetSocketAddress, int)} instead!
	 */
	@Deprecated
	public void setUpdateIntervall(int updateintervall) {
		this.updateintervall = updateintervall;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 * @deprecated Deprecated since version 1.5.1! Use {@link BungeeServer#updateData(String)} or {@link BungeeServer#updateData(String, String, int, int, InetSocketAddress, int)} instead!
	 */
	@Deprecated
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the port.
	 *
	 * @param port
	 * @deprecated Deprecated since version 1.5.1! Use {@link BungeeServer#updateData(String)} or {@link BungeeServer#updateData(String, String, int, int, InetSocketAddress, int)} instead!
	 */
	@Deprecated
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Sets the address.
	 *
	 * @param address
* @deprecated Deprecated since version 1.5.1! Use {@link BungeeServer#updateData(String)} or {@link BungeeServer#updateData(String, String, int, int, InetSocketAddress, int)} instead!
	 */
	@Deprecated
	public void setAddress(InetSocketAddress address) {
		this.address = address;
	}
	
	/**
	 * Sets the slots.
	 *
	 * @param slots the new slots
	 * @deprecated Deprecated since version 1.5.1! Use {@link BungeeServer#updateData(String)} or {@link BungeeServer#updateData(String, String, int, int, InetSocketAddress, int)} instead!
	 */
	@Deprecated
	public void setSlots(int slots) {
		this.slots = slots;
	}
	
	/**
	 * Updates the data of a server after receiving PacketServerRunning.
	 *
	 * @param name the name
	 * @param motd the motd
	 * @param port the port
	 * @param updateintervall the updateintervall
	 * @param address the address
	 * @param slots the slots
	 */
	public void updateData(String name, String motd, int port, int updateintervall, InetSocketAddress address, int slots) {
		this.name = name;
		this.motd = motd;
		this.port = port;
		this.updateintervall = updateintervall;
		this.address = address;
		this.slots = slots;
	}
	
	/**
	 * Updates the data of a server after receiving PacketKeepAlive.
	 *
	 * @param motd the motd
	 */
	public void updateData(String motd) {
		this.motd = motd;
	}
	
	/**
	 * Gets a BungeeServer by it's bungeename.
	 *
	 * @return server; null if no server is found
	 */
	public static BungeeServer getByBungeename(String bungeename) {
		BungeeServer result = null;
		for(BungeeServer server : servers) {
			if(server.getBungeename().equals(bungeename)) result = server;
		}
		return result;
	}
	
	/**
	 * Gets a BungeeServer by it's address.
	 *
	 * @return server; null if no server is found
	 */
	public static BungeeServer getByAddress(InetSocketAddress inputaddress) {
		BungeeServer result = null;
		if(!servers.isEmpty()) {
			for(BungeeServer server : servers) {
				if(server.getAddress().equals(inputaddress)) result = server;
			}
		}
		return result;
	}
	
	public static void remove(BungeeServer server) {
		servers.remove(server);
		ServerWatcher.remove(server);
	}
	

}
