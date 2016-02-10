package ru.qilnet.mvcfx.model;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Модель
 *
 * @param <P> свойство модели
 */
public class Model<P> {
	private P property;

	private final Collection<IModelSubscriber<P>> subscribers = new CopyOnWriteArrayList<IModelSubscriber<P>>();

	/**
	 * Конструктор
	 *
	 * @param property свойство модели
	 */
	public Model(P property) {
		if (property == null)
			throw new NullPointerException("Пустой параметр");
		this.property = property;
	}

	/**
	 * Получить свойство модели
	 *
	 * @return свойство модели
	 */
	public P getProperty() {
		return property;
	}

	/**
	 * Установить свойство модели
	 *
	 * @param property свойство модели
	 */
	public void setProperty(P property) {
		if (property == null)
			throw new NullPointerException("Пустой параметр");
		this.property = property;
		notifySubscribers();
	}

	/**
	 * Оповестить всех подписчиков
	 */
	protected void notifySubscribers() {
		for (final IModelSubscriber<P> subscriber : subscribers)
			notifySubscriber(subscriber);
	}

	/**
	 * Оповестить подписчика
	 *
	 * @param subscriber подписчик модели
	 */
	private void notifySubscriber(IModelSubscriber<P> subscriber) {
		assert subscriber != null;
		subscriber.modelChanged(this);
	}

	/**
	 * Подписаться
	 *
	 * @param subscriber подписчик модели
	 */
	public void subscribe(IModelSubscriber<P> subscriber) {
		if (subscriber == null)
			throw new NullPointerException("Пустой параметр");
		if (subscribers.contains(subscriber))
			throw new IllegalArgumentException("Повторная подписка: " +
					subscriber);
		subscribers.add(subscriber);
		notifySubscriber(subscriber);
	}

	/**
	 * Отписаться
	 *
	 * @param subscriber подписчик модели
	 */
	public void unsubscribe(IModelSubscriber<P> subscriber) {
		if (subscriber == null)
			throw new NullPointerException("Пустой параметр");
		if (!subscribers.contains(subscriber))
			throw new IllegalArgumentException("Неизвестный подписчик: " +
					subscriber);
		subscribers.remove(subscriber);
	}

	@Override
	public String toString() {
		return property.toString();
	}
}