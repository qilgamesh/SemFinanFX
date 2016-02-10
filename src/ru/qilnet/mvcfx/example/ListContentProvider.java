package ru.qilnet.mvcfx.example;

import javafx.scene.control.ListCell;

/**
 * Провайдер содержимого списка
 *
 * @param <P> свойство модели
 */
public class ListContentProvider<P> extends ListCell<String> {

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);

	}
}
