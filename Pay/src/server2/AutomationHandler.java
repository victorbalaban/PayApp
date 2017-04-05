package server2;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import database.ClientDAO;
import database.InvoiceDAO;
import database.MyClientsDAO;
import entity.Client;
import entity.Invoice;
import entity.MyClient;

public class AutomationHandler implements Runnable {

	private static List<MyClient> myClients;
	private static MyClientsDAO myClientsDAO = new MyClientsDAO();
	private static ClientDAO clientDAO = new ClientDAO();
	private static InvoiceDAO facturaDAO = new InvoiceDAO();

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {

			try {
				// asteptare pentru generare facturi == o zi
				Thread.sleep(TimeUnit.DAYS.toMillis(1));
			} catch (InterruptedException e) {

			}
			myClients = myClientsDAO.getAll();

			for (MyClient mc : myClients) {
				generareFactura(mc);
			}
		}
	}

	
	private static void generareFactura(MyClient mc) {
		Date date = new Date();
		Client client = mc.getClient();
		boolean emitere = asLocalDate(date).getDayOfMonth() == mc.getSupplier().getReleaseDay();
		List<Invoice> facturi = facturaDAO.getByUnpaid(client, mc.getSupplier());
		
		if (mc.getBanned()) {			
			for(Invoice f : facturi){
				synchronized(f){
					double amenda = f.getCurrentValue()*mc.getSupplier().getPenalty();
					facturaDAO.updateField(f, InvoiceDAO.FINE_VALUE, String.format("%.2f",(amenda + f.getFineValue())) );
					facturaDAO.updateField(f, InvoiceDAO.TOTAL_VALUE, String.format("%.2f", (amenda + f.getTotalValue())));
				}
			}
		//emitere facturi	
		}else if(emitere && facturi.size()<3){
			Invoice factura = new Invoice();
			factura.setClient(client);
			factura.setSupplier(mc.getSupplier());
			factura.setReleaseDate(date);
			factura.setMaturityDate(asDate(asLocalDate(date).plusDays(mc.getSupplier().getMaturityDays())));
			factura.setTotalValue(Math.random() * 600 + 50);
			factura.setCurrentValue(factura.getTotalValue());
			
			if(facturi.size() > 0 && facturi.size() < 3){
				for(Invoice f : facturi){
					synchronized(f){
						double amenda = f.getCurrentValue()*mc.getSupplier().getPenalty();
						facturaDAO.updateField(f, InvoiceDAO.FINE_VALUE, String.format("%.2f",(amenda + f.getFineValue())) );
						facturaDAO.updateField(f, InvoiceDAO.TOTAL_VALUE, String.format("%.2f", (amenda + f.getTotalValue())));
					}
				}
			}
			
			if(mc.getSubscribed() && client.getValue() >= factura.getTotalValue()){
				double newValue = client.getValue() - factura.getTotalValue();
				synchronized (client) {
					client.setValue(newValue);
					clientDAO.updateField(client, ClientDAO.VALUE,Double.toString(newValue));
				}
				factura.setPaid(true);
				factura.setResidual(false);
				
			}
			facturaDAO.add(factura);
			/*
			 * TRiMITE MAIL S-A EMIS FACTURA
			 */
			if(factura.getPaid()){
				//EMAIL = emis + platit
				new Thread(new SendEmail(factura,2)).start();
			}else{
				//EMAIL = emis + trebuie platit
				new Thread(new SendEmail(factura,1)).start();
			}
			
		/*
		 * Nu e data emitere + verificare banare
		 */
		}else if(facturi.size() == 3 && LocalDate.now().isAfter(asLocalDate(facturi.get(facturi.size()-1).getMaturityDate()))){
			synchronized(mc){
				myClientsDAO.updateBanned(mc, true);
			}
			
			/*
			 * MAIL = serviciile de la furnizorul *** fost sistate
			 */
			new Thread(new SendEmail(facturi.get(0),3)).start();			
			for(Invoice f : facturi){
				synchronized(f){
					double amenda = f.getCurrentValue()*mc.getSupplier().getPenalty();
					facturaDAO.updateField(f, InvoiceDAO.FINE_VALUE, String.format("%.2f",(amenda + f.getFineValue())) );
					facturaDAO.updateField(f, InvoiceDAO.TOTAL_VALUE, String.format("%.2f", (amenda + f.getTotalValue())));
				}
			}
			/*
			 * nu e data emitere facturi neplatite 1 sau 2
			 */
		}else if(facturi.size() > 0 && facturi.size() < 3 && !emitere){
			if(asLocalDate(facturi.get(facturi.size()-1).getMaturityDate()).isBefore(LocalDate.now())){
				for(Invoice f : facturi){
					synchronized(f){
						double amenda = f.getCurrentValue()*mc.getSupplier().getPenalty();
						facturaDAO.updateField(f, InvoiceDAO.FINE_VALUE, String.format("%.2f",(amenda + f.getFineValue())) );
						facturaDAO.updateField(f, InvoiceDAO.TOTAL_VALUE, String.format("%.2f", (amenda + f.getTotalValue())));
					}
				}
			}
		}

	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
