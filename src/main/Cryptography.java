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


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import constanst.Mode;
import model.Algorithm;

public class Cryptography {
	byte[] buf;
	Cipher cipher;
	PublicKey publicKey = null;
	public Cryptography(Algorithm al, String pathToKey, Mode mode, String pathToInput, String pathToOutput) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
	    FileInputStream inputFile = new FileInputStream(pathToInput);
	    FileOutputStream outputFile = new FileOutputStream(pathToOutput);
	    
	    if (mode == Mode.ENCRYPTION) {
	    	encrypt(pathToKey, al,inputFile, outputFile);
	    }
	    else {
	    	decrypt(pathToKey, al, inputFile, outputFile);
	    }
	}
	
	private void encrypt(String pathToKey,Algorithm al,InputStream in, OutputStream out) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		byte[] iv;

		if (al == Algorithm.AES) {
			iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A , 0x07, 0x09, 0x1A, 0x3C, 0x4D, 0x7F, (byte)0x8C, 0x5A};
		    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			SecretKey key = KeyGenerator.getInstance("AES").generateKey();
			byte[] encoded = key.getEncoded();
			FileOutputStream keyfos = new FileOutputStream(pathToKey);
			keyfos.write(encoded);
			keyfos.close();
			buf = new byte[16];
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		}
		else if (al == Algorithm.DES) {
			iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A };
		    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			buf = new byte[8];
			SecretKey key = KeyGenerator.getInstance("DES").generateKey();
			byte[] encoded = key.getEncoded();
			FileOutputStream keyfos = new FileOutputStream("resources/publickey");
			keyfos.write(encoded);
			keyfos.close();
			cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
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
	
	private void decrypt(String pathToKey, Algorithm al, InputStream in, OutputStream out) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Path path = Paths.get(pathToKey);
		byte[] encoded = Files.readAllBytes(path);
		byte[] iv;
		if (al == Algorithm.AES) {
			iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A , 0x07, 0x09, 0x1A, 0x3C, 0x4D, 0x7F, (byte)0x8C, 0x5A};
		    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			SecretKey key = new SecretKeySpec(encoded, "AES");
			buf = new byte[16];
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		}
		else if (al == Algorithm.DES) {
			iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07, 0x72, 0x6F, 0x5A };
		    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			buf = new byte[8];
			SecretKey key = new SecretKeySpec(encoded, "DES");
			cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
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
