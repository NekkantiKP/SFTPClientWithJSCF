package com.secure.ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTP{
	
	private ChannelSftp channelSftp;
	private String workingDirectory=null;
	public SFTP(String hostName, String port, String userName, String password,String workingDir) throws Exception {
		Session session = null;
		Channel channel = null;
		System.out.println("preparing the host information for sftp.");
		JSch jsch = new JSch();
		session = jsch.getSession(userName, hostName, Integer.parseInt(port));
		session.setPassword(password);
		Properties config = new Properties();
		System.out.println("connection pending.");
		config.put("StrictHostKeyChecking", "no");
		session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
		session.setConfig(config);
		System.out.println("before connecting");
		session.connect();
		System.out.println("Host connected.");
		channel = session.openChannel("sftp");
		channel.connect();
		channelSftp = (ChannelSftp)channel;
		this.workingDirectory=workingDir;
		channelSftp.cd(workingDir);
	}
	public void closeAll() {
		channelSftp.exit();
		System.out.println("sftp Channel exited.");
		channel.disconnect();
		System.out.println("Channel disconnected.");
		session.disconnect();
		System.out.println("Host Session disconnected.");
	}


	public Vector<String> getListOfFiles(String workingDirectory) throws Exception {
		Vector<String> filelist = channelSftp.ls(workingDirectory);
		for(int i=0; i<filelist.size();i++){
			System.out.println(filelist.get(i).toString());
		}
		return filelist;
	}

	public String  getFileContent(String workingDirectory, String filePath) throws Exception {
		InputStream is = channelSftp.get(filePath);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[250];
		int length;
		while ((length = is.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString("UTF-8");
	}
	public void  removeFile(String workingDirectory, String filePath) throws Exception {
		channelSftp.rm(filePath);
	}

	public void  putFile(String workingDirectory, String filePath) throws Exception {
		File f=new File(filePath);
		channelSftp.put(new FileInputStream(f), "SFTP_"+new Date().getTime());
	}

	public static void main(String[] args) throws Exception {
		SFTP sftp=new SFTP("10.3.84.136", "22", "test", "test","/KP");
		sftp.getListOfFiles("/KP");
		System.out.println(sftp.getFileContent("/KP","SFTP_1674040663188"));
		//sftp.removeFile("/KP","SFTP.java");
		sftp.getListOfFiles("/KP");
		sftp.putFile("/KP","E:\\KP_Personal\\SFTPClient\\src\\main\\java\\com\\secure\\ftp\\SFTP.java");
		sftp.getListOfFiles("/KP");

	}
	public static String send(String hostName, String port, String userName, String password) {
		String workingDirectory="/KP";
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		System.out.println("preparing the host information for sftp.");
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(userName, hostName, Integer.parseInt(port));
			session.setPassword(password);
			Properties config = new Properties();
			System.out.println("connection pending.");
			config.put("StrictHostKeyChecking", "no");
			session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
			session.setConfig(config);
			System.out.println("before connecting");
			session.connect();
			System.out.println("Host connected.");
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("sftp channel opened and connected.");
			System.out.println("working sftp");
			channelSftp = (ChannelSftp)channel;
			System.out.println(workingDirectory);
			channelSftp.cd(workingDirectory);
			Vector filelist = channelSftp.ls(workingDirectory);
			for(int i=0; i<filelist.size();i++){
				System.out.println(filelist.get(i).toString());
			}


			//      File f = new File(filePath);
			File f=new File("E:\\KP_Personal\\SFTPClient\\src\\main\\java\\com\\secure\\ftp\\SFTP.java");
			//      String fileNameWithOutExt = FilenameUtils.removeExtension(f.getName());
			// channelSftp.put(new FileInputStream(f), "SFTP.java");
			//   channelSftp.rm("SFTP.java");
			//      System.out.println("File transfered successfully to host.");
			InputStream is = channelSftp.get("SFTP.java");
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[250];
			int length;
			while ((length = is.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}

			// Java 1.1
			//return result.toString(StandardCharsets.UTF_8.name());

			System.out.println(result.toString("UTF-8"));

			return "File transfered successfully through SFTP";
		} catch (Exception ex) {
			ex.printStackTrace();
			channelSftp.exit();
			System.out.println("sftp Channel exited.");
			channel.disconnect();
			System.out.println("Channel disconnected.");
			session.disconnect();
			System.out.println("Host Session disconnected.");
			return ex.getMessage();
		} finally {
			channelSftp.exit();
			System.out.println("sftp Channel exited.");
			channel.disconnect();
			System.out.println("Channel disconnected.");
			session.disconnect();
			System.out.println("Host Session disconnected.");
		} 
	}
}

