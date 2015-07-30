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
 * Core class of BungeeBridgeC (Spigot).
 * Keep in mind you always have to use the same version of BungeeBridgeS (Bungeecord) and BungeeBridgeC (Spigot)!
 * Please see the <a href="http://www.spigotmc.org/resources/bungeebridge.5820/">project Page</a> for additional information.
 */
public class BungeeBridgeC extends JavaPlugin {		
	
	/** The static instance of BungeeBridgeC. */
	protected static BungeeBridgeC instance = null;
	
	/** Displays the unique bungeename of this server. Obtained by sending PacketServerRunning. */
	protected static String bungeename = "";

	/** The version of the config. */
	protected static int configversion;
	
	/** The host to send packets to. Obtained from the config. */
	protected static String host;	
	
	/** The port to send packets to. Obtained from the config. */
	protected static int port;
	
	/** Displays the SecurityMode of the Server. .Obtained from the config. */
	protected static SecurityMode secmode;
	
	/** The password used to secure packets. Obtained from the config. */
	protected static String pass;
	
	/** The interval in seconds to send PacketKeepAlives automatically. */
	protected static int updateinterval;
	
	/** Displays whether packetlogger is enabled or not. Obtained from the config. */
	protected static boolean loggerenabled;
	
	/** Displays whether AsyncPlayerChatEvent should be passed to Bungee. Obtained from the config. */
	protected static boolean notifybungeeChat;
	
	/** Displays whether PlayerCommandPreprocessEvent should be passed to Bungee. Obtained from the config. */
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
		
		if(result.getVersion() != getVersion()) {
			ConsolePrinter.err("Your version of BungeeBridgeS(Bungeecord) is incompatible to your version of BungeeBridgeC(Spigot)!\nYou have to update immediately!");
			Bukkit.getPluginManager().disablePlugin(instance);
		} else {
			bungeename = result.getBungeename();
			ConsolePrinter.print("Connected! Server ---[" + (result.getTime() - sended) + "ms]---> Bungee ---[" + (System.currentTimeMillis() - result.getTime()) + "ms]---> Server");
			Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					int error = (int) new PacketKeepAlive(bungeename, true, Bukkit.getMotd()).send();
					if(error != 0) {
						fixKeepAlive(error);
					}
				}		
			}, updateinterval * 20L, updateinterval * 20L);
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
	
	private void fixKeepAlive(int error) {
		ConsolePrinter.warn("An error occurred with code " + error + " whilst sending a PacketKeepAlive! Trying to fix it..."); 
		switch(error) {
		case 1:
			PacketServerRunning fixpacket = new PacketServerRunning(Bukkit.getServerName(), Bukkit.getMotd(), Bukkit.getPort(), updateinterval, getVersion(), Bukkit.getMaxPlayers());
			ServerRunningResult fixresult = (ServerRunningResult) fixpacket.send();
			
			if(fixresult.getVersion() != getVersion()) {
				ConsolePrinter.err("Your version of BungeeBridgeS(Bungeecord) is incompatible to your version of BungeeBridgeC(Spigot)!\nYou have to update immediately!");
				Bukkit.getPluginManager().disablePlugin(instance);
			} else {
				ConsolePrinter.print("Fixed!");
			}
			break;
		default:
			ConsolePrinter.warn("Unknown error!"); //Should never happen...
			break;
		}
		
	}

	/**
	 * Manually sends a PacketKeepAlive to BungeeBridgeS.
	 * Use this to increase the accuracy of PacketGetMOTDServer after setting the MOTD.
	 * 
	 * @return 0, if no error occurs; 1, if you have to resent a PacketServerRunning
	 */
	public static int sendKeepAlive() {
		return (int) new PacketKeepAlive(bungeename, false, Bukkit.getMotd()).send();
	}
	/**
	 * Gets the local version of BungeeBridgeC.
	 *
	 * @return the version
	 */
	public static int getVersion() {
		return Integer.valueOf(instance.getDescription().getVersion().replace(".", ""));
	}
	
	/**
	 * Gets the single instance of BungeeBridgeC.
	 *
	 * @return single instance of BungeeBridgeC
	 */
	public static BungeeBridgeC getInstance() {
		return instance;
	}
	
	/**
	 * Gets the bungeename of this server.
	 *
	 * @return the bungeename
	 */
	public static String getBungeename() {
		return bungeename;
	}

	/**
	 * Gets the host of BungeeBridgeS.
	 *
	 * @return the host
	 */
	public static String getHost() {
		return host;
	}

	/**
	 * Gets the port of BungeeBridgeS.
	 *
	 * @return the port
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * Gets the security mode.
	 *
	 * @return the security mode
	 */
	public static SecurityMode getSecurityMode() {
		return secmode;
	}

	/**
	 * Gets the password.
	 *
	 * @return the pass
	 */
	public static String getPass() {
		return pass;
	}

	/**
	 * Gets the interval in seconds to send PacketKeepAlives automatically.
	 *
	 * @return the updateinterval.
	 * @deprecated as of version 1.6.0! Reason: Misspelled method-name. Use {@link BungeeBridgeC#getUpdateinterval()} instead.
	 */
	@Deprecated
	public static int getUpdateintervall() {
		return updateinterval;
	}
	
	/**
	 * Gets the interval in seconds to send PacketKeepAlives automatically.
	 *
	 * @return the updateinterval.
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