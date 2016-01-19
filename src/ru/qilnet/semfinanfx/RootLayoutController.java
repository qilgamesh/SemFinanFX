package ru.qilnet.semfinanfx;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

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
		this.mainApp = mainApp;
	}

	/**
	 * Creates an empty SemFinanFX DataBase.
	 */
	@FXML
	private void handleNew() {
		mainApp.getTransactionData().clear();
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

		/**
		 * TODO open SemFinanFX DataBase method

		if (file != null) {
			mainApp.loadSemFinanDB(file);
		}
		 */
	}

	/**
	 * Saves the file to the SemFinanFX DataBase file that is currently open. If there is no
	 * open file, the "save as" dialog is shown.
	 */
	@FXML
	private void handleSave() {
		File sfdbFile = mainApp.getTransactionFilePath();

		/** TODO Save SemFinanFX DataBase method
		if (personFile != null) {
			mainApp.saveSemFinanDB(sfdbFile);
		} else {
			handleSaveAs();
		}
		 */
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
			/** TODO Save SemFinanFX DataBase method
			mainApp.saveSemFinanDB(file);
			 */
		}
	}

	/**
	 * TODO Opens an about dialog.
	 *

	@FXML
	private void handleAbout() {
		Dialogs.create()
				.title("SemFinanFX")
				.masthead("About")
				.message("Author: Andrey Semenyuk\nMailTo: andrey@qilnet.ru")
				.showInformation();
	}
	 */
	
	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}
}