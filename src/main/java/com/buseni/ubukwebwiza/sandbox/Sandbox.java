package com.buseni.ubukwebwiza.sandbox;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Normalizer;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.imgscalr.Scalr;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
import com.buseni.ubukwebwiza.utils.UbUtils;

public class Sandbox {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//PasswordEncoder encoder = new BCryptPasswordEncoder();
		//System.out.println("123456 encode is " + encoder.encode("123456"));
		//resizeImagScal();
		//resizeThumbnail();
		
		//System.out.println(MimeTypeUtils.parseMimeType(MediaType.IMAGE_JPEG_VALUE));
		 System.out.println("Start...");
		//downloadFileFromAmazonS3();
		 //uploadFileToAmazonS3();
		// uploadLargeFile();
		// getImagesUrl();
		// writeBOS();
		 String test =  "_dte ser.gif ";
		 System.out.println(test);
		 System.out.println(UbUtils.normalizeName(test));
		 System.out.println(UbUtils.normalizeFileName(test));
			System.out.println("done...");
	}

	private static void getImagesUrl(){
		String existingBucketName = "ubfiles";
		AmazonS3Client s3Client = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider("application.properties"));
		ObjectListing objects = s3Client.listObjects(existingBucketName);
		do {
	        for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
	                System.out.println(objectSummary.getKey() + "\t" +
	                        objectSummary.getSize() + "\t" +
	                        StringUtils.fromDate(objectSummary.getLastModified()) +"\t" +  s3Client.getUrl(existingBucketName, objectSummary.getKey()));
	        }
	        objects = s3Client.listNextBatchOfObjects(objects);
	} while (objects.isTruncated());
	}
	
	private static void uploadLargeFile() {
		String existingBucketName = "ubfiles";
		AWSCredentials myCredentials = new  ClasspathPropertiesFileCredentialsProvider("application.properties").getCredentials();
		TransferManager tx = new TransferManager(myCredentials);
		 String filePath = "C://Users/fahabumu/git/ubukwebwiza/userimages/new_look.jpg";
		 File myFile = new File(filePath);
		try {
			 Upload myUpload = tx.upload(new PutObjectRequest(existingBucketName, myFile.getName(), myFile).withCannedAcl(CannedAccessControlList.PublicRead));
			 
			 // While the transfer is processing, you can work with the transfer object
			 while (myUpload.isDone() == false) {
			     System.out.println(myUpload.getProgress().getPercentTransferred() + "%");
			 }
		} catch (AmazonServiceException ase) {
	        System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
		}
		
		
	}
private static void uploadFileToAmazonS3() throws FileNotFoundException{
	String existingBucketName = "ubfiles";
	  String keyName = "mypic.JPG";
	  
	  String filePath = "C://Users/fahabumu/git/ubukwebwiza/userimages/new_look.jpg";
	  String amazonFileUploadLocationOriginal=existingBucketName+"/";
	  

	  AmazonS3 s3Client = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider("application.properties"));

	  FileInputStream stream = new FileInputStream(filePath);
	  ObjectMetadata objectMetadata = new ObjectMetadata();
	  PutObjectRequest putObjectRequest = new PutObjectRequest(existingBucketName, keyName, stream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);
	  PutObjectResult result = s3Client.putObject(putObjectRequest);
	  System.out.println("Etag:" + result.getETag() + "-->" + result);
}

/**
 * 
 * @throws IOException
 * @throws FileNotFoundException
 */
	private static void downloadFileFromAmazonS3() throws IOException,
			FileNotFoundException {
		String existingBucketName = "ubfiles";
		  String keyName = "wedding-banner.png";
		 
		  AmazonS3 s3Client = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider("application.properties"));
		  
		  GetObjectRequest request = new GetObjectRequest(existingBucketName,   keyName);
				  S3Object object = s3Client.getObject(request);
				  S3ObjectInputStream objectContent = object.getObjectContent();
				  IOUtils.copy(objectContent, new FileOutputStream("testAmazonS3.jpg"));
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
	
	
	public static File writeBOS() throws IOException{
     String filename = "output.txt";
     File f = File.createTempFile("tempuploadedfile", ".suffix");
     
    File bf =  new File(filename);
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;

		try {
			
			// create FileOutputStream from filename
			fos = new FileOutputStream(bf);

			// create BufferedOutputStream for FileOutputStream
			bos = new BufferedOutputStream(fos);

			byte b = 23;
			bos.write(filename.getBytes());
			
		}
		catch (FileNotFoundException fnfe) {
			System.out.println("File not found" + fnfe);
		}
		catch (IOException ioe) {
			System.out.println("Error while writing to file" + ioe);
		}
		finally {
			try {
				if (bos != null) {
					bos.flush();
					bos.close();
				}
			}
			catch (Exception e) {
				System.out.println("Error while closing streams" + e);
			}

		}
		return bf;
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
	
	public static void resizeImgCarousel() {
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
				               944, 300, Scalr.OP_ANTIALIAS, Scalr.OP_BRIGHTER);
		
		try {
		ImageIO.write(thumbnail, "png", new File("BoboThumnailImageScal2.jpg"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
