package ru.qilnet.mvcfx.example.intellect;

import ru.qilnet.mvcfx.model.Model;
import ru.qilnet.mvcfx.view.intellect.View;

/**
 * Интеллектуальное представление переключателя
 */
public class SwitchView extends View<Switch> {
	private final Label label;

	/**
	 * Конструктор
	 *
	 * @param parent композит
	 */
	public SwitchView(Composite parent) {
		label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER |
				GridData.HORIZONTAL_ALIGN_CENTER));
		final Button onButton = new Button(parent, SWT.PUSH);
		onButton.setText("Включить");
		onButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				edit(new Switch(true));
			}
		});
		final Button offButton = new Button(parent, SWT.PUSH);
		offButton.setText("Включить");
		offButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				edit(new Switch(false));
			}
		});
		final Button undoButton = new Button(parent, SWT.PUSH);
		undoButton.setText("Отменить");
		undoButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				undo();
			}
		});
	}

	public void modelChanged(Model<Switch> model) {
		label.setText(model.toString());
	}
}
