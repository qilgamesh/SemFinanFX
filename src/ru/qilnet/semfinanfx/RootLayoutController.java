package ru.qilnet.semfinanfx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import ru.qilnet.semfinanfx.model.TransactionsData;

import java.io.File;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a left menu and space where other JavaFX
 * elements can be placed.
 *
 * @author Andrey Semenyuk
 */
public class RootLayoutController {

	// Reference to the main application
	private MainApp mainApp;

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		System.out.println("RootLayoutController setMainApp");
		this.mainApp = mainApp;
	}

	/**
	 * Creates an empty SemFinanFX DataBase.
	 */
	@FXML
	private void handleNew() {
		mainApp.setTransactionsData(new TransactionsData());
		mainApp.setTransactionFilePath(null);
	}

	/**
	 * Opens a FileChooser to let the user select an SemFinanFX DataBase to load.
	 */
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"SemFinanFX DataBase file (*.sfdb)", "*.sfdb");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

		if (file != null) {
			mainApp.loadTransactionData(file);
		}
	}

	/**
	 * Saves the file to the SemFinanFX DataBase file that is currently open. If there is no
	 * open file, the "save as" dialog is shown.
	 */
	@FXML
	private void handleSave() {
		File sfdbFile = mainApp.getTransactionFilePath();
		if (sfdbFile != null) {
			mainApp.saveTransactionData(sfdbFile);
		} else {
			handleSaveAs();
		}
	}

	/**
	 * Opens a FileChooser to let the user select a file to save to.
	 */
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"SemFinanFX DataBase file (*.sfdb)", "*.sfdb");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".sfdb")) {
				file = new File(file.getPath() + ".sfdb");
			}
			mainApp.saveTransactionData(file);
		}
	}

	@FXML
	private void handleAbout() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("SemFinanFX");
		alert.setHeaderText(null);
		alert.setContentText("Author: Andrey Semenyuk\nMailTo: andrey@qilnet.ru");
		alert.showAndWait();
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}
}