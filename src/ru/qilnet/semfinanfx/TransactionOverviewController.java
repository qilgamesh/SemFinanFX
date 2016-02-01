package ru.qilnet.semfinanfx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.qilnet.semfinanfx.model.Transaction;
import ru.qilnet.semfinanfx.util.DateUtil;

import java.time.LocalDate;

/**
 * @author Andrey Semenyuk
 */
public class TransactionOverviewController {
	@FXML
	private ChoiceBox<String> monthChoice;
	@FXML
	private Label yearLabel;
	@FXML
	private TableView<Transaction> transactionTable;
	@FXML
	private TableView<Transaction> debitTable;
	@FXML
	private TableView<Transaction> scheduledTable;
	@FXML
	private TableColumn<Transaction, String> dayColumn;
	@FXML
	private TableColumn<Transaction, String> scheduledDayColumn;
	@FXML
	private TableColumn<Transaction, String> descriptionColumn;
	@FXML
	private TableColumn<Transaction, String> creditColumn;
	@FXML
	private TableColumn<Transaction, String> scheduledCreditSumColumn;
	@FXML
	private TableColumn<Transaction, String> debitDescriptionColumn;
	@FXML
	private TableColumn<Transaction, String> debitColumn;
	@FXML
	private Label debitTotalLabel;
	@FXML
	private Label creditTotalLabel;
	@FXML
	private Label balanceLabel;

	private LocalDate date;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public TransactionOverviewController() {
		System.out.println("TransactionOverviewController constructor");
		date = LocalDate.now().withDayOfMonth(1);
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		System.out.println("TransactionOverviewController initialize");

		// Initialize the transaction table with the two columns.
		dayColumn.setCellValueFactory(cellData -> cellData.getValue().dayOfMonthProperty());
		//dayColumn.setStyle("-fx-alignment: CENTER;");
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

		creditColumn.setCellValueFactory(cellData -> cellData.getValue().creditSumProperty());
		//creditColumn.setStyle("-fx-alignment: CENTER; -fx-text-fill: blue;");

		debitColumn.setCellValueFactory(cellData -> cellData.getValue().debitSumProperty());
		//debitColumn.setStyle("-fx-alignment: CENTER; -fx-text-fill: red;");

		showTransactionDetails(null);
		transactionTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showTransactionDetails(newValue));

		yearLabel.setText(String.valueOf(date.getYear()));

		monthChoice.setItems(DateUtil.getListOfMonths());
		monthChoice.setValue(DateUtil.getMonthName(date));

		monthChoice.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
			date = date.withYear(Integer.valueOf(yearLabel.getText())).withMonth(new_value.intValue() + 1);
			transactionTable.setItems(mainApp.getTransactions(date, false));
			updateTotals();
			System.out.println("updateTotals in method monthChoice");
		});


	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		// Add observable list data to the table
		transactionTable.setItems(mainApp.getTransactions(date));
		updateTotals();
	}

	/**
	 * Fills all text fields to show details about the transaction.
	 * If the specified transaction is null, all text fields are cleared.
	 *
	 * TODO show details of given transaction
	 *
	 * @param transaction the transaction or null
	 */
	private void showTransactionDetails(Transaction transaction) {
/*		if (transaction != null) {
			// Fill the labels with info from the transaction object.
			uidLabel.setText(String.valueOf(mainApp.getTransactions(date).indexOf(transaction)));
			descriptionLabel.setText(transaction.getDescription());
			sumLabel.setText(transaction.getCreditSum() + "руб.");
		} else {
			// transaction is null, remove all the text.
			descriptionLabel.setText("");
			sumLabel.setText("");
			uidLabel.setText("");
		}*/
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new transaction.
	 */
	@FXML
	private void handleNewTransaction() {
		Transaction tempTransaction = new Transaction();
		boolean okClicked;
		if (date.isEqual(LocalDate.now().withDayOfMonth(1))) {
			okClicked = mainApp.showTransactionEditDialog(LocalDate.now(), tempTransaction);
		} else {
			okClicked = mainApp.showTransactionEditDialog(date, tempTransaction);
		}
		if (okClicked) {
			mainApp.getTransactions(date).add(tempTransaction);
			System.out.println("updateTotals in method handleNewTransaction");
			updateTotals();
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected transaction.
	 */
	@FXML
	private void handleEditTransaction() {
		Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
		if (selectedTransaction != null) {
			boolean okClicked = mainApp.showTransactionEditDialog(date.withDayOfMonth(Integer.valueOf(selectedTransaction.getDayOfMonth())), selectedTransaction);
			if (okClicked) {
				showTransactionDetails(selectedTransaction);
				System.out.println("updateTotals in method handleEditTransaction");
				updateTotals();
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

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteTransaction() {
		int selectedIndex = transactionTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			transactionTable.getItems().remove(selectedIndex);
			updateTotals();
			System.out.println("updateTotals in method handleDeleteTransaction");
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
	 * Called when the user clicks the prev button. Set value to name of prev month
	 */
	@FXML
	private void handlePrevMonth() {
		if (date.getMonthValue() == 1) {
			yearLabel.setText(String.valueOf(Integer.valueOf(yearLabel.getText()) - 1));
			monthChoice.setValue(DateUtil.getMonthName(12));
		} else {
			monthChoice.setValue(DateUtil.getMonthName(date.getMonthValue() - 1));
		}
	}

	/**
	 * Called when the user clicks the next button. Set value to name of next month
	 */
	@FXML
	private void handleNextMonth() {
		if (date.getMonthValue() == 12) {
			yearLabel.setText(String.valueOf(Integer.valueOf(yearLabel.getText()) + 1));
			monthChoice.setValue(DateUtil.getMonthName(1));
		} else {
			monthChoice.setValue(DateUtil.getMonthName(date.getMonthValue() + 1));
		}
	}

	private void updateTotals() {
		int c = mainApp.getAllTransactions().getMonthTransactions(date).getCreditTotal();
		int d = mainApp.getAllTransactions().getMonthTransactions(date).getDebitTotal();
		creditTotalLabel.setText(String.valueOf(c));
		debitTotalLabel.setText(String.valueOf(d));
		if ((c - d) > 0) {
			balanceLabel.setText("Остаток: " + String.valueOf(c - d) + "руб.");
			balanceLabel.setStyle("-fx-text-fill: black;");
		} else if ((c - d) < 0) {
			balanceLabel.setStyle("-fx-text-fill: red;");
			balanceLabel.setText("Как так? " + (c - d) + "руб.");
		} else {
			balanceLabel.setText("Тии...");
		}
	}


}
