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

		        for (Provider provider : Security.getProviders()) {
		            System.out.println("Provider: " + provider.getName());
		            for (Provider.Service service : provider.getServices()) {
		                System.out.println("  Algorithm: " + service.getAlgorithm());
		            }
		        }
				try 
				{
					BeautyEyeLNFHelper . frameBorderStyle = FrameBorderStyle.generalNoTranslucencyShadow;   
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
					new MainFrame();
					//new Cryptography(Algorithm.DES, "resources/publickey", Mode.ENCRYPTION, "resources/plaintext.txt", "resources/ciphertext.txt");
					new Cryptography(Algorithm.DES, "resources/publickey", Mode.DECRYPTION, "resources/ciphertext.txt", "resources/plaintext.txt");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
