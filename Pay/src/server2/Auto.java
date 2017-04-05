package server2;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import database.ClientDAO;
import database.InvoiceDAO;
import database.MyClientsDAO;
import entity.Client;
import entity.Invoice;
import entity.MyClient;


	/**
	 * Class for Handling the Automation Process
	 * It runs in a separate Thread and generates Invoices, bans Clients, and pays their Invoices accordingly
	 */
public class Auto implements Runnable {
	
	

	private static List<MyClient> myClients;

	private static MyClientsDAO myClientsDAO = new MyClientsDAO();
	private static ClientDAO clientDAO = new ClientDAO();
	private static InvoiceDAO facturaDAO = new InvoiceDAO();

	
	@Override
	public void run() {
		
		while (!Thread.currentThread().isInterrupted()) {

			try {
				// asteptare pentru generare facturi
				Thread.sleep(3000);
			} catch (InterruptedException e) {

			}
			System.out.println("Start executing");
			myClients = myClientsDAO.getAll();

			for (MyClient mc : myClients) {
				generareFactura(mc);
			}
		}
	}

	
	private static void generareFactura(MyClient mc) {
		Date date = new Date();
		Client client = mc.getClient();
		List<Invoice> invoices = facturaDAO.getByUnpaid(client, mc.getSupplier());
		Invoice invoice = new Invoice();
		
		if (mc.getBanned()) {
			for(Invoice f : invoices){
				synchronized(f){
					double amenda = f.getCurrentValue()*mc.getSupplier().getPenalty()*30;
					facturaDAO.updateField(f, InvoiceDAO.FINE_VALUE, String.format("%.2f",(amenda + f.getFineValue())));
					facturaDAO.updateField(f, InvoiceDAO.TOTAL_VALUE, String.format("%.2f",( f.getTotalValue()+amenda)));
				}
			}
		//emitere facturi	
		}else if(invoices.size()<3){
			invoice.setClient(client);
			invoice.setSupplier(mc.getSupplier());
			invoice.setReleaseDate(date);
			invoice.setMaturityDate(asDate(asLocalDate(date).plusDays(mc.getSupplier().getMaturityDays())));
			invoice.setTotalValue(Double.parseDouble(String.format("%.2f",(Math.random() * 600 + 50 ))));
			invoice.setCurrentValue(invoice.getTotalValue());
			
			
			if(invoices.size() > 0 && invoices.size() < 3){
				System.out.println("client = "+ client.getId() );
				for(Invoice f : invoices){
					synchronized(f){
						double fineValue = f.getCurrentValue()*mc.getSupplier().getPenalty()*30;
						facturaDAO.updateField(f, InvoiceDAO.FINE_VALUE, String.format("%.2f",(fineValue + f.getFineValue())));
						facturaDAO.updateField(f, InvoiceDAO.TOTAL_VALUE, String.format("%.2f",  (f.getTotalValue()+fineValue)));
					}
				}
			}
			
			if(mc.getSubscribed() && client.getValue() >= invoice.getTotalValue()){
				double newValue = client.getValue() - invoice.getTotalValue();
				synchronized (client) {
					client.setValue(newValue);
					clientDAO.updateField(client, ClientDAO.VALUE,Double.toString(newValue));
				}
				invoice.setPaid(true);
				invoice.setResidual(false);
			}
			System.out.println("Adaug factura");
			facturaDAO.add(invoice);
			/*
			 * Send Email : Invoice has been released
			 */
			if(invoice.getPaid()){
				//email = released + paid
				new Thread(new SendEmail(invoice,2)).start();
			}else{
				//email = released + needs to be paid
				new Thread(new SendEmail(invoice,1)).start();
			}
			
		}else if(invoices.size() == 3){
			synchronized(mc){
				myClientsDAO.updateBanned(mc, true);
			}
			/*
			 * MAIL = services of the supplier have been cut down
			 */
			new Thread(new SendEmail(invoices.get(0),3)).start();
			for(Invoice f : invoices){
				synchronized(f){
					double amenda = f.getCurrentValue()*mc.getSupplier().getPenalty()*30;
					facturaDAO.updateField(f, InvoiceDAO.FINE_VALUE, String.format("%.2f",(amenda + f.getFineValue())));
					facturaDAO.updateField(f, InvoiceDAO.TOTAL_VALUE, String.format("%.2f", ( f.getTotalValue()+amenda)));
				}
			}
		}
	}

	
	private static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	private static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
