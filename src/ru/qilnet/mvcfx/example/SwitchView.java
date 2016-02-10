package ru.qilnet.mvcfx.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import ru.qilnet.mvcfx.model.Model;
import ru.qilnet.mvcfx.view.View;

/**
 * Представление переключателя
 */
public class SwitchView extends View<Switch> {

	private final Label label;
	private final GridPane gridPane;

	/**
	 * Конструктор
	 *
	 */
	public SwitchView() {
		gridPane = new GridPane();
		gridPane.setPadding(new Insets(5));
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		ColumnConstraints col1 = new ColumnConstraints(150, 150,
				Double.MAX_VALUE);
		ColumnConstraints col2 = new ColumnConstraints(50);
		ColumnConstraints col3 = new ColumnConstraints(150, 150,
				Double.MAX_VALUE);
		col1.setHgrow(Priority.ALWAYS);
		col3.setHgrow(Priority.ALWAYS);
		gridPane.getColumnConstraints().addAll(col1, col2, col3);

		label = new Label("");
		GridPane.setHalignment(label, HPos.CENTER);
		gridPane.add(label, 0, 0);

		final Button onButton = new Button("Включить");
		onButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				edit(new Switch(true));
			}
		});

		final Button offButton = new Button("Выключить");
		offButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				edit(new Switch(false));
			}
		});


		gridPane.add(onButton, 1, 0);
		gridPane.add(offButton, 2, 0);
	}

	public Parent getSwitchView() {
		return gridPane;
	}

	@Override
	public void modelChanged(Model<Switch> model) {
		label.setText(model.toString());
	}
}
