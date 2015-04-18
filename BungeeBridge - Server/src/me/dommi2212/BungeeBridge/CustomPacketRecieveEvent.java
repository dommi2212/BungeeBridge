package me.dommi2212.BungeeBridge;

import net.md_5.bungee.api.plugin.Event;

public class CustomPacketRecieveEvent extends Event {
	
	private String channel;
	private Object subject;
	private Object answer;
	
	public CustomPacketRecieveEvent(String channel, Object subject) {
		this.channel = channel;
		this.subject = subject;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public Object getSubject() {
		return subject;
	}
	
	public void setAnswer(Object answer) {
		this.answer = answer;
	}
	
	public Object getAnswer() {
		return answer;
	}
	
}