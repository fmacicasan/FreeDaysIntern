package freedays.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.roo.addon.javabean.RooJavaBean;
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
	private static final String DEFAULT_UPPER_REQUESTNOTIFICATION_SUBJECT = "Freedays - Request curtesy notification";
	private static final String DEFAULT_UPPER_REQUESTNOTIFICATION_CONTENT = "Hello %s,\n your subordinate %s has a new request to approve!\n %s \n\n";
    private static final String DEFAULT_UPPER_REQUESTNOTIFICATION_DENY_CONTENT="Hello %s,\n your subordinate %s denied the request!\n %s \n\n";
    private static final String DEFAULT_UPPER_REQUESTNOTIFICATION_CANCEL_CONTENT="Hello %s,\n the following request was canceled!\n %s \n\n ";
	private static final String DEFAULT_APPLICATION_LINK="\nFind us at {0}!";
    
	@Autowired
    private JavaMailSenderImpl mailSender;
    
    @Autowired
    private String applicationHome;
	
	/**
	 * Sends the simple mail message via the corresponding MailSender
	 * @param smm
	 * @see MailSender
	 * @see SimpleMailMessage
	 */
//	public void send(SimpleMailMessage smm){
//		mailSender.send(smm);
//	}
	
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
    	finalizeTo(tol);
    	String[] to = (String[])tol.toArray(new String[0]);
    	try{
    		helper.setTo(to);
    		helper.setSubject(subject);
    		helper.setText(finalizeContent(content),isHtml);
    		helper.setFrom(MailUtils.SOURCE);
    	
    		mailSender.send(mm);
    	} catch (MessagingException e) {
			
			e.printStackTrace();
		}
	}
	private void finalizeTo(final List<String> tol) {
		tol.add("fmacicasan@sdl.com");
	}
	private String finalizeContent(final String content){
		StringBuilder sb = new StringBuilder();
		sb.append(content).append("\n");
		sb.append(MessageFormat.format(MailUtils.DEFAULT_APPLICATION_LINK, this.getApplicationHome())).append("\n\n");
		return sb.toString();
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
//    			SimpleMailMessage mail = new SimpleMailMessage();
//        		mail.setTo(to);
//        		mail.setSubject(subject);
//        		mail.setText(content);
//        		mail.setFrom(MailUtils.SOURCE);
        		MailUtils mu = new MailUtils();
        		mu.sendPlainMsg(to, subject, content);
 //       		mu.send(mail);
        		
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
//    	            //: solve problem with disabled gmail account
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
		MailUtils.sendAsyncMail(adminemails,  MailUtils.DEFAULT_REGISTERNOTIFICATION_SUBJECT, content);
	}

	public static void sendPostRegisterProcessing(final String fullname, final String email) {
		final String content = String.format(MailUtils.DEFAULT_POSTPROCESSINGNOTIF_CONTENT,fullname);
		MailUtils.sendAsyncMail(email,  MailUtils.DEFAULT_POSTPROCESSINGNOTIF_SUBJECT, content);
	}
	
	public static void sendUpperRequestNotification(final String email, final String superapprover, final String approver, final String request){
		final String content = String.format(MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_CONTENT,superapprover,approver,request);
		MailUtils.sendAsyncMail(email,  MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_SUBJECT, content);
	}
	
	public static void sendUpperRequestDenyNotification(final String email, final String superapprover, final String approver, final String request){
		final String content = String.format(MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_DENY_CONTENT,superapprover,approver,request);
		MailUtils.sendAsyncMail(email,  MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_SUBJECT, content);
	}
	
	public static void sendUpperRequestCancelNotification(final String email, final String superapprover, final String request){
		final String content = String.format(MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_CANCEL_CONTENT,superapprover,request);
		MailUtils.sendAsyncMail(email,  MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_SUBJECT, content);
	}
	
	
	
	public static void sendAsyncMail(final String email, final String subject, final String content){
		List<String> lst = new ArrayList<String>();
		lst.add(email);
		MailUtils.sendAsyncMail(lst, subject, content);
	}
	public static void sendAsyncMail(final List<String> email, final String subject, final String content){
		Thread th = new Thread(){
			@Override
			public void run(){
				MailUtils mu = new MailUtils();
				mu.sendPlainMsg(email, subject, content);
			}
		};
		th.start();
	}
    
}
    
