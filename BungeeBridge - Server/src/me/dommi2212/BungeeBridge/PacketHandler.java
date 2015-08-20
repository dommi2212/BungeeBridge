package me.dommi2212.BungeeBridge;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import me.dommi2212.BungeeBridge.packets.PacketChat;
import me.dommi2212.BungeeBridge.packets.PacketConnectPlayer;
import me.dommi2212.BungeeBridge.packets.PacketCustom;
import me.dommi2212.BungeeBridge.packets.PacketFireEvent;
import me.dommi2212.BungeeBridge.packets.PacketGetMOTDServer;
import me.dommi2212.BungeeBridge.packets.PacketGetOnlineCountServer;
import me.dommi2212.BungeeBridge.packets.PacketGetPlayerIP;
import me.dommi2212.BungeeBridge.packets.PacketGetPlayerName;
import me.dommi2212.BungeeBridge.packets.PacketGetPlayerUUID;
import me.dommi2212.BungeeBridge.packets.PacketGetPlayersServer;
import me.dommi2212.BungeeBridge.packets.PacketGetServerByPlayer;
import me.dommi2212.BungeeBridge.packets.PacketGetServerIP;
import me.dommi2212.BungeeBridge.packets.PacketGetSlotsServer;
import me.dommi2212.BungeeBridge.packets.PacketIsPlayerOnline;
import me.dommi2212.BungeeBridge.packets.PacketIsServerOnline;
import me.dommi2212.BungeeBridge.packets.PacketKeepAlive;
import me.dommi2212.BungeeBridge.packets.PacketKickAllPlayers;
import me.dommi2212.BungeeBridge.packets.PacketKickPlayer;
import me.dommi2212.BungeeBridge.packets.PacketMessageAllPlayers;
import me.dommi2212.BungeeBridge.packets.PacketMessagePlayer;
import me.dommi2212.BungeeBridge.packets.PacketRunCommand;
import me.dommi2212.BungeeBridge.packets.PacketSendActionbar;
import me.dommi2212.BungeeBridge.packets.PacketSendTitle;
import me.dommi2212.BungeeBridge.packets.PacketServerRunning;
import me.dommi2212.BungeeBridge.packets.PacketServerStopping;
import me.dommi2212.BungeeBridge.packets.PacketStopProxy;
import me.dommi2212.BungeeBridge.packets.PacketTellraw;
import me.dommi2212.BungeeBridge.packets.PacketWriteConsole;
import me.dommi2212.BungeeBridge.util.ConnectResult;
import me.dommi2212.BungeeBridge.util.IsOnlineResult;
import me.dommi2212.BungeeBridge.util.ServerRunningResult;
import me.dommi2212.BungeeBridge.util.TitleUtil;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.chat.ComponentSerializer;

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
	public static Object handlePacket(BungeePacket packet, InetAddress source) {
		Object answer = null;
		switch (packet.getType()) {
		case CHAT: {
			PacketChat finalpacket = (PacketChat) packet;
			BungeeCord.getInstance().getPlayer(finalpacket.getUUID()).chat(finalpacket.getMessage());
			} break;
		case CONNECTPLAYER: {
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
			} break;
		case CUSTOM: {
			PacketCustom finalpacket = (PacketCustom) packet;
			CustomPacketRecieveEvent event = new CustomPacketRecieveEvent(finalpacket.getChannel(), finalpacket.getSubject());
			BungeeCord.getInstance().getPluginManager().callEvent(event);
			answer = event.getAnswer();
			} break;
		case FIREEVENT: {
			PacketFireEvent finalpacket = (PacketFireEvent) packet;
			PacketFireEventHandler.handlePacket(finalpacket);
			} break;
		case GETMOTDSERVER: {
			PacketGetMOTDServer finalpacket = (PacketGetMOTDServer) packet;
			answer = (Object) BungeeServer.getByBungeename(finalpacket.getBungeename()).getMOTD();
			} break;
		case GETONLINECOUNTGLOBAL: {
			answer = (Object) BungeeCord.getInstance().getOnlineCount();
			} break;
		case GETONLINECOUNTSERVER: {
			PacketGetOnlineCountServer finalpacket = (PacketGetOnlineCountServer) packet;
			answer = (Object) BungeeCord.getInstance().getServerInfo(finalpacket.getServer()).getPlayers().size();
			} break;
		case GETPLAYERIP: {
			PacketGetPlayerIP finalpacket = (PacketGetPlayerIP) packet;
			ProxiedPlayer player = BungeeCord.getInstance().getPlayer(finalpacket.getUUID());
			if(player != null) {
				InetSocketAddress address = player.getAddress();
				answer = (Object) address;
			}
			} break;
		case GETPLAYERNAME: {
			PacketGetPlayerName finalpacket = (PacketGetPlayerName) packet;
			answer = (Object) BungeeCord.getInstance().getPlayer(finalpacket.getUUID()).getName();
			} break;
		case GETPLAYERSGLOBAL: {
			List<UUID> players = new ArrayList<UUID>();
			for(ProxiedPlayer online : BungeeCord.getInstance().getPlayers()) {
				players.add(online.getUniqueId());
			}
			answer = (Object) players;
			} break;
		case GETPLAYERSSERVER: {
			PacketGetPlayersServer finalpacket = (PacketGetPlayersServer) packet;
			List<UUID> players = new ArrayList<UUID>();
			for(ProxiedPlayer online : BungeeCord.getInstance().getServerInfo(finalpacket.getServer()).getPlayers()) {
				players.add(online.getUniqueId());
			}
			answer = (Object) players;
			} break;
		case GETPLAYERUUID: {
			PacketGetPlayerUUID finalpacket = (PacketGetPlayerUUID) packet;
			answer = (Object) BungeeCord.getInstance().getPlayer(finalpacket.getName()).getUniqueId();
			} break;
		case GETSERVERBYPLAYER: {
			PacketGetServerByPlayer finalpacket = (PacketGetServerByPlayer) packet;
			answer = (Object) BungeeCord.getInstance().getPlayer(finalpacket.getUUID()).getServer().getInfo().getName();
			} break;
		case GETSERVERIP: {
			PacketGetServerIP finalpacket = (PacketGetServerIP) packet;
			answer = (Object) BungeeCord.getInstance().getServerInfo(finalpacket.getServer()).getAddress();
			} break;
		case GETSERVERS: {
			Map<String, ServerInfo> servers = BungeeCord.getInstance().getServers();
			List<String> result = new ArrayList<String>();
			for(Entry<String, ServerInfo> entry : servers.entrySet()) {
				result.add(entry.getKey());
			}
			answer = (Object) result;
			} break;
		case GETSLOTSSERVER: {
			PacketGetSlotsServer finalpacket = (PacketGetSlotsServer) packet;
			answer = (Object) BungeeServer.getByBungeename(finalpacket.getBungeename()).getSlots();
			} break;
		case ISPLAYERONLINE: {
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
			} break;
		case ISSERVERONLINE: {
			PacketIsServerOnline finalpacket = (PacketIsServerOnline) packet;
			BungeeServer server = BungeeServer.getByBungeename(finalpacket.getBungeename());
			if(server != null) {
				answer = (Object) ServerWatcher.isResponding(server);
			} else answer = (Object) false;
			} break;
		case KEEPALIVE: {
			PacketKeepAlive finalpacket = (PacketKeepAlive) packet;
			BungeeServer server = BungeeServer.getByBungeename(finalpacket.getBungeename());
			if(server != null) {
				server.updateData(finalpacket.getMOTD());
				ServerWatcher.resetTimer(server);
				answer = (Object) 0;
			} else {
				answer = (Object) 1;
			}
			} break;
		case KICKALLPLAYERS: {
			PacketKickAllPlayers finalpacket = (PacketKickAllPlayers) packet;
			for(ProxiedPlayer online : BungeeCord.getInstance().getPlayers()) {
				online.disconnect(new TextComponent(finalpacket.getMessage()));
			}
			} break;
		case KICKPLAYER: {
			PacketKickPlayer finalpacket = (PacketKickPlayer) packet;
			ProxiedPlayer player = BungeeCord.getInstance().getPlayer(finalpacket.getUUID());
			player.disconnect(new TextComponent(finalpacket.getMessage()));
			} break;
		case MESSAGEALLPLAYERS: {
			PacketMessageAllPlayers finalpacket = (PacketMessageAllPlayers) packet;
			for(ProxiedPlayer online : BungeeCord.getInstance().getPlayers()) {
				online.sendMessage(new TextComponent(finalpacket.getMessage()));
			}
			answer = (Object) UUID.randomUUID().toString();
			} break;
		case MESSAGEPLAYER: {
			PacketMessagePlayer finalpacket = (PacketMessagePlayer) packet;
			ProxiedPlayer player = BungeeCord.getInstance().getPlayer(finalpacket.getUUID());
			player.sendMessage(new TextComponent(finalpacket.getMessage()));
			} break;
		case RUNCOMMAND: {
			PacketRunCommand finalpacket = (PacketRunCommand) packet;
			for(String command : finalpacket.getCommands()) {
				BungeeCord.getInstance().getPluginManager().dispatchCommand(BungeeCord.getInstance().getConsole(), command);
			}	
			} break;		
		case SENDACTIONBAR: {
			PacketSendActionbar finalpacket = (PacketSendActionbar) packet;
			BungeeCord.getInstance().getPlayer(finalpacket.getUUID()).sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(finalpacket.getActionbar()));
			} break;
		case SENDALLTITLE: {
			PacketSendTitle finalpacket = (PacketSendTitle) packet;			
			for(ProxiedPlayer online : BungeeCord.getInstance().getPlayers()) {
				TitleUtil.sendTitle(finalpacket.getTitle(), online);
			}
			} break;
		case SENDTITLE: {
			PacketSendTitle finalpacket = (PacketSendTitle) packet;
			TitleUtil.sendTitle(finalpacket.getTitle(), BungeeCord.getInstance().getPlayer(finalpacket.getUUID()));
			} break;
		case SERVERRUNNING: {
			PacketServerRunning finalpacket = (PacketServerRunning) packet;
			InetSocketAddress address = new InetSocketAddress(source, finalpacket.getPort());
			if(finalpacket.getVersion() == BungeeBridgeS.getVersion()) {
				BungeeServer server = BungeeServer.getByAddress(address);
				if(server != null) {
					server.updateData(finalpacket.getName(), finalpacket.getMOTD(), finalpacket.getPort(), finalpacket.getUpdateInterval(), address, finalpacket.getSlots());
				} else {
					Map<String, ServerInfo> servers = BungeeCord.getInstance().getServers();
					for(Entry<String, ServerInfo> entry : servers.entrySet()) {
						InetSocketAddress serveraddress = entry.getValue().getAddress();
						if(serveraddress.equals(new InetSocketAddress(source, finalpacket.getPort()))) {
							new BungeeServer(finalpacket.getName(), finalpacket.getMOTD(), entry.getValue().getName(), finalpacket.getPort(), finalpacket.getUpdateInterval(), address, finalpacket.getSlots());
						}
					}
				}
				server = BungeeServer.getByAddress(address);
				ServerWatcher.resetTimer(server);
				ConsolePrinter.print(server.getBungeename() + " connected!");
				answer = (Object) new ServerRunningResult(server.getBungeename(), BungeeBridgeS.getVersion(), System.currentTimeMillis());
			} else {
				ConsolePrinter.err(address.toString() + " failed to connect as versions don't match!\nYour version of BungeeBridgeS(Bungeecord) is incompatible to your version of BungeeBridgeC(Spigot)!\nYou have to update immediately!");
				answer = (Object) new ServerRunningResult(null, BungeeBridgeS.getVersion(), System.currentTimeMillis());
			}
			} break;
		case SERVERSTOPPING: {
			PacketServerStopping finalpacket = (PacketServerStopping) packet;
			BungeeServer server = BungeeServer.getByBungeename(finalpacket.getBungeename());
			BungeeServer.remove(server);
			ConsolePrinter.print(server.getBungeename() + " disconnected!");
			} break;
		case STOPPROXY: {
			PacketStopProxy finalpacket = (PacketStopProxy) packet;
			if(finalpacket.getMessage() != null) {
				BungeeCord.getInstance().stop(finalpacket.getMessage());
			} else {
				BungeeCord.getInstance().stop();
			}
			} break;
		case TELLRAW: {
			PacketTellraw finalpacket = (PacketTellraw) packet;
			ProxiedPlayer player = BungeeCord.getInstance().getPlayer(finalpacket.getUUID());
			player.sendMessage(ComponentSerializer.parse(finalpacket.getJSONString()));
			} break;
		case WRITECONSOLE: {
			PacketWriteConsole finalpacket = (PacketWriteConsole) packet;
			ConsolePrinter.log(finalpacket.getLevel(), finalpacket.getMessage());
			} break;
		default:
			ConsolePrinter.err("§4Recieved packet with unknown type!");
			break;		
		}
		
		return answer;
	}

}
