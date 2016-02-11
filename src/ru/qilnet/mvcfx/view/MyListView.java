package ru.qilnet.mvcfx.view;

import ru.qilnet.mvcfx.controller.Controller;
import ru.qilnet.mvcfx.controller.ListController;
import ru.qilnet.mvcfx.model.ListModel;
import ru.qilnet.mvcfx.model.Model;

import java.util.Collection;

/**
 * Представление списка
 *
 * @param <P> свойство модели
 */
public abstract class MyListView<P> extends BaseView<ListModel<P>, Collection<Model<P>>> {
	private final Controller<P> controller = new Controller<>();

	private final ListController<P> listController = new ListController<>();

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
}