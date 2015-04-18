package me.dommi2212.BungeeBridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import net.md_5.bungee.BungeeCord;

public class ServerWatcher {
	
	private static HashMap<BungeeServer, Long> servermap = new HashMap<BungeeServer, Long>();
	private static List<BungeeServer> stoppedresponding = new ArrayList<BungeeServer>();

	public static void start() {
		BungeeCord.getInstance().getScheduler().schedule(BungeeBridgeS.instance, new Runnable() {
			@Override
			public void run() {
				if(!BungeeServer.servers.isEmpty()) {
					for(BungeeServer server : BungeeServer.servers) {
						if(servermap.containsKey(server)) {
							long intervall = server.getUpdateIntervall() * 1000L;
							long lastupdated = servermap.get(server);
							
							if(lastupdated + (2 * intervall) + 1000 < System.currentTimeMillis()) {
								if(!stoppedresponding.contains(server)) {
									BungeeBridgeS.logger.log(Level.WARNING, server.getBungeename() + " has stopped responding!");
									stoppedresponding.add(server);									
								}
							}
						} else servermap.put(server, System.currentTimeMillis());
					}
				}
			}
		}, 1L, 1L, TimeUnit.SECONDS);
	}

	public static void resetTimer(BungeeServer server) {
		if(servermap.containsKey(server)) {
			servermap.remove(server);
		}
		servermap.put(server, System.currentTimeMillis());
		if(stoppedresponding.contains(server)) {
			stoppedresponding.remove(server);
			BungeeBridgeS.logger.log(Level.INFO, server.getBungeename() + " started responding again!");
		}
	}
	
	public static boolean isResponding(BungeeServer server) {
		if(stoppedresponding.contains(server)) return false;
		else return true;
	}
	
	protected static void remove(BungeeServer server) {
		if(servermap.containsKey(server)) servermap.remove(server);
		if(stoppedresponding.contains(server)) stoppedresponding.remove(server);
	}

}
