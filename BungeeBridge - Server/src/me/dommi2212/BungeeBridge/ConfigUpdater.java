package me.dommi2212.BungeeBridge;

import java.io.IOException;

import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigUpdater {
	
	public static void update() {
		final int CURRENTVERSION = BungeeBridgeS.getVersion();
		try {
			BungeeBridgeS.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeBridgeS.configfile);
			if(BungeeBridgeS.config.get("configversion") == null) {
				//Update Config from 1.3.0 or lower to 1.4.0 or higher
			} else {
				BungeeBridgeS.configversion = BungeeBridgeS.config.getInt("configversion");
				if(BungeeBridgeS.configversion < CURRENTVERSION) {
					ConsolePrinter.print("Your config is outdated! Running updater...");
					/*
					 * if(BungeeBridgeS.configversion == ???) {
					 * 		//Update...
					 * }
					*/
					ConsolePrinter.print("Done!");
				}
			}
			BungeeBridgeS.config.set("configversion", BungeeBridgeS.getVersion());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
