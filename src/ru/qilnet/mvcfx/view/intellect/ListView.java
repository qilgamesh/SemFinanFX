package ru.qilnet.mvcfx.view.intellect;

import ru.qilnet.mvcfx.controller.intellect.Controller;
import ru.qilnet.mvcfx.controller.intellect.ListController;
import ru.qilnet.mvcfx.model.ListModel;
import ru.qilnet.mvcfx.model.Model;
import ru.qilnet.mvcfx.view.BaseView;

import java.util.Collection;

/**
 * Интеллектуальное представление списка
 *
 * @param <P> свойство модели
 */
public abstract class ListView<P> extends
		BaseView<ListModel<P>, Collection<Model<P>>> {
	private final Controller<P> controller = new Controller<P>();

	private final ListController<P> listController = new ListController<P>();

	/**
	 * Редактировать модель
	 *
	 * @param model    модель
	 * @param property свойство модели
	 */
	protected void edit(Model<P> model, P property) {
		controller.execute(Controller.O.EDIT, model, property);
	}

	/**
	 * Добавить модель
	 *
	 * @param model модель
	 */
	protected void add(Model<P> model) {
		listController.execute(ListController.O.ADD, getModel(), model);
	}

	/**
	 * Удалить модель
	 *
	 * @param model модель
	 */
	protected void delete(Model<P> model) {
		listController.execute(ListController.O.REMOVE, getModel(), model);
	}

	/**
	 * Отменить последнее действие
	 */
	protected void undo() {
		final Model<P> model = null;
		listController.execute(ListController.O.UNDO, getModel(), model);
	}
}