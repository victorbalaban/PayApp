package server2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.persistence.NoResultException;

import database.ClientDAO;
import database.InvoiceDAO;
import database.SupplierDAO;
import database.MyClientsDAO;
import entity.Client;
import entity.Invoice;
import entity.Supplier;
import entity.MyClient;


/**
 * Class that processes a request from the Client and returns via the ObjectOutputStream the corresponding response
 */
public class RequestHandler implements Runnable{
	
	private Socket socket;
	
	public RequestHandler(Socket socket){
		this.socket = socket;	
	}

	@Override
	public void run() {
		try(ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());){
			
			Request req = (Request)in.readObject();
			System.out.println("Handling request..." + req.getRequest());
			
			switch(req.getRequest()){
			case 1 : new ClientDAO().add((Client)req.getObject());break;
			case 2 : new SupplierDAO().add((Supplier)req.getObject());break;
			case 3 : new InvoiceDAO().add((Invoice)req.getObject());break;
			case 4 : new MyClientsDAO().add((MyClient)req.getObject());break;
			case 5 : out.writeObject(new ClientDAO().containsEmail(req.getValue()));break;
			case 6 : ;break;
			case 7 : {
				Invoice factura = (Invoice)req.getObject();
				synchronized(factura){
					if(req.getValue().equals("true")){
						new InvoiceDAO().updatePaid(factura, true);
					}else {
						new InvoiceDAO().updatePaid(factura, false);
					}
				}
			};break;
			case 8 :{
				MyClient mc = (MyClient)req.getObject();
				synchronized(mc){
					if(req.getValue().equals("true")){
						new MyClientsDAO().updateBanned(mc, true);
					}else{
						new MyClientsDAO().updateBanned(mc, false);
					}
				}
			};break;
			case 9 :{
				MyClient mc = (MyClient)req.getObject();
				synchronized(mc){
					if(req.getValue().equals("true")){
						new MyClientsDAO().updateSubscribed(mc, true);
					}else{
						new MyClientsDAO().updateSubscribed(mc, false);
					}
				}
			} ;break;
			case 10 :{
				Supplier oldFurnizor = (Supplier)new SupplierDAO().getByUsername((String)(req.getValue()));
				Supplier newFurnizor = (Supplier)req.getObject();
				synchronized(oldFurnizor){
					new SupplierDAO().updateSupplier(newFurnizor, oldFurnizor);
				}
			} break;
			case 11 :out.writeObject(new ClientDAO().getById(Integer.parseInt(req.getValue().trim()))) ;break;
			case 12 :out.writeObject(new ClientDAO().getByUsername(req.getValue().trim().toLowerCase())) ;break;
			case 13 :out.writeObject(new SupplierDAO().getById(Integer.parseInt(req.getValue().trim()))) ;break;
			case 14 :out.writeObject(new SupplierDAO().getByUsername(req.getValue().trim().toLowerCase())) ;break;
			case 15 :out.writeObject(new InvoiceDAO().getByClient((Client)req.getObject())) ;break;
			case 16 :out.writeObject(new InvoiceDAO().getBySupplier((Supplier)req.getObject())) ;break;
			case 17 :out.writeObject(new InvoiceDAO().getByUnpaid((Client)req.getObject(), new SupplierDAO().getById(Integer.parseInt(req.getValue()))));break;
			case 18 :out.writeObject(new ClientDAO().containsUsername(req.getValue()));break;
			case 19 :out.writeObject(new SupplierDAO().containsUsername(req.getValue()));break;
			case 20 :out.writeObject(new MyClientsDAO().getBySupplier((Supplier) req.getObject()));break;
			case 21 :out.writeObject(new MyClientsDAO().getByClient((Client) req.getObject()));break;
			case 22 :new MyClientsDAO().deleteMyClient((MyClient)req.getObject());break;
			case 23 :{
				Client oldClient = (Client)new ClientDAO().getByUsername((String)(req.getValue()));
				Client newClient = (Client)req.getObject();
				synchronized(oldClient){
					oldClient = new ClientDAO().updateClient(newClient, oldClient);
				}
				out.writeObject(oldClient);
			};break;
			case 24 :out.writeObject(new MyClientsDAO().getByClientAndSupplier((Client)req.getObject(),Integer.parseInt(req.getValue()) )); break;
			case 25 : {
				Client client = (Client)req.getObject();
				List<MyClient> mcList = new MyClientsDAO().getByClient(client);
				for(MyClient mc : mcList){
					if(mc.getSubscribed()){
						List<Invoice> facturi = new InvoiceDAO().getByUnpaid(client, mc.getSupplier());
						synchronized(client){
							for(Invoice f : facturi){
								if(client.getValue() >= f.getTotalValue()){
									synchronized(f){
										new InvoiceDAO().updatePaid(f, true);
									}
									double newValue = client.getValue() - f.getTotalValue();
									client = new ClientDAO().updateField(client, ClientDAO.VALUE, newValue+"");
									/*
									 * MAIL s-a platit factura
									 */
									new Thread(new SendEmail(f,4)).start();
								}
							}
						}
					}
				}
				out.writeObject(client);
				
			}break;
			case 26 : {
				try{
					new MyClientsDAO().getByClientAndSupplier((Client)req.getObject(), Integer.parseInt(req.getValue()));
					out.writeObject(true);
				}catch(NoResultException e){
					out.writeObject(false);
				}
			}
			}
			
			System.out.println("Request handled");
			
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
