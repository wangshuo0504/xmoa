package com.zkxy.xmoa.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 *<b>Description</b><br>
 *  邮件 发送 帮助类
 *  
 * @author DyLanM
 */
public class EmailUtil {
protected static Logger log = LoggerFactory.getLogger(EmailUtil.class);
	
    
    /**
     *<b>Description</b><br>
     * 邮件服务器IP或名称
     */
    private static String mailHost;
    
    /**
     *<b>Description</b><br>
     * 发送者的邮箱用户名
     */
    private static String senderUsername ;
    
    /**
     *<b>Description</b><br>
     *发送者的邮箱密码
     */
    private static String senderPassword ;
	
    private static Properties properties = new Properties();
    
    /**
     * <b>Description</b><br>
     * 发送邮件方  昵称
     * @author DyLanM
     */
    private static String NICKNAME; 
    
    /**
     * <b>Description</b><br>
     *  Executor框架的线程池
     */
    private static Executor executor = Executors.newFixedThreadPool(10);

    /**
     * <b>Description</b><br>
     * 
     * @author DyLanM
     */
    static {
        InputStream in = EmailUtil.class.getClassLoader().getResourceAsStream("email/MailServer.properties");
        try {
            properties.load(in);
            mailHost = properties.getProperty("mail.smtp.host");
            senderUsername = properties.getProperty("mail.sender.username");
            senderPassword = properties.getProperty("mail.sender.password");
            NICKNAME = properties.getProperty("mail.sender.nickname"); 
        } catch (IOException e) {
            e.printStackTrace();
            log.error("邮件服务 读取 配置文件异常", e);
        }
    }
   
    /**
     * <b>Description</b><br>
     *  异步发送邮件
     *  
     * @param  subject 邮件主题
     * @param  receiveUser  收件人邮箱
     * @param  copyToUser   抄送人邮箱   不需要可设置成 null
     * @param  blindCarbonCopyUser  密送人邮箱  不需要可设置成 null
     * @param  sendText  文本信息
     * @param  sendHtml  HTML信息
     * @param  fileList  附件File 对象
     * 
     * @author DyLanM
     * @return
     */
    public static boolean send( final String subject , final String[] receiveUser , final String[] copyToUser , final String[] blindCarbonCopyUser , final String sendText , final String sendHtml , final List<File> fileList ){
    			
    	Runnable task = new Runnable() {
		   @Override  
           public void run() {
		    	Session session = Session.getInstance( properties );
		        //session.setDebug(true);//开启后有调试信息
		    	MimeMessage message = new MimeMessage( session );
		    	Multipart mainPart = new MimeMultipart(); 
		        
		        // 下面这个是设置发送人的Nick name
		        
				try {
					InternetAddress from = new InternetAddress( MimeUtility.encodeWord( NICKNAME ) + "<"+senderUsername+">" );
					 message.setFrom(from);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					log.error(" 邮件服务 设置  发件方 昵称 异常", e);
				}  catch (AddressException e) {
					e.printStackTrace();
					log.error(" 邮件服务 创建 发件方异常", e);
				}catch (MessagingException e) {
					e.printStackTrace();
					log.error(" 邮件服务 设置 发件方 异常", e);
				}
				
				try {
					//收件人
					if( receiveUser != null && receiveUser.length > 0 ){
			            InternetAddress[] tos = new InternetAddress[ receiveUser.length ];  
			            
			            for (int i = 0 ,length = receiveUser.length; i < length; i++) {  
			            	tos[i] = new InternetAddress(receiveUser[i]);  
			            } 
					
			            message.setRecipients(Message.RecipientType.TO, tos);
					}
					//抄送人
					if( copyToUser != null && copyToUser.length > 0 ){
			            InternetAddress[] cc = new InternetAddress[ copyToUser.length ];  
			            
			            for (int i = 0 ,length = copyToUser.length; i < length; i++) {  
			            	cc[i] = new InternetAddress(copyToUser[i]);  
			            } 
					
			            message.setRecipients(Message.RecipientType.CC, cc);
					}
					//密送人
					if( blindCarbonCopyUser != null && blindCarbonCopyUser.length > 0 ){
			            InternetAddress[] bcc = new InternetAddress[ blindCarbonCopyUser.length ];  
			            
			            for (int i = 0 ,length = blindCarbonCopyUser.length; i < length; i++) {  
			            	bcc[i] = new InternetAddress(blindCarbonCopyUser[i]);  
			            } 
					
			            message.setRecipients(Message.RecipientType.BCC, bcc);
					}
			        // 邮件主题
			        message.setSubject( subject );
			        
				} catch (AddressException e) {
					e.printStackTrace();
					log.error(" 邮件服务 设置 收件方 异常", e);
			
				} catch (MessagingException e) {
					e.printStackTrace();
					log.error(" 邮件服务 设置 邮件主题 异常", e);
		
				}
				
		    	try {	 
			        // 创建一个包含HTML内容的MimeBodyPart   
			        BodyPart html = new MimeBodyPart();   
			        //设置HTML内容  
			        if ( sendHtml != null ){
			            html.setContent( sendHtml, "text/html; charset=utf-8");   
			        }
			        if ( sendText != null ){
			            html.setContent( sendText, "text/plain;charset=UTF-8");   
			        }
			        mainPart.addBodyPart(html);
				} catch (MessagingException e) {
					e.printStackTrace();
					log.error(" 邮件服务 设置 HTML内容形式 发送邮件 异常", e);
				}  
				
		    	if (fileList != null && fileList.size() > 0) {
		     	   for (File file : fileList) {
		   	    	  BodyPart attachmentBodyPart = new MimeBodyPart();
		 	          DataSource source = new FileDataSource( file );
		 	          try {
		 		          attachmentBodyPart.setDataHandler( new DataHandler(source) );
		 		         
		 		          attachmentBodyPart.setFileName( MimeUtility.encodeWord( source.getName() ) );
		 		          mainPart.addBodyPart( attachmentBodyPart );
		 			  } catch (MessagingException e) {
		 				 e.printStackTrace();
		 				 log.error(" 邮件服务 设置 添加 附件 异常", e);
		 			  } catch (UnsupportedEncodingException e) {
		 				 e.printStackTrace();
		 				 log.error(" 邮件服务 设置 添加 附件 异常", e);
		 			  }
		 		   }

		     	}
		    	Transport transport = null;
		    	  try {
		          	  //System.out.println("============= 多线程 发送===============");
		              message.setContent( mainPart, "text/html;charset=UTF-8");
		              // 保存邮件
		              message.saveChanges();
		          	
		              transport = session.getTransport("smtp");
		              // smtp验证，就是你用来发邮件的邮箱用户名密码
		              transport.connect(mailHost, senderUsername, senderPassword);
		              // 发送
		              transport.sendMessage(message, message.getAllRecipients());
		              //System.out.println("send success!");
		          } catch (Exception e) {
		              e.printStackTrace();
		              log.error(" 邮件服务 发送邮件  异常", e);
		          }finally {
		              if(transport!=null){
		                  try {
		                      transport.close();
		                  } catch (MessagingException e) {
		                      e.printStackTrace();
		                  }
		              }
		          }
				
		   }
       };
  
       executor.execute(task); 
	   return true;
    }
    
    public static void main(String[] args) {
    	
    	String[] ReceiveUser = {"mjdylan@163.com"};
    	ArrayList<File> fileList = new ArrayList<File>();
    	 File  file = new File( EmailUtil.class.getResource("").getPath()+"EmailUtil.class"  );
    	fileList.add(file);
    	
    	EmailUtil.send("邮件主题1-Text", ReceiveUser,null,null, "<html><b>我是来打酱油的呜呜呜呜</b></html>", null, null);
    	 System.out.println("============= 邮件主题1-Text  主线程===============");
    	EmailUtil.send("邮件主题2-HTML", ReceiveUser,null,null, null,"<html>The apache logo - <img src=\"http://www.apache.org/images/asf_logo_wide.gif\"></html>", null);
    	 System.out.println("============= 邮件主题2-HTML  主线程===============");
    	EmailUtil.send("邮件主题3-Text+附件", ReceiveUser,null,null, "<html><b>我是来打酱油的呜呜呜呜</b></html>", null, fileList);
    	 System.out.println("=============邮件主题3-Text+附件  主线程===============");
    	EmailUtil.send("邮件主题4-HTML+附件", ReceiveUser,null,null, null, "<html>The apache logo - <img src=\"http://www.apache.org/images/asf_logo_wide.gif\"></html>", fileList);
    	 System.out.println("=============邮件主题4-HTML+附件  主线程===============");
    	
    	//System.out.println( CopyOfEmailUtil.class.getResource("").getPath()+"EmailUtil.class" );
	}
}
