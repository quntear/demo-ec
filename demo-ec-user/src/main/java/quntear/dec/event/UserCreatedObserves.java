package quntear.dec.event;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.event.ObservesAsync;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import quntear.dec.entity.User;

@Stateless
public class UserCreatedObserves {

	@Resource(lookup = "mail/fakeSMTP2.0")
    private Session mailSession;

	public void sendEmail(@ObservesAsync @UserCreated User entity) {
		MimeMessage message = new MimeMessage(mailSession);
		
		try {
			message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
			InternetAddress[] address = { new InternetAddress(entity.getEmail()) };
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject("Please confirm");
			message.setSentDate(new Date());
			message.setText(String.format("http://localhost:8080/demo-ec-user/resources/users/%s/actions/activate", entity.getId()));

			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
