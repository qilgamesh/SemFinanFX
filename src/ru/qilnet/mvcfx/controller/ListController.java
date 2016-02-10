package ru.qilnet.mvcfx.controller;

import ru.qilnet.mvcfx.model.ListModel;
import ru.qilnet.mvcfx.model.Model;

import java.util.Arrays;
import java.util.Collection;

/**
 * Контроллер списка
 *
 * @param <P> свойство модели
 */
public class ListController<P> implements
		IController<ListController.O, ListModel<P>, Collection<Model<P>>> {
	/**
	 * Операции над моделью списка
	 */
	public enum O {
		/**
		 * Добавить элемент
		 */
		ADD,
		/**
		 * Удалить элемент
		 */
		REMOVE
	}

	public void execute(O operation, ListModel<P> model,
						Collection<Model<P>> attribute) {
		if (operation == null)
			throw new NullPointerException("Пустой параметр operation");
		if (model == null)
			throw new NullPointerException("Пустой параметр model");
		if (attribute == null)
			throw new NullPointerException("Пустой параметр attribute");
		switch (operation) {
			case ADD:
				for (final Model<P> _model : attribute)
					model.add(_model);
				break;
			case REMOVE:
				for (final Model<P> _model : attribute)
					model.remove(_model);
				break;
			default:
				throw new IllegalArgumentException("Неизвестная операция: " +
						operation);
		}
	}

	@SuppressWarnings("unchecked")
	public void execute(O operation, ListModel<P> model, Model<P> attribute) {
		execute(operation, model, Arrays.asList(attribute));
	}
}
