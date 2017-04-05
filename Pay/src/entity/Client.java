package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@NamedQuery(name="Client.findAll", query="SELECT c FROM Client c")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String email;

	private String name;

	private String password;

	private String phone;

	private String surname;

	private String username;

	private double value;

	//bi-directional many-to-one association to Invoice
	@OneToMany(mappedBy="client")
	private List<Invoice> invoices;

	//bi-directional many-to-one association to MyClient
	@OneToMany(mappedBy="client")
	private List<MyClient> myClients;

	public Client() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public List<Invoice> getInvoices() {
		return this.invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public Invoice addInvoice(Invoice invoice) {
		getInvoices().add(invoice);
		invoice.setClient(this);

		return invoice;
	}

	public Invoice removeInvoice(Invoice invoice) {
		getInvoices().remove(invoice);
		invoice.setClient(null);

		return invoice;
	}

	public List<MyClient> getMyClients() {
		return this.myClients;
	}

	public void setMyClients(List<MyClient> myClients) {
		this.myClients = myClients;
	}

	public MyClient addMyClient(MyClient myClient) {
		getMyClients().add(myClient);
		myClient.setClient(this);

		return myClient;
	}

	public MyClient removeMyClient(MyClient myClient) {
		getMyClients().remove(myClient);
		myClient.setClient(null);

		return myClient;
	}
	
	@Override
	public String toString(){
		return name+" "+surname;
	}

}