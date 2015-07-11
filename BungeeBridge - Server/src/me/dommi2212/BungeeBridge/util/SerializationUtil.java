package me.dommi2212.BungeeBridge.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Used to (de)serialize Packets.
 */
public class SerializationUtil {
	
	/**
	 * Serializes a packet.
	 *
	 * @param obj the object/packet to serialize.
	 * @return the serialized byte-array
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(out);
		os.writeObject(obj);
		return out.toByteArray();
	}
	
	/**
	 * Deserializes a packet.
	 *
	 * @param data the data
	 * @return packet
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}

}
