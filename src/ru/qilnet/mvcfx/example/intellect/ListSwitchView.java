package ru.qilnet.mvcfx.example.intellect;

import ru.qilnet.mvcfx.example.ListContentProvider;
import ru.qilnet.mvcfx.model.ListModel;
import ru.qilnet.mvcfx.model.Model;
import ru.qilnet.mvcfx.view.intellect.ListView;

import java.util.Collection;

/**
 * Интеллектуальное представление списка переключателей
 */
public class ListSwitchView extends ListView<Switch> {
	private final ListViewer viewer;

	/**
	 * Конструктор
	 *
	 * @param parent родительский визуальный элемент
	 */
	public ListSwitchView(Composite parent) {
		final List list = new List(parent, SWT.BORDER | SWT.H_SCROLL |
				SWT.V_SCROLL);
		list.setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer = new ListViewer(list);
		viewer.setContentProvider(new ListContentProvider<Switch>());
		final Button addButton = new Button(parent, SWT.PUSH);
		addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addButton.setText("Добавить");
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				add(new Model<Switch>(new Switch()));
			}
		});
		final Button deleteButton = new Button(parent, SWT.PUSH);
		deleteButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		deleteButton.setText("Удалить");
		deleteButton.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent e) {
				final IStructuredSelection selection = (IStructuredSelection) viewer
						.getSelection();
				if (!selection.isEmpty())
					delete((Model<Switch>) selection.getFirstElement());
			}
		});
		final Button undoButton = new Button(parent, SWT.PUSH);
		undoButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		undoButton.setText("Отменить");
		undoButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				undo();
			}
		});
		final Button onButton = new Button(parent, SWT.PUSH);
		onButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		onButton.setText("Включить");
		onButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				turn(true);
			}
		});
		final Button offButton = new Button(parent, SWT.PUSH);
		offButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		offButton.setText("Выключить");
		offButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				turn(false);
			}
		});
	}

	@Override
	public void setModel(ListModel<Switch> model) {
		super.setModel(model);
		viewer.setInput(model);
	}

	/**
	 * Переключить
	 *
	 * @param state состоние
	 */
	@SuppressWarnings("unchecked")
	private void turn(boolean state) {
		final IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		if (!selection.isEmpty())
			edit((Model<Switch>) selection.getFirstElement(), new Switch(
					state));
	}

	@Override
	public void modelChanged(Model<Collection<Model<Switch>>> model) {
		viewer.refresh();
	}
}
