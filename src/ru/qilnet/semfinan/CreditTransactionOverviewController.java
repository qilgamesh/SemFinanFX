package ru.qilnet.semfinan;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.qilnet.semfinan.model.Transaction;
import ru.qilnet.semfinan.util.DateUtil;

/**
 * @author Andrey Semenyuk
 */
public class CreditTransactionOverviewController {
	@FXML
	private TableView<Transaction> creditTransactionTable;
	@FXML
	private TableColumn<Transaction, String> creditDescriptionColumn;
	@FXML
	private TableColumn<Transaction, String> creditSumColumn;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Label sumLabel;
	@FXML
	private Label dayLabel;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public CreditTransactionOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the transaction table with the two columns.
		creditDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		creditSumColumn.setCellValueFactory(cellData -> cellData.getValue().sumProperty());

		// Clear transaction details.
		showTransactionDetails(null);

		// Listen for selection changes and show the transaction details when changed.
		creditTransactionTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showTransactionDetails(newValue));
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		creditTransactionTable.setItems(mainApp.getTransactionData());
	}

	/**
	 * Fills all text fields to show details about the transaction.
	 * If the specified transaction is null, all text fields are cleared.
	 *
	 * @param transaction the transaction or null
	 */
	private void showTransactionDetails(Transaction transaction) {
		if (transaction != null) {
			// Fill the labels with info from the transaction object.
			descriptionLabel.setText(transaction.getDescription());
			sumLabel.setText(transaction.getSum() + "руб.");
			dayLabel.setText(DateUtil.format(transaction.getDay()));

		} else {
			// transaction is null, remove all the text.
			descriptionLabel.setText("");
			sumLabel.setText("");
			dayLabel.setText("");
		}
	}


	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteTransaction() {
		int selectedIndex = creditTransactionTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			creditTransactionTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Ничего не выбрано");
			alert.setHeaderText("Не выбрана транзакция");
			alert.setContentText("Пожалуйста, выберите транзакцию");
			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new transaction.
	 */
	@FXML
	private void handleNewTransaction() {
		Transaction tempTransaction = new Transaction();
		boolean okClicked = mainApp.showTransactionEditDialog(tempTransaction);
		if (okClicked) {
			mainApp.getTransactionData().add(tempTransaction);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected transaction.
	 */
	@FXML
	private void handleEditTransaction() {
		Transaction selectedTransaction = creditTransactionTable.getSelectionModel().getSelectedItem();
		if (selectedTransaction != null) {
			boolean okClicked = mainApp.showTransactionEditDialog(selectedTransaction);
			if (okClicked) {
				showTransactionDetails(selectedTransaction);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Ничего не выбрано");
			alert.setHeaderText("Не выбрана транзакция");
			alert.setContentText("Пожалуйста, выберите транзакцию");

			alert.showAndWait();
		}
	}
}
