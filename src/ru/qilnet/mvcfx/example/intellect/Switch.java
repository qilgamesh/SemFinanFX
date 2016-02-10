package ru.qilnet.mvcfx.example.intellect;

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

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj.getClass() == Switch.class &&
				getState() == ((Switch) obj).getState())
			return true;
		return super.equals(obj);
	}
}
