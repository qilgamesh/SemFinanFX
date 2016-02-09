package ru.qilnet.semfinanfx;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing an accordion where other JavaFX elements can be placed.
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

}