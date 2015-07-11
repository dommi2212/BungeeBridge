package me.dommi2212.BungeeBridge;

import net.md_5.bungee.api.plugin.Event;

/**
 * The event fired, when a PacketCustom is received. See the project-site for additional information.
 */
public class CustomPacketRecieveEvent extends Event {
	
	private String channel;
	private Object subject;
	private Object answer;
	
	public CustomPacketRecieveEvent(String channel, Object subject) {
		this.channel = channel;
		this.subject = subject;
	}
	
	/**
	 * Gets the channel the packet was sent to.
	 *
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}
	
	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public Object getSubject() {
		return subject;
	}
	
	/**
	 * Sets the answer of your plugin.
	 *
	 * @param answer the new answer
	 */
	public void setAnswer(Object answer) {
		this.answer = answer;
	}
	
	/**
	 * Gets the answer of your plugin.
	 *
	 * @return the answer
	 */
	public Object getAnswer() {
		return answer;
	}
	
}