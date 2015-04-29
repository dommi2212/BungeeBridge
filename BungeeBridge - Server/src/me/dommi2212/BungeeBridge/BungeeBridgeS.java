package me.dommi2212.BungeeBridge;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

/**
 * Core class of BungeeBridgeC(Spigot).
 * Keep in mind you always have to use the same version of BungeeBridgeS(Bungeecord) and BungeeBridgeC(Spigot)!
 * http://www.spigotmc.org/resources/bungeebridge.5820/
 */
public class BungeeBridgeS extends Plugin {		
	
	/** The static instance of BungeeBridgeS. */
	protected static Plugin instance = null;
	
	/** The version of the config. */
	protected static int CONFIGVERSION;
	
	/** The port of BungeeBridgeS. Obtained from the config. */
	protected static int PORT;
	
	/** Displays the SecurityMode of the Server. Obtained from the config. */
	protected static SecurityMode SECMODE;
	
	/** The password used to secure packets. Obtained from the config. */
	protected static String PASS;
	
	/** The File of the config. */
	protected static File configfile;
	
	/** The Configuration of the config. */
	protected static Configuration config;
	
	@SuppressWarnings("unused")
	private static ServerSocket server;
	
	@Override
	public void onEnable() {
		BungeeBridgeS.instance = this;
		BungeeBridgeS.enable();
		ConsolePrinter.print("Enabled BungeeBridgeS! Keep in mind you always have to use the same version of BungeeBridgeS(Bungeecord) and BungeeBridgeC(Spigot)!");
		ConsolePrinter.print("Binding to Port " + PORT + "!");
		ConsolePrinter.print("SecurityMode: " + SECMODE);
		
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
		if(!BungeeBridgeS.instance.getDataFolder().exists()) {
			BungeeBridgeS.instance.getDataFolder().mkdir();
		}
		configfile = new File(BungeeBridgeS.instance.getDataFolder().getPath() + File.separator + "config.yml");
		if(configfile.exists()) {
			ConfigUpdater.update();
		} else {
			ConfigManager.createConfig();
		}
		ConfigManager.loadConfig();
	}
	
	public static int getVersion() {
		return Integer.valueOf(instance.getDescription().getVersion().replace(".", ""));
	}
}
