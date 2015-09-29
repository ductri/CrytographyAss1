package main;

import java.awt.EventQueue;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

import model.Algorithm;
import model.Mode;
import java.security.*;
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
					//new Cryptography(Algorithm.AES, "resources/publickey", Mode.ENCRYPTION, "resources/plaintext.txt", "resources/ciphertext.txt");
					new Cryptography(Algorithm.AES, "resources/publickey", Mode.DECRYPTION, "resources/ciphertext.txt", "resources/plaintext.txt");
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
