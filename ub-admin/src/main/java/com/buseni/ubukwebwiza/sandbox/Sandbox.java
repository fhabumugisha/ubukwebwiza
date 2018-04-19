package com.buseni.ubukwebwiza.sandbox;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;

import net.coobird.thumbnailator.Thumbnails;


public class Sandbox {
	
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(Sandbox.class);
	
	@PersistenceContext
	static 	 EntityManagerFactory emfactory;
	
	public static void main(String[] args) throws IOException {
		
	
		// TODO Auto-generated method stub
	
		//resizeImagScal();
		//resizeThumbnail();
		//resizeImgCarousel();
		
	//	System.out.println(UbUtils.normalizeName("W + / , edding professianal's job"));
		//System.out.println(generatePIN());
		
		//uploadFileToAmazonS3() ;
		
		 listingFiles();
		
	}
	 public static String generatePIN() {
	        //generate a 4 digit integer 1000 <10000
	        int randomPIN = (int)(Math.random()*9000)+1000;

	        //Store integer in a string
	       return String.valueOf(randomPIN);

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
	

	private static void listingFiles() {
		String existingBucketName = "ubfiles";
		//AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
		AmazonS3 s3client = new AmazonS3Client(new SystemPropertiesCredentialsProvider());
		
		try {
			 System.out.println("Listing objects");
			   
	            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
	                .withBucketName(existingBucketName);
	            ObjectListing objectListing;            
	            do {
	                objectListing = s3client.listObjects(listObjectsRequest);
	                for (S3ObjectSummary objectSummary : 
	                	objectListing.getObjectSummaries()) {
	                    System.out.println(" - " + objectSummary.getKey() + "  " +
	                            "(size = " + objectSummary.getSize() + 
	                            ")");
	                }
	                listObjectsRequest.setMarker(objectListing.getNextMarker());
	            } while (objectListing.isTruncated());
			 
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
	/*String existingBucketName = "ubfiles";
	  String keyName = "mypic.JPG";

	  String filePath = "C://Users/Fabrice/git/ubukwebwiza/Banquet-Events-Setup_fitExact.jpg";
	  String amazonFileUploadLocationOriginal=existingBucketName+"/";
	  SystemPropertiesCredentialsProvider

	  AmazonS3 s3Client = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider("application.properties"));

	  FileInputStream stream = new FileInputStream(filePath);
	  ObjectMetadata objectMetadata = new ObjectMetadata();
	  PutObjectRequest putObjectRequest = new PutObjectRequest(existingBucketName, keyName, stream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);
	  PutObjectResult result = s3Client.putObject(putObjectRequest);
	  System.out.println("Etag:" + result.getETag() + "-->" + result);*/
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
			originalImage = ImageIO.read(new File("Banquet-Events-Setup.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//BufferedImage thumbnail = Scalr.resize(originalImage, 150);
		System.out.println("Origiginal size : height : " + originalImage.getHeight() + " and width : " + originalImage.getWidth());
		BufferedImage thumbnail =
				  Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC,
						  1900, 500, Scalr.OP_ANTIALIAS, Scalr.OP_BRIGHTER);
		
		System.out.println("resize size : height : " + thumbnail.getHeight() + " and width : " + thumbnail.getWidth());
		
		try {
		ImageIO.write(thumbnail, "png", new File("Banquet-Events-Setup_fitAutomatique.jpg"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	
	

}
