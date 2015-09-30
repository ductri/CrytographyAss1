package main;

import java.awt.EventQueue;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

import constanst.Mode;
import model.Algorithm;
import java.security.Provider;
import java.security.Security;

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
					
					/* Encrypt */
					new Cryptography(Algorithm.AES, Mode.ENCRYPTION, "resources/a.pdf", true);
					
					/* Decrypt */
					//new Cryptography(Algorithm.AES, Mode.DECRYPTION, "resources/ciphertext/a.zip", false);

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
