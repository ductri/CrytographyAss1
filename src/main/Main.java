package main;

import java.awt.EventQueue;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

import constanst.Algorithm;
import constanst.Mode;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					BeautyEyeLNFHelper . frameBorderStyle = FrameBorderStyle.generalNoTranslucencyShadow;   
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
					new MainFrame();
/*<<<<<<< HEAD
					long startTime = System.currentTimeMillis();
					
					 Encrypt 
					//new Cryptography(Algorithm.AES, Mode.ENCRYPTION, "resources/a.pdf", false);
					
					 Decrypt 
					//new Cryptography(Algorithm.AES, Mode.DECRYPTION, "F:\\Clone Code\\Cryptography\\Assignment 1\\resources\\en_cipher.jpg", false);
					

					
					long endTime   = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					System.out.println((float)totalTime/1000);
=======*/

					// Transparent
/*					String pathToEncryptFolder = "F:\\Clone Code\\Cryptography\\Assignment 1\\resources\\Encrypt";
					Thread t = new Thread(new AutoEncryptDecrypt(2, pathToEncryptFolder, Algorithm.DESede, Mode.ENCRYPTION));
					t.start();*/
					//new Cryptography(Algorithm.AES, Mode.ENCRYPTION, "resources/a.pdf", true);
					//new Cryptography(Algorithm.AES, Mode.DECRYPTION, "resources/en_a.zip", true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
}
