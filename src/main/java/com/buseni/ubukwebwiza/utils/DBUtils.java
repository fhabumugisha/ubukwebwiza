package com.buseni.ubukwebwiza.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.buseni.ubukwebwiza.sandbox.Sandbox;

public class DBUtils {
	

	public  static final Logger LOGGER = LoggerFactory.getLogger(DBUtils.class);

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		//System.out.println( System.getProperty("user.dir"));
		
			//backupDatabase();
			
			export();
		

	}
	//datasource.url=jdbc:mysql://104.131.9.243:3306/ubukwebwiza
		
	public  static void backupDatabase() {
		/******************************************************/
		//Database Properties
		/******************************************************/
		String dbName = "ubukwebwiza";
		String dbUser = "ubukwebwiza";
		String dbPass = "UbukweBwiza123&";
		String host = "104.131.9.243";
		String port = "3306";
		 int STREAM_BUFFER = 512000;
	     boolean status = false;
	     StringBuilder dumpdata = new StringBuilder();
		/***********************************************************/
		// Execute Shell Command
		/***********************************************************/
		String executeCmd = "";
		//mysqldump -h 104.131.9.243 --port 3306 -u ubukwebwiza --password UbukweBwiza123& ubukwebwiza -r backup.sql
		//mysqldump -h 104.131.9.243 --port=3306 -u ubukwebwiza --password=UbukweBwiza123& --databases ubukwebwiza > backup.sql
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String filepath = "backup-" + dateFormat.format(date) + ".sql";
				
		// batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " " + database + " -r \"" + backupPath + "" + filepath + "\"";
		//executeCmd = "mysqldump -u "+dbUser+" -p"+dbPass+" "+dbName+"-r backup.sql";
		StringBuilder sb =  new StringBuilder();
		sb.append(" ssh ubukwebwiza@104.131.9.243 mysqldump ")
			.append(" -h ").append(host)
			.append(" --port=").append(port)
			.append(" -u ").append(dbUser)
			.append(" --password='").append(dbPass).append("'")
			.append(" --add-drop-database -B  ").append(dbName)
			.append(" > ").append(filepath);
		executeCmd =  sb.toString();
		System.out.println("Command : " +  executeCmd);
		
		
		String command[] = new String[]{"ssh ubukwebwiza@104.131.9.243 mysqldump ",
                "--host=" + host,
                "--port=" + port,
                "--user=" + dbUser,
                "--password='" + dbPass+"'",
                "--skip-comments --add-drop-database ",
                "--databases",
                dbName};
		
		/*Process runtimeProcess;
		
		try {
			runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();
			if(processComplete == 0){

				System.out.println("Backup taken successfully");

				} else {

					System.out.println("Could not take mysql backup");

				}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		 // Run mysqldump
		// String command[] = new String[]{executeCmd.toString()};
        ProcessBuilder pb = new ProcessBuilder(command);
        Process process;
		try {
			process = pb.start();
			InputStream in = process.getInputStream();
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));

	        int count;
	        char[] cbuf = new char[STREAM_BUFFER];

	        // Read datastream
	        while ((count = br.read(cbuf, 0, STREAM_BUFFER)) != -1) {
	            dumpdata.append(cbuf, 0, count);
	        }

	        //set the status
	        int processComplete = process.waitFor();
	        if (processComplete == 0) {                   
	            status = true;
	        } else {
	            status = false;
	        }
	        // Close
	        br.close();
	        in.close();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(status){
			byte[] data = dumpdata.toString().getBytes();
            // Set backup folder
            String rootpath = System.getProperty("user.dir") + "backup";

            // See if backup folder exists
            File file = new File(rootpath);
            if (!file.isDirectory()) {
                // Create backup folder when missing. Write access is needed.
                file.mkdir();
            }
            // Compose full path, create a filename as you wish
          
            // Write SQL DUMP file
            File filedst = new File(filepath);
            FileOutputStream dest = null;
			try {
				dest = new FileOutputStream(filedst);
				 dest.write(data);
		            dest.close();
			} catch (IOException e) {
				IOUtils.closeQuietly(dest);
				e.printStackTrace();
			}
           
		}
        
	}
	
	public static void export(){
		String dbName = "ubukwebwiza";
		String dbUser = "ubukwebwiza";
		String dbPass = "UbukweBwiza123&";
		String host = "104.131.9.243";
		String port = "3306";
		 
		String executeCmd = "";
		//mysqldump -h 104.131.9.243 --port 3306 -u ubukwebwiza --password UbukweBwiza123& ubukwebwiza -r backup.sql
		//mysqldump -h 104.131.9.243 --port=3306 -u ubukwebwiza --password=UbukweBwiza123& --databases ubukwebwiza > backup.sql
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hhmmss");
        Date date = new Date();
        String filepath = "backup-" + dateFormat.format(date) + ".sql";
				
		// batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " " + database + " -r \"" + backupPath + "" + filepath + "\"";
		//executeCmd = "mysqldump -u "+dbUser+" -p"+dbPass+" "+dbName+"-r backup.sql";
		StringBuilder sb =  new StringBuilder();
		sb.append(" ssh ")
			.append(dbUser).append("@").append(host)
			.append(" mysqldump ")
			.append(" -h ").append(host)
			.append(" --port=").append(port)
			.append(" -u ").append(dbUser)
			.append(" --password='").append(dbPass).append("'")
			.append(" --add-drop-database -B  ").append(dbName);
			//.append(" > ").append(filepath);
		executeCmd =  sb.toString();
		LOGGER.debug("Command : " +  executeCmd);
		Runtime rt = Runtime.getRuntime();
		File test = new File(filepath);
		PrintStream ps;

		try {
			Process child = rt.exec(executeCmd);
			ps = new PrintStream(test);
			InputStream in = child.getInputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				ps.write(ch);
				//System.out.write(ch); // to view it by console
			}

			InputStream err = child.getErrorStream();
			while ((ch = err.read()) != -1) {
				//System.out.write(ch);
			}
			uploadFile(test, filepath);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		}

	/**
	 * Uplaod a file to amazon S3
	 * @param file : the file to upload
	 * @param filename : filename
	 */
	public static  void uploadFile(File file, String filename) {
		if(file != null){
		//	AWSCredentials myCredentials = new BasicAWSCredentials(accessKey, secretKey);
			TransferManager tx = new TransferManager(new ProfileCredentialsProvider());

			try {
				DateTime now  =  new DateTime();
				 ObjectMetadata objectMetadata = new ObjectMetadata();
				
				Upload myUpload = tx.upload(new PutObjectRequest("ubdatabasebackup", filename, file).withMetadata(objectMetadata).withCannedAcl(CannedAccessControlList.Private));

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
