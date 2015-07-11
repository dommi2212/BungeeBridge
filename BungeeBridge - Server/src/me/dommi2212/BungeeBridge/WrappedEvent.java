package me.dommi2212.BungeeBridge;

import java.io.Serializable;

import net.md_5.bungee.api.plugin.Event;

/**
 * The abstract base for all wrapped events.
 */
public abstract class WrappedEvent implements Serializable {
	
	private static final long serialVersionUID = 5673742829562873373L;
	
	/**
	 * Abstract method used to transform an wrapped event to an event.
	 *
	 * @return the event
	 */
	public abstract Event toEvent();

}
