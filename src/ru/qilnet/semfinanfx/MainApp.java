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
import ru.qilnet.semfinanfx.model.AllTransactionListWrapper;
import ru.qilnet.semfinanfx.model.Transaction;
import ru.qilnet.semfinanfx.model.TransactionListWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	/**
	 * The observable list of all Transactions.
	 */
	private List<TransactionListWrapper> allTransactions;

	/**
	 * The observable list of month Transactions.
	 */
	private ObservableList<Transaction> transactions = FXCollections.observableArrayList(new Transaction());

	/**
	 * Constructor
	 */
	public MainApp() {
		System.out.println("method MainApp");
		allTransactions = new ArrayList<>();
		TransactionListWrapper monthTransactions = new TransactionListWrapper();
		monthTransactions.setTransactions(FXCollections.observableArrayList(new Transaction()));
		allTransactions.add(monthTransactions);

	}

	/**
	 * Returns the data as an observable list of all Transactions.
	 *
	 * @return list of transaction
	 */
	public ObservableList<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * Returns the data as an observable list of Transactions for given month.
	 *
	 * @return list of transaction
	 */
	public ObservableList<Transaction> getTransactions(LocalDate date) {
		ObservableList<Transaction> tempTransactions = FXCollections.observableArrayList();
		int lm = date.lengthOfMonth();
		tempTransactions.addAll(transactions.stream().filter(transaction -> transaction.getDate().isBefore(date.withDayOfMonth(lm)) &&
				transaction.getDate().isAfter(date.withDayOfMonth(1))).collect(Collectors.toList()));
		if (tempTransactions.size() == 0) {
			tempTransactions.add(new Transaction());
		}
		return tempTransactions;
	}

	public void setTransactionData(ObservableList<Transaction> transactions) {
		this.transactions = transactions;
	}
	/**
	 * TODO method to set MonthTransactionData for current month
	 * <p>
	 * public setTransactionData(ObservableList<Transaction> currentTransactionData) {
	 * this.currentTransactionData = currentTransactionData;
	 * }
	 */

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("method start");
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
			System.out.println("method initRootLayout");
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
			loadTransactionData(file);
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
		} else {
			prefs.remove("filePath");
		}
	}

	/**
	 * Loads all transactions data from the specified file. The current TransactionData data will
	 * be replaced.
	 *
	 * @param file
	 */
	public void loadTransactionData(File file) {
		System.out.println("method loadTransactionData");
		FileInputStream fIn = null;
		ObjectInputStream oIn = null;
		try {
			fIn = new FileInputStream(file);
			oIn = new ObjectInputStream(fIn);
			/** TODO Transaction list wrapper for load from file
			transactions = oIn.readObject();
			 */
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Ошибка");
			alert.setHeaderText("Не удалось загрузить данные из файла:\n" + file.getPath());
			alert.setContentText("Будет создана новая база данных");
			alert.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves all transactions data to the specified file.
	 *
	 * @param file
	 */
	public void saveTransactionData(File file) {
		try {
			JAXBContext context = JAXBContext
					.newInstance(TransactionListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our person data.
			AllTransactionListWrapper wrapper = new AllTransactionListWrapper();
			wrapper.setTransactions(allTransactions);

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
		/** TODO Transaction list wrapper for save into file
		FileOutputStream fOut = null;
		ObjectOutputStream oOut = null;
		try {
			fOut = new FileOutputStream(file);
			oOut = new ObjectOutputStream(fOut);
			/** TODO Transaction list wrapper for save into file
			oOut.writeObject(transactionData);

			setTransactionFilePath(file);
		} catch (IOException e) {
			try {
				assert oOut != null;
				oOut.flush();
				oOut.close();
				fOut.close();
			} catch (IOException ioe) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Ошибка");
				alert.setHeaderText("Не удалось сохранить данные в файл:\n" + file.getPath());
				alert.setContentText(ioe.toString());
				alert.showAndWait();
			}
		}
		 */
	}

	public static void main(String[] args) {
		launch(args);
	}
}
