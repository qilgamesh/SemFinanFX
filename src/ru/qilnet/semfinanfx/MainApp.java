package ru.qilnet.semfinanfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.qilnet.semfinanfx.model.Transaction;
import ru.qilnet.semfinanfx.model.TransactionListWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	/**
	 * The data as an observable list of Transactions.
	 */
	private ObservableList<Transaction> transactionData = FXCollections.observableArrayList();

	/**
	 * Constructor
	 */
	public MainApp() {
		// Add some sample data
		transactionData.add(new Transaction("01", "Начальный остаток", "1000"));

	}

	/**
	 * Returns the data as an observable list of Transactions.
	 *
	 * @return list of transaction
	 */
	public ObservableList<Transaction> getTransactionData() {
		return transactionData;
	}

	/**
	 * TODO method to set TransactionData for current month
	 * <p>
	 * public setTransactionData(ObservableList<Transaction> transactionData) {
	 * this.transactionData = transactionData;
	 * }
	 */

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SemFinanFX");
		initRootLayout();
		showTransactionTableOverview();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Try to load last opened person file.
		File file = getTransactionFilePath();
		if (file != null) {
			loadSemFinanDB(file);
		}
	}

	/**
	 * Shows the transaction overview inside the root layout.
	 */
	public void showTransactionTableOverview() {
		try {
			// Load transaction overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TransactionsOverview.fxml"));
			Pane transactionsOverview = loader.load();

			// Set transaction overview into the center of root layout.
			rootLayout.setCenter(transactionsOverview);

			// Give the controller access to the main app.
			TransactionOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 *
	 * @return primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Opens a dialog to edit details for the specified transaction. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 *
	 * @param transaction the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showTransactionEditDialog(Transaction transaction) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TransactionEditDialog.fxml"));
			AnchorPane page = loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Редактирование транзакции");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the transaction into the controller.
			TransactionEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setTransaction(transaction);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns the SemFinanFX file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 *
	 * @return path of file or null if no preference
	 */
	public File getTransactionFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 *
	 * @param file the file or null to remove the path
	 */
	public void setTransactionFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());

			// Update the stage title.
			primaryStage.setTitle("SemFinanFX - " + file.getName());
		} else {
			prefs.remove("filePath");

			// Update the stage title.
			primaryStage.setTitle("SemFinanFX");
		}
	}

	/**
	 * Loads SemFinanFX data from the specified file. The current SemFinanFX data will
	 * be replaced.
	 *
	 * @param file
	 */
	public void loadSemFinanDB(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(TransactionListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			TransactionListWrapper wrapper = (TransactionListWrapper) um.unmarshal(file);

			transactionData.clear();
			transactionData.addAll(wrapper.getTransactions());

			// Save the file path to the registry.
			setTransactionFilePath(file);

		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Ошибка");
			alert.setHeaderText("Не удалось загрузить данные из файла:\n" + file.getPath());
			alert.setContentText(e.toString());
			alert.showAndWait();
		}

	}

	/**
	 * Saves the current SemFinanFX data to the specified file.
	 *
	 * @param file
	 */
	public void saveSemFinanDB(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(TransactionListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our transaction data.
			TransactionListWrapper wrapper = new TransactionListWrapper();
			wrapper.setTransactions(transactionData);

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);

			// Save the file path to the registry.
			setTransactionFilePath(file);
		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Ошибка");
			alert.setHeaderText("Не удалось сохранить данные в файл:\n" + file.getPath());
			alert.setContentText(e.toString());
			alert.showAndWait();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
