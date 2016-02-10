package ru.qilnet.mvcfx.example;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.qilnet.mvcfx.model.Model;

/**
 * Демонстрация
 */
public class SwitchDemo extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		final SwitchView view = new SwitchView();
		view.setModel(new Model<Switch>(new Switch()));

		Parent root = view.getSwitchView();

		primaryStage.setTitle("Switch");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}

}
