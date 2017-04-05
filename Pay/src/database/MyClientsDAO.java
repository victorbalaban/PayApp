package database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.Client;
import entity.Supplier;
import entity.MyClient;

public class MyClientsDAO {

	@PersistenceContext
	private static EntityManager em = getEntityManager();

	private static EntityManager getEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Pay");
		return emf.createEntityManager();
	}

	public void add(MyClient myClient) {
		em.getTransaction().begin();
		em.persist(myClient);
		em.getTransaction().commit();
	}
	
	/**
	 * Updates the field banned to the new value newBanned
	 */
	public void updateBanned(MyClient mc, boolean newBanned) {
		MyClient oldMyClient = getByClientAndSupplier(mc.getClient(), mc.getSupplier().getId());
		em.getTransaction().begin();
		oldMyClient.setBanned(newBanned);
		em.getTransaction().commit();
	}

	
	/**
	 * Updates the field subscribed to the new value newSubscribed
	 */
	public void updateSubscribed(MyClient mc, boolean newSubscribed) {
		MyClient oldMyClient = getByClientAndSupplier(mc.getClient(), mc.getSupplier().getId());
		em.getTransaction().begin();
		oldMyClient.setSubscribed(newSubscribed);
		em.getTransaction().commit();
	}

	public void deleteMyClient(MyClient myClient) {
		MyClient myclient = em.find(MyClient.class, myClient.getId());
		em.getTransaction().begin();
		em.remove(myclient);
		em.getTransaction().commit();
	}

	
	/**
	 * Returns a List of MyClient which all have the field Client equal to the @param client
	 */
	public List<MyClient> getByClient(Client client) {
		TypedQuery<MyClient> qr = em.createQuery(
				"Select mc from MyClient mc JOIN mc.client c " + "where c.id = '" + client.getId() + "'",
				MyClient.class);
		return qr.getResultList();
	}

	/**
	 * Returns a List of MyCLient which all have the field Supplier equal to the parameter supplier
	 */
	public List<MyClient> getBySupplier(Supplier supplier) {
		TypedQuery<MyClient> qr = em.createQuery(
				"Select mc from MyClient mc JOIN mc.supplier s " + "WHERE s.id = '" + supplier.getId() + "'",MyClient.class);
		return qr.getResultList();
	}
	
	/**
	 * Returns a List of MyClient which have the field Client equal to the param. client, and the field Supplier equal
	 * to the supplier with the id given by the param. idSupplier
	 */
	public MyClient getByClientAndSupplier(Client client, int idSupplier) {
		TypedQuery<MyClient> qr = em.createQuery("Select mc FROM MyClient mc JOIN mc.supplier s, mc.client c "
				+ "WHERE s.id = '" + idSupplier + "' AND c.id = '" + client.getId() + "'", MyClient.class);
		return qr.getSingleResult();
	}

	public List<MyClient> getAll() {
		TypedQuery<MyClient> qr = em.createQuery("Select mc from MyClient mc", MyClient.class);
		return qr.getResultList();
	}

	public MyClient getById(int id) {
		return em.find(MyClient.class, id);
	}

}
