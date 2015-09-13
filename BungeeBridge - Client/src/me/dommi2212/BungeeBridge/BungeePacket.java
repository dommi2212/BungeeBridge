package me.dommi2212.BungeeBridge;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import me.dommi2212.BungeeBridge.packets.PacketCustom;
import me.dommi2212.BungeeBridge.util.EncryptionUtil;
import me.dommi2212.BungeeBridge.util.SerializationUtil;

/**
 * Abstract base-class for all packets. You may <b>not</b> extend this class as you have to set a type in the constructor. Use {@link PacketCustom} instead.
 */
public abstract class BungeePacket implements Serializable {
	
	private static final long serialVersionUID = 3728278382368494804L;
	private BungeePacketType type;
	private boolean shouldanswer;
	private String pass = null;
	
	public BungeePacket(BungeePacketType type, boolean shouldanswer) {
		this.type = type;
		this.shouldanswer = shouldanswer;
	}
	
	/**
	 * Gets the type of a packet.
	 *
	 * @return The type of the packet.
	 */
	public BungeePacketType getType() {
		return type;
	}
	
	/**
	 * Checks, whether the packet should answer or not.
	 *
	 * @return Whether the packet answers or not.
	 */
	public boolean shouldAnswer() {
		return shouldanswer;
	}
	
	/**
	 * Gets the password if SECMODE is set to {@link SecurityMode#PASS}. 
	 *
	 * @return The password or {@code null}, if no password has been set.
	 */
	public String getPassword() {
		return pass;
	}
	
	/**
	 * Sends the packet to BungeeBridgeS, where it is processed in a new Thread. You can use this method asynchronously.
	 *
	 * @return The answer of BungeeBridgeS. You may cast this to the expected value (Please see the <a href="http://www.spigotmc.org/resources/bungeebridge.5820/">project Page</a> for additional information.) The answer can be {@code null}, if the packet doesn't return an answer. This can be checked by using {@link #shouldAnswer()}.
	 */
	public Object send() {
		if(BungeeBridgeC.isLoggerEnabled()) {
			TypeCountEntry.getByType(type).increment();
			PacketSubscriptionManager.notify(type);
		}
		Socket client;
		Object answer = null;
		if(BungeeBridgeC.getSecurityMode() == SecurityMode.OFF) {
			try {
				client = new Socket(BungeeBridgeC.getHost(), BungeeBridgeC.getPort());
				ObjectOutputStream objOUT = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream objIN = new ObjectInputStream(client.getInputStream());
				objOUT.writeObject((Object) this);
				if(this.shouldAnswer()) {
					answer = objIN.readObject();
				}
				objOUT.close();
				objIN.close();
				client.close();
			} catch(InvalidClassException e) {
				ConsolePrinter.err("§4Your version of BungeeBridgeS(Bungeecord) is incompatible to your version of BungeeBridgeC(Spigot)!\n§4You have to update immediately!");
			} catch(IOException e) {
				throw new PacketFailSendException("An IOException occured!\n" + e.getMessage());
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			}		
		} else if(BungeeBridgeC.getSecurityMode() == SecurityMode.PASS) {
			pass = BungeeBridgeC.getPass();
			try {
				client = new Socket(BungeeBridgeC.getHost(), BungeeBridgeC.getPort());
				ObjectOutputStream objOUT = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream objIN = new ObjectInputStream(client.getInputStream());
				objOUT.writeObject((Object) this);
				if(this.shouldAnswer()) {
					answer = objIN.readObject();
				}
				objOUT.close();
				objIN.close();
				client.close();
			} catch(InvalidClassException e) {
				ConsolePrinter.err("§4Your version of BungeeBridgeS(Bungeecord) is incompatible to your version of BungeeBridgeC(Spigot)!\n§4You have to update immediately!");
			} catch(IOException e) {
				throw new PacketFailSendException("An IOException occured!\n" + e.getMessage());
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else if(BungeeBridgeC.getSecurityMode() == SecurityMode.CIPHER) {			
			try {
				byte[] serialized = SerializationUtil.serialize(this);
				byte[] encoded = EncryptionUtil.encode(serialized, BungeeBridgeC.getPass());
				
				client = new Socket(BungeeBridgeC.getHost(), BungeeBridgeC.getPort());
				ObjectOutputStream objOUT = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream objIN = new ObjectInputStream(client.getInputStream());
				objOUT.writeObject((Object) encoded);
				if(this.shouldAnswer()) {
					Object rawanswer = objIN.readObject();
					byte[] decoded = EncryptionUtil.decode((byte[]) rawanswer, BungeeBridgeC.getPass());
					answer = SerializationUtil.deserialize(decoded);
				}
				objOUT.close();
				objIN.close();
				client.close();				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return answer;
	}
} 