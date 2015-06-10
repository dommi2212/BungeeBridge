package me.dommi2212.BungeeBridge;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Manages the Config.
 */
public class ConfigManager {
	
	protected static void createConfig() {
		try {
			BungeeBridgeC.configfile.createNewFile();
			FileConfiguration config = YamlConfiguration.loadConfiguration(BungeeBridgeC.configfile);
			String pass = UUID.randomUUID().toString().replace("-", "");
			config.set("configversion", BungeeBridgeC.getVersion());
			config.set("host", "localhost");
			config.set("port", 7331);
			config.set("securitymode", "OFF");
			config.set("pass", pass.substring(pass.length()-10, pass.length()));
			config.set("updateintervall", 2);
			config.set("packetlogger", true);
			BungeeBridgeC.config = config;
			config.save(BungeeBridgeC.configfile);
			loadConfig();
		} catch (IOException e) {
			ConsolePrinter.err("Failed to load/create config.yml!");
			e.printStackTrace();
			Bukkit.getPluginManager().disablePlugin(BungeeBridgeC.instance);
		}
	}
	
	protected static void loadConfig() {
		BungeeBridgeC.configversion = BungeeBridgeC.config.getInt("configversion");
		BungeeBridgeC.host = BungeeBridgeC.config.getString("host");
		BungeeBridgeC.port = BungeeBridgeC.config.getInt("port");
		BungeeBridgeC.secmode = SecurityMode.valueOf(BungeeBridgeC.config.getString("securitymode").toUpperCase());
		BungeeBridgeC.pass = BungeeBridgeC.config.getString("pass");
		BungeeBridgeC.updateintervall = BungeeBridgeC.config.getInt("updateintervall");
		BungeeBridgeC.loggerenabled = BungeeBridgeC.config.getBoolean("packetlogger");
	}

}
