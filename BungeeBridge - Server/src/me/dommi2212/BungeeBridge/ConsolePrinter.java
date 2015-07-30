package me.dommi2212.BungeeBridge;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.md_5.bungee.BungeeCord;

/**
 * The Class ConsolePrinter.
 */
public class ConsolePrinter {
	
	/** Specifies the prefix. */
	private static final String prefix = "[BungeeBridgeS] ";
	
	/** Logger used to send messages to the console. */
	private static Logger logger = BungeeCord.getInstance().getLogger();
	
	/**
	 * Log a message with a Level
	 *
	 * @param lvl the level
	 * @param msg the message
	 */
	public static void log(Level lvl, String msg) {
		logger.log(lvl, prefix + msg);
	}
	
	/**
	 * Print to the console (Level.INFO)
	 *
	 * @param msg the message
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