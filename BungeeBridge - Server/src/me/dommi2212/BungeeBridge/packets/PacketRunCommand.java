package me.dommi2212.BungeeBridge.packets;

import java.io.Serializable;

import me.dommi2212.BungeeBridge.BungeePacket;
import me.dommi2212.BungeeBridge.BungeePacketType;

/**
 * Packet used run command(s) from BungeeCord-Console.
 */
@SuppressWarnings("serial")
public class PacketRunCommand extends BungeePacket implements Serializable {
	
	private String[] commands;
	
	/**
	 * Instantiates a new PacketRunCommand.
	 *
	 * @param commands the command(s) to run with args.
	 */
	public PacketRunCommand(String... commands) {
		this.commands = commands;
		this.type = BungeePacketType.RUNCOMMAND;
		this.shouldanswer = false;
	}
	
	/**
	 * Gets the commands.
	 *
	 * @return commands
	 */
	public String[] getCommands() {
		return commands;
	}
	
}
