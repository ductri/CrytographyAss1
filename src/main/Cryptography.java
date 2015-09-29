package main;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import constanst.Mode;
import model.Algorithm;

public class Cryptography {
	byte[] buf;
	Cipher cipher;
	PublicKey publicKey = null;
	boolean MD5 = true;
	String pathToZipFile = "resources";
	public Cryptography(Algorithm al, String pathToKey, Mode mode, String pathToFile, boolean zip) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
	    if (mode == Mode.ENCRYPTION) {
	    	encrypt(pathToKey, al, pathToFile, zip);
	    }
	    else {
	    	decrypt(pathToKey, al, pathToFile, zip);
	    }
	}
	
	/*** Encryption Function ***/
	private void encrypt(String pathToKey,Algorithm al,String pathToFile,boolean zip) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		byte[] iv;
		String fileName = getFileName(pathToFile);
		String extension = getExtension(pathToFile);
		InputStream in = new FileInputStream(pathToFile);
		// Generate hash value   
		byte[] hashValue = generateMD5(in);
		
		//User chooses to compress file before encrypting
		if (zip) {
			compress(pathToFile);
			extension = "zip";
			pathToFile = "resources/zip/" + fileName + ".zip";
		}
		
		// Create Output file

		String pathToOutput = "resources/ciphertext/" + fileName + "." + extension;
		File file = new File(pathToOutput);
		file.createNewFile();
		OutputStream out = new FileOutputStream(pathToOutput);

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
	    // Encrypt hash value
	    out.write(hashValue, 0, hashValue.length);
	    in = new FileInputStream(pathToFile);
	    int numRead = 0;
	    while ((numRead = in.read(buf)) >= 0) {
	    	out.write(buf, 0, numRead);
	    }

		out.flush();
		out.close();
		in.close();
	}
	
	/*** Decryption Function ***/
	private void decrypt(String pathToKey, Algorithm al, String pathToFile, boolean unzip) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Path path = Paths.get(pathToKey);
		byte[] encoded = Files.readAllBytes(path);
		byte[] iv;
		String pathToOutput = "";
		String fileName = getFileName(pathToFile);
		String extension = getExtension(pathToFile);
		InputStream in = new FileInputStream(pathToFile);
		if (extension.equals("zip")) {
			pathToOutput = "resources/zip/" + fileName + "." + extension;
		}
		else {
			pathToOutput = "resources/plaintext/" + fileName + "." + extension;
		}
		File file = new File(pathToOutput);
		file.createNewFile();
		OutputStream out = new FileOutputStream(pathToOutput);

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
		
		//Get hash value from plain text
		byte[] hashValueInPlainText = new byte[16];
		if (in.read(hashValueInPlainText) == -1) {
			System.out.println("Wrong message!");
		}
	    int numRead = 0;
	    while ((numRead = in.read(buf)) >= 0) {
	    	out.write(buf, 0, numRead);
	    }
		out.flush();
		out.close();
		in.close();
		
		if (extension.equals("zip")) {
			extension = decompress(pathToOutput, "resources/plaintext");
			pathToOutput = "resources/plaintext/" + fileName + "." + extension;
		}
	    InputStream inputTemp = new FileInputStream(pathToOutput);
	    //Get hash value from ciphertext
	    byte[] hashValueInCipherText = generateMD5(inputTemp);
	    
	    //Compare hash value
	    if (!Arrays.equals(hashValueInPlainText, hashValueInCipherText)) {
	    	System.out.println("Check MD5: False");
	    	MD5 = false;
	    }

	}
	
	/*** Md5 function ***/
	private byte[] generateMD5(InputStream in) throws NoSuchAlgorithmException, IOException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] bytebuffer = new byte[1024];
		int numRead = 0;
		
		while((numRead = in.read(bytebuffer)) > 0) {
			digest.update(bytebuffer, 0, numRead);
		}
		
		byte[] hashValue = digest.digest();
		
		return hashValue;
	}
	
	/*** Compress function  ***/
	private void compress(String pathToFile) throws IOException {
	       // input file 
        FileInputStream in = new FileInputStream(pathToFile);

        // out put file 
        String fileName = getFileName(pathToFile);
        String extension = getExtension(pathToFile);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream("resources/zip/" + fileName +".zip"));

        // name the file inside the zip  file 
        out.putNextEntry(new ZipEntry(fileName + "." + extension)); 

        // buffer size
        byte[] b = new byte[1024];
        int numRead;

        while ((numRead = in.read(b)) > 0) {
            out.write(b, 0, numRead);
        }
        out.close();
        in.close();
	}
	
	/*** Decompress function  ***/
	private String decompress(String pathToFile, String pathToDir) throws IOException {
		//get the zip file content
    	ZipInputStream zis = new ZipInputStream(new FileInputStream(pathToFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
    	String extension = "";
    	while(ze!=null){
    		
    		String fileName = ze.getName();
    		extension = getExtension(fileName);
    		File newFile = new File(pathToDir + "/" + fileName);
    		FileOutputStream fos = new FileOutputStream(newFile);             

            int numRead;
            byte[] b = new byte[1024];
            while ((numRead = zis.read(b)) > 0) {
            	fos.write(b, 0, numRead);
            }
            fos.close();   
            ze = zis.getNextEntry();
    	}
    	
        zis.closeEntry();
    	zis.close();
    	return extension;
	}
	/***** Help function *****/
	
	/*** Get name of a file without extension ***/
	private String getFileName(String pathToFile){
		int pos1 = pathToFile.lastIndexOf("/");
		int pos2 = pathToFile.lastIndexOf(".");
		return pathToFile.substring(pos1 + 1, pos2);
	}
	/*** Get extension of a file ***/
	private String getExtension(String pathToFile){
		int pos = pathToFile.lastIndexOf(".");
		return pathToFile.substring(pos + 1, pathToFile.length());
	}
	
	/*** Convert from bytes to hex character : Use to show Md5 hash value ***/
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	private static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	
}
