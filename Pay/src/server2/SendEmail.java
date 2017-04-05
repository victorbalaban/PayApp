package server2;

import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import entity.Invoice;


/**
 *Class designed for sending an email regarding the Invoices of a Client
 */
public class SendEmail implements Runnable{

	private int choice;
	private Invoice invoice;

	/**
	 * 
	 */
	public SendEmail(Invoice invoice, int choice) {
		this.invoice = invoice;
		this.choice = choice;

	}

	/**
	 * selects the message of the email to be sent
	 */
	public String selectMessage() {

		switch (choice) {
		case 1: {
			return "Stimat(a) " + invoice.getClient().getName() + " " + invoice.getClient().getSurname()
					+ "\nFactura dvs. la " + invoice.getSupplier().getFirmName() + " a fost emisa." + "\nAveti de plata "
					+ invoice.getTotalValue() + " RON, pana in data de " + new SimpleDateFormat("dd.MM.yyyy").format(invoice.getMaturityDate()) + "\n\nVa multumim !";
		}
		case 2: {
			return "Stimat(a) " + invoice.getClient().getName() + " " + invoice.getClient().getSurname()
					+ "\nFactura dvs. la " + invoice.getSupplier().getFirmName() + " a fost emisa."
					+ "\nFactura a fost platita. S-au extras " + invoice.getTotalValue() + " RON din contul dvs."
					+ "\n\nVa multumim !";
		}
		case 3: {
			return "Stimat(a) " + invoice.getClient().getName() + " " + invoice.getClient().getSurname()
					+ "\nAveti de plata 3 facturi restante la" + invoice.getSupplier().getFirmName()
					+ "\nFurnizorul a oprit furnizarea de servicii pana platiti una dintre cele 3 facturi restante\n\nVa multumim !";
		}
		case 4: {
			return "Stimat(a) " + invoice.getClient().getName() + " " + invoice.getClient().getSurname()
					+ "\nFactura dvs. din data de "+new SimpleDateFormat("dd.MM.yyyy").format(invoice.getReleaseDate())+" a fost platita. S-au extras " + invoice.getTotalValue() + " RON din contul dvs."
					+ "\n\nVa multumim !";
		}
		}
		return null;
	}

	
	/**
	 * selects the subject of the email to be sent
	 * @return
	 */
	public String selectSubject() {
		switch (choice) {
		case 1:
			return "Factura emisa";
		case 2:
			return "Factura emisa si platita";
		case 3:
			return "Servicii oprite";
		case 4:
			return "Factura platita";
		}
		return null;
	}

	
	@Override
	public void run() {
		final String username = "easypayapplication@gmail.com";
		final String password = "pay13579";
		
		
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("easypayapplication@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(invoice.getClient().getEmail()));
			message.setSubject(selectSubject());
			message.setText(selectMessage());
			Transport.send(message);

			System.out.println("Email Sent");

		} catch (MessagingException e) {
			System.out.println("Nu s-a putut trimite emailul.No connection");
		}
	}
}