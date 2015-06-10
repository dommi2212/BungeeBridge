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
			BungeeBridgeC.config.set("packetlogger", true);
			ConsolePrinter.print("Added 1 option!");
			ConsolePrinter.print("Done!");
		} else {
			BungeeBridgeC.configversion = BungeeBridgeC.config.getInt("configversion");
			if(BungeeBridgeC.configversion < CURRENTVERSION) {
				ConsolePrinter.print("Your config is outdated! Running updater...");
				if(BungeeBridgeC.configversion <= 140) {
					BungeeBridgeC.config.set("packetlogger", true);
					ConsolePrinter.print("Added 1 option!");
				}
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
