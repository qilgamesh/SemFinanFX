package ru.qilnet.mvcfx.view.intellect;

import ru.qilnet.mvcfx.controller.intellect.Controller;
import ru.qilnet.mvcfx.model.Model;
import ru.qilnet.mvcfx.view.BaseView;

/**
 * Интеллектуальное представление
 *
 * @param <P> свойство модели
 */
public abstract class View<P> extends BaseView<Model<P>, P> {
	private final Controller<P> controller = new Controller<P>();

	/**
	 * Редактировать модель
	 *
	 * @param property свойство модели
	 */
	protected void edit(P property) {
		controller.execute(Controller.O.EDIT, getModel(), property);
	}

	/**
	 * Отменить последнее действие
	 */
	protected void undo() {
		controller.execute(Controller.O.UNDO, getModel(), null);
	}
}