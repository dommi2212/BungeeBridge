package me.dommi2212.BungeeBridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.dommi2212.BungeeBridge.packets.PacketFireEvent;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Used to handle and process PacketFireEvents.
 */
public class PacketFireEventHandler {
	
	private static HashMap<Class<? extends WrappedEvent>, Plugin> registeredEvents = new HashMap<Class<? extends WrappedEvent>, Plugin>();
	
	/**
	 * Handle a PacketFireEvent.
	 *
	 * @param packet the packet
	 */
	public static void handlePacket(PacketFireEvent packet) {
		boolean caught = false;
		for(Class<? extends WrappedEvent> event : registeredEvents.keySet()) {
			if(event.isInstance(packet.getEvent())) {
				Event bungeeEvent = event.cast(packet.getEvent()).toEvent();
				BungeeCord.getInstance().getPluginManager().callEvent(bungeeEvent);
				caught = true;
				break;
			}
		}
		if(!caught) {
			ConsolePrinter.warn("[Events] Failed to handle PacketFireEvent: " + packet.getEvent().getClass().toString().replace("class ", ""));
		}
	}
	
	/**
	 * Used to register an event to the handler.
	 *
	 * @param plugin the plugin, which registers the plugin
	 * @param clazz the Event-Class to register.
	 */
	public static void registerEvent(Plugin plugin, Class<? extends WrappedEvent> clazz) {
		ConsolePrinter.print("[Events] Registering " + clazz.getSimpleName() + " for " + plugin.getDescription().getName() + "!");
		registeredEvents.put(clazz, plugin);
	}
	
	/**
	 * Used to unregister an event from the handler.
	 *
	 * @param clazz Event-Class to unregister.
	 */
	public static void unregisterEvent(Class<? extends WrappedEvent> clazz) {
		ConsolePrinter.print("[Events] Unregistering " + clazz.getSimpleName() + "!");
		registeredEvents.remove(clazz);
	}
	
	/**
	 * Used to unregister all events owned by a plugin from the handler.
	 *
	 * @param plugin the plugin
	 */
	public static void unregisterAll(Plugin plugin) {
		List<Class<? extends WrappedEvent>> toUnregister = new ArrayList<Class<? extends WrappedEvent>>();
		for(Entry<Class<? extends WrappedEvent>, Plugin> entry : registeredEvents.entrySet()) {
			if(entry.getValue().getDescription().getName().equals(plugin.getDescription().getName())) {
				toUnregister.add(entry.getKey());
			}				
		}
		if(!toUnregister.isEmpty()) {
			for(Class<? extends WrappedEvent> clazz : toUnregister) {
				unregisterEvent(clazz);
			}
		}
	}

}
