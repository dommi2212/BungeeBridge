package me.dommi2212.BungeeBridge;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Level;

import me.dommi2212.BungeeBridge.packets.PacketConnectPlayer;
import me.dommi2212.BungeeBridge.packets.PacketCustom;
import me.dommi2212.BungeeBridge.packets.PacketGetMOTDServer;
import me.dommi2212.BungeeBridge.packets.PacketGetOnlineCountServer;
import me.dommi2212.BungeeBridge.packets.PacketGetPlayerIP;
import me.dommi2212.BungeeBridge.packets.PacketGetPlayerName;
import me.dommi2212.BungeeBridge.packets.PacketGetPlayerUUID;
import me.dommi2212.BungeeBridge.packets.PacketGetPlayersServer;
import me.dommi2212.BungeeBridge.packets.PacketGetServerByPlayer;
import me.dommi2212.BungeeBridge.packets.PacketGetServerIP;
import me.dommi2212.BungeeBridge.packets.PacketIsPlayerOnline;
import me.dommi2212.BungeeBridge.packets.PacketIsServerOnline;
import me.dommi2212.BungeeBridge.packets.PacketKeepAlive;
import me.dommi2212.BungeeBridge.packets.PacketKickAllPlayers;
import me.dommi2212.BungeeBridge.packets.PacketKickPlayer;
import me.dommi2212.BungeeBridge.packets.PacketMessageAllPlayers;
import me.dommi2212.BungeeBridge.packets.PacketMessagePlayer;
import me.dommi2212.BungeeBridge.packets.PacketSendActionbar;
import me.dommi2212.BungeeBridge.packets.PacketSendTitle;
import me.dommi2212.BungeeBridge.packets.PacketServerRunning;
import me.dommi2212.BungeeBridge.packets.PacketServerStopping;
import me.dommi2212.BungeeBridge.packets.PacketStopProxy;
import me.dommi2212.BungeeBridge.packets.PacketWriteConsole;
import me.dommi2212.BungeeBridge.util.ConnectResult;
import me.dommi2212.BungeeBridge.util.IsOnlineResult;
import me.dommi2212.BungeeBridge.util.TitleUtil;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Used to handle and process packets.
 */
public class PacketHandler {
	
	/**
	 * Handles and processes a packet.
	 *
	 * @param packet packet
	 * @param source source
	 * @return Object answer
	 */
	@SuppressWarnings("deprecation")
	public static Object handlePacket(BungeePacket packet, InetAddress source) {
		Object answer = null;
		if(packet.getType() == BungeePacketType.CONNECTPLAYER) {
			PacketConnectPlayer finalpacket = (PacketConnectPlayer) packet;
			ProxiedPlayer player = BungeeCord.getInstance().getPlayer(finalpacket.getUUID());
			if(player != null) {
				try {
					ServerInfo info = BungeeCord.getInstance().getServerInfo(finalpacket.getServer());
					player.connect(info);
					answer = (Object) ConnectResult.CONNECT;
				} catch (NullPointerException e) {
					answer = (Object) ConnectResult.SERVER_NOT_FOUND;
				}
			} else {
				answer = (Object) ConnectResult.PLAYER_NOT_FOUND;
			}
		} else if(packet.getType() == BungeePacketType.CUSTOM) {
			PacketCustom finalpacket = (PacketCustom) packet;
			CustomPacketRecieveEvent event = new CustomPacketRecieveEvent(finalpacket.getChannel(), finalpacket.getSubject());
			BungeeCord.getInstance().getPluginManager().callEvent(event);
			answer = event.getAnswer();
		} else if(packet.getType() == BungeePacketType.GETMOTDSERVER) {
			PacketGetMOTDServer finalpacket = (PacketGetMOTDServer) packet;
			answer = (Object) BungeeServer.getByBungeename(finalpacket.getBungeename()).getMOTD();
		} else if(packet.getType() == BungeePacketType.GETONLINECOUNTGLOBAL) {
			answer = (Object) BungeeCord.getInstance().getOnlineCount();
		} else if(packet.getType() == BungeePacketType.GETONLINECOUNTSERVER) {
			PacketGetOnlineCountServer finalpacket = (PacketGetOnlineCountServer) packet;
			answer = (Object) BungeeCord.getInstance().getServerInfo(finalpacket.getServer()).getPlayers().size();
		} else if(packet.getType() == BungeePacketType.GETPLAYERIP) {
			PacketGetPlayerIP finalpacket = (PacketGetPlayerIP) packet;
			ProxiedPlayer player = BungeeCord.getInstance().getPlayer(finalpacket.getUUID());
			if(player != null) {
				InetSocketAddress address = player.getAddress();
				answer = (Object) address;
			}
		} else if(packet.getType() == BungeePacketType.GETPLAYERNAME) {
			PacketGetPlayerName finalpacket = (PacketGetPlayerName) packet;
			answer = (Object) BungeeCord.getInstance().getPlayer(finalpacket.getUUID()).getName();
		} else if(packet.getType() == BungeePacketType.GETPLAYERSGLOBAL) {
			List<UUID> players = new ArrayList<UUID>();
			for(ProxiedPlayer online : BungeeCord.getInstance().getPlayers()) {
				players.add(online.getUniqueId());
			}
			answer = (Object) players;
		} else if(packet.getType() == BungeePacketType.GETPLAYERSSERVER) {
			PacketGetPlayersServer finalpacket = (PacketGetPlayersServer) packet;
			List<UUID> players = new ArrayList<UUID>();
			for(ProxiedPlayer online : BungeeCord.getInstance().getServerInfo(finalpacket.getServer()).getPlayers()) {
				players.add(online.getUniqueId());
			}
			answer = (Object) players;
		} else if(packet.getType() == BungeePacketType.GETPLAYERUUID) {
			PacketGetPlayerUUID finalpacket = (PacketGetPlayerUUID) packet;
			answer = (Object) BungeeCord.getInstance().getPlayer(finalpacket.getName()).getUniqueId();
		} else if(packet.getType() == BungeePacketType.GETSERVERBYPLAYER) {
			PacketGetServerByPlayer finalpacket = (PacketGetServerByPlayer) packet;
			answer = (Object) BungeeCord.getInstance().getPlayer(finalpacket.getUUID()).getServer().getInfo().getName();
		} else if(packet.getType() == BungeePacketType.GETSERVERIP) {
			PacketGetServerIP finalpacket = (PacketGetServerIP) packet;
			answer = (Object) BungeeCord.getInstance().getServerInfo(finalpacket.getServer()).getAddress();
		} else if(packet.getType() == BungeePacketType.GETSERVERS) {
			Map<String, ServerInfo> servers = BungeeCord.getInstance().getServers();
			List<String> result = new ArrayList<String>();
			for(Entry<String, ServerInfo> entry : servers.entrySet()) {
				result.add(entry.getKey());
			}
			answer = (Object) result;
		} else if(packet.getType() == BungeePacketType.ISPLAYERONLINE) {
			PacketIsPlayerOnline finalpacket = (PacketIsPlayerOnline) packet;
			if(finalpacket.getUUID() != null) {
				ProxiedPlayer player = BungeeCord.getInstance().getPlayer(finalpacket.getUUID());
				if(player != null) {
					answer = (Object) IsOnlineResult.ONLINE;
				} else {
					answer = (Object) IsOnlineResult.OFFLINE;
				}
			} else if(finalpacket.getName() != null) {
				ProxiedPlayer player = BungeeCord.getInstance().getPlayer(finalpacket.getName());
				if(player != null) {
					answer = (Object) IsOnlineResult.ONLINE;
				} else {
					answer = (Object) IsOnlineResult.OFFLINE;
				}
			} else {
				answer = (Object) IsOnlineResult.UUID_AND_NAME_NULL;
			}
		} else if(packet.getType() == BungeePacketType.ISSERVERONLINE) {
			PacketIsServerOnline finalpacket = (PacketIsServerOnline) packet;
			BungeeServer server = BungeeServer.getByBungeename(finalpacket.getBungeename());
			if(server != null) {
				answer = (Object) ServerWatcher.isResponding(server);
			} else answer = (Object) false;
		} else if(packet.getType() == BungeePacketType.KEEPALIVE) {
			PacketKeepAlive finalpacket = (PacketKeepAlive) packet;
			BungeeServer server = BungeeServer.getByBungeename(finalpacket.getBungeename());
			server.setMOTD(finalpacket.getMOTD());
			ServerWatcher.resetTimer(server);
		} else if(packet.getType() == BungeePacketType.KICKALLPLAYERS) {
			PacketKickAllPlayers finalpacket = (PacketKickAllPlayers) packet;
			for(ProxiedPlayer online : BungeeCord.getInstance().getPlayers()) {
				online.disconnect(finalpacket.getMessage());
			}
		} else if(packet.getType() == BungeePacketType.KICKPLAYER) {
			PacketKickPlayer finalpacket = (PacketKickPlayer) packet;
			ProxiedPlayer player = BungeeCord.getInstance().getPlayer(finalpacket.getUUID());
			for(ProxiedPlayer online : BungeeCord.getInstance().getPlayers()) {
				online.sendMessage(online.getUniqueId().toString());
			}
			if(player != null) {
				player.disconnect(finalpacket.getMessage());
			}
		} else if(packet.getType() == BungeePacketType.MESSAGEALLPLAYERS) {
			PacketMessageAllPlayers finalpacket = (PacketMessageAllPlayers) packet;
			for(ProxiedPlayer online : BungeeCord.getInstance().getPlayers()) {
				online.sendMessage(finalpacket.getMessage());
			}
			answer = (Object) UUID.randomUUID().toString();
		} else if(packet.getType() == BungeePacketType.MESSAGEPLAYER) {
			PacketMessagePlayer finalpacket = (PacketMessagePlayer) packet;
			ProxiedPlayer player = BungeeCord.getInstance().getPlayer(finalpacket.getUUID());
			if(player != null) {
				player.sendMessage(finalpacket.getMessage());
			}
		} else if(packet.getType() == BungeePacketType.SENDACTIONBAR) {
			PacketSendActionbar finalpacket = (PacketSendActionbar) packet;
			BungeeCord.getInstance().getPlayer(finalpacket.getUUID()).sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(finalpacket.getActionbar()));
		} else if(packet.getType() == BungeePacketType.SENDTITLE) {
			PacketSendTitle finalpacket = (PacketSendTitle) packet;
			TitleUtil.sendTitle(finalpacket.getTitle(), BungeeCord.getInstance().getPlayer(finalpacket.getUUID()));
		} else if(packet.getType() == BungeePacketType.SERVERRUNNING) {
			PacketServerRunning finalpacket = (PacketServerRunning) packet;
			InetSocketAddress address = new InetSocketAddress(source, finalpacket.getPort());
			BungeeServer server = BungeeServer.getByAddress(address);
			if(server != null) {
				server.setName(finalpacket.getName());
				server.setMOTD(finalpacket.getMOTD());
				server.setPort(finalpacket.getPort());
				server.setUpdateIntervall(finalpacket.getUpdateIntervall());
				server.setAddress(address);
			} else {
				Map<String, ServerInfo> servers = BungeeCord.getInstance().getServers();
				for(Entry<String, ServerInfo> entry : servers.entrySet()) {
					InetSocketAddress serveraddress = entry.getValue().getAddress();
					if(serveraddress.equals(new InetSocketAddress(source, finalpacket.getPort()))) {
						new BungeeServer(finalpacket.getName(), finalpacket.getMOTD(), entry.getValue().getName(), finalpacket.getPort(), finalpacket.getUpdateIntervall(), address);
					}
				}
			}
			server = BungeeServer.getByAddress(address);
			ServerWatcher.resetTimer(server);
			BungeeBridgeS.logger.log(Level.INFO, server.getBungeename() + " connected!");
			answer = (Object) server.getBungeename();
		} else if(packet.getType() == BungeePacketType.SERVERSTOPPING) {
			PacketServerStopping finalpacket = (PacketServerStopping) packet;
			BungeeServer server = BungeeServer.getByBungeename(finalpacket.getBungeename());
			BungeeServer.remove(server);
			BungeeBridgeS.logger.log(Level.INFO, server.getBungeename() + " disconnected!");
		} else if(packet.getType() == BungeePacketType.STOPPROXY) {
			PacketStopProxy finalpacket = (PacketStopProxy) packet;
			if(finalpacket.getMessage() != null) {
				BungeeCord.getInstance().stop(finalpacket.getMessage());
			} else {
				BungeeCord.getInstance().stop();
			}
		} else if(packet.getType() == BungeePacketType.WRITECONSOLE) {
			PacketWriteConsole finalpacket = (PacketWriteConsole) packet;
			System.out.println(finalpacket.getMessage());
		} else {
			/*
			 * Maybe return Exception?
			 */
//			throw new UnknownPacketException("Recieved unknown packet!");
		}
		return answer;
	}

}
