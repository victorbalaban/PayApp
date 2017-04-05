package application;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.NoResultException;

import entity.Client;
import entity.Invoice;
import entity.Invoice;
import entity.Supplier;
import entity.MyClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import server2.Command;
import server2.Request;


/**
 *Controller for ClientWindow.fxml, contains methods for operations on teh client
 */
public class ClientController {

	private Client currentClient;

	/**
	 * Sets the currentClient(Session)
	 */
	public void setCurrentClient(Client client) {
		currentClient = client;
		currentUserLabel.setText(currentClient.getName() + " " + currentClient.getSurname());
	}

	public void setValoareCont(double valoareCont) {
		String valoare = String.format("%.2f RON", valoareCont);
		value.setText(valoare);
	}

	public String providerID;

	@FXML
	void setID1(MouseEvent event) {
		providerID = "1";
	}
	@FXML
	void setID2(MouseEvent event) {
		providerID = "2";
	}
	@FXML
	void setID3(MouseEvent event) {
		providerID = "3";
	}
	@FXML
	void setID4(MouseEvent event) {
		providerID = "4";
	}
	@FXML
	void setID5(MouseEvent event) {
		providerID = "5";
	}
	@FXML
	void setID6(MouseEvent event) {
		providerID = "6";
	}
	@FXML
	void setID7(MouseEvent event) {
		providerID = "7";
	}
	@FXML
	void setID8(MouseEvent event) {
		providerID = "8";
	}
	@FXML
	void setID9(MouseEvent event) {
		providerID = "9";
	}
	/*
	 * menu pane small
	 */
	@FXML
	private Hyperlink btnMenu;
	@FXML
	private Label value;
	@FXML
	private Label currentUserLabel;
	@FXML
	private Hyperlink btnLogout;

	/*
	 * meniu pane big
	 */
	@FXML
	private Pane menuPaneBig;
	/*
	 * toti furnizorii
	 */
	@FXML
	private Label allSuppliersLabel;
	@FXML
	private Pane allSuppliersPane;
	// apa
	@FXML
	private Hyperlink btnAddApa;
	@FXML
	private Hyperlink btnSubscribeApa;
	// digi
	@FXML
	private Hyperlink btnAddDigi;
	@FXML
	private Hyperlink btnSubscribeDigi;
	// electrica
	@FXML
	private Hyperlink btnAddElectrica;
	@FXML
	private Hyperlink btnSubscribeElectrica;
	// gaz
	@FXML
	private Hyperlink btnAddGaz;
	@FXML
	private Hyperlink btnSubscribeGaz;
	// orange
	@FXML
	private Hyperlink btnAddOrange;
	@FXML
	private Hyperlink btnSubscribeOrange;
	// telekom
	@FXML
	private Hyperlink btnAddTelekom;
	@FXML
	private Hyperlink btnSubscribeTelekom;
	// upc
	@FXML
	private Hyperlink btnAddUpc;
	@FXML
	private Hyperlink btnSubscribeUpc;
	// urban
	@FXML
	private Hyperlink btnAddUrban;
	@FXML
	private Hyperlink btnSubscribeUrban;
	// vodafone
	@FXML
	private Hyperlink btnAddVodafone;
	@FXML
	private Hyperlink btnSubscribeVodafone;
	/*
	 * furnizorii mei
	 */
	@FXML
	private Pane mySuppliersPane;
	@FXML
	private TableView<Supplier> mySuppliersTable;
	@FXML
	private TableColumn<Supplier, Integer> columnId;
	@FXML
	private TableColumn<Supplier, String> columnFirmName;
	@FXML
	private TableColumn<Supplier, String> columnPhone;
	@FXML
	private TableColumn<Supplier, String> columnEmail;
	@FXML
	private TableColumn<Supplier, String> columnService;
	@FXML
	private Button btnDeleteMySuppliers;
	/*
	 * facturile mele
	 */
	@FXML
	private Pane myInvoicesPane;
	@FXML
	private TableView<Invoice> myInvoicesTable;
	@FXML
	private TableColumn<Invoice, String> columnSupplier;
	@FXML
	private TableColumn<Invoice, String> columnValue;
	@FXML
	private TableColumn<Invoice, String> columnDate;
	@FXML
	private TableColumn<Invoice, String> columnMaturityDate;
	@FXML
	private TableColumn<Invoice, String> columnPaid;
	@FXML
	private TableColumn<Invoice, String> columnResidual;
	@FXML
	private TableColumn<Invoice, String> columnResidualValue;
	@FXML
	private TableColumn<Invoice, String> columnFineValue;
	@FXML
	private TableColumn<Invoice, String> columnCurrentValue;
	@FXML
	private ListView<Invoice> listTest;
	@FXML
	private Button btnPayInvoice;
	@FXML
	private Label invoiceLabel;
	/*
	 * alimentare cont
	 */
	@FXML
	private Pane feedAccountPane;
	@FXML
	private TextField salary;
	@FXML
	private TextField feedAccountLabel;
	@FXML
	private Button btnFeedAccount;
	@FXML
	private Label feedLabel;
	/*
	 * setari cont
	 */
	@FXML
	private Pane settingsPane;
	@FXML
	private TextField settingsName;
	@FXML
	private TextField settingsSurname;
	@FXML
	private TextField settingsPhone;
	@FXML
	private TextField settingsEmail;
	@FXML
	private PasswordField settingsPassword1;
	@FXML
	private PasswordField settingsPassword2;
	@FXML
	private Button btnSaveSettings;
	@FXML
	private Label settingsLabel;
	
	/**
	 * Returns a encrypted representation of the String 
	 * @throws NoSuchAlgorithmException
	 */
	private String encryptMD5(String string) throws NoSuchAlgorithmException {

		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(string.getBytes(), 0, string.length());
		return new BigInteger(1, m.digest()).toString(16);
	}

	@FXML
	void logout(ActionEvent event) throws IOException {
		Main.getStage().close();

		Stage primaryStage = new Stage();

		Parent login = FXMLLoader.load(getClass().getResource("LoginWindow.fxml"));

		Scene loginScene = new Scene(login, 450, 600);

		loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		primaryStage.setScene(loginScene);
		primaryStage.setTitle("Easy Pay");
		primaryStage.getIcons().add(new Image("/images/logo.png"));
		primaryStage.show();

		Main.setStage(primaryStage);
	}

	/**
	 *Brings the main menu to front
	 */
	@FXML
	void showMenuBig(ActionEvent event) {
		menuPaneBig.toFront();

	}
	/*
	 * functii butoane meniu
	 */

	@FXML
	void showAllSuppliers(MouseEvent event) {
		allSuppliersLabel.setText("");
		allSuppliersPane.toFront();

		Request req = new Request();
		List<MyClient> list = (List<MyClient>) req.getResponse(Command.FIND_BY_MY_CLIENT, currentClient);

		for (MyClient myc : list) {
			if (myc.getSupplier().getId() == 1) {
				btnAddApa.setText("Delete supplier");
			} else if (myc.getSupplier().getId() == 2) {
				btnAddDigi.setText("Delete supplier");
			} else if (myc.getSupplier().getId() == 3) {
				btnAddElectrica.setText("Delete supplier");
			} else if (myc.getSupplier().getId() == 4) {
				btnAddGaz.setText("Delete supplier");
			} else if (myc.getSupplier().getId() == 5) {
				btnAddOrange.setText("Delete supplier");
			} else if (myc.getSupplier().getId() == 6) {
				btnAddTelekom.setText("Delete supplier");
			} else if (myc.getSupplier().getId() == 7) {
				btnAddUpc.setText("Delete supplier");
			} else if (myc.getSupplier().getId() == 8) {
				btnAddUrban.setText("Delete supplier");
			} else if (myc.getSupplier().getId() == 9) {
				btnAddVodafone.setText("Delete supplier");
			}

			for (MyClient my : list) {
				if (my.getSupplier().getId() == 1 && my.getSubscribed() == true) {
					btnSubscribeApa.setText("Unsubscribe");
				} else if (my.getSupplier().getId() == 2 && my.getSubscribed() == true) {
					btnSubscribeDigi.setText("Unsubscribe");
				} else if (my.getSupplier().getId() == 3 && my.getSubscribed() == true) {
					btnSubscribeElectrica.setText("Unsubscribe");
				} else if (my.getSupplier().getId() == 4 && my.getSubscribed() == true) {
					btnSubscribeGaz.setText("Unsubscribe");
				} else if (my.getSupplier().getId() == 5 && my.getSubscribed() == true) {
					btnSubscribeOrange.setText("Unsubscribe");
				} else if (my.getSupplier().getId() == 6 && my.getSubscribed() == true) {
					btnSubscribeTelekom.setText("Unsubscribe");
				} else if (my.getSupplier().getId() == 7 && my.getSubscribed() == true) {
					btnSubscribeUpc.setText("Unsubscribe");
				} else if (my.getSupplier().getId() == 8 && my.getSubscribed() == true) {
					btnSubscribeUrban.setText("Unsubscribe");
				} else if (my.getSupplier().getId() == 9 && my.getSubscribed() == true) {
					btnSubscribeVodafone.setText("Unsubscribe");
				}
			}
		}
	}

	@FXML
	void showMySuppliers(MouseEvent event) {
		mySuppliersPane.toFront();
		Request req = new Request();
		List<MyClient> list = (List<MyClient>) req.getResponse(Command.FIND_BY_MY_CLIENT, currentClient);
		ObservableList<Supplier> obsList = FXCollections.observableArrayList();
		for (int i = 0; i < list.size(); i++) {
			obsList.add(list.get(i).getSupplier());
		}
		columnFirmName.setCellValueFactory(new PropertyValueFactory<>("firmName"));
		columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		columnService.setCellValueFactory(new PropertyValueFactory<>("service"));
		mySuppliersTable.setItems(obsList);
	}

	
	/**
	 * Deletes the supplier selected in the table, if all the Invoices are paid
	 */
	@FXML
	void deleteMySuppliers(ActionEvent event) {
			ObservableList<Supplier> str = mySuppliersTable.getSelectionModel().getSelectedItems();
			Request req = new Request();
			List<MyClient> mcList = (List<MyClient>) req.getResponse(Command.FIND_BY_MY_SUPPLIER, str.get(0));
	
			req.getResponse(Command.DELETE_MY_CLIENT, mcList.get(0));
			if(((List<Invoice>)req.getResponse(Command.FIND_INVOICE_UNPAID, currentClient, Integer.toString(mcList.get(0).getSupplier().getId()))).isEmpty()){
				ObservableList<Supplier> selectedSupplier, allSuppliers;
				allSuppliers = mySuppliersTable.getItems();
				selectedSupplier = mySuppliersTable.getSelectionModel().getSelectedItems();
				selectedSupplier.forEach(allSuppliers::remove);
				mySuppliersTable.refresh();
			}
	}

	@FXML
	void showMyInvoices(MouseEvent event) {
		myInvoicesPane.toFront();
		Request req = new Request();
		List<Invoice> list = (List<Invoice>) req.getResponse(Command.FIND_INVOICE_CLIENT, currentClient);
		ObservableList<Invoice> ls = FXCollections.observableArrayList(list);
		columnSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		columnValue.setCellValueFactory(new PropertyValueFactory<>("totalValue"));
		columnDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
		columnMaturityDate.setCellValueFactory(new PropertyValueFactory<>("maturityDate"));
		columnPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
		columnCurrentValue.setCellValueFactory(new PropertyValueFactory<>("currentValue"));
		columnFineValue.setCellValueFactory(new PropertyValueFactory<>("fineValue"));
		myInvoicesTable.setItems(ls);
		invoiceLabel.setText("");
	}

	@FXML
	void payInvoices(ActionEvent event) {

		ObservableList<Invoice> facturi = myInvoicesTable.getSelectionModel().getSelectedItems();
		Request req = new Request();
		Invoice invoice = facturi.get(0);

		if (currentClient.getValue() >= invoice.getTotalValue()) {
			req.getResponse(Command.UPDATE_INVOICE, invoice, "true");

			Client client = new Client();
			double newValue = currentClient.getValue() - invoice.getTotalValue();
			client.setValue(newValue);
			client.setName(currentClient.getName());
			client.setSurname(currentClient.getSurname());
			client.setPassword(currentClient.getPassword());
			client.setEmail(currentClient.getEmail());
			client.setPhone(currentClient.getPhone());
			currentClient = (Client) req.getResponse(Command.UPDATE_CLIENT, client, currentClient.getUsername());
			String accValue = String.format("%.2f RON", client.getValue());
			value.setText(accValue);

			MyClient thisClient = (MyClient) req.getResponse(Command.FIND_MYCLIENT_CLIENT_SUPPLIER, currentClient,
					invoice.getSupplier().getId() + "");
			if (thisClient.getBanned()) {
				req.getResponse(Command.UPDATE_MYCLIENT_BANNED, thisClient, "false");
			}
			List<Invoice> list = (List<Invoice>) req.getResponse(Command.FIND_INVOICE_CLIENT, currentClient);
			ObservableList<Invoice> ls = FXCollections.observableArrayList(list);
			columnSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
			columnValue.setCellValueFactory(new PropertyValueFactory<>("totalValue"));
			columnDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
			columnMaturityDate.setCellValueFactory(new PropertyValueFactory<>("maturityDate"));
			columnPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
			columnFineValue.setCellValueFactory(new PropertyValueFactory<>("fineValue"));
			columnCurrentValue.setCellValueFactory(new PropertyValueFactory<>("currentValue"));
			myInvoicesTable.setItems(ls);
			invoiceLabel.setTextFill(Color.web("#009900"));
			invoiceLabel.setText("Succes paying the invoice!");
		} else {
			invoiceLabel.setTextFill(Color.web("#e60000"));
			invoiceLabel.setText("You don't have enough money in your account!");
		}

	}

	@FXML
	void showAccountFeed(MouseEvent event) {
		feedAccountPane.toFront();
	}

	@FXML
	void feedAccount(ActionEvent event) {
		try{
			Client client = new Client();
			double newValoareCont = currentClient.getValue() + Double.parseDouble(feedAccountLabel.getText());
	
			client.setValue(newValoareCont);
			client.setName(currentClient.getName());
			client.setSurname(currentClient.getSurname());
			client.setPassword(currentClient.getPassword());
			client.setEmail(currentClient.getEmail());
			client.setPhone(currentClient.getPhone());
			Request req = new Request();
			// update client
			currentClient = (Client) req.getResponse(Command.UPDATE_CLIENT, client, currentClient.getUsername());
			feedLabel.setTextFill(Color.web("#009900"));
			feedLabel.setText("The money has been succesfully added to your Account!");
			// platire facturi in caz de abonament
			currentClient = (Client) req.getResponse(Command.PAY_INVOICES, currentClient);
			feedLabel.setTextFill(Color.web("#009900"));
			feedLabel.setText("Invoices from your subscribed Suppliers have been paid succesfully!");
			String accValue = String.format("%.2f RON", currentClient.getValue());
			value.setText(accValue);
	
			feedAccountLabel.clear();
		}
		catch(Exception e){
			feedLabel.setTextFill(Color.RED);
			feedLabel.setText("Please enter a valid value!");
			feedAccountLabel.clear();
		}
	}

	@FXML
	void showSettings(MouseEvent event) {
		settingsPane.toFront();
		settingsName.setText(currentClient.getName());
		settingsSurname.setText(currentClient.getSurname());
		settingsPhone.setText(currentClient.getPhone());
		settingsEmail.setText(currentClient.getEmail());
		settingsLabel.setText("");
	}

	@FXML
	void saveSettings(ActionEvent event) throws NoSuchAlgorithmException {
		Client client = new Client();
		client.setName(settingsName.getText().trim());
		client.setSurname(settingsSurname.getText().trim());
		client.setEmail(settingsEmail.getText().trim().toLowerCase());
		client.setPhone(settingsPhone.getText().trim());
		client.setValue(currentClient.getValue());
		Request req = new Request();
		String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (Pattern.matches(regex, client.getEmail())) {
			if (!currentClient.getEmail().equals(client.getEmail())
					&& (Boolean) req.getResponse(Command.CONTAINS_CLIENT_EMAIL, client.getEmail())) {
				settingsLabel.setTextFill(Color.web("#e60000"));
				settingsLabel.setText("Email adress is already taken !");
			} else {
				if (settingsPassword1.getText().length() >= 1) {
					if (settingsPassword1.getText().equals(settingsPassword2.getText())) {
						client.setPassword(encryptMD5(settingsPassword1.getText()));

						req.getResponse(Command.UPDATE_CLIENT, client, currentClient.getUsername());

						currentClient.setName(client.getName());
						currentClient.setSurname(client.getSurname());
						currentUserLabel.setText(currentClient.getName() + " " + currentClient.getSurname());
						settingsPassword1.clear();
						settingsPassword2.clear();
						settingsLabel.setTextFill(Color.web("#009900"));
						settingsLabel.setText("Your profile information has been updated successfully !");
					} else {

						settingsLabel.setTextFill(Color.web("#e60000"));
						settingsLabel.setText("The new Password is not confirmed !");
					}
				} else {
					settingsLabel.setTextFill(Color.web("#e60000"));
					settingsLabel.setText("Password is to short !");
				}
			}
		} else {
			settingsLabel.setTextFill(Color.web("#e60000"));
			settingsLabel.setText("Please enter a valid email adress !");
		}
	}
	/*
	 * functii furnizori
	 */

	public String getProviderAddBtn() {
		if (providerID.equals("1"))
			return btnAddApa.getText();
		else if (providerID.equals("2"))
			return btnAddDigi.getText();
		else if (providerID.equals("3"))
			return btnAddElectrica.getText();
		else if (providerID.equals("4"))
			return btnAddGaz.getText();
		else if (providerID.equals("5"))
			return btnAddOrange.getText();
		else if (providerID.equals("6"))
			return btnAddTelekom.getText();
		else if (providerID.equals("7"))
			return btnAddUpc.getText();
		else if (providerID.equals("8"))
			return btnAddUrban.getText();
		else if (providerID.equals("9"))
			return btnAddVodafone.getText();
		return null;
	}

	public void setProviderAddBtn(String str) {
		if (providerID.equals("1"))
			btnAddApa.setText(str);
		else if (providerID.equals("2"))
			btnAddDigi.setText(str);
		else if (providerID.equals("3"))
			btnAddElectrica.setText(str);
		else if (providerID.equals("4"))
			btnAddGaz.setText(str);
		else if (providerID.equals("5"))
			btnAddOrange.setText(str);
		else if (providerID.equals("6"))
			btnAddTelekom.setText(str);
		else if (providerID.equals("7"))
			btnAddUpc.setText(str);
		else if (providerID.equals("8"))
			btnAddUrban.setText(str);
		else if (providerID.equals("9"))
			btnAddVodafone.setText(str);
	}

	public String getProviderSubscribeBtn() {
		if (providerID.equals("1"))
			return btnSubscribeApa.getText();
		else if (providerID.equals("2"))
			return btnSubscribeDigi.getText();
		else if (providerID.equals("3"))
			return btnSubscribeElectrica.getText();
		else if (providerID.equals("4"))
			return btnSubscribeGaz.getText();
		else if (providerID.equals("5"))
			return btnSubscribeOrange.getText();
		else if (providerID.equals("6"))
			return btnSubscribeTelekom.getText();
		else if (providerID.equals("7"))
			return btnSubscribeUpc.getText();
		else if (providerID.equals("8"))
			return btnSubscribeUrban.getText();
		else if (providerID.equals("9"))
			return btnSubscribeVodafone.getText();
		return null;
	}

	public void setProviderSubscribeBtn(String str) {
		if (providerID.equals("1"))
			btnSubscribeApa.setText(str);
		else if (providerID.equals("2"))
			btnSubscribeDigi.setText(str);
		else if (providerID.equals("3"))
			btnSubscribeElectrica.setText(str);
		else if (providerID.equals("4"))
			btnSubscribeGaz.setText(str);
		else if (providerID.equals("5"))
			btnSubscribeOrange.setText(str);
		else if (providerID.equals("6"))
			btnSubscribeTelekom.setText(str);
		else if (providerID.equals("7"))
			btnSubscribeUpc.setText(str);
		else if (providerID.equals("8"))
			btnSubscribeUrban.setText(str);
		else if (providerID.equals("9"))
			btnSubscribeVodafone.setText(str);
	}

	@FXML
	void addProvider(ActionEvent event) {
		Request req = new Request();
		System.out.println("id provider: " + providerID);
		String btn = getProviderAddBtn();
		System.out.println("btn: " + btn);

		if (btn.equals("Add supplier")) {
			Supplier supplier = (Supplier) req.getResponse(Command.FIND_SUPPLIER_ID, providerID);
			MyClient myClient = new MyClient();
			myClient.setSupplier(supplier);
			myClient.setClient(currentClient);
			myClient.setBanned(false);
			myClient.setSubscribed(false);
			req.getResponse(Command.INSERT_MYCLIENT, myClient);
			allSuppliersLabel.setTextFill(Color.web("#009900"));
			allSuppliersLabel.setText("Supplier has been added successfully!");
			setProviderAddBtn("Delete supplier");
		} else if (btn.equals("Delete supplier")) {
			MyClient mc = (MyClient) req.getResponse(Command.FIND_MYCLIENT_CLIENT_SUPPLIER, currentClient, providerID);
			if (!mc.getBanned() && ((List<Invoice>) req.getResponse(Command.FIND_INVOICE_UNPAID, currentClient,
					mc.getSupplier().getId() + "")).isEmpty()) {
				req.getResponse(Command.DELETE_MY_CLIENT, mc);
				allSuppliersLabel.setTextFill(Color.web("#009900"));
				allSuppliersLabel.setText("Delete successfull!");
				setProviderAddBtn("Add supplier");
				setProviderSubscribeBtn("Subscribe");
			} else {
				allSuppliersLabel.setTextFill(Color.web("#e60000"));
				allSuppliersLabel
						.setText("You can't delete this supplier from your list until you pay all your Invoices!");
			}

		}
	}

	@FXML
	void subscribeProvider(ActionEvent event) {
		String btn = getProviderSubscribeBtn();
		Request req = new Request();
		if (btn.equals("Unsubscribe")) {
			MyClient mc = (MyClient) req.getResponse(Command.FIND_MYCLIENT_CLIENT_SUPPLIER, currentClient, providerID);
			req.getResponse(Command.UPDATE_MYCLIENT_SUBSCRIBED, mc, "false");
			allSuppliersLabel.setTextFill(Color.web("#009900"));
			allSuppliersLabel.setText("Successfully unsubscribed!");
			setProviderSubscribeBtn("Subscribe");
		} else if (btn.equals("Subscribe")
				&& (Boolean) req.getResponse(Command.CONTAINS_MYCLIENT, currentClient, providerID)) {
			MyClient mc = (MyClient) req.getResponse(Command.FIND_MYCLIENT_CLIENT_SUPPLIER, currentClient, providerID);
			req.getResponse(Command.UPDATE_MYCLIENT_SUBSCRIBED, mc, "true");
			allSuppliersLabel.setTextFill(Color.web("#009900"));
			allSuppliersLabel.setText("You are now subscribed! Application is trying to pay your Invoices automatically");
			setProviderSubscribeBtn("Unsubscribe");
			currentClient = (Client) req.getResponse(Command.PAY_INVOICES, currentClient);
			String valoare = String.format("%.2f RON", currentClient.getValue());
			value.setText(valoare);
		} else {
			allSuppliersLabel.setTextFill(Color.web("#e60000"));
			allSuppliersLabel.setText("You can't subscribe to this supplier until you add him to your list!!");
		}
	}
}
