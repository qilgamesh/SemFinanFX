package ru.qilnet.mvcfx.controller.intellect;

import ru.qilnet.mvcfx.controller.IController;
import ru.qilnet.mvcfx.model.Model;

import java.util.Stack;

/**
 * Интеллектуальный контроллер
 *
 * @param <P> свойство модели
 */
public class Controller<P> implements IController<Controller.O, Model<P>, P> {
	/**
	 * Операции над моделью
	 */
	public enum O {
		/**
		 * Редактировать
		 */
		EDIT,
		/**
		 * Отменить последнее действие
		 */
		UNDO
	}

	private final Stack<P> history = new Stack<P>();

	public void execute(O operation, Model<P> model, P attribute) {
		if (operation == null)
			throw new NullPointerException("Пустой параметр operation");
		if (model == null)
			throw new NullPointerException("Пустой параметр model");
		switch (operation) {
			case EDIT:
				if (attribute == null)
					throw new NullPointerException("Пустой параметр attribute");
				if (!model.getProperty().equals(attribute)) {
					history.push(model.getProperty());
					model.setProperty(attribute);
				}
				break;
			case UNDO:
				if (!history.empty()) {
					final P property = history.pop();
					if (!model.getProperty().equals(property))
						model.setProperty(property);
				}
				break;
			default:
				throw new IllegalArgumentException("Неизвестная операция: " +
						operation);
		}
	}
}
