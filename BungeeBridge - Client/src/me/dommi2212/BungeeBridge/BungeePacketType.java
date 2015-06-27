package me.dommi2212.BungeeBridge;

import java.util.HashMap;

/**
 * Displays the type of a packet.
 */
public enum BungeePacketType {
	
	CONNECTPLAYER(0),
	CUSTOM(1),
	GETMOTDSERVER(2),
	GETONLINECOUNTGLOBAL(3),
	GETONLINECOUNTSERVER(4),
	GETPLAYERIP(5),
	GETPLAYERNAME(6),
	GETPLAYERSGLOBAL(7),
	GETPLAYERSSERVER(8),
	GETPLAYERUUID(9),
	GETSERVERBYPLAYER(10),
	GETSERVERIP(11),
	GETSERVERS(12),
	GETSLOTSSERVER(28),
	ISPLAYERONLINE(13),
	ISSERVERONLINE(14),
	KEEPALIVE(15),
	KICKALLPLAYERS(16),
	KICKPLAYER(17),
	MESSAGEALLPLAYERS(18),
	MESSAGEPLAYER(19),
	RUNCOMMAND(27),
	SENDACTIONBAR(20),
	SENDALLTITLE(26),
	SENDTITLE(21),
	SERVERRUNNING(22),
	SERVERSTOPPING(23),
	STOPPROXY(24),
	WRITECONSOLE(25),
	UNKNOWN(-1);
	
	private int id;
	private static HashMap<Integer, BungeePacketType> idmap = new HashMap<Integer, BungeePacketType>();
	
	private BungeePacketType(int id) {
		this.id = id;
	}
	
	/**
	 * Gets the id of a PacketType.
	 *
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the PacketType by it's id.
	 *
	 * @param id
	 * @return the PacketType; UNKNOWN if no type is found.
	 */
	public static BungeePacketType getById(int id) {
		if(idmap.containsKey(id)) return idmap.get(id);
		return UNKNOWN;
	}
	
	static {
		for(BungeePacketType type : values()) {
			idmap.put(type.getId(), type);
		}
	}
	
}
