package ru.qilnet.mvcfx.example;

/**
 * Переключатель
 */
public class Switch {
	private boolean state;

	/**
	 * Конструктор
	 */
	public Switch() {
	}

	/**
	 * Конструктор
	 *
	 * @param state состояние
	 */
	public Switch(boolean state) {
		this.state = state;
	}

	/**
	 * Получить состояние
	 *
	 * @return состояние
	 */
	public boolean getState() {
		return state;
	}

	@Override
	public String toString() {
		if (state)
			return "Включен";
		return "Выключен";
	}
}
