package database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.Supplier;

public class SupplierDAO {
	
	public static final int NAME = 1;
	public static final int FIRM_NAME = 2;
	public static final int PHONE = 3;
	public static final int EMAIL = 4;
	
	@PersistenceContext
	private static EntityManager em = getEntityManager();
	
	public static EntityManager getEntityManager(){
		EntityManagerFactory ef = Persistence.createEntityManagerFactory("Pay");
		return ef.createEntityManager();
	}
	
	
	public void add(Supplier furnizor){
		em.getTransaction().begin();
		em.persist(furnizor);
		em.getTransaction().commit();
	}
	
	/**
	 * Updates a field given by the Constant field with the value of the param. newValue
	 */
	public void updateField(Supplier furnizor, int field, String newValue){
		Supplier oldFurnizor = getById(furnizor.getId());
		em.getTransaction().begin();
		
		switch(field){
		case 1 : oldFurnizor.setName(newValue);break;
		case 2 : oldFurnizor.setFirmName(newValue);break;
		case 3 : oldFurnizor.setPhone(newValue);break;
		case 4 : oldFurnizor.setEmail(newValue);break;
		}
		em.getTransaction().commit();
	}
	
	/**
	 * Updates all fields of the oldSupplier with the fields of the newSupplier
	 */
	public void updateSupplier(Supplier newSupplier, Supplier oldSupplier){
		em.getTransaction().begin();
		em.merge(oldSupplier);
		oldSupplier.setFirmName(newSupplier.getFirmName());
		oldSupplier.setCui(newSupplier.getCui());
		oldSupplier.setPhone(newSupplier.getPhone());
		oldSupplier.setEmail(newSupplier.getEmail());
		oldSupplier.setPassword(newSupplier.getPassword());
		oldSupplier.setPenalty(newSupplier.getPenalty());
		oldSupplier.setMaturityDays(newSupplier.getMaturityDays());
		oldSupplier.setReleaseDay(newSupplier.getReleaseDay());
		em.merge(oldSupplier);
		em.getTransaction().commit();
	}
	
	public Supplier getByUsername(String username) {
		TypedQuery<Supplier> qr = em.createQuery("Select s from Supplier s where s.username = '"+username+"'", Supplier.class);
		return qr.getSingleResult();
	}
	
	public Supplier getById(int id) {
		return em.find(Supplier.class, id);
	}
	
	/**
	 * Returns true if the username given by the param. is contained in the DataBase
	 */
	public boolean containsUsername(String username){
		try{
			getByUsername(username);
			return true;
		}catch(Exception e){
			return false;
		}
	}
}
