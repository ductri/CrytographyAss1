package main;

import java.awt.EventQueue;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;

import model.Algorithm;
import model.Mode;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					BeautyEyeLNFHelper . frameBorderStyle = FrameBorderStyle.generalNoTranslucencyShadow;   
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
					new MainFrame();
					new Cryptography(Algorithm.DES, "resources/publickey", Mode.ENCRYPTION, "resources/plaintext.txt", "resources/ciphertext.txt");
					//new Cryptography(Algorithm.DES, "resources/publickey", Mode.ENCRYPTION, "resources/ciphertext.txt", "resources/plaintext.txt");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
