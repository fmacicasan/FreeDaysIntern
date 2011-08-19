package freedays.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * Mail Utilities
 * 
 */
@Component
@RooJavaBean
@Configurable
public class MailUtils {
	private static final String DEFAULT_REGISTERNOTIFICATION_SUBJECT = "FreeDays - New User Notification";
	private static final String DEFAULT_REGISTERNOTIFICATION_CONTENT = "New user registration!\nPlease process the account of %s.\n";
	private static final String DEFAULT_POSTPROCESSINGNOTIF_SUBJECT = "FreeDays - Account activation";
	private static final String DEFAULT_POSTPROCESSINGNOTIF_CONTENT = "Hello %s,\n your account has been processed.\n";
	private static final String SOURCE = "internlwtest@sdl.com";
    
	@Autowired
    private JavaMailSenderImpl mailSender;
	
	/**
	 * Sends the simple mail message via the corresponding MailSender
	 * @param smm
	 * @see MailSender
	 * @see SimpleMailMessage
	 */
	public void send(SimpleMailMessage smm){
		mailSender.send(smm);
	}
	
	public void sendHtmlMsg(final List<String> to, final String subject, final String content){
    	this.sendMsg(to, subject, content, true);
    }
	public void sendPlainMsg(final List<String> to, final String subject, final String content){
    	this.sendMsg(to, subject, content, false);
    }
	public void sendHtmlMsg(final String to, final String subject, final String content){
		List<String> l = new ArrayList<String>();
		l.add(to);
    	this.sendMsg(l, subject, content, true);
    }
	public void sendPlainMsg(final String to, final String subject, final String content){
		List<String> l = new ArrayList<String>();
		l.add(to);
    	this.sendMsg(l, subject, content, false);
    }
	
	protected void sendMsg(final List<String> tol, final String subject, final String content, final boolean isHtml){
		MimeMessage mm = mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mm);
    	tol.add("fmacicasan@sdl.com");
    	String[] to = (String[])tol.toArray(new String[0]);
    	try{
    		helper.setTo(to);
    		helper.setSubject(subject);
    		helper.setText(content,isHtml);
    		helper.setFrom(MailUtils.SOURCE);
    		mailSender.send(mm);
    	} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	@Autowired
//    private transient MailSender mailSender;
//
//    private transient SimpleMailMessage simpleMailMessage;
//	
//    public void sendMessage(String mailTo, String message) {
//        simpleMailMessage.setTo(mailTo);
//        simpleMailMessage.setText(message);
//        mailSender.send(simpleMailMessage);
//    }
    
	/**
     * <b>public static void send (String to, String subject, String
     * content)</b>
     * <p>
     * Sends an e-mail to the given address, subject and content
     * 
     * @param to
     *            - Destination address
     * @param subject
     *            - Subject
     * @param content
     *            - Content
     */
    public static void send(final String to, final String subject, final String content) {
		//change with Executor and Runnable or some other spring thingy
    	Thread th = new Thread(){
    		
    		
    		@Override
    		public void run(){
    			SimpleMailMessage mail = new SimpleMailMessage();
        		mail.setTo(to);
        		mail.setSubject(subject);
        		mail.setText(content);
        		mail.setFrom(MailUtils.SOURCE);
        		MailUtils mu = new MailUtils();
        		
        		mu.send(mail);
        		
//////    			//this.checkOperation();
//   			Properties props = new Properties();
////////    	        props.put("mail.smtp.host", "smtp.gmail.com");
////////    	        props.put("mail.smtp.socketFactory.port", "465");
////////    	        props.put("mail.smtp.socketFactory.class",
////////    	                "javax.net.ssl.SSLSocketFactory");
////////    	        props.put("mail.smtp.auth", "true");
////////    	        props.put("mail.smtp.port", "465");
//////    	        // props.put("mail.debug", "true");
//////    			
//////    			
//   	        props.put("mail.smtp.host", "clujservices01");
//////    	        //props.put("mail.smtp.socketFactory.port", "465");
////////    	        props.put("mail.smtp.socketFactory.class",
////////    	                "javax.net.ssl.SSLSocketFactory");
//   	        props.put("mail.smtp.auth", "false");
//////    	        //props.put("mail.smtp.port", "465");
//   	        props.put("mail.smtp.port", "25");
//////    	        // props.put("mail.debug", "true");
//////    	        
////////    	        Session session = Session.getDefaultInstance(props,
////////    	                new javax.mail.Authenticator() {
////////    	                    protected PasswordAuthentication getPasswordAuthentication() {
////////    	                        return new PasswordAuthentication(
////////    	                                MailUtils.SOURCE, MailUtils.PASS);
////////    	                    }
////////    	                });
//   	        Session session = Session.getDefaultInstance(props, null);
//////
//    	        try {
//    	            Message message = new MimeMessage(session);
//    	            message.setFrom(new InternetAddress(MailUtils.SOURCE));
//    	            message.setRecipients(Message.RecipientType.TO,
//    	                    InternetAddress.parse(to));
//    	            message.setSubject(subject);
//    	            message.setText(content);
//    	            
//    	            //TODO: solve problem with disabled gmail account
//    	            Transport.send(message);
//
//    	        } catch (MessagingException e) {
//    	            e.printStackTrace();
//    	        }
    		}
    		
//    		private void checkOperation(){
//    			System.out.println(" I Will be formatting html mail and sending it  ");
//				try {
//				Thread.sleep(5000);
//		
//				} catch (InterruptedException e) {
//		
//				e.printStackTrace();
//				}
//		
//				System.out.println(" Asynchronous method call of send email � Complete ");
//    		}

    	};
    	th.start();
    }
    
    public static void sendHtml(final String to, final String subject, final String content) {
		//change with Executor and Runnable or some other spring thingy
    	Thread th = new Thread(){
    		
    		@Override
    		public void run(){
        		MailUtils mu = new MailUtils();
        		
        		mu.sendHtmlMsg(to,subject,content);
    		}


    	};
    	th.start();
    }

	public static void sendAfterRegisterNotification(final String fullname, final List<String> adminemails) {
		final String content = String.format(MailUtils.DEFAULT_REGISTERNOTIFICATION_CONTENT, fullname);
		Thread th = new Thread(){
			@Override
			public void run(){
				MailUtils mu = new MailUtils();
				mu.sendPlainMsg(adminemails, MailUtils.DEFAULT_REGISTERNOTIFICATION_SUBJECT, content);
			}
		};
		th.start();
		
	}

	public static void sendPostRegisterProcessing(final String fullname, final String email) {
		final String content = String.format(MailUtils.DEFAULT_POSTPROCESSINGNOTIF_CONTENT,fullname);
		Thread th = new Thread(){
			@Override
			public void run(){
				MailUtils mu = new MailUtils();
				mu.sendPlainMsg(email, MailUtils.DEFAULT_POSTPROCESSINGNOTIF_SUBJECT, content);
			}
		};
		th.start();
		
	}
    
}
    
