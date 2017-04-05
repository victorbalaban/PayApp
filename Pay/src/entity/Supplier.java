package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the supplier database table.
 * 
 */
@Entity
@NamedQuery(name="Supplier.findAll", query="SELECT s FROM Supplier s")
public class Supplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String cui;

	private String email;

	@Column(name="firm_name")
	private String firmName;

	@Column(name="maturity_days")
	private int maturityDays;

	private String name;

	private String password;

	private double penalty;

	private String phone;

	@Column(name="release_day")
	private int releaseDay;

	private String service;

	private String username;

	//bi-directional many-to-one association to Invoice
	@OneToMany(mappedBy="supplier")
	private List<Invoice> invoices;

	//bi-directional many-to-one association to MyClient
	@OneToMany(mappedBy="supplier")
	private List<MyClient> myClients;

	public Supplier() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCui() {
		return this.cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirmName() {
		return this.firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public int getMaturityDays() {
		return this.maturityDays;
	}

	public void setMaturityDays(int maturityDays) {
		this.maturityDays = maturityDays;
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

	public double getPenalty() {
		return this.penalty;
	}

	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getReleaseDay() {
		return this.releaseDay;
	}

	public void setReleaseDay(int releaseDay) {
		this.releaseDay = releaseDay;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Invoice> getInvoices() {
		return this.invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public Invoice addInvoice(Invoice invoice) {
		getInvoices().add(invoice);
		invoice.setSupplier(this);

		return invoice;
	}

	public Invoice removeInvoice(Invoice invoice) {
		getInvoices().remove(invoice);
		invoice.setSupplier(null);

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
		myClient.setSupplier(this);

		return myClient;
	}

	public MyClient removeMyClient(MyClient myClient) {
		getMyClients().remove(myClient);
		myClient.setSupplier(null);

		return myClient;
	}
	
	@Override
	public String toString(){
		return firmName;
	}

}