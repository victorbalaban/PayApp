package application;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import entity.Client;
import entity.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import server2.Command;
import server2.Request;

public class LoginController {

	/*
	 * Main login Pane
	 */
	@FXML
	private Pane mainLoginPane;

	/*
	 * User login
	 */

	@FXML
	private Pane loginPane;
	@FXML
	private TextField userLogin;
	@FXML
	private Button btnLogin;
	@FXML
	private PasswordField userPassConfirm;
	@FXML
	private PasswordField userPass;
	@FXML
	private Label loginLabel;
	@FXML
	private Button btnNewRegister;
	@FXML
	private Hyperlink linkBackToMain1;

	/*
	 * register pane
	 */
	@FXML
	private Pane registerPane;
	@FXML
	private TextField userRegister;
	@FXML
	private PasswordField userPass1;
	@FXML
	private Button btnRegister;
	@FXML
	private Label registerMessage;
	@FXML
	private TextField telefon;
	@FXML
	private TextField name;
	@FXML
	private TextField surname;
	@FXML
	private TextField email;

	/*
	 * Furnizor pane
	 */
	@FXML
	private Pane supplierLoginPane;
	@FXML
	private Hyperlink linkBackToLogin;
	@FXML
	private TextField supplierLogin;
	@FXML
	private PasswordField supplierPass;
	@FXML
	private Button btnLoginSupplier;
	@FXML
	private Hyperlink linkBackToMain2;
	@FXML
	private Label loginSupplierLabel;

	private String encryptMD5(String string) throws NoSuchAlgorithmException {

		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(string.getBytes(), 0, string.length());
		return new BigInteger(1, m.digest()).toString(16);
	}

	@FXML
	void loginAction(ActionEvent event) throws IOException, NoSuchAlgorithmException {
		Request req = new Request();
		String username = userLogin.getText().trim().toLowerCase();
		String password = encryptMD5(userPass.getText());

		if ((Boolean) req.getResponse(Command.CONTAINS_CLIENT_USERNAME, username)) {
			Client client = (Client) req.getResponse(Command.FIND_CLIENT_USER, username);

			if (password.equals(client.getPassword())) {

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientWindow.fxml"));
				Stage primaryStage = new Stage();
				Parent root = fxmlLoader.load();
				Scene scene = new Scene(root, 1280, 720);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Easy Pay");
				primaryStage.getIcons().add(new Image("/images/logo.png"));
				primaryStage.setResizable(false);
				primaryStage.show();

				ClientController controller = fxmlLoader.getController();
				controller.setCurrentClient(client);
				controller.setValoareCont(client.getValue());

				Main.getStage().close();
				Main.setStage(primaryStage);
			} else {
				loginLabel.setText("Wrong passrowd");
			}
		} else {
			loginLabel.setText("Username does not exist");
		}
	}

	@FXML
	void registerAction(ActionEvent event) throws NoSuchAlgorithmException {
		Client cl = new Client();
		cl.setUsername(userRegister.getText().trim().toLowerCase());
		cl.setName(name.getText().trim());
		cl.setSurname(surname.getText().trim());
		cl.setEmail(email.getText().trim().toLowerCase());
		cl.setPhone(telefon.getText().trim());
		cl.setPassword(encryptMD5(userPass1.getText()));

		Request request = new Request();
		String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (!Pattern.matches(regex, cl.getEmail())) {
			registerMessage.setTextFill(Color.RED);
			registerMessage.setText("Enter a valid email address");

		} else if ((Boolean) request.getResponse(Command.CONTAINS_CLIENT_EMAIL, cl.getEmail().trim().toLowerCase())) {
			registerMessage.setTextFill(Color.RED);
			registerMessage.setText("This email address is already in use");
		} else if (!(Boolean) request.getResponse(Command.CONTAINS_CLIENT_USERNAME,
				cl.getUsername().trim().toLowerCase())) {

			registerMessage.setTextFill(Color.RED);
			if (userRegister.getText().equals("") || userRegister.getText() == null) {
				registerMessage.setText("Enter username");
			} else if (userPass1.getText().equals("") || userPass1.getText() == null) {
				registerMessage.setText("Enter password");
			} else if (name.getText().equals("") || name.getText() == null) {
				registerMessage.setText("Enter surname");
			} else if (surname.getText().equals("") || surname.getText() == null) {
				registerMessage.setText("Enter name");
			} else if (telefon.getText().equals("") || telefon.getText() == null) {
				registerMessage.setText("Enter telefon");
			} else if (email.getText().equals("") || email.getText() == null) {
				registerMessage.setText("Enter email");
			}

			else {
				if (!(userPass1.getText().length() >= 1)) {
					registerMessage.setText("Password is too short");
				} else if (!userPass1.getText().equals(userPassConfirm.getText())) {
					registerMessage.setText("Password is not confirmed");
				} else {
					request.getResponse(Command.INSERT_CLIENT, cl);
					registerMessage.setTextFill(Color.GREEN);
					registerMessage.setText("The accout was created succesfully");
					loginPane.toFront();
					loginLabel.setText(null);
				}
			}
		} else {
			registerMessage.setText("Username already in use");
		}

	}

	@FXML
	void loginSupplierAction(ActionEvent event) throws IOException, NoSuchAlgorithmException {
		Request req = new Request();
		String username = supplierLogin.getText().trim().toLowerCase();
		String password = encryptMD5(supplierPass.getText());
		if ((Boolean) req.getResponse(Command.CONTAINS_SUPPLIER_USERNAME, username)) {
			Supplier furnizor = (Supplier) req.getResponse(Command.FIND_SUPPLIER_USER, username);
			if (furnizor.getPassword().equals(password)) {

				Stage primaryStage = new Stage();
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SupplierWindow.fxml"));
				Parent root = fxmlLoader.load();
				Scene scene = new Scene(root, 1280, 720);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Easy Pay");
				primaryStage.getIcons().add(new Image("/images/logo.png"));
				primaryStage.setResizable(false);
				primaryStage.show();
				SupplierController control = fxmlLoader.getController();
				control.setCurrentFurnizor(furnizor);

				Main.getStage().close();
				Main.setStage(primaryStage);

			} else {
				loginSupplierLabel.setText("Wrong password");
			}
		} else {
			loginSupplierLabel.setText("Username does not exist");
		}

	}

	@FXML
	void showClientPane(MouseEvent event) {
		loginPane.toFront();
	}

	@FXML
	void showFurnizorPane(MouseEvent event) {
		supplierLoginPane.toFront();
	}

	@FXML
	void actionBackToMain(ActionEvent event) {
		mainLoginPane.toFront();

	}

	@FXML
	void showRegisterPane(ActionEvent event) {
		registerPane.toFront();

	}

	@FXML
	void actionBackToLogin(ActionEvent event) {
		loginPane.toFront();

	}

}
