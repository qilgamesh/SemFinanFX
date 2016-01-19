package ru.qilnet.semfinanfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.qilnet.semfinanfx.model.Transaction;

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
        transactionData.add(new Transaction("05", "Зарплата", "20000"));
        transactionData.add(new Transaction("06", "Шабашка", "1000"));
        transactionData.add(new Transaction("07", "Подарок", "1000"));
        transactionData.add(new Transaction("15", "Премия", "5000"));
    }

    /**
     * Returns the data as an observable list of Transactions.
     * @return
     */
    public ObservableList<Transaction> getTransactionData() {
        return transactionData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SemFinanFX");
        initRootLayout();
        showAllTableOverview();
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
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the transaction overview inside the root layout.
     */
    public void showAllTableOverview() {
        try {
            // Load transaction overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AllTransactionsOverview.fxml"));
            Pane allTransactionsOverview = loader.load();

            // Set transaction overview into the center of root layout.
            rootLayout.setCenter(allTransactionsOverview);

            // Give the controller access to the main app.
            CreditTransactionOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
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
	 * @return
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
			primaryStage.setTitle("AddressApp - " + file.getName());
		} else {
			prefs.remove("filePath");

			// Update the stage title.
			primaryStage.setTitle("AddressApp");
		}
	}

	/**
	 * Loads SemFinanFX data from the specified file. The current SemFinanFX data will
	 * be replaced.
	 *
	 * @param file
	 *
	 * TODO SemFinanFX load
	 */
	public void loadSemFinanDB(File file) {

	}

	/**
	 * Saves the current SemFinanFX data to the specified file.
	 *
	 * @param file
	 *
	 * TODO SemFinanFX save
	 */
	public void saveSemFinanDB(File file) {

	}

    public static void main(String[] args) {
        launch(args);
    }
}
