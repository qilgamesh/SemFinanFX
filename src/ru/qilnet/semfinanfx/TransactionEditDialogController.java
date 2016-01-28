package ru.qilnet.semfinanfx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.qilnet.semfinanfx.model.Transaction;

import java.time.LocalDate;

/**
 * Dialog to edit details of a transaction
 *
 * @author Andrey Semenyuk
 */
public class TransactionEditDialogController {

	@FXML
	private TextField descriptionField;
	@FXML
	private TextField sumField;
	@FXML
	private DatePicker dayPicker;

	private Stage dialogStage;
	private Transaction transaction;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the transaction to be edited in the dialog.
	 *
	 * @param transaction
	 */
	public void setTransaction(LocalDate date, Transaction transaction) {
		this.transaction = transaction;
		descriptionField.setText(transaction.getDescription());
		sumField.setText(transaction.getSum());
		if (LocalDate.now().isBefore(date)) {
			dayPicker.setValue(date.withDayOfMonth(1));
		} else {
			dayPicker.setValue(date.withDayOfMonth(Integer.valueOf(transaction.getDayOfMonth())));
		}

	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			transaction.setDescription(descriptionField.getText());
			transaction.setSum(sumField.getText());
			transaction.setDayOfMonth(dayPicker.getValue().getDayOfMonth());
			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (descriptionField.getText() == null || descriptionField.getText().length() == 0) {
			errorMessage += "Неверное описание!\n";
		}
		if (sumField.getText() == null || sumField.getText().length() == 0) {
			errorMessage += "Неверная сумма!\n";
		} else {
			// try to parse the sum into an double.
			try {
				Double.parseDouble(sumField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "Неверная сумма (должно быть число)!\n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Ошибки заполнения");
			alert.setHeaderText("Пожалуйста, исправьте неверно заполненые поля");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
}
