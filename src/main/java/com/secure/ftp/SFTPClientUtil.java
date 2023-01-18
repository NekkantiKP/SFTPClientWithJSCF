package com.secure.ftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SFTPClientUtil {
	
	private ChannelSftp setupJsch() throws JSchException {
		String remoteHost="ftp.chinasystems-me.com";
		String username="nedbank";
		String password="Nedbank5pecial@ccess";
			
	    JSch jsch = new JSch();
	   // jsch.setKnownHosts("/Users/john/.ssh/known_hosts");
	    java.util.Properties config = new java.util.Properties();
	    config.put("StrictHostKeyChecking", "no");
	    
	    Session jschSession = jsch.getSession(username, remoteHost);
	    jschSession.setPassword(password);
	    jschSession.setConfig(config);

	    jschSession.connect();
	    return (ChannelSftp) jschSession.openChannel("sftp");
	}
	
	private ChannelSftp openSession() throws JSchException {
		
		JSch.setConfig("StrictHostKeyChecking", "no"); // Not recommended, but useful
	    JSch jsch = new JSch();
	    //jsch.setKnownHosts(sshKnownHostsFile);
	    int port = Integer.valueOf("21");

	    Session session = jsch.getSession("nedbank", "ftp.chinasystems-me.com",22);

//	    java.util.Properties config = new java.util.Properties();
//	    config.put("StrictHostKeyChecking", "no");
//	    session.setConfig(config);
	    session.setPassword("Nedbank5pecial@ccess");
	    session.connect();
	    Channel channel = session.openChannel("ftp");
	    channel.connect();
	    ChannelSftp channelSftp = (ChannelSftp) channel;

	    return channelSftp;
	}
	
	public void connect() throws JSchException {
		ChannelSftp channelSftp = setupJsch();
	    channelSftp.connect();
	}
	public static void main(String[] args) {
		try {
			SFTPClientUtil clientUtil=new SFTPClientUtil();
			clientUtil.openSession();
			System.out.println("Seesion opend");
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

}
