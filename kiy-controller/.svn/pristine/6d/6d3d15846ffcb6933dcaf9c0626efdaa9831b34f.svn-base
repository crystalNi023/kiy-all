package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

import com.kiy.common.Device;
import com.kiy.common.Feature;
import com.kiy.common.SceneDevice;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

public class FSelectDeviceFeature extends Dialog {

	private Shell shell;
	private Device device;
	private CCombo combo;
	private CLabel labelFeature;
	private SceneDevice sceneDevice;
	private Scale scale;
	
	private boolean closing;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public FSelectDeviceFeature(Shell arg0) {
		super(arg0);
	}

	/**
	 * Open the window.
	 */
	public SceneDevice open(Device device) {
		this.device = device;
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return sceneDevice;
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.SYSTEM_MODAL | SWT.BORDER | SWT.CLOSE);
		shell.setBackground(Resource.getColor(SWT.COLOR_WHITE));
		shell.setText(Lang.getString("FScene.switch.text")+device.getName());
		shell.setSize(324, 183);
		shell.addShellListener(shellAdapter);
		
		F.center(getParent(), shell);

		CLabel label = new CLabel(shell, SWT.NONE);
		label.setBackground(Resource.getColor(SWT.COLOR_WHITE));
		label.setBounds(16, 16, 67, 23);
		label.setText(Lang.getString("FScene.tblclmnFea.text"));

		combo = new CCombo(shell, SWT.BORDER);
		combo.setBounds(116, 16, 186, 21);

		CLabel label_1 = new CLabel(shell, SWT.NONE);
		label_1.setBackground(Resource.getColor(SWT.COLOR_WHITE));
		label_1.setText(Lang.getString("FScene.action.text"));
		label_1.setBounds(16, 62, 67, 37);

		scale = new Scale(shell, SWT.NONE);
		scale.setBackground(Resource.getColor(SWT.COLOR_WHITE));
		scale.setBounds(116, 57, 144, 42);

		labelFeature = new CLabel(shell, SWT.NONE);
		labelFeature.setBackground(Resource.getColor(SWT.COLOR_WHITE));
		labelFeature.setBounds(266, 56, 36, 43);

		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.setBounds(180, 118, 61, 27);
		btnNewButton_1.setText(Lang.getString("Ensure.text"));
		btnNewButton_1.addSelectionListener(ensureButtonAdapter);

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(247, 118, 61, 27);
		btnNewButton.setText(Lang.getString("Cancel.text"));
		btnNewButton.addSelectionListener(btnCancelSelectionAdapter);

		Feature[] features = device.getFeatures();
		for (Feature feature : features) {
			if (feature.PRIMARY != true || feature.READ_ONLY == true) {
				continue;
			}
			combo.add(feature.getName());
			combo.setData(feature.getName(), feature.INDEX);
		}
		combo.select(0);
		combo.addSelectionListener(comboSelectionAdapter);
		scale.addSelectionListener(scaleSelectionAdapter);
		
		selectCombo();
	}
	
	
	private void accept(){
		sceneDevice = new SceneDevice();
		sceneDevice.setDeviceId(device.getId());
		sceneDevice.setFeatureIndex(getSelectFeature().INDEX);
		sceneDevice.setFeatureValue(scale.getSelection());
	}	
	

	/**
	 * 通过Combo选中的值更改Scale基本属性
	 */
	private void selectCombo(){
		Integer indexOfFeature = (Integer)combo.getData(combo.getText());
		Feature feature = device.getFeature(indexOfFeature);
		scale.setMinimum(feature.MINIMUM);
		scale.setMaximum(feature.MAXIMUM);
		scale.setPageIncrement(feature.STEP);
		feature.setValue(0);
		labelFeature.setText(feature.getText());
	}
	
	/**
	 * 获取选中的feature
	 */
	private Feature getSelectFeature(){
		Integer indexOfFeature = (Integer)combo.getData(combo.getText());
		return device.getFeature(indexOfFeature);
	}
	
	private SelectionAdapter scaleSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			int selection = scale.getSelection();
			Feature selectFeature = getSelectFeature();
			selectFeature.setValue(selection);
			labelFeature.setText(selectFeature.getText());
		}
	};
	
	private SelectionAdapter comboSelectionAdapter = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			selectCombo();
		}
	};
	
	
	private ShellAdapter shellAdapter = new ShellAdapter() {

		@Override
		public void shellClosed(ShellEvent arg0) {
			closing = true;
			close();
		}

	};
	
	
	private SelectionAdapter ensureButtonAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			accept();
			close();
		}

	};
	
	private SelectionAdapter btnCancelSelectionAdapter = new SelectionAdapter() {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			cancel();
		}
	};
	
	private void close() {
		if (closing) {

		} else {
			shell.close();
		}
	}

	private void cancel() {
		sceneDevice = null;
		close();
	}
}
