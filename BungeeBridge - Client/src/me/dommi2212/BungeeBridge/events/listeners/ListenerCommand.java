package me.dommi2212.BungeeBridge.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.dommi2212.BungeeBridge.BungeeBridgeC;
import me.dommi2212.BungeeBridge.events.wrapped.WrappedPlayerCommandPreprocessEvent;
import me.dommi2212.BungeeBridge.packets.PacketFireEvent;

/**
 * The CommandListener used to send PlayerCommandPreprocessEvents to BungeeBridgeS.
 */
public class ListenerCommand implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onChat(PlayerCommandPreprocessEvent e) {
		PacketFireEvent packet = new PacketFireEvent(new WrappedPlayerCommandPreprocessEvent(e.getPlayer().getUniqueId(), e.getMessage(), BungeeBridgeC.getBungeename()));
		packet.send();
	}

}
