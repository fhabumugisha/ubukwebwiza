package com.buseni.ubukwebwiza.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public class ImagesUtils {
	
	public static byte[] resizeImage(MultipartFile profileImage, int imageWidth, int imageHeight) throws IOException {
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(profileImage.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedImage thumbnailImage = Scalr.resize(originalImage,
				Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT
				, imageWidth, imageHeight,
				Scalr.OP_ANTIALIAS, Scalr.OP_BRIGHTER);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String contentType = profileImage.getContentType();
		String imgExt = "png";
		if(MediaType.IMAGE_GIF_VALUE.equals(contentType)){
			imgExt = "gif";
		}else if(MediaType.IMAGE_JPEG_VALUE.equals(contentType)){
			imgExt = "jpg";
		}
		try {
			ImageIO.write(thumbnailImage, imgExt, baos);
			baos.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();
		baos.close();
		return bytes;
	}

	/*public  void resizeImagScal(File original, File thumnail) {
	BufferedImage originalImage = null;
	try {
		originalImage = ImageIO.read(original);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//BufferedImage thumbnail = Scalr.resize(originalImage, 150);

	BufferedImage thumbnailImage =
			  Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH,
			               213, 150, Scalr.OP_ANTIALIAS, Scalr.OP_BRIGHTER);
	
	
	try {
	ImageIO.write(thumbnailImage, "png", thumnail);
	
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}}*/
}
