package ru.qilnet.mvcfx.model;

import java.util.Collection;
import java.util.HashSet;

/**
 * Модель списка
 *
 * @param <P> свойство модели
 */
public class ListModel<P> extends Model<Collection<Model<P>>> implements IModelSubscriber<P> {

	/**
	 * Конструктор
	 */
	public ListModel() {
		super(new HashSet<>());
	}

	/**
	 * Добавить модель
	 *
	 * @param model модель
	 */
	public void add(Model<P> model) {
		if (model == null)
			throw new NullPointerException("Пустой параметр");
		getProperty().add(model);
		model.subscribe(this);
	}

	@Override
	public void modelChanged(Model<P> model) {
		if (model == null)
			throw new NullPointerException("Пустой параметр");
		notifySubscribers();
	}

	/**
	 * Удалить модель
	 *
	 * @param model модель
	 */
	public void remove(Model<P> model) {
		if (model == null)
			throw new NullPointerException("Пустой параметр");
		model.unSubscribe(this);
		getProperty().remove(model);
		notifySubscribers();
	}
}