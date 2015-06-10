package me.dommi2212.BungeeBridge;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * The CommandExecutor used to manage the command "PacketManager"
 * Aliases: PacketMan, PacketMon, PacketMonitor
 */
public class CommandPacketManager implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(!cs.hasPermission("bungeebridge.packetmanager")) {
			cs.sendMessage("§cYou don't have the permission to use this command!");
			return false;
		}
		if(!BungeeBridgeC.isLoggerEnabled()) {
			cs.sendMessage("§cThe packetlogger/packetmanager is disabled! Change this setting in your config.yml and try again!");
			return false;
		}
		if(args.length == 0) {
			cs.sendMessage("§cSyntax: /PacketMan List|Notify [args...]");
		} else if(args.length == 1) {
			if(args[0].equalsIgnoreCase("list")) {
				cs.sendMessage("§7Packets sent since last restart/reload:");
				for(BungeePacketType type : BungeePacketType.values()) {
					TypeCountEntry entry = TypeCountEntry.getByType(type);
					if(entry.getCount() > 0) {
						cs.sendMessage("§7" + type + " §7(§6" + entry.getCount() + "§7)");
					}
				}
			} else if(args[0].equalsIgnoreCase("notify")) cs.sendMessage("§cSyntax: /PacketMan Notify <IDs>");
			else cs.sendMessage("§cSyntax: /PacketMan List|Notify [args...]");
		} else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("notify")) {			
				try {
					List<BungeePacketType> types = PacketSubscriptionManager.getPacketsByString(args[1]);
					if(types.isEmpty()) cs.sendMessage("§cPlease provide atleast one id!");
					PacketSubscriptionManager.setSubscriptions(cs, types);
				} catch (InvalidFormatException e) {
					if(e.getMessage().equalsIgnoreCase("Invalid Format!")) {
						cs.sendMessage("§cFormat: §4ID §cOR §4ID,ID,ID,... §cOR §4ID-ID §cOR §4ID,ID,ID-ID,ID-ID,...");
					} else if(e.getMessage().equalsIgnoreCase("Unknown packet!")) {
						cs.sendMessage("§cThere is no packet by this id!");
					} else if(e.getMessage().equalsIgnoreCase("Invalid character(s)!")) {
						cs.sendMessage("§cOnly numeric characters allowed!");
					} else throw new IllegalArgumentException();
				}
			} else if(args[0].equalsIgnoreCase("list")) cs.sendMessage("§cSyntax: /PacketMan List");
			else cs.sendMessage("§cSyntax: /PacketMan List|Notify [args...]");
		} else cs.sendMessage("§cSyntax: /PacketMan List|Notify [args...]");
		
		return true;
	}


}
