package ru.qilnet.mvcfx.example;

import ru.qilnet.mvcfx.model.ListModel;

/**
 * Демонстрация
 */
public class TwoListSwitchDemo {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("TwoListSwitch");
		final Group group1 = new Group(shell, SWT.NONE);
		group1.setLayout(new GridLayout());
		group1.setText("First List View");
		final ListModel<Switch> model = new ListModel<Switch>();
		final ListSwitchView view1 = new ListSwitchView(group1);
		view1.setModel(model);
		final Group group2 = new Group(shell, SWT.NONE);
		group2.setLayout(new GridLayout());
		group2.setText("Second List View");
		final ListSwitchView view2 = new ListSwitchView(group2);
		view2.setModel(model);
		shell.pack();
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		display.dispose();
	}
}
