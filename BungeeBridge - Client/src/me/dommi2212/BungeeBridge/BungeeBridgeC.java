/* The MIT License (MIT)
 * 
 * Copyright (c) 2015 dommi2212 (https://github.com/dommi2212/)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE. 
 */
package me.dommi2212.BungeeBridge;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.dommi2212.BungeeBridge.events.listeners.ListenerChat;
import me.dommi2212.BungeeBridge.events.listeners.ListenerCommand;
import me.dommi2212.BungeeBridge.packets.PacketKeepAlive;
import me.dommi2212.BungeeBridge.packets.PacketServerRunning;
import me.dommi2212.BungeeBridge.packets.PacketServerStopping;
import me.dommi2212.BungeeBridge.util.ServerRunningResult;

/**
 * Core class of BungeeBridgeC (Spigot), which provides some useful static methods.
 * Keep in mind you always have to use the same version of BungeeBridgeS (Bungeecord) and BungeeBridgeC (Spigot)!
 * Please see the <a href="http://www.spigotmc.org/resources/bungeebridge.5820/">project Page</a> for additional information.
 */
public class BungeeBridgeC extends JavaPlugin {		
	
	/** The static instance of BungeeBridgeC. */
	protected static BungeeBridgeC instance = null;
	
	/** The unique bungeename of this server. Obtained by sending PacketServerRunning. */
	protected static String bungeename = "";

	/** The version of the config. Obtained from the config. */
	protected static int configversion;
	
	/** The host to send packets to. Obtained from the config. */
	protected static String host;	
	
	/** The port to send packets to. Obtained from the config. */
	protected static int port;
	
	/** The SecurityMode of this client. Obtained from the config. */
	protected static SecurityMode secmode;
	
	/** The password used to secure packets. Obtained from the config. */
	protected static String pass;
	
	/** The interval in seconds to send PacketKeepAlives automatically. Obtained from the config. */
	protected static int updateinterval;
	
	/** Whether packetlogger is enabled or not. Obtained from the config. */
	protected static boolean loggerenabled;
	
	/** Whether AsyncPlayerChatEvent should be passed to Bungeecord. Obtained from the config. */
	protected static boolean notifybungeeChat;
	
	/** Whether PlayerCommandPreprocessEvent should be passed to Bungeecord. Obtained from the config. */
	protected static boolean notifybungeeCommand;

	/** The File of the config. */
	protected static File configfile;
	
	/** The FileConfiguration of the config. */
	protected static FileConfiguration config;
	
	@Override
	public void onEnable() {
		ConsolePrinter.print("Starting BungeeBridgeC... Keep in mind you always have to use the same version of BungeeBridgeS(Bungeecord) and BungeeBridgeC(Spigot)!");
		BungeeBridgeC.instance = this;
		enable();
		
		this.getCommand("packetmanager").setExecutor(new CommandPacketManager());	
		registerListeners();
		
		ConsolePrinter.print("Port: " + port);
		ConsolePrinter.print("SecurityMode: " + secmode);
		
		long sended = System.currentTimeMillis();
		PacketServerRunning startpacket = new PacketServerRunning(Bukkit.getServerName(), Bukkit.getMotd(), Bukkit.getPort(), updateinterval, getVersion(), Bukkit.getMaxPlayers());
		ServerRunningResult result = (ServerRunningResult) startpacket.send();
		
		if(result.getErrorCode() == 0) {
			bungeename = result.getBungeename();
			ConsolePrinter.print("Connected! Server ---[" + (result.getTime() - sended) + "ms]---> Bungee ---[" + (System.currentTimeMillis() - result.getTime()) + "ms]---> Server");
			Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
				@Override
				public void run() {
					int errorCode = (int) new PacketKeepAlive(bungeename, true, Bukkit.getMotd()).send();
					if(errorCode != 0) {
						fixKeepAlive(errorCode);
					}
				}		
			}, updateinterval * 20L, updateinterval * 20L);
		} else {
			handleServerRunningError(result.getErrorCode());
		}
	}
	
	@Override
	public void onDisable() {
		PacketServerStopping stoppacket = new PacketServerStopping(bungeename);
		stoppacket.send();
		BungeeBridgeC.instance = null;		
	}
	
	private void enable() {
		if(!BungeeBridgeC.instance.getDataFolder().exists()) {
			BungeeBridgeC.instance.getDataFolder().mkdir();
		}
		configfile = new File(BungeeBridgeC.instance.getDataFolder().getPath() + File.separator + "config.yml");
		if(configfile.exists()) ConfigUpdater.update();
		else ConfigManager.createConfig();
		ConfigManager.loadConfig();		
	}
	
	private void registerListeners() {
		if(notifybungeeChat) Bukkit.getPluginManager().registerEvents(new ListenerChat(), this);
		if(notifybungeeCommand) Bukkit.getPluginManager().registerEvents(new ListenerCommand(), this);
	}
	
	private static void fixKeepAlive(int errorCode) {
		ConsolePrinter.warn("An error occurred with code " + errorCode + " whilst sending a PacketKeepAlive! Trying to fix it..."); 
		switch(errorCode) {
		case 1: //Resent PacketServerRunning
			PacketServerRunning fixPacket = new PacketServerRunning(Bukkit.getServerName(), Bukkit.getMotd(), Bukkit.getPort(), updateinterval, getVersion(), Bukkit.getMaxPlayers());
			ServerRunningResult fixResult = (ServerRunningResult) fixPacket.send();
			if(fixResult.getErrorCode() == 0) {
				ConsolePrinter.print("Fixed!");
			} else {
				handleServerRunningError(fixResult.getErrorCode());
			}
			break;
		default: //Should never happen...
			ConsolePrinter.warn("Unknown error!");
			break;
		}
	}
	
	private static void handleServerRunningError(int errorCode) {
		switch (errorCode) {
		case 1: //Invalid version
			ConsolePrinter.err("Your version of BungeeBridgeS(Bungeecord) is incompatible to your version of BungeeBridgeC(Spigot)!\nYou have to update immediately! Shutting down BungeeBridgeClient...");
			break;
		case 2: //Unknown server
			ConsolePrinter.err("This server hasn't been registered in config.yml of Bungeecord! Shutting down BungeeBridgeClient...");
			break;
		default: //Should never happen...
			ConsolePrinter.warn("An unknown error occurred, whilst sending PacketServerRunning! Shutting down BungeeBridgeClient...");
			break;
		}
		Bukkit.getPluginManager().disablePlugin(instance);
	}

	/**
	 * Manually sends a PacketKeepAlive to BungeeBridgeS.
	 * Use this to increase the accuracy of PacketGetMOTDServer after setting the MOTD.
	 * The return-value is just used to signal the result of the operation. Any error is already handled by BungeeBridgeC.
	 * 
	 * @return The error-code, if an error occurred; else 0
	 */
	public static int sendKeepAlive() {
		int errorCode = (int) new PacketKeepAlive(bungeename, false, Bukkit.getMotd()).send();
		if(errorCode != 0) {
			fixKeepAlive(errorCode);
		}
		return errorCode;
	}
	
	/**
	 * Gets the version of BungeeBridgeC.
	 *
	 * @return The version of BungeeBridgeC.
	 */
	public static int getVersion() {
		return Integer.valueOf(instance.getDescription().getVersion().replace(".", ""));
	}
	
	/**
	 * Gets the static singleton instance of BungeeBridgeC.
	 * This instance should <b>not</b> be used to register Schedulers, Listeners or CommandExecutors.
	 *
	 * @return The singleton instance of BungeeBridgeC.
	 */
	public static BungeeBridgeC getInstance() {
		return instance;
	}
	
	/**
	 * Gets the bungeename of this server.
	 *
	 * @return The bungeename of this server.
	 */
	public static String getBungeename() {
		return bungeename;
	}

	/**
	 * Gets the host of BungeeBridgeS.
	 *
	 * @return The host of BungeeBridgeS.
	 */
	public static String getHost() {
		return host;
	}

	/**
	 * Gets the port of BungeeBridgeS.
	 *
	 * @return The port of BungeeBridgeS.
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * Gets the security mode.
	 *
	 * @return The security mode.
	 */
	public static SecurityMode getSecurityMode() {
		return secmode;
	}

	/**
	 * Gets the password used to authenticate with BungeeBridgeS.
	 *
	 * @return The password used to authenticate with BungeeBridgeS.
	 */
	public static String getPass() {
		return pass;
	}

	/**
	 * Gets the interval in seconds to send PacketKeepAlives automatically.
	 *
	 * @return The updateinterval in seconds.
	 * @deprecated as of version 1.6.0! Reason: Misspelled method-name. Use {@link BungeeBridgeC#getUpdateinterval()} instead.
	 */
	@Deprecated
	public static int getUpdateintervall() {
		return updateinterval;
	}
	
	/**
	 * Gets the interval in seconds to send PacketKeepAlives automatically.
	 *
	 * @return The updateinterval in seconds.
	 */
	public static int getUpdateinterval() {
		return updateinterval;
	}

	/**
	 * Checks if the packet-logger is enabled.
	 *
	 * @return true, if the packet-logger is enabled
	 */
	public static boolean isLoggerEnabled() {
		return loggerenabled;
	}
	
}