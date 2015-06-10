package me.dommi2212.BungeeBridge;

import java.io.IOException;
import java.util.UUID;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * Manages the Config.
 */
public class ConfigManager {

	protected static void createConfig() {
		try {
			BungeeBridgeS.configfile.createNewFile();
			Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(BungeeBridgeS.configfile);
			String pass = UUID.randomUUID().toString().replace("-", "");
			config.set("port", 7331);
			config.set("securitymode", "OFF");
			config.set("pass", pass.substring(pass.length()-10, pass.length()));
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, BungeeBridgeS.configfile);
			BungeeBridgeS.config = config;
			loadConfig();
		} catch (IOException e) {
			ConsolePrinter.err("Failed to load/create config.yml!");
			e.printStackTrace();
		}
	}
	
	protected static void loadConfig() {
		BungeeBridgeS.port = BungeeBridgeS.config.getInt("port");
		BungeeBridgeS.secmode = SecurityMode.valueOf(BungeeBridgeS.config.getString("securitymode").toUpperCase());
		BungeeBridgeS.pass = BungeeBridgeS.config.getString("pass");
	}
	
}
