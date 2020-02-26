package com.kiy.controller.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.kiy.common.Feature;

public class CustomLabelView {
	private int baseY;
	// Label Key One
	private final int labelX = 16;
	private final int labelWidth = 76;
	private final int labelHeight = 20;

	// Label Value One
	private final int labelValueX = 100;
	private final int labelValueWidth = 120;
	private final int labelValueHeight = 20;

	// Label Key Two
	private final int label2X = 228;
	private final int label2Width = 76;
	private final int label2Height = 20;

	// Label Value Two
	private final int labelValue2X = 312;
	private final int labelValue2Width = 120;
	private final int labelValue2Height = 20;

	// Label Key Three
	private final int label3X = 440;
	private final int label3Width = 76;
	private final int label3Height = 20;

	// Label Value Three
	private final int labelValue3X = 524;
	private final int labelValue3Width = 110;
	private final int labelValue3Height = 20;

	private Feature feature;
	private Composite composite;
//	private boolean isSecond;
	private int index;

	public CustomLabelView(Feature feature, Composite c, int y, int index) {
		this.baseY = y;
		this.feature = feature;
		this.composite = c;
//		this.isSecond = isSecond;
		this.index = index;

	}

	/**
	 * 鑷畾涔夋帶浠跺竷灞?
	 */
	public void layout() {
		if (feature == null) {
			return;
		}

		if (index==0) {
			CLabel labelKey1 = new CLabel(composite, SWT.NONE);
			labelKey1.setText(feature.getName());
			labelKey1.setBounds(labelX, baseY, labelWidth, labelHeight);

			Label labelValue1 = new Label(composite, SWT.NONE);
			labelValue1.setText(feature.getText());
			labelValue1.setBounds(labelValueX, baseY, labelValueWidth, labelValueHeight);
		} else if(index ==1){
			CLabel labelKey1 = new CLabel(composite, SWT.NONE);
			labelKey1.setText(feature.getName());
			labelKey1.setBounds(label2X, baseY, label2Width, label2Height);

			Label labelValue1 = new Label(composite, SWT.NONE);
			labelValue1.setText(feature.getText());
			labelValue1.setBounds(labelValue2X, baseY, labelValue2Width, labelValue2Height);
		}else if(index ==2){
			CLabel labelKey1 = new CLabel(composite, SWT.NONE);
			labelKey1.setText(feature.getName());
			labelKey1.setBounds(label3X, baseY, label3Width, label3Height);

			Label labelValue1 = new Label(composite, SWT.NONE);
			labelValue1.setText(feature.getText());
			labelValue1.setBounds(labelValue3X, baseY, labelValue3Width, labelValue3Height);
		}

	}
}
