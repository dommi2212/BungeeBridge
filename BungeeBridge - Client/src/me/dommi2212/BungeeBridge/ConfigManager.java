package me.dommi2212.BungeeBridge;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Manages the Config.
 */
public class ConfigManager {
	
	protected static void createConfig(File file) {
		try {
			file.createNewFile();
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			String pass = UUID.randomUUID().toString().replace("-", "");
			config.set("host", "localhost");
			config.set("port", 7331);
			config.set("securitymode", "OFF");
			config.set("pass", pass.substring(pass.length()-10, pass.length()));
			config.set("updateintervall", 2);
			config.save(file);
		} catch (IOException e) {
			BungeeBridgeC.logger.log(Level.SEVERE, "Failed to load/create config.yml!");
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(BungeeBridgeC.instance);
		}
	}
	
	protected static void loadConfig(FileConfiguration config) {
		BungeeBridgeC.HOST = config.getString("host");
		BungeeBridgeC.PORT = config.getInt("port");
		BungeeBridgeC.SECMODE = SecurityMode.valueOf(config.getString("securitymode").toUpperCase());
		BungeeBridgeC.PASS = config.getString("pass");
		BungeeBridgeC.UPDATEINTERVALL = config.getInt("updateintervall");
	}

}
