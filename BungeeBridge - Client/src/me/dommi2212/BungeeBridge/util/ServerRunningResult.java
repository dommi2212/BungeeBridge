package me.dommi2212.BungeeBridge.util;

import java.io.Serializable;

/**
 * Represents the result of a PacketServerRunning. It contains important informations for the server.
 */
@SuppressWarnings("serial")
public class ServerRunningResult implements Serializable {
	
	private String bungeename;
	private int version;
	private long time;
	private int errorCode;
	
	/**
	 * Instantiates a new ServerRunningResult.
	 *
	 * @param bungeename bungeename
	 * @param version version
	 * @param time time
	 * @param errorCode Problems, that have occurred whilst processing the packet. (0 = OK; 1 = Invalid version; 2 = Unknown server)
	 * 
	 */
	public ServerRunningResult(String bungeename, int version, long time, int errorCode) {
		this.bungeename = bungeename;
		this.version = version;
		this.time = time;
		this.errorCode = errorCode;
	}
	
	/**
	 * Gets the bungeename.
	 *
	 * @return bungeename
	 */
	public String getBungeename() {
		return  bungeename;
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
	 * Gets the time.
	 *
	 * @return time
	 */
	public long getTime() {
		return time;
	}
	
	/**
	 * Gets the error-code.
	 *
	 * @return errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

}
