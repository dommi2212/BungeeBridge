package me.dommi2212.BungeeBridge;

import java.io.File;

import me.dommi2212.BungeeBridge.packets.PacketKeepAlive;
import me.dommi2212.BungeeBridge.packets.PacketServerRunning;
import me.dommi2212.BungeeBridge.packets.PacketServerStopping;
import me.dommi2212.BungeeBridge.util.ServerRunningResult;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Core class of BungeeBridgeC(Spigot).
 * Keep in mind you always have to use the same version of BungeeBridgeS(Bungeecord) and BungeeBridgeC(Spigot)!
 * http://www.spigotmc.org/resources/bungeebridge.5820/
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
	
	/** The timeintervall to send PacketKeepAlive(s). */
	protected static int updateintervall;
	
	/** Displays whether packetlogger is enabled or not. Obtained from the config. */
	protected static boolean loggerenabled;

	/** The File of the config. */
	protected static File configfile;
	
	/** The FileConfiguration of the config. */
	protected static FileConfiguration config;

	@Override
	public void onEnable() {
		BungeeBridgeC.instance = this;
		BungeeBridgeC.enable();
		
		this.getCommand("packetmanager").setExecutor(new CommandPacketManager());
		
		ConsolePrinter.print("Starting BungeeBridgeC... Keep in mind you always have to use the same version of BungeeBridgeS(Bungeecord) and BungeeBridgeC(Spigot)!");
		ConsolePrinter.print("Port: " + port);
		ConsolePrinter.print("SecurityMode: " + secmode);
		
		long sended = System.currentTimeMillis();
		PacketServerRunning startpacket = new PacketServerRunning(Bukkit.getServerName(), Bukkit.getMotd(), Bukkit.getPort(), updateintervall, getVersion());
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
					new PacketKeepAlive(bungeename, true, Bukkit.getMotd()).send();
				}		
			}, updateintervall * 20L, updateintervall * 20L);
		}

	}
	
	@Override
	public void onDisable() {
		PacketServerStopping stoppacket = new PacketServerStopping(bungeename);
		stoppacket.send();
		BungeeBridgeC.instance = null;		
	}
	
	private static void enable() {
		if(!BungeeBridgeC.instance.getDataFolder().exists()) {
			BungeeBridgeC.instance.getDataFolder().mkdir();
		}
		configfile = new File(BungeeBridgeC.instance.getDataFolder().getPath() + File.separator + "config.yml");
		if(configfile.exists()) {
			ConfigUpdater.update();
		} else {
			ConfigManager.createConfig();
		}
		ConfigManager.loadConfig();
		
	}
	
	public static void sendKeepAlive() {
		new PacketKeepAlive(bungeename, false, Bukkit.getMotd());
	}
	
	public static int getVersion() {
		return Integer.valueOf(instance.getDescription().getVersion().replace(".", ""));
	}
	
	public static BungeeBridgeC getInstance() {
		return instance;
	}
	
	public static String getBungeename() {
		return bungeename;
	}

	public static String getHost() {
		return host;
	}

	public static int getPort() {
		return port;
	}

	public static SecurityMode getSecurityMode() {
		return secmode;
	}

	public static String getPass() {
		return pass;
	}

	public static int getUpdateintervall() {
		return updateintervall;
	}

	public static boolean isLoggerEnabled() {
		return loggerenabled;
	}
	
}