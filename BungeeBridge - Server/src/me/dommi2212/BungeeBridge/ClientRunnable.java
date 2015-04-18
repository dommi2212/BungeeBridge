package me.dommi2212.BungeeBridge;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import me.dommi2212.BungeeBridge.util.EncryptionUtil;
import me.dommi2212.BungeeBridge.util.SerializationUtil;

/**
 * Used to manage the Clients using Mutlithreading.
 */
public class ClientRunnable implements Runnable {
	
	private Socket client = null;
	
	/**
	 * Instantiates a Thread to manage a request.
	 *
	 * @param client client
	 */
	public ClientRunnable(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		if(BungeeBridgeS.SECMODE == SecurityMode.OFF) {
		    try {
		    	ObjectInputStream objIN = new ObjectInputStream(client.getInputStream());
		    	ObjectOutputStream objOUT = new ObjectOutputStream(client.getOutputStream());
		    	Object obj = objIN.readObject();
		    	BungeePacket packet = (BungeePacket) obj;
		    	Object answer = PacketHandler.handlePacket(packet, client.getInetAddress());
		    	if(packet.shouldAnswer()) {  
		    		objOUT.writeObject(answer);
		    	}
			    objIN.close();
	    	 	objOUT.close();
		    } catch(ClassCastException e) {
		    	BungeeBridgeS.logger.log(Level.SEVERE, "§4Failed to read packet!");
			} catch(InvalidClassException e) {
	//			e.printStackTrace();
				BungeeBridgeS.logger.log(Level.SEVERE, "§4Your version of BungeeBridgeS(Bungeecord) is incompatible to your version of BungeeBridgeC(Spigot)!\n§4You have to update immediately!");
		    } catch(IOException e) {
		    	e.printStackTrace();
		    } catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else if(BungeeBridgeS.SECMODE == SecurityMode.PASS) {
		    try {
		    	ObjectInputStream objIN = new ObjectInputStream(client.getInputStream());
		    	ObjectOutputStream objOUT = new ObjectOutputStream(client.getOutputStream());
		    	Object obj = objIN.readObject();
		    	BungeePacket packet = (BungeePacket) obj;
		    	String pass = packet.getPassword();
		    	if(pass != null) {
		    		if(pass.equals(BungeeBridgeS.PASS)) {
				    	Object answer = PacketHandler.handlePacket(packet, client.getInetAddress());
				    	if(packet.shouldAnswer()) {  
				    		objOUT.writeObject(answer);
				    	}
		    		} else {
			    		BungeeBridgeS.logger.log(Level.SEVERE, "§4Recieved packet with wrong password!");
			    		BungeeBridgeS.logger.log(Level.SEVERE, "§4Source-InetAddress: " + client.getInetAddress());
			    		BungeeBridgeS.logger.log(Level.SEVERE, "§4Used password: \"" + packet.getPassword() + "\"");
		    		}
		    	} else {
		    		BungeeBridgeS.logger.log(Level.SEVERE, "§4Recieved packet without password!");
		    		BungeeBridgeS.logger.log(Level.SEVERE, "§4Source-InetAddress: " + client.getInetAddress());
		    	}
			    objIN.close();
	    	 	objOUT.close();
		    } catch(ClassCastException e) {
		    	BungeeBridgeS.logger.log(Level.SEVERE, "§4Failed to read packet!");
			} catch(InvalidClassException e) {
	//			e.printStackTrace();
				BungeeBridgeS.logger.log(Level.SEVERE, "§4Your version of BungeeBridgeS(Bungeecord) is incompatible to your version of BungeeBridgeC(Spigot)!\n§4You have to update immediately!");
		    } catch(IOException e) {
		    	e.printStackTrace();
		    } catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else if(BungeeBridgeS.SECMODE == SecurityMode.CIPHER) {
			try {
				ObjectInputStream in = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				Object rawpacket = in.readObject();
				byte[] decoded = EncryptionUtil.decode((byte[]) rawpacket, BungeeBridgeS.PASS);
				Object obj = SerializationUtil.deserialize(decoded);
				BungeePacket packet = (BungeePacket) obj;
				Object answer = PacketHandler.handlePacket(packet, client.getInetAddress());
				if(packet.shouldAnswer()) {
					byte[] serialized = SerializationUtil.serialize(answer);
					byte[] encoded;
					try {
						encoded = EncryptionUtil.encode(serialized, BungeeBridgeS.PASS);
						out.writeObject((Object) encoded);
					} catch (BadPaddingException e) {
						e.printStackTrace();
					}
				}
			    in.close();
	    	 	out.close();
			} catch (InvalidClassException e) {
				BungeeBridgeS.logger.log(Level.SEVERE, "§4Your version of BungeeBridgeS(Bungeecord) is incompatible to your version of BungeeBridgeC(Spigot)!\n§4You have to update immediately!");
			} catch (IOException e) {
				e.printStackTrace();	
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				BungeeBridgeS.logger.log(Level.SEVERE, "§4Recieved wrong encoded packet!");
				BungeeBridgeS.logger.log(Level.SEVERE, "§4Source-InetAddress: " + client.getInetAddress());
			}

		}
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
