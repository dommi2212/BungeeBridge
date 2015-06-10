package me.dommi2212.BungeeBridge;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;

/**
 * Manages subscriptions to packets done by CommandSenders using "/PacketManager notify ..."
 */
public class PacketSubscriptionManager {
	
	private static HashMap<BungeePacketType, LinkedList<CommandSender>> subMap = new HashMap<BungeePacketType, LinkedList<CommandSender>>();
	
	/**
	 * Gets all packets by their ID in a String.
	 *
	 * @param s the String
	 * @return all packets
	 * @throws InvalidFormatException if an error occurrs.
	 */
	public static List<BungeePacketType> getPacketsByString(String s) throws InvalidFormatException {
		List<BungeePacketType> types = new ArrayList<BungeePacketType>();
		
		String[] blocks = s.split(",");
		for(String block : blocks) {
			if(block.equalsIgnoreCase("")) throw new InvalidFormatException("Invalid Format!");
			if(block.contains("-")) {
				String[] ids = block.split("-");
				if(ids.length != 2) throw new InvalidFormatException("Invalid Format!");
				try {
					int end = Integer.valueOf(ids[1]);
					for(int id = Integer.valueOf(ids[0]); id <= end; id++) {
						if(BungeePacketType.getById(id) == BungeePacketType.UNKNOWN) throw new InvalidFormatException("Unknown packet!");
						types.add(BungeePacketType.getById(id));
					}
				} catch (NumberFormatException e) {
					throw new InvalidFormatException("Invalid character(s)!");
				}
			} else {
				try {
					int id = Integer.valueOf(block);
					if(BungeePacketType.getById(id) == BungeePacketType.UNKNOWN) throw new InvalidFormatException("Unknown packet!");
					types.add(BungeePacketType.getById(id));
				} catch (NumberFormatException e) {
					throw new InvalidFormatException("Invalid character(s)!");
				}
			}
		}

		return types;	
	}
	
	/**
	 * Clears a CommandSender's subs.
	 *
	 * @param cs the CommandSender
	 */
	private static void clear(CommandSender cs) {
		if(subMap.isEmpty()) return;
		for(Entry<BungeePacketType, LinkedList<CommandSender>> entry : subMap.entrySet()) {
			LinkedList<CommandSender> senders = entry.getValue();
			if(senders.contains(cs)) {
				senders.remove(cs);
				if(senders.isEmpty()) subMap.remove(entry);
			}
		}
	}
	
	/**
	 * Sets a CommandSender's subscriptions.
	 *
	 * @param cs the CommandSender
	 * @param types the subscribed packets
	 */
	public static void setSubscriptions(CommandSender cs, List<BungeePacketType> types) {
		clear(cs);
		for(BungeePacketType type : types) {
			if(subMap.containsKey(type)) {
				LinkedList<CommandSender> notified = subMap.get(type);
				notified.add(cs);
			} else subMap.put(type, new LinkedList<>(Arrays.asList(cs)));
		}
	}
	
	/**
	 * Notify all Suubscribers about a sent packet.
	 *
	 * @param type the type
	 */
	public static void notify(BungeePacketType type) {
		if(!subMap.containsKey(type)) return;		
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		
		for(CommandSender cs : subMap.get(type)) {
			cs.sendMessage("§7[§6BungeeBridgeC§7] " + format.format(date) + " | Sent packet: §6" + type + " (" + type.getId() + ")");
		}
	}
	
}
