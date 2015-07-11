package me.dommi2212.BungeeBridge.events.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.dommi2212.BungeeBridge.BungeeBridgeC;
import me.dommi2212.BungeeBridge.events.wrapped.WrappedAsyncPlayerChatEvent;
import me.dommi2212.BungeeBridge.packets.PacketFireEvent;

/**
 * The ChatListener used to send AsyncPlayerChatEvents to BungeeBridgeS.
 */
public class ListenerChat implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onChat(AsyncPlayerChatEvent e) {		
		PacketFireEvent packet = new PacketFireEvent(new WrappedAsyncPlayerChatEvent(e.getPlayer().getUniqueId(), e.getMessage(), e.getFormat(), BungeeBridgeC.getBungeename()));
		packet.send();
	}

}
