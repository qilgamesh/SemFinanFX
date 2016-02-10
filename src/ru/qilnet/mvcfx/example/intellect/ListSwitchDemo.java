package ru.qilnet.mvcfx.example.intellect;

import ru.qilnet.mvcfx.model.ListModel;

/**
 * Демонстрация
 */
public class ListSwitchDemo {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		shell.setText("ListSwitch");
		final ListSwitchView view = new ListSwitchView(shell);
		view.setModel(new ListModel<Switch>());
		shell.pack();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}
}
