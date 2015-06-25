package com.buseni.ubukwebwiza.utils;

import java.io.File;
import java.io.InputStream;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

/**
 * Utility class for upload images on amazon S3
 * @author fahabumu
 *
 */
@Component
public class AmazonS3Util {
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(AmazonS3Util.class);

		
	@Value("${aws.bucketName}")
	private String bucketName;
	
	@Value("${aws.accessKey}")
	private String accessKey;
	
	@Value("${aws.secretKey}")
	private String secretKey;
		
	/**
	 * Uplaod a file to amazon S3
	 * @param file : the file to upload
	 * @param filename : filename
	 */
	public   void uploadFile(File file, String filename) {
		if(file != null){
			AWSCredentials myCredentials = new BasicAWSCredentials(accessKey, secretKey);
			TransferManager tx = new TransferManager(myCredentials);

			try {
				DateTime now  =  new DateTime();
				 ObjectMetadata objectMetadata = new ObjectMetadata();
				 objectMetadata.setCacheControl("2592000");
				 objectMetadata.setExpirationTime(now.plusMonths(2).toDate());
				 objectMetadata.setHttpExpiresDate(now.plusMonths(2).toDate());
				Upload myUpload = tx.upload(new PutObjectRequest(bucketName, filename, file).withMetadata(objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));

				// While the transfer is processing, you can work with the transfer object
				while (myUpload.isDone() == false) {
					LOGGER.info(myUpload.getProgress().getPercentTransferred() + "%");
				}
			} catch (AmazonServiceException ase) {
				LOGGER.error("Caught an AmazonServiceException, which means your request made it "
						+ "to Amazon S3, but was rejected with an error response for some reason.");
				LOGGER.error("Error Message:    " + ase.getMessage());
				LOGGER.error("HTTP Status Code: " + ase.getStatusCode());
				LOGGER.error("AWS Error Code:   " + ase.getErrorCode());
				LOGGER.error("Error Type:       " + ase.getErrorType());
				LOGGER.error("Request ID:       " + ase.getRequestId());
			} catch (AmazonClientException ace) {
				LOGGER.error("Caught an AmazonClientException, which means the client encountered "
						+ "a serious internal problem while trying to communicate with S3, "
						+ "such as not being able to access the network.");
				LOGGER.error("Error Message: " + ace.getMessage());
			}

		}
	}
	
	public   void uploadFile(InputStream inpustream, String filename) {
		AWSCredentials myCredentials = new BasicAWSCredentials(accessKey, secretKey);
		TransferManager tx = new TransferManager(myCredentials);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		try {
			 Upload myUpload = tx.upload(new PutObjectRequest(bucketName, filename, inpustream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
			 
			 // While the transfer is processing, you can work with the transfer object
			while (myUpload.isDone() == false) {
			     LOGGER.info(myUpload.getProgress().getPercentTransferred() + "%");
			 }
		} catch (AmazonServiceException ase) {
	        LOGGER.error("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            LOGGER.error("Error Message:    " + ase.getMessage());
            LOGGER.error("HTTP Status Code: " + ase.getStatusCode());
            LOGGER.error("AWS Error Code:   " + ase.getErrorCode());
            LOGGER.error("Error Type:       " + ase.getErrorType());
            LOGGER.error("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            LOGGER.error("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            LOGGER.error("Error Message: " + ace.getMessage());
		}
		
	}
	
	public   void deleteFile(String filename) {
		AWSCredentials myCredentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 s3client = new AmazonS3Client(myCredentials);
		try {
			s3client.deleteObject(bucketName, filename);
			
		} catch (AmazonServiceException ase) {
	        LOGGER.error("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            LOGGER.error("Error Message:    " + ase.getMessage());
            LOGGER.error("HTTP Status Code: " + ase.getStatusCode());
            LOGGER.error("AWS Error Code:   " + ase.getErrorCode());
            LOGGER.error("Error Type:       " + ase.getErrorType());
            LOGGER.error("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            LOGGER.error("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            LOGGER.error("Error Message: " + ace.getMessage());
		}
		
	}
	public   void deleteBucket(String bucketToDelete) {
		AWSCredentials myCredentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 s3client = new AmazonS3Client(myCredentials);
		try {
			s3client.deleteBucket(bucketToDelete);
			
		} catch (AmazonServiceException ase) {
	        LOGGER.error("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            LOGGER.error("Error Message:    " + ase.getMessage());
            LOGGER.error("HTTP Status Code: " + ase.getStatusCode());
            LOGGER.error("AWS Error Code:   " + ase.getErrorCode());
            LOGGER.error("Error Type:       " + ase.getErrorType());
            LOGGER.error("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            LOGGER.error("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            LOGGER.error("Error Message: " + ace.getMessage());
		}
		
	}
}
