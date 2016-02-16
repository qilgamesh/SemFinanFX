package ru.qilnet.semfinanfx;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.qilnet.semfinanfx.model.Transaction;
import ru.qilnet.semfinanfx.model.TransactionsData;
import ru.qilnet.semfinanfx.util.DateUtil;

import java.time.LocalDate;

/**
 * @author Andrey Semenyuk
 */
public class TransactionOverviewController4 {

	// controls for date choice
	@FXML
	private ChoiceBox<String> monthChoice;
	@FXML
	private Label yearLabel;

	// done credit table
	@FXML
	private TableView<Transaction> credTable;
	@FXML
	private TableColumn<Transaction, String> credDayCol;
	@FXML
	private TableColumn<Transaction, String> credDescCol;
	@FXML
	private TableColumn<Transaction, String> credCol;

	// done debit table
	@FXML
	private TableView<Transaction> debTable;
	@FXML
	private TableColumn<Transaction, String> debDayCol;
	@FXML
	private TableColumn<Transaction, String> debDescCol;
	@FXML
	private TableColumn<Transaction, String> debCol;

	// scheduled credit table
	@FXML
	private TableView<Transaction> schedCredTable;
	@FXML
	private TableColumn<Transaction, String> schedCredDayCol;
	@FXML
	private TableColumn<Transaction, String> schedCredDescCol;
	@FXML
	private TableColumn<Transaction, String> schedCredCol;

	// scheduled debit table
	@FXML
	private TableView<Transaction> schedDebTable;
	@FXML
	private TableColumn<Transaction, String> schedDebDayCol;
	@FXML
	private TableColumn<Transaction, String> schedDebCol;
	@FXML
	private TableColumn<Transaction, String> schedDebDescCol;


	// show total info
	@FXML
	private Label debTotalLabel;
	@FXML
	private Label credTotalLabel;
	@FXML
	private Label balanceLabel;

	private LocalDate currDate;
	private TableView<Transaction> selectedTable;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public TransactionOverviewController4() {
		currDate = LocalDate.now();
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		// credit table
		credDayCol.setCellValueFactory(cellData -> cellData.getValue().dayOfMonthProperty());
		credDescCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		credCol.setCellValueFactory(cellData -> cellData.getValue().creditSumProperty());

		// debit table
		debDayCol.setCellValueFactory(cellData -> cellData.getValue().dayOfMonthProperty());
		debDescCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		debCol.setCellValueFactory(cellData -> cellData.getValue().debitSumProperty());

		// scheduled credit table
		schedCredDayCol.setCellValueFactory(cellData -> cellData.getValue().dayOfMonthProperty());
		schedCredDescCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		schedCredCol.setCellValueFactory(cellData -> cellData.getValue().creditSumProperty());

		// scheduled debit table
		schedDebDayCol.setCellValueFactory(cellData -> cellData.getValue().dayOfMonthProperty());
		schedDebDescCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		schedDebCol.setCellValueFactory(cellData -> cellData.getValue().debitSumProperty());

		showTransactionDetails(null);

		// listeners for selection on table
		credTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectedTable = credTable;
			showTransactionDetails(newValue);
		});

		debTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectedTable = debTable;
			showTransactionDetails(newValue);
		});

		schedCredTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectedTable = schedCredTable;
			showTransactionDetails(newValue);
		});

		schedDebTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectedTable = schedDebTable;
			showTransactionDetails(newValue);
		});

		yearLabel.setText(String.valueOf(LocalDate.now().getYear()));

		monthChoice.setItems(DateUtil.getListOfMonths());
		monthChoice.setValue(DateUtil.getMonthName(LocalDate.now()));

		monthChoice.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
			currDate = currDate.withYear(Integer.valueOf(yearLabel.getText())).withMonth(new_value.intValue() + 1);
			mainApp.getTransactionsData().updateTransactions(currDate.withDayOfMonth(1));
			updateTables();
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
		updateTables();
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
		if (currDate.getMonthValue() > LocalDate.now().getMonthValue()) {
			tempTransaction.setScheduled(true);
		}
		boolean okClicked = false;
		if (currDate.isEqual(LocalDate.now().withDayOfMonth(1))) {
			okClicked = mainApp.showTransactionEditDialog(LocalDate.now(), tempTransaction);
		} else {
			okClicked = mainApp.showTransactionEditDialog(currDate, tempTransaction);
		}
		if (okClicked) {
			if (tempTransaction.getScheduled()) {
				mainApp.getTransactionsData().getSchedTransactions().add(tempTransaction);
			} else {
				mainApp.getTransactionsData().getDoneTransactions().add(tempTransaction);
			}
			updateTables();
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected transaction.
	 */
	@FXML
	private void handleEditTransaction() {
		Transaction selectedTransaction = selectedTable.getSelectionModel().getSelectedItem();
		if (selectedTransaction != null) {
			boolean okClicked = mainApp.showTransactionEditDialog(currDate.withDayOfMonth(Integer.valueOf(
							selectedTransaction.getDayOfMonth())), selectedTransaction);
			if (okClicked) {
				showTransactionDetails(selectedTransaction);
				updateTables();
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
		int selectedIndex = selectedTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			selectedTable.getItems().remove(selectedIndex);
			updateTotals();
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
		if (currDate.getMonthValue() == 1) {
			yearLabel.setText(String.valueOf(Integer.valueOf(yearLabel.getText()) - 1));
			monthChoice.setValue(DateUtil.getMonthName(12));
		} else {
			monthChoice.setValue(DateUtil.getMonthName(currDate.getMonthValue() - 1));
		}
	}

	/**
	 * Called when the user clicks the next button. Set value to name of next month
	 */
	@FXML
	private void handleNextMonth() {
		if (currDate.getMonthValue() == 12) {
			yearLabel.setText(String.valueOf(Integer.valueOf(yearLabel.getText()) + 1));
			monthChoice.setValue(DateUtil.getMonthName(1));
		} else {
			monthChoice.setValue(DateUtil.getMonthName(currDate.getMonthValue() + 1));
		}
	}

	private void updateTables(){
		credTable.setItems(mainApp.getTransactionsData().getDoneTransactions(TransactionsData.type.CREDIT));
		debTable.setItems(mainApp.getTransactionsData().getDoneTransactions(TransactionsData.type.DEBIT));
		schedCredTable.setItems(mainApp.getTransactionsData().getSchedTransactions(TransactionsData.type.CREDIT));
		schedDebTable.setItems(mainApp.getTransactionsData().getSchedTransactions(TransactionsData.type.DEBIT));
		updateTotals(true);
	}

	private void updateTotals() {
		updateTotals(false);
	}

	private void updateTotals(boolean force) {
		if (selectedTable == credTable || selectedTable == debTable || force) {
			ObservableList<Transaction> tt = mainApp.getTransactionsData().getDoneTransactions();
			int debit = 0;
			int credit = 0;
			if (tt.size() > 0) {
				for (Transaction tr : tt) {
					if (tr.getCreditSum() == null) {
						debit += Integer.valueOf(tr.getDebitSum());
					} else {
						credit += Integer.valueOf(tr.getCreditSum());
					}
				}
			}
			int bal = credit - debit;
			credTotalLabel.setText(String.valueOf(credit));
			debTotalLabel.setText(String.valueOf(debit));
			if ((bal) > 0) {
				balanceLabel.setText("Остаток: " + bal + "руб.");
				balanceLabel.setStyle("-fx-text-fill: black;");
			} else if ((bal) < 0) {
				balanceLabel.setStyle("-fx-text-fill: red;");
				balanceLabel.setText("Как так? " + bal + "руб.");
			} else {
				balanceLabel.setText("Ти...");
			}
		}

	}


}
