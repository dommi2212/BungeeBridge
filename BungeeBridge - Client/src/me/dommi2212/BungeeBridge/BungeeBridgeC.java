package me.dommi2212.BungeeBridge;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.dommi2212.BungeeBridge.packets.PacketKeepAlive;
import me.dommi2212.BungeeBridge.packets.PacketServerRunning;
import me.dommi2212.BungeeBridge.packets.PacketServerStopping;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Core class of BungeeBridgeC(Spigot).
 * Keep in mind you always have to use the same version of BungeeBridgeS(Bungeecord) and BungeeBridgeC(Spigot)!
 * http://www.spigotmc.org/resources/bungeebridge.5820/
 */
public class BungeeBridgeC extends JavaPlugin {		
	public static final String prefix = "[BungeeBridgeC] ";
	public static Logger logger = Bukkit.getLogger();
	public static JavaPlugin instance = null;
	public static String BUNGEENAME = "";

	public static String HOST;	
	public static int PORT;
	public static SecurityMode SECMODE;
	public static String PASS;
	public static int UPDATEINTERVALL;

	@Override
	public void onEnable() {
		BungeeBridgeC.instance = this;
		BungeeBridgeC.enable();
		logger.log(Level.INFO, prefix + "Enabled BungeeBridgeC! Keep in mind you always have to use the same version of BungeeBridgeS(Bungeecord) and BungeeBridgeC(Spigot)!");
		logger.log(Level.INFO, prefix + "Port: " + PORT);
		logger.log(Level.INFO, prefix + "SecurityMode: " + SECMODE);
		
		PacketServerRunning startpacket = new PacketServerRunning(Bukkit.getServerName(), Bukkit.getMotd(), Bukkit.getPort(), UPDATEINTERVALL);
		BUNGEENAME = (String) startpacket.send();
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				new PacketKeepAlive(BUNGEENAME, true, Bukkit.getMotd()).send();
			}		
		}, UPDATEINTERVALL * 20L, UPDATEINTERVALL * 20L);
	}
	
	@Override
	public void onDisable() {
		PacketServerStopping stoppacket = new PacketServerStopping(BUNGEENAME);
		stoppacket.send();
		BungeeBridgeC.instance = null;		
	}
	
	private static void enable() {
		if(!BungeeBridgeC.instance.getDataFolder().exists()) {
			BungeeBridgeC.instance.getDataFolder().mkdir();
		}
		File file = new File(BungeeBridgeC.instance.getDataFolder().getPath() + File.separator + "config.yml");
		if(!file.exists()) {
			ConfigManager.createConfig(file);
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigManager.loadConfig(config);	
	}
	
	public static void sendKeepAlive() {
		new PacketKeepAlive(BUNGEENAME, false, Bukkit.getMotd());
	}

}
