package com.secure.ftp;

import java.io.IOException;
import java.util.List;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier; 
  
public class SftpListFiles { 
    public static void main(String[] args) throws IOException { 
        // create a instance of SSHClient 
        SSHClient client = new SSHClient(); 
  
        // add host key verifier 
        client.addHostKeyVerifier(new PromiscuousVerifier()); 
        client.authPassword("nedbank", "Nedbank5pecial@ccess"); 

        // connect to the sftp server 
        client.connect( "ftp.chinasystems-me.com"); 
  
        // authenticate by username and password. 
  
        // get new sftpClient. 
        SFTPClient sftpClient = client.newSFTPClient(); 
  
        // Give the path to the directory from which 
        // you want to get a list of all the files. 
        String remoteDir = "/test_user/demo"; 
        List<RemoteResourceInfo> resourceInfoList = sftpClient.ls(remoteDir); 
  
        for (RemoteResourceInfo file : resourceInfoList) { 
            System.out.printf("File name is %s", file.getName()); 
            System.out.println(""); 
        } 
    } 
} 
