package ru.qilnet.mvcfx.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import ru.qilnet.mvcfx.model.Model;
import ru.qilnet.mvcfx.view.MyListView;

import java.util.Collection;

/**
 * Представление списка переключателей
 */
public class ListSwitchView extends MyListView<Switch> {
	private final ListView<Model<Switch>> viewer;
	private final GridPane gridPane;

	/**
	 * Конструктор
	 */
	public ListSwitchView() {
		gridPane = new GridPane();
		ObservableList<Model<Switch>> list = FXCollections.observableArrayList();
		viewer = new ListView<>(list);

		viewer.setCellFactory(new Callback<ListView<Model<Switch>>, ListCell<Model<Switch>>>() {
			@Override
			public ListCell<Model<Switch>> call(ListView<Model<Switch>> p) {
				ListCell<Model<Switch>> cell = new ListCell<Model<Switch>>(){
					@Override
					protected void updateItem(Model<Switch> t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getProperty().toString());
						}
					}
				};
				return cell;
			}
		});

		gridPane.add(viewer, 0, 0);

		final Button addButton = new Button("Добавить");
		addButton.setOnAction(event -> add(new Model<Switch>(new Switch())));
		gridPane.add(addButton, 0, 1);

		final Button deleteButton = new Button("Удалить");
		deleteButton.setOnAction(event -> {
			final MultipleSelectionModel<Model<Switch>> selection = viewer.getSelectionModel();
			if (!selection.isEmpty())
			delete(selection.getSelectedItem());
		});
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

	/**
	 * Переключить
	 *
	 * @param state состоние
	 */
	@SuppressWarnings("unchecked")
	private void turn(boolean state) {
		final MultipleSelectionModel<Model<Switch>> selection = viewer.getSelectionModel();
		if (!selection.isEmpty())
			edit(selection.getSelectedItem(), new Switch(state));
	}

	@Override
	public void modelChanged(Model<Collection<Model<Switch>>> model) {
		viewer.refresh();
	}

}
