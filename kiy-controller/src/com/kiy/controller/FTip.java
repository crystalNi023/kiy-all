package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

/**
 * 危险操作提示框
 * @author HLX
 *
 */
public class FTip {
	private Shell shell;
	private String userName;
	private String deviceName;
	private String content;
	
	public static final String CONTROL = "Control";
//	public static final String THRESHOLD = "Threshold";

	public FTip(String user, String device,String content) {
		deviceName = device;
		userName = user;
		this.content = content;
	}

	public FTip(String device) {
		deviceName = device;
	}

	public FTip() {
	}

	public void open() {
		createContent();
		shell.open();
		shell.layout();

		Display display = Display.getDefault();

		final Runnable timer = new Runnable() {
			public void run() {
				synchronized (this) {
					try {
						shell.dispose();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		display.timerExec(2000, timer);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void createContent() {
		shell = new Shell(SWT.CLOSE);
		shell.setSize(300, 123);
		shell.setBackground(new Color(Display.getCurrent(), 255, 255, 255));
		shell.setText(Lang.getString("FTip.Title.text"));

		Rectangle area = Display.getDefault().getClientArea();
		int width = area.width;
		int height = area.height;
		shell.setLocation(width - 300, height - 123);
		shell.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/controller.png"));

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setBounds(16, 16, 268, 60);
		if(content.equals(CONTROL)) {
			lblNewLabel_1.setText((userName == null ? Lang.getString("FTip.UnKonwUser.text") : userName) + Lang.getString("FTip.IsWork.text") + (deviceName == null ? Lang.getString("FTip.UnKonwDevice.text") : deviceName) + Lang.getString("FTip.Status.text"));
		}else {
			lblNewLabel_1.setText(deviceName+content);
		}
		
		lblNewLabel_1.setBackground(new Color(Display.getCurrent(), 255, 255, 255));

	}
}
