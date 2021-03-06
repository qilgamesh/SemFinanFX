package ru.qilnet.semfinanfx.view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 * Представление главного окна
 *
 * @author Andrey Semenyuk
 */
public class RootLayout extends BorderPane {

	private TabPane rootTabPane;

	public RootLayout() {
		rootTabPane = new TabPane();
		rootTabPane.setMinWidth(900);
		rootTabPane.setMinHeight(600);
		setCenter(rootTabPane);
	}

	public void addTab(Tab tab) {
		rootTabPane.getTabs().add(tab);
	}

	public TabPane getRootTabPane() {
		return rootTabPane;
	}
}
