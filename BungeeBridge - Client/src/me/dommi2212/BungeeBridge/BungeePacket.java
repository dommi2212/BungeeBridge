package me.dommi2212.BungeeBridge;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import me.dommi2212.BungeeBridge.util.EncryptionUtil;
import me.dommi2212.BungeeBridge.util.SerializationUtil;

/**
 * Core of all packets.
 */
public abstract class BungeePacket implements Serializable {
	
	private static final long serialVersionUID = 3728278382368494804L;
	protected BungeePacketType type;
	protected boolean shouldanswer;
	protected String pass = null;
	
	/**
	 * Gets the type of a packet.
	 *
	 * @return type
	 */
	public BungeePacketType getType() {
		return type;
	}
	
	/**
	 * Controls if a packet should answer or not.
	 *
	 * @return true, if successful
	 */
	public boolean shouldAnswer() {
		return shouldanswer;
	}
	
	/**
	 * Displays password if SECMODE is set to SecurityMode.PASS.
	 *
	 * @return pass
	 */
	public String getPassword() {
		return pass;
	}
	
	/**
	 * Sends the packet.
	 *
	 * @return the answer of BungeeBridgeS.
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
//				e.printStackTrace();
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
//				e.printStackTrace();
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