package freedays.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Mail Utilities
 * 
 */
//@Component
public class MailUtils {
	private static final String SOURCE = "internlwtest@gmail.com";
	private static final String PASS = "weaver01";
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
	//@Async
    public static void send(final String to, final String subject, final String content) {
		//change with Executor and Runnable or some other spring thingy
    	Thread th = new Thread(){
    		public void run(){
    			//this.checkOperation();
    			Properties props = new Properties();
    	        props.put("mail.smtp.host", "smtp.gmail.com");
    	        props.put("mail.smtp.socketFactory.port", "465");
    	        props.put("mail.smtp.socketFactory.class",
    	                "javax.net.ssl.SSLSocketFactory");
    	        props.put("mail.smtp.auth", "true");
    	        props.put("mail.smtp.port", "465");
    	        // props.put("mail.debug", "true");
    	        
    	        Session session = Session.getDefaultInstance(props,
    	                new javax.mail.Authenticator() {
    	                    protected PasswordAuthentication getPasswordAuthentication() {
    	                        return new PasswordAuthentication(
    	                                MailUtils.SOURCE, MailUtils.PASS);
    	                    }
    	                });

    	        try {
    	            Message message = new MimeMessage(session);
    	            message.setFrom(new InternetAddress(MailUtils.SOURCE));
    	            message.setRecipients(Message.RecipientType.TO,
    	                    InternetAddress.parse(to));
    	            message.setSubject(subject);
    	            message.setText(content);

    	            Transport.send(message);

    	        } catch (MessagingException e) {
    	            throw new RuntimeException(e);
    	        }
    		}
    		
    		private void checkOperation(){
    			System.out.println(" I Will be formatting html mail and sending it  ");
				try {
				Thread.sleep(5000);
		
				} catch (InterruptedException e) {
		
				e.printStackTrace();
				}
		
				System.out.println(" Asynchronous method call of send email � Complete ");
    		}
    	};
    	th.start();
        
    }
    
    public static void main(String[] args){
    	//test works for concurrent sending
    	MailUtils.send("iulia_teglas@yahoo.com", "Hello", "testam sa nuramaname");
    	MailUtils.send("burtoflex89@yahoo.com","Hello","cucurigu sa nu iti muti cuibu");
    }
}
