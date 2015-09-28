package main;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import model.Algorithm;
import model.Mode;

public class Cryptography {
	byte[] buf = new byte[1024];
	Cipher cipher;
	private String algorithm = "";
	PublicKey publicKey = null;
	public Cryptography(Algorithm al, String pathToKey, Mode mode, String pathToInput, String pathToOutput) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
	    FileInputStream inputFile = new FileInputStream(pathToInput);
	    FileOutputStream outputFile = new FileOutputStream(pathToOutput);
	    
	    if (mode == Mode.ENCRYPTION) {
	    	encrypt(pathToKey, al, cipher ,inputFile, outputFile);
	    }
	    else {
	    	decrypt(pathToKey, al, cipher, inputFile, outputFile);
	    }
	}
	
	private void encrypt(String pathToKey,Algorithm al, Cipher cipher,InputStream in, OutputStream out) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		byte[] iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A };
	    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		if (al == Algorithm.AES) {
			SecretKey key = KeyGenerator.getInstance("AES").generateKey();
			byte[] encoded = key.getEncoded();
			FileOutputStream keyfos = new FileOutputStream(pathToKey);
			keyfos.write(encoded);
			keyfos.close();
			cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		}
		else if (al == Algorithm.DES) {
			SecretKey key = KeyGenerator.getInstance("DES").generateKey();
			byte[] encoded = key.getEncoded();
			FileOutputStream keyfos = new FileOutputStream("resources/publickey");
			keyfos.write(encoded);
			keyfos.close();
			cipher = Cipher.getInstance("DES/CBC/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		}
		else {
			// Unknown Algorithm
		}
		
	    out = new CipherOutputStream(out, cipher);

	    int numRead = 0;
	    while ((numRead = in.read(buf)) >= 0) {
	      out.write(buf, 0, numRead);
	    }
		out.flush();
		out.close();
		in.close();
	}
	
	private void decrypt(String pathToKey, Algorithm al, Cipher cipher, InputStream in, OutputStream out) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Path path = Paths.get(pathToKey);
		byte[] encoded = Files.readAllBytes(path);

		byte[] iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A };
	    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		if (al == Algorithm.AES) {
			SecretKey key = new SecretKeySpec(encoded, "AES");
			cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		}
		else if (al == Algorithm.DES) {
			SecretKey key = new SecretKeySpec(encoded, "DES");
			cipher = Cipher.getInstance("DES/CBC/NoPadding");
			cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		}
		else {
			// Unknown Algorithm
		}
		in = new CipherInputStream(in, cipher);

	    int numRead = 0;
	    while ((numRead = in.read(buf)) >= 0) {
	      out.write(buf, 0, numRead);
	    }
		out.flush();
		out.close();
		in.close();
	}
}
