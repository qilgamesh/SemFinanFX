package ru.qilnet.mvcfx.example.intellect;

import ru.qilnet.mvcfx.model.Model;

/**
 * Демонстрация
 */
public class SwitchDemo {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(4, false));
		shell.setText("Switch");
		final SwitchView view = new SwitchView(shell);
		view.setModel(new Model<Switch>(new Switch()));
		shell.pack();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}
}
