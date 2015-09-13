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
 * Core class of BungeeBridgeS (Bungeecord), which provides some useful static methods.
 * Keep in mind you always have to use the same version of BungeeBridgeS (Bungeecord) and BungeeBridgeC (Spigot)!
 * Please see the <a href="http://www.spigotmc.org/resources/bungeebridge.5820/">project Page</a> for additional information.
 */
public class BungeeBridgeS extends Plugin {		
	
	/** The static instance of BungeeBridgeS. */
	protected static BungeeBridgeS instance = null;
	
	/** The version of the config. Obtained from the config. */
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
	 * Gets the version of BungeeBridgeS.
	 *
	 * @return The version of BungeeBridgeS.
	 */
	public static int getVersion() {
		return Integer.valueOf(instance.getDescription().getVersion().replace(".", ""));
	}
	
	/**
	 * Gets the static singleton instance of BungeeBridgeS.
	 * This instance should <b>not</b> be used to register Schedulers, Listeners or CommandExecutors.
	 *
	 * @return The singleton instance of BungeeBridgeS.
	 */
	public static BungeeBridgeS getInstance() {
		return instance;
	}

	/**
	 * Gets the port.
	 *
	 * @return The port.
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
	 * Gets the password BungeeBridgeC has to use to authenticate.
	 *
	 * @return The password BungeeBridgeC has to use to authenticate.
	 */
	public static String getPass() {
		return pass;
	}
}
