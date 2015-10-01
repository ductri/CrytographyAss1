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


					//new Cryptography(Algorithm.AES, Mode.ENCRYPTION, "resources/a.pdf", true);
					//new Cryptography(Algorithm.AES, Mode.DECRYPTION, "resources/en_a.zip", true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
}
