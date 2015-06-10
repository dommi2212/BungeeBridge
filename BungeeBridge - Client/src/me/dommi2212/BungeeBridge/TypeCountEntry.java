package me.dommi2212.BungeeBridge;

import java.util.ArrayList;
import java.util.List;

/**
 * Container used to save the count of sent packets of a specified type.
 */
public class TypeCountEntry {
	
	private BungeePacketType type;
	private int count;
	
	private static List<TypeCountEntry> entries = new ArrayList<TypeCountEntry>();
	
	/**
	 * Instantiates a new TypeCountEntry.
	 *
	 * @param type the type
	 */
	private TypeCountEntry(BungeePacketType type) {
		this.type = type;
		this.count = 0;
		entries.add(this);
	}
	
	/**
	 * Sets the count.
	 *
	 * @param newcount the new count
	 */
	public void setCount(int newcount) {
		count = newcount;
	}
	
	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public BungeePacketType getType() {
		return type;
	}
	
	/**
	 * Increment.
	 */
	public void increment() {
		count++;
	}
	
	/**
	 * Gets a entry by the BungeePacketType.
	 *
	 * @param type the type
	 * @return the entry
	 */
	public static TypeCountEntry getByType(BungeePacketType type) {
		for(TypeCountEntry entry : entries) {
			if(entry.getType() == type) return entry;
		}
		return null;
	}
	
	/**
	 * Gets all entries.
	 *
	 * @return the entries
	 */
	public static List<TypeCountEntry> getEntries() {
		return entries;
	}

	static {
		for(BungeePacketType packetType : BungeePacketType.values()) {
			new TypeCountEntry(packetType);
		}
	}
	
}
