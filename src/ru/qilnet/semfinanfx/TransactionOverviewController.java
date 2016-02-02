package ru.qilnet.semfinanfx;

import javafx.collections.ObservableList;
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
	private TableView<Transaction> scheduledCreditTable;
	@FXML
	private TableView<Transaction> scheduledDebitTable;
	@FXML
	private TableColumn<Transaction, String> dayColumn;
	@FXML
	private TableColumn<Transaction, String> scheduledDayColumn;
	@FXML
	private TableColumn<Transaction, String> descriptionColumn;
	@FXML
	private TableColumn<Transaction, String> scheduledDescriptionColumn;
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

	private LocalDate currentDate;
	private TableView<Transaction> selectedTable;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public TransactionOverviewController() {
		System.out.println("set work date to now()");
		currentDate = LocalDate.now();
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		// transaction table
		dayColumn.setCellValueFactory(cellData -> cellData.getValue().dayOfMonthProperty());
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		creditColumn.setCellValueFactory(cellData -> cellData.getValue().creditSumProperty());
		debitColumn.setCellValueFactory(cellData -> cellData.getValue().debitSumProperty());

		// scheduled table
		scheduledDayColumn.setCellValueFactory(cellData -> cellData.getValue().dayOfMonthProperty());
		scheduledDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		scheduledCreditSumColumn.setCellValueFactory(cellData -> cellData.getValue().creditSumProperty());

		showTransactionDetails(null);

		// listener for selection on table
		transactionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectedTable = transactionTable;
			showTransactionDetails(newValue);
		});

		scheduledCreditTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectedTable = scheduledCreditTable;
			showTransactionDetails(newValue);
		});

		yearLabel.setText(String.valueOf(LocalDate.now().getYear()));

		monthChoice.setItems(DateUtil.getListOfMonths());
		monthChoice.setValue(DateUtil.getMonthName(LocalDate.now()));

		monthChoice.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
			currentDate = currentDate.withYear(Integer.valueOf(yearLabel.getText())).withMonth(new_value.intValue() + 1);
			mainApp.getTransactionsData().changeCurrentDate(currentDate);
			transactionTable.setItems(mainApp.getTransactionsData().getCompleteTransactions());
			scheduledCreditTable.setItems(mainApp.getTransactionsData().getScheduledTransactions());
			updateTotals();
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
		transactionTable.setItems(mainApp.getTransactionsData().getCompleteTransactions());
		scheduledCreditTable.setItems(mainApp.getTransactionsData().getScheduledTransactions());
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
		if (currentDate.isEqual(LocalDate.now().withDayOfMonth(1))) {
			okClicked = mainApp.showTransactionEditDialog(LocalDate.now(), tempTransaction);
		} else {
			okClicked = mainApp.showTransactionEditDialog(currentDate, tempTransaction);
		}
		if (okClicked) {
			if (tempTransaction.getScheduled()) {
				mainApp.getTransactionsData().getScheduledTransactions().add(tempTransaction);
			} else {
				mainApp.getTransactionsData().getCompleteTransactions().add(tempTransaction);
			}
			updateTotals();
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
			boolean okClicked = mainApp.showTransactionEditDialog(currentDate.withDayOfMonth(Integer.valueOf(
							selectedTransaction.getDayOfMonth())), selectedTransaction);
			if (okClicked) {
				showTransactionDetails(selectedTransaction);
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
		if (currentDate.getMonthValue() == 1) {
			yearLabel.setText(String.valueOf(Integer.valueOf(yearLabel.getText()) - 1));
			monthChoice.setValue(DateUtil.getMonthName(12));
		} else {
			monthChoice.setValue(DateUtil.getMonthName(currentDate.getMonthValue() - 1));
		}
	}

	/**
	 * Called when the user clicks the next button. Set value to name of next month
	 */
	@FXML
	private void handleNextMonth() {
		if (currentDate.getMonthValue() == 12) {
			yearLabel.setText(String.valueOf(Integer.valueOf(yearLabel.getText()) + 1));
			monthChoice.setValue(DateUtil.getMonthName(1));
		} else {
			monthChoice.setValue(DateUtil.getMonthName(currentDate.getMonthValue() + 1));
		}
	}

	private void updateTotals() {
		ObservableList<Transaction> tt = mainApp.getTransactionsData().getCompleteTransactions();
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
		creditTotalLabel.setText(String.valueOf(credit));
		debitTotalLabel.setText(String.valueOf(debit));
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
