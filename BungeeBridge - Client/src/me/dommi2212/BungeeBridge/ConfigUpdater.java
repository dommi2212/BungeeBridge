package me.dommi2212.BungeeBridge;

import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Updates the config from an older version.
 */
public class ConfigUpdater {
	
	/**
	 * Update a config.
	 */
	protected static void update() {
		final int CURRENTVERSION = BungeeBridgeC.getVersion();
		BungeeBridgeC.config = YamlConfiguration.loadConfiguration(BungeeBridgeC.configfile);
		if(BungeeBridgeC.config.get("configversion") == null) {
			//Update Config from 1.3.0 or lower
			ConsolePrinter.print("Your config is outdated! Running updater...");
			int oldInterval = BungeeBridgeC.config.getInt("updateintervall");
			BungeeBridgeC.config.set("updateintervall", null);
			BungeeBridgeC.config.set("updateinterval", oldInterval);
			
			BungeeBridgeC.config.set("packetlogger", true);
			BungeeBridgeC.config.set("notify-bungee.chat", true);
			BungeeBridgeC.config.set("notify-bungee.command", true);
			ConsolePrinter.print("Added 2 option(s)!");
			ConsolePrinter.print("Done!");
		} else {
			BungeeBridgeC.configversion = BungeeBridgeC.config.getInt("configversion");
			if(BungeeBridgeC.configversion < CURRENTVERSION) {
				ConsolePrinter.print("Your config is outdated! Running updater...");
				int added = 0;
				if(BungeeBridgeC.configversion <= 140) {
					BungeeBridgeC.config.set("packetlogger", true);
					added++;
				}
				if(BungeeBridgeC.configversion <= 151) {
					int oldInterval = BungeeBridgeC.config.getInt("updateintervall");
					BungeeBridgeC.config.set("updateintervall", null);
					BungeeBridgeC.config.set("updateinterval", oldInterval);
					
					BungeeBridgeC.config.set("notify-bungee.chat", true);
					BungeeBridgeC.config.set("notify-bungee.command", true);
					added = added + 2;
				}
				ConsolePrinter.print("Added " + added + " option(s)!");
				ConsolePrinter.print("Done!");
			}
		}
		BungeeBridgeC.config.set("configversion", BungeeBridgeC.getVersion());
		try {
			BungeeBridgeC.config.save(BungeeBridgeC.configfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
