package main;

import java.awt.EventQueue;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

import constanst.Mode;
import model.Algorithm;
public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					BeautyEyeLNFHelper . frameBorderStyle = FrameBorderStyle.generalNoTranslucencyShadow;   
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
					new MainFrame();
					long startTime = System.currentTimeMillis();
<<<<<<< HEAD
					//new Cryptography(Algorithm.AES, "resources/publickey", Mode.ENCRYPTION, "resources/plaintext.txt", "resources/ciphertext.txt");
					new Cryptography(Algorithm.AES, "resources/publickey", Mode.DECRYPTION, "resources/ciphertext.txt", "resources/plaintext.txt");
=======
					new Cryptography(Algorithm.AES, "resources/publickey", Mode.ENCRYPTION, "resources/plaintext.txt", "resources/ciphertext.txt");
>>>>>>> 560dab3833fd5f0d61bcfe16a95c959d47928dd6
					long endTime   = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					System.out.println((float)totalTime/1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
