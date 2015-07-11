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
	private int updateinterval;
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
	 * @param updateinterval updateintervll
	 * @param address address
	 * @param slots slots
	 */
	public BungeeServer(String name, String motd, String bungeename, int port, int updateinterval, InetSocketAddress address, int slots) {
		this.name = name;
		this.motd = motd;
		this.bungeename = bungeename;
		this.port = port;
		this.updateinterval = updateinterval;
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
	 * Gets the updateinterval.
	 *
	 * @return updateinterval
	 * @deprecated as of version 1.6.0! Reason: Misspelled method-name. Use {@link #getUpdateInterval()} instead.
	 */
	@Deprecated
	public int getUpdateIntervall() {
		return updateinterval;
	}
	
	/**
	 * Gets the updateinterval.
	 *
	 * @return updateinterval
	 */
	public int getUpdateInterval() {
		return updateinterval;
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
	 * @param motd the motd
	 * @deprecated Deprecated since version 1.5.1! Use {@link BungeeServer#updateData(String)} or {@link BungeeServer#updateData(String, String, int, int, InetSocketAddress, int)} instead!
	 */
	@Deprecated
	public void setMOTD(String motd) {
		this.motd = motd;
	}

	/**
	 * Sets the updateinterval.
	 *
	 * @param updateinterval the updateinterval
	 * @deprecated Deprecated since version 1.5.1! Use {@link BungeeServer#updateData(String)} or {@link BungeeServer#updateData(String, String, int, int, InetSocketAddress, int)} instead!
	 */
	@Deprecated
	public void setUpdateIntervall(int updateinterval) {
		this.updateinterval = updateinterval;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name
	 * @deprecated Deprecated since version 1.5.1! Use {@link BungeeServer#updateData(String)} or {@link BungeeServer#updateData(String, String, int, int, InetSocketAddress, int)} instead!
	 */
	@Deprecated
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the port.
	 *
	 * @param port the port
	 * @deprecated Deprecated since version 1.5.1! Use {@link BungeeServer#updateData(String)} or {@link BungeeServer#updateData(String, String, int, int, InetSocketAddress, int)} instead!
	 */
	@Deprecated
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the address
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
	 * @param updateinterval the updateinterval
	 * @param address the address
	 * @param slots the slots
	 */
	public void updateData(String name, String motd, int port, int updateinterval, InetSocketAddress address, int slots) {
		this.name = name;
		this.motd = motd;
		this.port = port;
		this.updateinterval = updateinterval;
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
	 * @param bungeename the bungeename
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
	 * @param inputaddress the inputaddress
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
