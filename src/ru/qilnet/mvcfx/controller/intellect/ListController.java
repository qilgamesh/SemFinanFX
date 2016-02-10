package ru.qilnet.mvcfx.controller.intellect;

import ru.qilnet.mvcfx.controller.IController;
import ru.qilnet.mvcfx.model.ListModel;
import ru.qilnet.mvcfx.model.Model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Stack;

/**
 * Интеллектуальный контроллер списка
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
		REMOVE,
		/**
		 * Отменить последнее действие
		 */
		UNDO
	}

	/**
	 * Элемент истории
	 */
	private class HistoryItem {
		private final O operation;

		private final Model<P> model;

		/**
		 * Конструктор
		 *
		 * @param operation операция над моделью списка
		 * @param model     модель
		 */
		public HistoryItem(O operation, Model<P> model) {
			if (operation == null)
				throw new NullPointerException("Пустой параметр operation");
			if (model == null)
				throw new NullPointerException("Пустой параметр model");
			this.operation = operation;
			this.model = model;
		}
	}

	private final Stack<HistoryItem> history = new Stack<HistoryItem>();

	public void execute(O operation, ListModel<P> model,
						Collection<Model<P>> attribute) {
		if (operation == null)
			throw new NullPointerException("Пустой параметр operation");
		if (model == null)
			throw new NullPointerException("Пустой параметр model");
		switch (operation) {
			case ADD:
				if (attribute == null)
					throw new NullPointerException("Пустой параметр attribute");
				for (final Model<P> _model : attribute) {
					history.push(new HistoryItem(operation, _model));
					model.add(_model);
				}
				break;
			case REMOVE:
				if (attribute == null)
					throw new NullPointerException("Пустой параметр attribute");
				for (final Model<P> _model : attribute) {
					history.push(new HistoryItem(operation, _model));
					model.remove(_model);
				}
				break;
			case UNDO:
				if (!history.empty()) {
					final HistoryItem item = history.pop();
					switch (item.operation) {
						case ADD:
							model.remove(item.model);
							break;
						case REMOVE:
							model.add(item.model);
							break;
						default:
							throw new IllegalArgumentException(
									"Неизвестная операция: " + item.operation);
					}
				}
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
