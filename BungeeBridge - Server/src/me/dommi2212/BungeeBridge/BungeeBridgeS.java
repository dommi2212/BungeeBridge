package me.dommi2212.BungeeBridge;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * Core class of BungeeBridgeC(Spigot).
 * Keep in mind you always have to use the same version of BungeeBridgeS(Bungeecord) and BungeeBridgeC(Spigot)!
 * http://www.spigotmc.org/resources/bungeebridge.5820/
 */
public class BungeeBridgeS extends Plugin {		
	public static final String prefix = "[BungeeBridgeS] ";
	@SuppressWarnings("unused")
	private static ServerSocket server;
	public static Logger logger = BungeeCord.getInstance().getLogger();
	public static Plugin instance = null;
	
	public static int PORT;
	public static SecurityMode SECMODE;
	public static String PASS;
	
	@Override
	public void onEnable() {
		BungeeBridgeS.instance = this;
		BungeeBridgeS.enable();
		logger.log(Level.INFO, prefix + "Enabled BungeeBridgeS! Keep in mind you always have to use the same version of BungeeBridgeS(Bungeecord) and BungeeBridgeC(Spigot)!");
		logger.log(Level.INFO, prefix + "Binding to Port " + PORT + "!");
		logger.log(Level.INFO, prefix + "SecurityMode: " + SECMODE);
		
		BungeeCord.getInstance().getScheduler().runAsync(this, new Runnable() {
			@Override
			public void run() {
				ServerSocket server = null;
				Socket client = null;
				try {
					server = new ServerSocket(PORT);
					while(true) {
						client = server.accept();
						new Thread(new ClientRunnable(client)).start();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		});
		
		ServerWatcher.start();
	}
	
	@Override
	public void onDisable() {
		BungeeBridgeS.instance = null;
	}

	private static void enable() {
		try {
			if(!BungeeBridgeS.instance.getDataFolder().exists()) {
				BungeeBridgeS.instance.getDataFolder().mkdir();
			}
			File file = new File(BungeeBridgeS.instance.getDataFolder().getPath() + File.separator + "config.yml");
			if(!file.exists()) {
				ConfigManager.createConfig(file);
			}
			Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			ConfigManager.loadConfig(config);
		} catch (IOException e) {
			BungeeBridgeS.logger.log(Level.SEVERE, "Failed to load/create config.yml!");
			e.printStackTrace();
		}	
	}
}
