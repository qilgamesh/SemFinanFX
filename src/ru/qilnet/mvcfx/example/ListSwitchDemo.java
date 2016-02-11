package ru.qilnet.mvcfx.example;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.qilnet.mvcfx.model.ListModel;

/**
 * Демонстрация
 */
public class ListSwitchDemo extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		final ListSwitchView view = new ListSwitchView();
		view.setModel(new ListModel<>());

		Parent root = view.getListSwitchView();

		primaryStage.setTitle("ListSwitch");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}

}
