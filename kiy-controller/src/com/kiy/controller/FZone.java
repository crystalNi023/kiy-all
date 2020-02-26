/**
 * 2017年1月19日
 */
package com.kiy.controller;

import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.Servo;
import com.kiy.common.Tool;
import com.kiy.common.Zone;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public class FZone extends Dialog {
	public boolean isUpdate = false;
	private Zone tag;
	private Servo servo;
	private Zone[] parents;

	private Shell shell;
	private Button btnCancel;
	private Button btnAccept;
	private Button btnZoneParent;
	private Text textName;
	private Text textRemark;
	private boolean closing;
	private CCombo comboParent;
	private Label label;
	private CLabel lblNewLabel_1;
	private Zone selectZone;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public FZone(Shell parent) {
		super(parent, 0);
		closing = false;
	}

	/**
	 * Open the dialog.
	 * 
	 * @param selectionZone
	 * 
	 * @return the result
	 */
	public Zone open(Servo s, Zone z, Zone sZ) {
		if (s == null)
			throw new NullPointerException();
		selectZone = sZ;
		tag = z;
		servo = s;
		createContents();
		F.center(getParent(), shell);

		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return tag;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE);
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				closing = true;
				close();
			}
		});
		shell.setSize(460, 254);
		shell.setText(Lang.getString("FZone.zone"));

		lblNewLabel_1 = new CLabel(shell, SWT.NONE);
		lblNewLabel_1.setBounds(16, 16, 382, 32);
		lblNewLabel_1.setImage(Resource.getImage(FZone.class, "/com/kiy/resources/verify.png"));
		lblNewLabel_1.setVisible(false);

		Label labelImage = new Label(shell, SWT.NONE);
		labelImage.setBounds(406, 16, 32, 32);
		labelImage.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/area_32.png"));

		label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(0, 70, 454, 1);

		CLabel lblName = new CLabel(shell, SWT.NONE);
		lblName.setBounds(16, 86, 61, 23);
		lblName.setText(Lang.getString("FZone.name")); //$NON-NLS-1$

		textName = new Text(shell, SWT.BORDER);
		textName.setBounds(85, 86, 353, 23);
		textName.setTextLimit(32);
		textName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				verify();
			}
		});

		CLabel lblParent = new CLabel(shell, SWT.NONE);
		lblParent.setBounds(16, 115, 52, 23);
		lblParent.setText(Lang.getString("FZone.parent"));

		comboParent = new CCombo(shell, SWT.BORDER | SWT.READ_ONLY);
		comboParent.setItems(new String[] {});
		comboParent.setBounds(85, 115, 324, 23);
		comboParent.add(Lang.getString("FZone.rootZone"));
		comboParent.setText(Lang.getString("FZone.rootZone"));

		btnZoneParent = new Button(shell, SWT.NONE);
		btnZoneParent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FZoneParent dialog = new FZoneParent(shell);
				dialog.open(servo,tag);
				if(dialog.combValue!=null){
					comboParent.setText(dialog.combValue);
				}
			}
		});

		btnZoneParent.setBounds(415, 115, 23, 23);
		btnZoneParent.setText("...");

		CLabel lblRemark = new CLabel(shell, SWT.NONE);
		lblRemark.setBounds(16, 144, 61, 23);
		lblRemark.setText(Lang.getString("FZone.remarks"));

		textRemark = new Text(shell, SWT.BORDER);
		textRemark.setBounds(85, 144, 353, 23);
		textRemark.setTextLimit(128);
		textRemark.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				verify();
			}
		});

		Label lblNewLabel = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel.setBounds(0, 183, 454, 1);

		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isUpdate = false;
				cancel();
			}
		});
		btnCancel.setBounds(361, 190, 80, 27);
		btnCancel.setText(Lang.getString("Cancel.text"));

		btnAccept = new Button(shell, SWT.NONE);
		btnAccept.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isUpdate = true;
				accept();
			}
		});
		btnAccept.setBounds(275, 190, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));
		btnAccept.setEnabled(false);

		Set<Zone> zones = servo.getZones();
		parents = new Zone[zones.size() + 1];
		int index = 1;
		for (Zone zone : zones) {
			
			if(tag!=null){
				if(zone ==tag){
					continue;
				}
			}
			
			comboParent.add(zone.getName(), index);
			parents[index] = zone;
			index++;
		}

		make();
	}


	private void make() {
		if (selectZone != null) {
			comboParent.setText(selectZone.getName());
		}
		if (tag == null)
			return;

		if (tag.getName() != null)
			textName.setText(tag.getName());
		if (tag.getRemark() != null)
			textRemark.setText(tag.getRemark());

		int index = 0;
		if (tag.getParent() == null) {
			for (int j = 0; j < parents.length; j++) {
				if (parents[j] == null) {
					index = j;
				}
			}
		} else {
			for (int i = 1; i < parents.length; i++) {

				if (parents[i] != null && parents[i].getId().equals(tag.getParent().getId())) {
					index = i;
				}
			}
		}

		comboParent.select(index);

	}

	private void cancel() {
		tag = null;
		close();
	}

	private void accept() {

		if (tag == null) {
			tag = new Zone();
			tag.newId();
		}

		tag.setName(textName.getText());
		tag.setRemark(textRemark.getText());

		int index = comboParent.getSelectionIndex();
		tag.setParentId(index > 0 ? parents[index].getId() : null);

		close();
	}

	private void close() {
		if (closing) {

		} else {
			shell.close();
		}
	}

	/**
	 * 验证输入
	 */
	private void verify() {
		btnAccept.setEnabled(false);
		if (lblNewLabel_1 == null) {
			return;
		}

		lblNewLabel_1.setVisible(true);

		if (Tool.isEmpty(textName.getText())) {
			lblNewLabel_1.setText(Lang.getString("FZone.zoneName.notEmpty"));
			return;
		}

		lblNewLabel_1.setText(Tool.EMPTY);
		lblNewLabel_1.setVisible(false);
		btnAccept.setEnabled(true);
	}
}