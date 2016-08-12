package com.o2r.helper;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHConnection {
	
private Session sesion; //represents each ssh session
org.springframework.core.io.Resource resource = new ClassPathResource(
		"database.properties");
static Logger log = Logger.getLogger(SSHConnection.class.getName());

Properties props = null;
public void closeSSH ()
{
	log.info("******************Disconnecting jsch session*************");
    sesion.disconnect();
}

public SSHConnection () throws Throwable
{

    JSch jsch = null;

        jsch = new JSch();
       /* jsch.setKnownHosts(S_PATH_FILE_KNOWN_HOSTS);
        jsch.addIdentity(S_PATH_FILE_PRIVATE_KEY, S_PASS_PHRASE.getBytes());*/
      try
      {
    	  props = PropertiesLoaderUtils.loadProperties(resource);
    	  if(props!=null)
    		  
    	  {
    		  
        sesion = jsch.getSession(props.getProperty("ssh_user"), props.getProperty("ssh_remote_server"),
        		Integer.parseInt(props.getProperty("ssh_remote_port")));
        sesion.setConfig("StrictHostKeyChecking", "no");
        sesion.setPassword(props.getProperty("ssh_password"));
       log.info(" Before Connecting to Remote DB");
        sesion.connect(); //ssh connection established!
        //by security policy, you must connect through a fowarded port          
        int assinged_port=sesion.setPortForwardingL(Integer.parseInt(props.getProperty("local_port")),
        		props.getProperty("mysql_remote_server"), Integer.parseInt(props.getProperty("remote_port"))); 
        log.info(" Got Connected "+assinged_port);
    		  }
      
      	  
      }catch(Exception e)
      {
    	  log.error("Failed in establishing connection to db ", e);
    	  e.printStackTrace();
    	  System.out.println(" Error : "+e.getMessage());
      }

}

}
