package me.dommi2212.BungeeBridge.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Used to encode/decode packets if SECMODE is set to SecurityMode.CIPHER.
 */
public class EncryptionUtil {
	
	/**
	 * Encodes a byte[].
	 *
	 * @param input input
	 * @param pass  pass
	 * @return byte[] encoded
	 * @throws NoSuchPaddingException if occurs.
	 * @throws NoSuchAlgorithmException if occurs.
	 * @throws InvalidKeyException if occurs.
	 * @throws BadPaddingException if occurs.
	 * @throws IllegalBlockSizeException if occurs.
	 */
	public static byte[] encode(byte[] input, String pass) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher c = Cipher.getInstance("Blowfish");
		Key k = new SecretKeySpec(pass.getBytes(), "Blowfish");
		c.init(Cipher.ENCRYPT_MODE, k);
		return c.doFinal(input);
	}
	
	/**
	 * Decodes a byte[].
	 *
	 * @param input input
	 * @param pass pass
	 * @return byte[] decoded
	 * @throws NoSuchPaddingException if occurs.
	 * @throws NoSuchAlgorithmException if occurs.
	 * @throws InvalidKeyException if occurs.
	 * @throws BadPaddingException if occurs.
	 * @throws IllegalBlockSizeException if occurs.
	 */
	public static byte[] decode(byte[] input, String pass) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher c = Cipher.getInstance("Blowfish");
		Key k = new SecretKeySpec(pass.getBytes(), "Blowfish");
		c.init(Cipher.DECRYPT_MODE, k);

		return c.doFinal(input);		
	}

}
