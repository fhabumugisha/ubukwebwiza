package com.buseni.ubukwebwiza.utils;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

/**
 * This class is used to back up the database
 * A quartz job is used to call this class
 * @author habumugisha
 *
 */
@Component
public class DBBackup {
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(DBBackup.class);
	
	@Value("${aws.backupBucketName}")
	private String bucketName;
	
	@Value("${aws.accessKey}")
	private String accessKey;
	
	@Value("${aws.secretKey}")
	private String secretKey;
	
	@Value("${datasource.host}")
	private String host;
	
	@Value("${datasource.port}")
	private String port;
	
	@Value("${datasource.dbname}")
	private String databaseName;
	
	@Value("${datasource.username}")
	private String user;
	
	@Value("${datasource.password}")
	private String password;
	
	
	public  void export(){
		
		 
		String executeCmd = "";
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hhmmss");
        Date date = new Date();
        String filename = "backup-" + dateFormat.format(date) + ".sql";
				
		StringBuilder sb =  new StringBuilder();		
		sb.append(" ssh ")
			.append(user).append("@").append(host)
			.append(" mysqldump ")
			.append(" -h ").append(host)
			.append(" --port=").append(port)
			.append(" -u ").append(user)
			.append(" --password='").append(password).append("'")
			.append(" --add-drop-database -B  ").append(databaseName);
			//.append(" > ").append(filepath);
		executeCmd =  sb.toString();
		LOGGER.debug("Command : " +  executeCmd);
		Runtime rt = Runtime.getRuntime();
		File test = new File(filename);
		PrintStream ps;

		try {
			Process child = rt.exec(executeCmd);
			ps = new PrintStream(test);
			InputStream in = child.getInputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				ps.write(ch);
				System.out.write(ch); // to view it by console
			}

			InputStream err = child.getErrorStream();
			while ((ch = err.read()) != -1) {
				System.out.write(ch);
			}
			uploadFile(test, filename);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		}

	/**
	 * Uplaod a file to amazon S3
	 * @param file : the file to upload
	 * @param filename : filename
	 */
	public   void uploadFile(File file, String filename) {
		if(file != null){
			AWSCredentials myCredentials = new BasicAWSCredentials(accessKey, secretKey);
			//TransferManager tx = new TransferManager(new ProfileCredentialsProvider());
			TransferManager tx = new TransferManager(myCredentials);
			try {
				 ObjectMetadata objectMetadata = new ObjectMetadata();
				
				Upload myUpload = tx.upload(new PutObjectRequest(bucketName, filename, file).withMetadata(objectMetadata).withCannedAcl(CannedAccessControlList.Private));

				// While the transfer is processing, you can work with the transfer object
				while (myUpload.isDone() == false) {
					LOGGER.debug(myUpload.getProgress().getPercentTransferred() + "%");
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

}
