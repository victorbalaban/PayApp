package application;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import entity.Client;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import server2.Command;
import server2.Request;

public class SupplierController {

	private Supplier currentFurnizor;

	public void setCurrentFurnizor(Supplier furnizor) {
		currentFurnizor = furnizor;
		currentProviderLabel.setText(currentFurnizor.getName());
	}

	/*
	 * menu pane small
	 */

	@FXML
	private Hyperlink btnMeniu;
	@FXML
	private Hyperlink btnLogout;
	@FXML
	private Label currentProviderLabel;

	/*
	 * menu pane big
	 */
	@FXML
	private Pane meniuPane;
	@FXML
	private Pane clientsPane;
	@FXML
	private Pane sentInvoicePane;
	@FXML
	private Pane settingsPane;

	/*
	 * clienti pane
	 */
	@FXML
	private TableView<Client> clientsTabel;
	@FXML
	private TableColumn<Client, String> columnName;
	@FXML
	private TableColumn<Client, String> columnSurname;
	@FXML
	private TableColumn<Client, String> columnTelefon;
	@FXML
	private TableColumn<Client, String> columnEmail;
	@FXML
	private TableView<MyClient> clients2Tabel;
	@FXML
	private TableColumn<MyClient, String> columnSubscribe;
	@FXML
	private TableColumn<MyClient, String> columnBanned;

	/*
	 * facturi emise
	 */
	@FXML
	private TableView<Invoice> sentInvoiceTabel;
	@FXML
	private TableColumn<Invoice, String> columnClient;
	@FXML
	private TableColumn<Invoice, String> columnValue;
	@FXML
	private TableColumn<Invoice, String> columnData;
	@FXML
	private TableColumn<Invoice, String> columnDataScadenta;
	@FXML
	private TableColumn<Invoice, String> columnPaid;
	@FXML
	private TableColumn<Invoice, String> columnResidual;
	@FXML
	private TableColumn<Invoice, String> columnValResidual;
	@FXML
	private TableColumn<Invoice, String> columnValCurrent;
	@FXML
	private TableColumn<Invoice, String> columnFine;

	/*
	 * setari pane
	 */
	@FXML
	private Button btnSaveSettings;
	@FXML
	private TextField settingsName;
	@FXML
	private TextField settingsCui;
	@FXML
	private TextField settingsTelefon;
	@FXML
	private TextField settingsEmail;
	@FXML
	private PasswordField settingsPass1;
	@FXML
	private PasswordField settingsPass2;
	@FXML
	private Label settingsLabel;
	@FXML
	private TextField settingsPenalty;
	@FXML
	private TextField setttingsReleaseDay;
	@FXML
	private TextField settingsMaturityDays;

	private String encryptMD5(String string) throws NoSuchAlgorithmException {

		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(string.getBytes(), 0, string.length());
		return new BigInteger(1, m.digest()).toString(16);
	}
	
	@FXML
	void showMeniu(ActionEvent event) {
		meniuPane.toFront();
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

	/*
	 * butoane & functii meniu big
	 */

	@FXML
	void showClienti(MouseEvent event) {
		clientsPane.toFront();

		Request req = new Request();
		List<MyClient> list = (List<MyClient>) req.getResponse(Command.FIND_BY_MY_SUPPLIER, currentFurnizor);
		ObservableList<Client> ls = FXCollections.observableArrayList();
		for (int i = 0; i < list.size(); i++) {
			ls.add(list.get(i).getClient());
		}
		ObservableList<MyClient> ls2 = FXCollections.observableArrayList(list);
		columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
		columnTelefon.setCellValueFactory(new PropertyValueFactory<>("phone"));
		columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		clientsTabel.setItems(ls);
		
		columnSubscribe.setCellValueFactory(new PropertyValueFactory<>("subscribed"));
		columnBanned.setCellValueFactory(new PropertyValueFactory<>("banned"));
		clients2Tabel.setItems(ls2);
	}


	@FXML
	void showSentInvoice(MouseEvent event) {
		sentInvoicePane.toFront();
		Request req = new Request();
		List<Invoice> list = (List<Invoice>) req.getResponse(Command.FIND_INVOICE_SUPPLIER, currentFurnizor);

		ObservableList<Invoice> ls = FXCollections.observableArrayList(list);

		columnClient.setCellValueFactory(new PropertyValueFactory<>("client"));
		columnValue.setCellValueFactory(new PropertyValueFactory<>("currentValue"));
		columnData.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
		columnDataScadenta.setCellValueFactory(new PropertyValueFactory<>("maturityDate"));
		columnPaid.setCellValueFactory(new PropertyValueFactory<>("paid"));
		columnValCurrent.setCellValueFactory(new PropertyValueFactory<>("currentValue"));
		columnFine.setCellValueFactory(new PropertyValueFactory<>("fineValue"));

		sentInvoiceTabel.setItems(ls);

	}

	@FXML
	void showSettings(MouseEvent event) {
		settingsPane.toFront();
		settingsName.setText(currentFurnizor.getFirmName());
		settingsCui.setText(currentFurnizor.getCui());
		settingsTelefon.setText(currentFurnizor.getPhone());
		settingsEmail.setText(currentFurnizor.getEmail());
		settingsPenalty.setText(Double.toString(currentFurnizor.getPenalty()));
		setttingsReleaseDay.setText(Integer.toString(currentFurnizor.getReleaseDay()));
		settingsMaturityDays.setText(Integer.toString(currentFurnizor.getMaturityDays()));
		settingsLabel.setText("");
	}
	

	@FXML
	void saveSettings(ActionEvent event) throws NoSuchAlgorithmException {
		Supplier furnizor = new Supplier();
		furnizor.setFirmName(settingsName.getText());
		furnizor.setCui(settingsCui.getText().trim());
		furnizor.setPhone(settingsTelefon.getText().trim());
		furnizor.setEmail(settingsEmail.getText().trim());
		furnizor.setPenalty(Double.parseDouble(settingsPenalty.getText()));
		furnizor.setReleaseDay(Integer.parseInt(setttingsReleaseDay.getText()));
		furnizor.setMaturityDays(Integer.parseInt(settingsMaturityDays.getText()));
		if (settingsPass1.getText().equals(settingsPass2.getText())){
			furnizor.setPassword(encryptMD5(settingsPass1.getText()));
			Request req = new Request();
			req.getResponse(Command.UPDATE_SUPPLIER, furnizor, currentFurnizor.getUsername());
			settingsPass1.clear();
			settingsPass2.clear();
			settingsLabel.setTextFill(Color.web("#009900"));
			settingsLabel.setText("Changes saved");
		} else
			settingsLabel.setText("Password not confirmed");
	}
}
