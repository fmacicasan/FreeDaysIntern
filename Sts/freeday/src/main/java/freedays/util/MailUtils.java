package freedays.util;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Component;

import freedays.domain.ApplicationRegularUser;
import freedays.security.InfoChanger;

/**
 * Mail Utilities
 * 
 */
@Component
@RooJavaBean
@Configurable
public class MailUtils {

	private final Log log = LogFactory.getLog(this.getClass());

	private static final String DEFAULT_REGISTERNOTIFICATION_SUBJECT = "HRApp - New User Notification";
	private static final String DEFAULT_REGISTERNOTIFICATION_CONTENT = "New user registration!\nPlease process the account of %s.\n";
	private static final String DEFAULT_POSTPROCESSINGNOTIF_SUBJECT = "HRApp - Account activation";
	private static final String DEFAULT_POSTPROCESSINGNOTIF_CONTENT = "Hello %s,\n your account has been processed.\n";
	private static final String SOURCE = "hrapp@sdl.com";
	private static final String DEFAULT_UPPER_REQUESTNOTIFICATION_SUBJECT = "HRApp - Request curtesy notification";
	private static final String DEFAULT_UPPER_REQUESTNOTIFICATION_CONTENT = "Hello %s,\n your subordinate %s has a new request to approve!\n %s \n\n";
	private static final String DEFAULT_UPPER_REQUESTNOTIFICATION_DENY_CONTENT = "Hello %s,\n your subordinate %s denied the request!\n %s \n\n";
	private static final String DEFAULT_UPPER_REQUESTNOTIFICATION_CANCEL_CONTENT = "Hello %s,\n the following request was canceled!\n %s \n\n ";
	private static final String DEFAULT_APPLICATION_LINK = "\nFind us at {0}!";
	private static final String DEFAULT_LOWER_REQUESTNOTIFICATION_ONUPPEREVENT = "Hello %s,\n your boss %s already %s the request!\n %s \n\n";

	private static final String RESET_PASS_TITLE = "HRApp-PasswordReset";
	private static final String RESET_PASS_MESSAGE = "Your new password is:\n\t\t\t {0}";
	private static final String RESET_PASS_MESSAGE_TOKEN = "To start the password reset process for your %s HRApp account click on the link bellow:\n\n %s/recoverpass?token=%s\n\nFor security reasons this link will expire in %d hours!";

	private static final String DEFAULT_TIMESHEET_CONTENT = "Hello %s,\n You can find attached your auto-generated timesheet.\n This feature is still under construction so please report any problems.\n\n";
	private static final String DEFAULT_TIMESHEET_SUBJECT = "HRApp - Timesheets";

	@Autowired
	private JavaMailSenderImpl mailSender;

	/**
	 * Sends the simple mail message via the corresponding MailSender
	 * 
	 * @param smm
	 * @see MailSender
	 * @see SimpleMailMessage
	 */
	// public void send(SimpleMailMessage smm){
	// mailSender.send(smm);
	// }

	public void sendHtmlMsg(final List<String> to, final String subject,
			final String content) {
		this.sendMsg(to, subject, content, true, null);
	}

	public void sendPlainMsg(final List<String> to, final String subject,
			final String content) {
		this.sendMsg(to, subject, content, false, null);
	}

	public void sendPlainMsg(final List<String> to, final String subject,
			final String content, final File f) {
		this.sendMsg(to, subject, content, false, f);
	}

	public void sendHtmlMsg(final String to, final String subject,
			final String content) {
		List<String> l = new ArrayList<String>();
		l.add(to);
		this.sendHtmlMsg(l, subject, content);
	}

	public void sendPlainMsg(final String to, final String subject,
			final String content) {
		List<String> l = new ArrayList<String>();
		l.add(to);
		this.sendPlainMsg(l, subject, content);
	}

	protected void sendMsg(final List<String> tol, final String subject,
			final String content, final boolean isHtml, final File f) {
		MimeMessage mm = mailSender.createMimeMessage();
		int multipart = MimeMessageHelper.MULTIPART_MODE_NO;
		if (f != null) {
			multipart = MimeMessageHelper.MULTIPART_MODE_RELATED;
		}
		finalizeTo(tol);
		String[] to = (String[]) tol.toArray(new String[0]);
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mm, multipart);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(finalizeContent(content), isHtml);
			helper.setFrom(MailUtils.SOURCE);
			if (f != null) {

				helper.addAttachment(f.getName(), f);
			}
			 mailSender.send(mm);
			log.info(String
					.format("Message with subject:\n\t %s\n and content:\n\t %s\n went ok to %s!",
							subject, content, tol.toString()));
		} catch (MessagingException e) {
			log.error("Problem at mail sending", e);
		}
	}

	private void finalizeTo(final List<String> tol) {
		tol.add("osuciu@sdl.com");
		tol.add("fmacicasan@sdl.com");
		// add HR ppl to email flows
		// tol.addAll(ApplicationRegularUser.findAllHRManagementEmails());
	}

	private String finalizeContent(final String content) {
		StringBuilder sb = new StringBuilder();
		sb.append(content).append("\n");
		sb.append(
				MessageFormat.format(MailUtils.DEFAULT_APPLICATION_LINK,
						PropertiesUtil.getProperty("applicationHome"))).append(
				"\n\n");
		return sb.toString();
	}

	// @Autowired
	// private transient MailSender mailSender;
	//
	// private transient SimpleMailMessage simpleMailMessage;
	//
	// public void sendMessage(String mailTo, String message) {
	// simpleMailMessage.setTo(mailTo);
	// simpleMailMessage.setText(message);
	// mailSender.send(simpleMailMessage);
	// }

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
	public static void send(final String to, final String subject,
			final String content) {
		// change with Executor and Runnable or some other spring thingy
		Thread th = new Thread() {

			@Override
			public void run() {
				// SimpleMailMessage mail = new SimpleMailMessage();
				// mail.setTo(to);
				// mail.setSubject(subject);
				// mail.setText(content);
				// mail.setFrom(MailUtils.SOURCE);
				MailUtils mu = new MailUtils();
				mu.sendPlainMsg(to, subject, content);
				// mu.send(mail);

				// //// //this.checkOperation();
				// Properties props = new Properties();
				// ////// props.put("mail.smtp.host", "smtp.gmail.com");
				// ////// props.put("mail.smtp.socketFactory.port", "465");
				// ////// props.put("mail.smtp.socketFactory.class",
				// ////// "javax.net.ssl.SSLSocketFactory");
				// ////// props.put("mail.smtp.auth", "true");
				// ////// props.put("mail.smtp.port", "465");
				// //// // props.put("mail.debug", "true");
				// ////
				// ////
				// props.put("mail.smtp.host", "clujservices01");
				// //// //props.put("mail.smtp.socketFactory.port", "465");
				// ////// props.put("mail.smtp.socketFactory.class",
				// ////// "javax.net.ssl.SSLSocketFactory");
				// props.put("mail.smtp.auth", "false");
				// //// //props.put("mail.smtp.port", "465");
				// props.put("mail.smtp.port", "25");
				// //// // props.put("mail.debug", "true");
				// ////
				// ////// Session session = Session.getDefaultInstance(props,
				// ////// new javax.mail.Authenticator() {
				// ////// protected PasswordAuthentication
				// getPasswordAuthentication() {
				// ////// return new PasswordAuthentication(
				// ////// MailUtils.SOURCE, MailUtils.PASS);
				// ////// }
				// ////// });
				// Session session = Session.getDefaultInstance(props, null);
				// ////
				// try {
				// Message message = new MimeMessage(session);
				// message.setFrom(new InternetAddress(MailUtils.SOURCE));
				// message.setRecipients(Message.RecipientType.TO,
				// InternetAddress.parse(to));
				// message.setSubject(subject);
				// message.setText(content);
				//
				// //: solve problem with disabled gmail account
				// Transport.send(message);
				//
				// } catch (MessagingException e) {
				// e.printStackTrace();
				// }
			}

			// private void checkOperation(){
			// System.out.println(" I Will be formatting html mail and sending it  ");
			// try {
			// Thread.sleep(5000);
			//
			// } catch (InterruptedException e) {
			//
			// e.printStackTrace();
			// }
			//
			// System.out.println(" Asynchronous method call of send email � Complete ");
			// }

		};
		th.start();
	}

	public static void sendHtml(final String to, final String subject,
			final String content) {
		// change with Executor and Runnable or some other spring thingy
		Thread th = new Thread() {

			@Override
			public void run() {
				MailUtils mu = new MailUtils();

				mu.sendHtmlMsg(to, subject, content);
			}

		};
		th.start();
	}

	public static void sendAfterRegisterNotification(final String fullname,
			final List<String> adminemails) {
		final String content = String.format(
				MailUtils.DEFAULT_REGISTERNOTIFICATION_CONTENT, fullname);
		MailUtils.sendAsyncMail(adminemails,
				MailUtils.DEFAULT_REGISTERNOTIFICATION_SUBJECT, content);
	}

	public static void sendPostRegisterProcessing(final String fullname,
			final String email) {
		final String content = String.format(
				MailUtils.DEFAULT_POSTPROCESSINGNOTIF_CONTENT, fullname);
		MailUtils.sendAsyncMail(email,
				MailUtils.DEFAULT_POSTPROCESSINGNOTIF_SUBJECT, content);
	}

	public static void sendUpperRequestNotification(final String email,
			final String superapprover, final String approver,
			final String request) {
		final String content = String.format(
				MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_CONTENT,
				superapprover, approver, request);
		MailUtils.sendAsyncMail(email,
				MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_SUBJECT, content);
	}

	public static void sendUpperRequestDenyNotification(final String email,
			final String superapprover, final String approver,
			final String request) {
		final String content = String.format(
				MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_DENY_CONTENT,
				superapprover, approver, request);
		MailUtils.sendAsyncMail(email,
				MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_SUBJECT, content);
	}

	public static void sendUpperRequestCancelNotification(final String email,
			final String superapprover, final String request) {
		final String content = String.format(
				MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_CANCEL_CONTENT,
				superapprover, request);
		MailUtils.sendAsyncMail(email,
				MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_SUBJECT, content);
	}

	public static void sendResetPasswordNotification(final String email,
			final String newPass) {
		final String content = MessageFormat.format(
				MailUtils.RESET_PASS_MESSAGE, newPass);
		MailUtils.sendAsyncMail(email, MailUtils.RESET_PASS_TITLE, content);
	}

	public static void send2LowerOnSuperApproveNotification(final String email,
			final String actualapprover, final String superapprover,
			final String outcome, final String request) {
		final String content = String.format(
				MailUtils.DEFAULT_LOWER_REQUESTNOTIFICATION_ONUPPEREVENT,
				actualapprover, superapprover, outcome, request);
		MailUtils.sendAsyncMail(email,
				MailUtils.DEFAULT_UPPER_REQUESTNOTIFICATION_SUBJECT, content);
	}

	public static void sendAsyncMail(final String email, final String subject,
			final String content) {
		MailUtils.sendAsyncMail(email, subject, content, null);
	}

	public static void sendAsyncMail(final List<String> email,
			final String subject, final String content) {
		MailUtils.sendAsyncMail(email, subject, content, null);
	}

	public static void sendAsyncMail(final String email, final String subject,
			final String content, final File f) {
		List<String> lst = new ArrayList<String>();
		lst.add(email);
		MailUtils.sendAsyncMail(lst, subject, content, f);
	}

	public static void sendAsyncMail(final List<String> email,
			final String subject, final String content, final File f) {
		Thread th = new Thread() {
			@Override
			public void run() {
				MailUtils mu = new MailUtils();
				mu.sendPlainMsg(email, subject, content, f);
			}
		};
		th.start();
	}

	public static void sendResetPasswordTokenNotification(String email,
			String token) {
		final String content = String.format(
				MailUtils.RESET_PASS_MESSAGE_TOKEN, email,
				PropertiesUtil.getProperty("applicationHome"), token,
				InfoChanger.DEFAULT_EXPIRE_INTERVAL_IN_HOURS);
		// System.out.println("reset pass token content:"+content);
		MailUtils.sendAsyncMail(email, RESET_PASS_TITLE, content);

	}

	public static void sendTimesheet(String email, String person, File f) {
		final String content = String.format(
				MailUtils.DEFAULT_TIMESHEET_CONTENT, person);
		
		System.out.println("the name in send is:" + f.getName());
		List<String> emails = new ArrayList<String>();
		emails.add(email);
		emails.add(PropertiesUtil.getProperty("timesheetHRDestinationAddress"));
		MailUtils.sendAsyncMail(emails, DEFAULT_TIMESHEET_SUBJECT + "-"
				+ person, content, f);

	}

}
