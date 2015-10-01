package main;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.NoSuchPaddingException;

import constanst.Algorithm;
import constanst.Mode;


public class AutoEncryptDecrypt implements Runnable{
	String pathToFolder = "";
	Algorithm algorithm = Algorithm.AES;
	Mode mode = Mode.ENCRYPTION;
	int time = 0;
	Timer timer;
	public AutoEncryptDecrypt(int seconds, String path, Algorithm al,Mode m) {
		pathToFolder = path;
		algorithm = al;
		time = seconds;
		mode = m;
	}
	public void run () {
		timer = new Timer();
		timer.scheduleAtFixedRate(new EncryptTask(pathToFolder, algorithm, mode),0 ,  time * 1000);
	}
	
	public void cancel() {
		timer.cancel();
	}
}

/*
 * Task class
 * Encrypt all files which are not encrypted in the folder
 */
class EncryptTask extends TimerTask {
	String pathToFolder;
	Algorithm algorithm;
	Mode mode;
	public EncryptTask(String path, Algorithm al,Mode m) {
		this.pathToFolder = path;
		this.algorithm = al;
		this.mode = m;
	}
	public void run() {
		ArrayList<File> listFiles;
		if (mode == Mode.ENCRYPTION) {
			listFiles = getUnencryptedFiles(pathToFolder);
		}
		else {
			listFiles = getUndecryptedFiles(pathToFolder);
		}
		
		for(File f:listFiles) {
			try {
				Cryptography cry = new Cryptography(algorithm, mode, f.getPath()
						,pathToFolder ,false, null, null, false);
				new Thread(cry).start();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed 1");
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed 2");
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed 3");
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				System.out.println("Failed 4");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Failed 5");
				e.printStackTrace();
			}
		}
	}
	/*
	 * Get an ArrayList of unencrypted files in the folder
	 */
	private ArrayList<File> getUnencryptedFiles(String pathToFolder) {
		File folder = new File(pathToFolder);
		File[] listFiles = folder.listFiles();
		ArrayList<File> listUnencryptedFiles = new ArrayList<File>();
 		
		for(File f1:listFiles) {
			boolean isEncrypted = false;
			if (f1.getName().lastIndexOf(".") >= 4 && f1.getName().substring(0, 3).equals("en_")) {
				continue;
			}
			for(File f2:listFiles) {
				if (f2.getName().lastIndexOf(".") < 4) {
					continue;
				}
				String postFix = f2.getName().substring(3, f2.getName().length());
				if (f1.getName().equals(postFix)) {
					isEncrypted = true;
					break;
				}
			}
			if (!isEncrypted) {
				listUnencryptedFiles.add(f1);
			}
		}
		return listUnencryptedFiles;
	}
	/*
	 * Get an ArrayList of undecrypted files in the folder
	 */
	private ArrayList<File> getUndecryptedFiles(String pathToFolder) {
		File folder = new File(pathToFolder);
		File[] listFiles = folder.listFiles();	
		ArrayList<File> listUndecryptedFiles = new ArrayList<File>();
 		
		for(File f1:listFiles) {
			boolean isDecrypted = false;
			int pos = f1.getName().lastIndexOf(".");
			if (pos < 4){
				continue;
			}
			else if (!f1.getName().substring(0, 3).equals("en_")) {
				continue;
			}
			for(File f2:listFiles) {
				String name = f1.getName();
				String name1 = name.substring(3, name.length());
				String name2 = f2.getName();
				if (name1.equals(name2)) {
					isDecrypted = true;
					break;
				}
			}
			if (!isDecrypted) {
				listUndecryptedFiles.add(f1);
			}
		}
		return listUndecryptedFiles;
	}
}

