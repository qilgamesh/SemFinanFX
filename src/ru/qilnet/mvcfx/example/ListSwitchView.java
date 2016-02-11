package ru.qilnet.mvcfx.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import ru.qilnet.mvcfx.model.ListModel;
import ru.qilnet.mvcfx.model.Model;
import ru.qilnet.mvcfx.view.MyListView;

import java.util.Collection;

/**
 * Представление списка переключателей
 */
public class ListSwitchView extends MyListView<Switch> {
	private final ListView<Model<Switch>> viewer;
	private final GridPane gridPane;
	ObservableList<Model<Switch>> list = FXCollections.observableArrayList();
	/**
	 * Конструктор
	 */
	public ListSwitchView() {
		viewer = new ListView<>();
		viewer.setItems(list);

		//viewer.getCellFactory().;

		gridPane = new GridPane();
		gridPane.add(viewer, 0, 0);

		final Button addButton = new Button("Добавить");
		addButton.setOnAction(event -> add(new Model<>(new Switch())));
		gridPane.add(addButton, 0, 1);

		final Button deleteButton = new Button("Удалить");
		deleteButton.setOnAction(event -> delete(viewer.getSelectionModel().getSelectedItem()));
		gridPane.add(deleteButton, 0, 2);

		final Button onButton = new Button("Включить");
		onButton.setOnAction(event -> turn(true));
		gridPane.add(onButton, 0, 3);

		final Button offButton = new Button("Выключить");
		offButton.setOnAction(event -> turn(false));
		gridPane.add(offButton, 0, 4);

	}

	public Parent getListSwitchView() {
		return gridPane;
	}

	@Override
	public void setModel(ListModel<Switch> model) {
		super.setModel(model);
	}

	/**
	 * Переключить
	 *
	 * @param state состоние
	 */
	private void turn(boolean state) {
		edit(viewer.getSelectionModel().getSelectedItem(), new Switch(state));

	}

	@Override
	public void modelChanged(Model<Collection<Model<Switch>>> model) {
		list = FXCollections.observableArrayList(model.getProperty());
		viewer.refresh();
	}

}
