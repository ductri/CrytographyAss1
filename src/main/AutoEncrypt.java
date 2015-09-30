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

import constanst.Mode;
import model.Algorithm;

public class AutoEncrypt implements Runnable{
	String pathToFolder = "";
	Algorithm algorithm = Algorithm.AES;
	int time = 0;
	Timer timer;
	public AutoEncrypt(int seconds, String path, Algorithm al) {
		pathToFolder = path;
		algorithm = al;
		time = seconds;
	}
	public void run () {
		timer = new Timer();
		timer.scheduleAtFixedRate(new EncryptTask(pathToFolder, algorithm),0 ,  time * 1000);
	}
}

/*
 * Task class
 * Encrypt all files which are not encrypted in the folder
 */
class EncryptTask extends TimerTask {
	String pathToFolder = "";
	Algorithm algorithm = Algorithm.AES;
	public EncryptTask(String path, Algorithm al) {
		pathToFolder = path;
		algorithm = al;
	}
	public void run() {
		System.out.println("Start");
		ArrayList<File> listUnencryptedFiles = getUncryptFiles(pathToFolder);
		for(File f:listUnencryptedFiles) {
			try {
				new Cryptography(algorithm, Mode.ENCRYPTION, f.getPath(), false);
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
		System.out.println("Finish");
	}
	/*
	 * Get an ArrayList of unencryped files in the folder
	 */
	private ArrayList<File> getUncryptFiles(String pathToFolder) {
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
}

