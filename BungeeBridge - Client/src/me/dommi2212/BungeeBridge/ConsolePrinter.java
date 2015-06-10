package me.dommi2212.BungeeBridge;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

/**
 * The Class ConsolePrinter.
 */
public class ConsolePrinter {
	
	/** Specifies the prefix. */
	private static final String prefix = "[BungeeBridgeC] ";
	
	/** Logger used to send messages to the console. */
	private static Logger logger = Bukkit.getLogger();
	
	/**
	 * Log a message with a Level
	 *
	 * @param lvl
	 * @param msg
	 */
	public static void log(Level lvl, String msg) {
		logger.log(lvl, prefix + msg);
	}
	
	/**
	 * Print to the console (Level.INFO)
	 *
	 * @param msg
	 */
	public static void print(String msg) {
		log(Level.INFO, msg);
	}
	
	/**
	 * Print to console (Level.SEVERE)
	 *
	 * @param msg the msg
	 */
	public static void err(String msg) {
		log(Level.SEVERE, msg);
	}
	
	/**
	 * Print to the console (Level.WARNING)
	 *
	 * @param msg the msg
	 */
	public static void warn(String msg) {
		log(Level.WARNING, msg);
	}
		
}