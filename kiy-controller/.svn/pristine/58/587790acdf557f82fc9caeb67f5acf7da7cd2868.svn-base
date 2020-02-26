/**
 * 2017年1月18日
 */
package com.kiy.controller;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;

import com.kiy.resources.Lang;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public class FLoading extends Dialog {

	protected Shell shell;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public FLoading(Shell parent) {
		super(parent, SWT.APPLICATION_MODAL);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public void open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		// return result;
	}

	public void close() {
		if (!shell.isDisposed())
			shell.close();
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.BORDER | SWT.TITLE);
		shell.setText("...");
		shell.setSize(460, 100);

		Rectangle parentBounds = shell.getParent().getBounds();
		Rectangle shellBounds = shell.getBounds();
		shell.setLocation(parentBounds.x + (parentBounds.width - shellBounds.width) / 2, parentBounds.y + (parentBounds.height - shellBounds.height) / 2);

		ProgressBar progress = new ProgressBar(shell, SWT.SMOOTH | SWT.INDETERMINATE);
		progress.setBounds(16, 41, 422, 14);

		Label lblPleaseWaiting = new Label(shell, SWT.NONE);
		lblPleaseWaiting.setBounds(16, 16, 422, 17);
		lblPleaseWaiting.setText(Lang.getString("FLoading.LabelPleaseWaiting.text"));

	}
}
