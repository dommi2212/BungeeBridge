package me.dommi2212.BungeeBridge;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * Manages the Config.
 */
public class ConfigManager {

	protected static void createConfig(File file) {
		try {
			file.createNewFile();
			Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			String pass = UUID.randomUUID().toString().replace("-", "");
			config.set("port", 7331);
			config.set("securitymode", "OFF");
			config.set("pass", pass.substring(pass.length()-10, pass.length()));
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
		} catch (IOException e) {
			BungeeBridgeS.logger.log(Level.SEVERE, "Failed to load/create config.yml!");
			e.printStackTrace();
		}
	}
	
	protected static void loadConfig(Configuration config) {
		BungeeBridgeS.PORT = config.getInt("port");
		BungeeBridgeS.SECMODE = SecurityMode.valueOf(config.getString("securitymode").toUpperCase());
		BungeeBridgeS.PASS = config.getString("pass");
	}
	
}
