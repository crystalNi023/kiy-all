/**
 * 2017年1月19日
 */
package com.kiy.controller;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

import com.kiy.resources.Lang;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public class FSystem {
	private Table table;
	private TableColumn columnName;
	private TableColumn columnValue;
	private ViewForm viewForm;
	private Button btnSave;
	private ToolBar toolBar;
	private ToolItem tltmNewItem;
	private Shell shell;

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		shell = new Shell();
		shell.setSize(640, 480);
		shell.setText(Lang.getString("FSystem.ShellTitle.text"));
		FillLayout fl_shell = new FillLayout(SWT.VERTICAL);
		fl_shell.marginWidth = 6;
		fl_shell.marginHeight = 6;
		shell.setLayout(fl_shell);

		viewForm = new ViewForm(shell, SWT.NONE);

		btnSave = new Button(viewForm, SWT.NONE);
		viewForm.setTopCenter(btnSave);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save();
			}
		});
		btnSave.setText(Lang.getString("FSystem.ButtonExport.text"));

		table = new Table(viewForm, SWT.FULL_SELECTION);
		viewForm.setContent(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		columnName = new TableColumn(table, SWT.NONE);
		columnName.setWidth(157);
		columnName.setText(Lang.getString("FSystem.ColumnName.text"));

		columnValue = new TableColumn(table, SWT.NONE);
		columnValue.setWidth(425);
		columnValue.setText(Lang.getString("FSystem.ColumnValue.text"));

		toolBar = new ToolBar(viewForm, SWT.FLAT | SWT.RIGHT);
		viewForm.setTopRight(toolBar);

		tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem.setText(Lang.getString("FSystem.ButtonExport.text"));

		Properties ps = System.getProperties();
		for (Object key : ps.keySet()) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, key.toString());
			item.setText(1, ps.getProperty((String) key));
		}

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void save() {
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		String filename = dialog.open();
		if (filename != null) {
			File file = new File(filename);
			try {
				System.getProperties().store(new FileOutputStream(file), null);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}