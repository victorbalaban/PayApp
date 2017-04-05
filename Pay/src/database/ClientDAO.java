package database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.Client;
	

	/**
	 * DAO for the entity {@link}Client
	 */
public class ClientDAO {
	

	
	//CLIENT FIELDS
	public static final int EMAIL = 1;
	public static final int PASSWORD = 2;
	public static final int PHONE = 3;
	public static final int VALUE = 4;
	
	
	@PersistenceContext
	private static EntityManager em = getEntityManager();
	
	public static EntityManager getEntityManager(){
	EntityManagerFactory ef = Persistence.createEntityManagerFactory("Pay");
	return ef.createEntityManager();
	}
	
	
	public void add(Client client){
		em.getTransaction().begin();
		em.persist(client);
		em.getTransaction().commit();
	}
	
	/**
	 * Updates a certain field given by the parameter "field", which is one of the constants in this class
	 */
	public Client updateField(Client client, int field, String newValue ){
		Client oldClient = em.find(Client.class, client.getId());;
		em.getTransaction().begin();
		em.merge(client);
		switch(field){
			case 1 : oldClient.setEmail(newValue);break;
			case 2 : oldClient.setPassword(newValue);break;
			case 3 : oldClient.setPhone(newValue);break;
			case 4 : oldClient.setValue(Double.parseDouble(String.format("%.2f", Double.parseDouble(newValue))));break;
		}
		em.getTransaction().commit();
		return oldClient;
	}
	
	
	/**
	 * 
	 * Updates the @param oldClient with all the attributes of the newClient
	 */
	public Client updateClient(Client newClient, Client oldClient) {
		em.getTransaction().begin();
		em.merge(oldClient);
		oldClient.setName(newClient.getName());
		oldClient.setSurname(newClient.getSurname());
		oldClient.setEmail(newClient.getEmail());
		oldClient.setPhone(newClient.getPhone());
		oldClient.setPassword(newClient.getPassword());
		oldClient.setValue(Double.parseDouble(String.format("%.2f",newClient.getValue() )));
		em.merge(oldClient);
		em.getTransaction().commit();
		return oldClient;
	}

	/**
	 * Returns the Client whith the given @param username
	 */
	public Client getByUsername(String username){
		TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.username = '"+username+"'", Client.class);
		return query.getSingleResult();
	}
	
	/**
	 * deletes a certain
	 * @param client
	 */
	public void delete(Client client){
		if(contains(client)){
			em.remove(client);
		}
	}
	
	/**
	 * Returns the Client whith the specified id
	 */
	public Client getById(int id){
		return em.find(Client.class, id);
	}
	
	/**
	 * Returns true if the username - password combination is contained by the Database
	 */
	public boolean contains(Client client){
		em.merge(client);
		try{
			Client cl = getByUsername(client.getUsername().toLowerCase().trim());
			if(client.getPassword().equals(cl.getPassword())){
				return true;
			}
			else return false;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * returns true if the Databse contains the specified username
	 */
	public boolean containsUsername(String username){
		try{
			getByUsername(username);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * returns the Client with the specified email
	 */
	public Client getByEmail(String email){
		TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.email = '"+email+"'", Client.class);
		return query.getSingleResult();
	}
	
	/**
	 * Checks whether the Databse contains the specified email or not
	 */
	public boolean containsEmail(String email){
		try{
			getByEmail(email);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}
	
	

}
