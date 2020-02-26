package com.kiy.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * 对话窗口基础
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public abstract class FDialog extends Shell {

	protected boolean is_accept;
	protected final Shell parent;
	protected CLabel labelMessage;
	protected CLabel labelImage;
	protected Button buttonAccept;
	protected Button buttonCancel;
	protected Composite body;
	protected CLabel labelRemark;

	public FDialog(Shell p) {
		super(p.getDisplay(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setParent(parent = p);

		is_accept = false;
		base();
		initialize();
		hooks();
	}

	private void base() {
		this.setLayout(new FormLayout());

		labelMessage = new CLabel(this, SWT.NONE);
		FormData fd_labelMessage = new FormData();
		fd_labelMessage.bottom = new FormAttachment(0, 48);
		fd_labelMessage.right = new FormAttachment(100, -64);
		fd_labelMessage.top = new FormAttachment(0, 16);
		fd_labelMessage.left = new FormAttachment(0, 16);
		labelMessage.setLayoutData(fd_labelMessage);
		labelMessage.setText("");
		labelMessage.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));

		labelImage = new CLabel(this, SWT.NONE);
		FormData fd_labelImage = new FormData();
		fd_labelImage.bottom = new FormAttachment(0, 48);
		fd_labelImage.top = new FormAttachment(0, 16);
		fd_labelImage.left = new FormAttachment(0, 390);
		labelImage.setLayoutData(fd_labelImage);
		labelImage.setText(F.EMPTY);

		Label h1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_h1 = new FormData();
		fd_h1.height = 1;
		fd_h1.right = new FormAttachment(100);
		fd_h1.top = new FormAttachment(0, 64);
		fd_h1.left = new FormAttachment(0);
		h1.setLayoutData(fd_h1);

		buttonCancel = new Button(this, SWT.NONE);
		buttonCancel.setText("取消");
		FormData fd_buttonCancel = new FormData();
		fd_buttonCancel.height = 28;
		fd_buttonCancel.width = 80;
		fd_buttonCancel.bottom = new FormAttachment(100, -16);
		fd_buttonCancel.right = new FormAttachment(100, -16);
		buttonCancel.setLayoutData(fd_buttonCancel);

		buttonAccept = new Button(this, SWT.NONE);
		buttonAccept.setText("确定");
		FormData fd_buttonAccept = new FormData();
		fd_buttonAccept.width = 80;
		fd_buttonAccept.top = new FormAttachment(buttonCancel, 0, SWT.TOP);
		fd_buttonAccept.right = new FormAttachment(buttonCancel, -8);
		buttonAccept.setLayoutData(fd_buttonAccept);

		Label h2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_h2 = new FormData();
		fd_h2.height = 1;
		fd_h2.left = new FormAttachment(0);
		fd_h2.right = new FormAttachment(100);
		fd_h2.bottom = new FormAttachment(100, -60);
		h2.setLayoutData(fd_h2);

		body = new Composite(this, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(100, -68);
		fd_composite.right = new FormAttachment(100, -8);
		fd_composite.left = new FormAttachment(0, 8);
		fd_composite.top = new FormAttachment(0, 72);
		body.setLayoutData(fd_composite);
		
		labelRemark = new CLabel(this, SWT.NONE);
		FormData fd_lblInfo = new FormData();
		fd_lblInfo.right = new FormAttachment(buttonAccept, -8);
		fd_lblInfo.bottom = new FormAttachment(100, -16);
		fd_lblInfo.left = new FormAttachment(0, 16);
		labelRemark.setLayoutData(fd_lblInfo);

		this.setDefaultButton(buttonAccept);
		this.setTabList(new Control[] { body, buttonAccept, buttonCancel });
	}

	private void hooks() {
		this.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (!is_accept)
					cancel();
			}
		});
		buttonCancel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
		buttonAccept.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (F.EMPTY.equals(labelMessage.getText())) {
					is_accept = true;
					accept();
					if (is_accept)
						close();
				} else
					FPrompt.showAlarm(getShell(), "请输入或填写正确的值后再点击确定。");
			}
		});
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				verify();
			}
		};
		for (Control c : body.getChildren()) {
			c.addListener(SWT.FocusOut, listener);
		}
	}

	protected void show() {
		F.parentCenter(parent, this);
		make();
		open();
		layout();
		Display display = getParent().getDisplay();
		while (!isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * 创建BODY内的控件(Composite body)
	 */
	protected abstract void initialize();

	/**
	 * 显示控件中的值
	 */
	protected abstract void make();

	/**
	 * 验证
	 */
	protected abstract void verify();

	/**
	 * 取消
	 */
	protected abstract void cancel();

	/**
	 * 确认
	 */
	protected abstract void accept();

	@Override
	protected void checkSubclass() {
	}

	public Shell getParent() {
		return parent;
	}
}