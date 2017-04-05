package database;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.Client;
import entity.Invoice;
import entity.Supplier;
	

	/**
	 * DAO for the entity Invoice
	 */
public class InvoiceDAO{
	

	
	public static final int PAID = 1;
	public static final int RESIDUAL = 2;
	public static final int TOTAL_VALUE = 3;
	public static final int RESIDUAL_VALUE = 4;
	public static final int FINE_VALUE = 5;
	public static final int CURRENT_VALUE = 6;
	
	@PersistenceContext
	private static EntityManager em = getEntityManager();
	
	private static EntityManager getEntityManager(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Pay");
		return emf.createEntityManager();
	}
	
	/**
	 * Adds a new Invoice to the Database
	 */
	public void add(Invoice invoice){
		em.getTransaction().begin();
		em.persist(invoice);
		em.getTransaction().commit();
	}
	
	/**
	 * Updates the field paid
	 */
	public void updatePaid(Invoice invoice, boolean newPaid){
		Invoice oldFactura = getById(invoice.getId());
		em.merge(invoice);
		em.getTransaction().begin();
		oldFactura.setPaid(newPaid);
		em.getTransaction().commit();
	}
	
	public void updateResidual(Invoice invoice, boolean newResidual){
		Invoice oldFactura = getById(invoice.getId());
		em.merge(invoice);
		em.getTransaction().begin();
		oldFactura.setResidual(newResidual);
		em.getTransaction().commit();
	}
	
	
	/**
	 * Updates a certain field given by the parameter field with the new value given by the param. value
	 */
	public void updateField(Invoice invoice, int field, String value){
		
		em.merge(invoice);
		em.getTransaction().begin();
		switch(field){
		case 1 : invoice.setPaid(true);break;
		case 2 : {
			if(value.equals("true")){
				invoice.setResidual(true);
			}else{
				invoice.setResidual(false);
			}
		}break;
		case 3 : invoice.setTotalValue(Double.parseDouble(value));break;
		case 5 : invoice.setFineValue(Double.parseDouble(value));break;
		case 6 : invoice.setCurrentValue(Double.parseDouble(value));break;
		}
		em.getTransaction().commit();
	}
	
	public Invoice getById(int id){
		return em.find(Invoice.class, id);
	}
	
	public List<Invoice> getBySupplier(Supplier supplier){
		TypedQuery<Invoice> qr = em.createQuery("Select i from Invoice i JOIN i.supplier s where s.id = '"+supplier.getId()+"'", Invoice.class);
		return qr.getResultList();
	}
	
	public List<Invoice> getByClient(Client client) {
		TypedQuery<Invoice> qr = em.createQuery(
				"Select i from Invoice i JOIN i.client ic where ic.id = '" + client.getId() + "'", Invoice.class);
		return qr.getResultList();
	}
	
	/**
	 * Returns a List of Invoice which have field "paid" set as false
	 */
	public List<Invoice> getByUnpaid(Client client, Supplier supplier) {
		TypedQuery<Invoice> qr = em.createQuery(
				"Select i from Invoice i JOIN i.client ic, i.supplier s where i.paid = '" + false + "' and ic.id = '"+client.getId()+"' AND "
						+ "s.id = '"+supplier.getId()+"'", Invoice.class);
		return qr.getResultList();
	}
	
	
	/**
	 * Returns the Invoices with the field residual set as true
	 */
	public List<Invoice> getByResidual(Client client, Supplier supplier) {
		
		TypedQuery<Invoice> qr = em.createQuery(
				"Select i from Factura i JOIN i.client ic, i.supplier s where i.residual = '" + true + "' and ic.id = '"+client.getId()+"'"
						+ " and s.id = '"+supplier.getId()+"'", Invoice.class);
		return qr.getResultList();
	}
	
	
}
