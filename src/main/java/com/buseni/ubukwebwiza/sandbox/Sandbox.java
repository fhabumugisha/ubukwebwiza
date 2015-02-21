package com.buseni.ubukwebwiza.sandbox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.imgscalr.Scalr;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Sandbox {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//PasswordEncoder encoder = new BCryptPasswordEncoder();
		//System.out.println("123456 encode is " + encoder.encode("123456"));
		resizeImagScal();
		//resizeThumbnail();
	}
	
	
	public static String encode(String var){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(var);
	}
	
	public static void resizeThumbnail(){
		try {
			Thumbnails.of(new File("Bobo.jpg"))
			.size(204, 150)
			.toFile(new File("BoboThumbnail.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void resizeImagScal() {
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(new File("Bobo.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//BufferedImage thumbnail = Scalr.resize(originalImage, 150);
	
		BufferedImage thumbnail =
				  Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH,
				               204, 150, Scalr.OP_ANTIALIAS, Scalr.OP_BRIGHTER);
		
		try {
		ImageIO.write(thumbnail, "png", new File("BoboThumnailImageScal2.jpg"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
