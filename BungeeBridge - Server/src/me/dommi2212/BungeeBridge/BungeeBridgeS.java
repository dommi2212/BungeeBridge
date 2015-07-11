package me.dommi2212.BungeeBridge;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import me.dommi2212.BungeeBridge.events.wrapped.WrappedAsyncPlayerChatEvent;
import me.dommi2212.BungeeBridge.events.wrapped.WrappedPlayerCommandPreprocessEvent;
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
	protected static BungeeBridgeS instance = null;
	
	/** The version of the config. */
	protected static int configversion;
	
	/** The port of BungeeBridgeS. Obtained from the config. */
	protected static int port;
	
	/** Displays the SecurityMode of the Server. Obtained from the config. */
	protected static SecurityMode secmode;
	
	/** The password used to secure packets. Obtained from the config. */
	protected static String pass;
	
	/** The File of the config. */
	protected static File configfile;
	
	/** The Configuration of the config. */
	protected static Configuration config;
	
	/** The ServerSocket, that is used to communicate with Spigot. */
	private static ServerSocket server;
	
	/** Whether BungeeBridgeS is enabled. */
	private static boolean enabled;
	
	@Override
	public void onEnable() {
		ConsolePrinter.print("Enabled BungeeBridgeS! Keep in mind you always have to use the same version of BungeeBridgeS(Bungeecord) and BungeeBridgeC(Spigot)!");
		BungeeBridgeS.instance = this;
		BungeeBridgeS.enable();
		ConsolePrinter.print("Binding to Port " + port + "!");
		ConsolePrinter.print("SecurityMode: " + secmode);
		
		BungeeCord.getInstance().getScheduler().runAsync(this, new Runnable() {
			@Override
			public void run() {
				Socket client = null;
				try {
					server = new ServerSocket(port);
					while(enabled) {
						try {
							client = server.accept();
							new Thread(new ClientRunnable(client)).start();
						} catch(SocketException e) {
							if(enabled) e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
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
		enabled = false;
		PacketFireEventHandler.unregisterAll(instance);
		BungeeBridgeS.instance = null;
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Enable BungeeBridgeS.
	 */
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
		
		PacketFireEventHandler.registerEvent(instance, WrappedAsyncPlayerChatEvent.class);
		PacketFireEventHandler.registerEvent(instance, WrappedPlayerCommandPreprocessEvent.class);
		
		enabled = true;
	}
	
	/**
	 * Gets the version.
	 *
	 * @return version
	 */
	public static int getVersion() {
		return Integer.valueOf(instance.getDescription().getVersion().replace(".", ""));
	}
	
	/**
	 * Gets the static instance of BungeeBridgeS.
	 *
	 * @return static instance of BungeeBridgeS
	 */
	public static BungeeBridgeS getInstance() {
		return instance;
	}

	/**
	 * Gets the port.
	 *
	 * @return port
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * Gets the SecurityMode.
	 *
	 * @return mode
	 */
	public static SecurityMode getSecurityMode() {
		return secmode;
	}

	/**
	 * Gets the pass.
	 *
	 * @return pass
	 */
	public static String getPass() {
		return pass;
	}
}
