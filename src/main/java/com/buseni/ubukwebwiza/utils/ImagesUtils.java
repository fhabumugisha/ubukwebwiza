package com.buseni.ubukwebwiza.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.buseni.ubukwebwiza.administrator.enums.EnumPhotoCategory;

public class ImagesUtils {
	
	public static final int PROFILE_IMAGE_HEIGHT = 150;

	public static final int PROFILE_IMAGE_WIDTH = 290;
	
	
	public static final int HP_IMAGE_HEIGHT = 300;

	public static final int HP_IMAGE_WIDTH = 944;
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(AmazonS3Util.class);
	
	
	
	public static File prepareUploading(MultipartFile multipartFile,
			Integer imgCateg) {
		File tempFile = null;
		if (multipartFile != null && !multipartFile.isEmpty()) {
			String contentType = multipartFile.getContentType();
			String imgExt = "png";
			if (MediaType.IMAGE_GIF_VALUE.equals(contentType)) {
				imgExt = "gif";
			} else if (MediaType.IMAGE_JPEG_VALUE.equals(contentType)) {
				imgExt = "jpg";
			}

			try {
				tempFile = File.createTempFile("tempuploadedfile" + System.currentTimeMillis(),	"." + imgExt);
				tempFile.deleteOnExit();
				if (EnumPhotoCategory.PROFILE.getId().equals(imgCateg)) {
					resizeImageScal(multipartFile, tempFile, PROFILE_IMAGE_WIDTH,PROFILE_IMAGE_HEIGHT, imgExt);
				} else if (EnumPhotoCategory.HOME_PAGE.getId().equals(imgCateg)) {
					resizeImageScal(multipartFile, tempFile, HP_IMAGE_WIDTH,HP_IMAGE_HEIGHT, imgExt);
				} else {					
					multipartFile.transferTo(tempFile);
				}

			} catch (IOException e) {
				LOGGER.error("Error prepareUploading Message:    " 	+ e.getMessage());
			}

		}

		return tempFile;

	}
	
	
	
	public static byte[] resizeImage(MultipartFile profileImage, int imageWidth, int imageHeight)  {
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
			LOGGER.error("Error resizeImage Message:    " 	+ e.getMessage());
		}
		byte[] bytes = baos.toByteArray();
		try {
			baos.close();
		} catch (IOException e) {
			LOGGER.error("Error resizeImage Message:    " 	+ e.getMessage());
		}
		return bytes;
	}
	
	
	public static File resizeImageScal(MultipartFile multipartFile, File tempFile,  int imageWidth, int imageHeight, String imgExt)  {
		BufferedImage originalImage = null;    	
		try {
			originalImage = ImageIO.read(multipartFile.getInputStream());
		} catch (IOException e) {
			LOGGER.error("Error resizeImageScal Message:    " 	+ e.getMessage());
		}

		BufferedImage thumbnailImage = Scalr.resize(originalImage,
				Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH
				, imageWidth, imageHeight,
				Scalr.OP_ANTIALIAS, Scalr.OP_BRIGHTER);

		
		
		try {
			ImageIO.write(thumbnailImage, imgExt, tempFile);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error resizeImageScal Message:    " 	+ e.getMessage());
		}
		
		return tempFile;
	}
	

}
