/**
 * 2017年1月19日
 */
package com.kiy.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.kiy.common.Device;
import com.kiy.common.Maintain;
import com.kiy.common.Tool;
import com.kiy.common.Types.Repair;
import com.kiy.resources.Lang;
import com.kiy.resources.Resource;

/**
 * @author (KeDaiQin TEL:18302367318)
 *
 */
public class FMaintain extends Dialog {

	public boolean isUpdate = false;
	private boolean closing;
	private Maintain maintain;
	private Shell shell;
	private Button btnAccept;
	private Text textSn;
	private CCombo comboRepair;
	private Text textLoad;
	private Text textPower;
	private Text textMutual;
	private Text textRadix;
	private Text textEB;
	private CLabel lblverify;
	private Device device;
	public FMaintain(Shell parent) {
		super(parent, 0);
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Maintain open(Device d,Maintain m) {
		
		maintain = m;
		device = d;
		
		if(maintain==null&&device==null){
			throw new NullPointerException();
		}
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return maintain;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE);
		shell.addShellListener(shellAdapter);
		shell.setSize(450, 360);
		shell.setText(Lang.getString("FMaintain.ShellTitle.text"));

		lblverify = new CLabel(shell, SWT.NONE);
		lblverify.setBounds(16, 16, 369, 32);
		lblverify.setVisible(false);
		lblverify.setImage(Resource.getImage(FZone.class, "/com/kiy/resources/verify.png"));

		Label labelImage = new Label(shell, SWT.NONE);
		labelImage.setBounds(393, 16, 32, 32);
		labelImage.setImage(Resource.getImage(FMain.class, "/com/kiy/resources/device_scheduling_32.png"));

		Label lblNewLabel_7 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel_7.setBounds(0, 59, 450, 1);

		CLabel lblNewLabel = new CLabel(shell, SWT.NONE);

		lblNewLabel.setBounds(40, 74, 93, 23);
		lblNewLabel.setText(Lang.getString("FMaintain.LabelMaintenanceType.text"));

		comboRepair = new CCombo(shell, SWT.READ_ONLY | SWT.BORDER);
		comboRepair.setBounds(139, 74, 228, 23);
		comboRepair.select(0);
		comboRepair.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				verify();
			}
			
		});

		CLabel lblNewLabel_1 = new CLabel(shell, SWT.NONE);
		lblNewLabel_1.setBounds(40, 103, 93, 23);
		lblNewLabel_1.setText(Lang.getString("FMaintain.LabelSerialNumber.text"));

		textSn = new Text(shell, SWT.BORDER);
		textSn.setEditable(false);
		textSn.setBounds(139, 103, 228, 23);

		CLabel lblNewLabel_2 = new CLabel(shell, SWT.NONE);
		lblNewLabel_2.setBounds(40, 132, 93, 23);
		lblNewLabel_2.setText(Lang.getString("FMaintain.LabelLoadPower.text"));

		textLoad = new Text(shell, SWT.BORDER);
		textLoad.setEditable(false);
		textLoad.setBounds(139, 132, 228, 23);

		CLabel lblNewLabel_3 = new CLabel(shell, SWT.NONE);
		lblNewLabel_3.setBounds(40, 161, 93, 23);
		lblNewLabel_3.setText(Lang.getString("FMaintain.LabelOwnPower.text"));

		textPower = new Text(shell, SWT.BORDER);
		textPower.setEditable(false);
		textPower.setBounds(139, 161, 228, 23);

		CLabel lblNewLabel_4 = new CLabel(shell, SWT.NONE);
		lblNewLabel_4.setBounds(40, 190, 93, 23);
		lblNewLabel_4.setText(Lang.getString("FMaintain.LabelMutualInductanceRatio.text"));

		textMutual = new Text(shell, SWT.BORDER);
		textMutual.setEditable(false);
		textMutual.setBounds(139, 190, 228, 23);

		CLabel lblNewLabel_5 = new CLabel(shell, SWT.NONE);
		lblNewLabel_5.setBounds(40, 219, 93, 23);
		lblNewLabel_5.setText(Lang.getString("FMaintain.LabelEndOfTabel.text"));

		textRadix = new Text(shell, SWT.BORDER);
		textRadix.setBounds(139, 219, 228, 23);
		textRadix.setTextLimit(9);
		// 限制只能输入数字
		textRadix.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				F.unIntNumberCheck(e, textRadix);
			}
		});

		textRadix.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {
				verify();
			}
		});

		CLabel lblNewLabel_6 = new CLabel(shell, SWT.NONE);
		lblNewLabel_6.setBounds(40, 248, 93, 23);
		lblNewLabel_6.setText(Lang.getString("FMaintain.LabelBalance.text"));
		// 表余量输入框
		textEB = new Text(shell, SWT.BORDER);
		textEB.setBounds(139, 248, 228, 23);
		textEB.setTextLimit(9);
		// 限制只能输入数字
		textEB.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				F.unIntNumberCheck(e, textEB);
			}
		});

		textEB.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {
				verify();
			}
		});

		Label lblNewLabel_8 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblNewLabel_8.setBounds(0, 287, 450, 1);

		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isUpdate = false;
				cancel();
			}
		});
		btnCancel.setBounds(350, 294, 80, 27);
		btnCancel.setText(Lang.getString("Cancel.text"));

		btnAccept = new Button(shell, SWT.NONE);
		btnAccept.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isUpdate = true;
				accept();
				close();
			}
		});
		btnAccept.setBounds(264, 294, 80, 27);
		btnAccept.setText(Lang.getString("Ensure.text"));
		btnAccept.setEnabled(false);

		F.center(getParent(), shell);
		Repair[] repairs = Repair.values();
		for (Repair repair : repairs) {
			comboRepair.add(Lang.getString("Maintain." + repair.name()));
			comboRepair.setData(Lang.getString("Maintain." + repair.name()),repair);
		}

		make();
	}

	private void make() {
		if (device != null) {
			textSn.setText(device.getSn());
			textLoad.setText(String.valueOf(device.getLoad()));
			textPower.setText(String.valueOf(device.getLoad()));
			textMutual.setText(String.valueOf(device.getMutual()));
		}
		
		if(maintain!=null){
			comboRepair.setText(Lang.getString("Maintain." + maintain.getRepair().name()));
			textSn.setText(maintain.getSn());
			textLoad.setText(String.valueOf(maintain.getLoad()));
			textPower.setText(String.valueOf(maintain.getLoad()));
			textMutual.setText(String.valueOf(maintain.getMutual()));
			textRadix.setText(String.valueOf(maintain.getRadix()));
			textEB.setText(String.valueOf(maintain.getEnergyBalance()));
		}
	}

	private void cancel() {
		maintain = null;
		close();
	}

	private void accept() {
		if (maintain == null) {
			maintain = new Maintain();
			maintain.newId();
		}
		String load = textLoad.getText().trim();
		if (load.isEmpty()) {
			load = "0";
		}
		String power = textPower.getText().trim();
		if (power.isEmpty()) {
			power = "0";
		}
		String mutual = textMutual.getText().trim();
		if (mutual.isEmpty()) {
			mutual = "0";
		}
		String radix = textRadix.getText().trim();
		if (radix.isEmpty()) {
			radix = "0";
		}
		String eB = textEB.getText().trim();
		if (eB.isEmpty()) {
			eB = "0";
		}
		if(device!=null){
			maintain.setDeviceId(device.getId());
		}
		
		String text = comboRepair.getText();
		Repair data = (Repair)comboRepair.getData(text);
		maintain.setRepair(data);
		maintain.setSn(textSn.getText());
		maintain.setLoad(Integer.valueOf(load));
		maintain.setPower(Integer.valueOf(power));
		maintain.setMutual(Float.valueOf(mutual));
		maintain.setRadix(Integer.valueOf(radix));
		maintain.setEnergyBalance(Integer.valueOf(eB));

	}

	/**
	 * 验证输入
	 */
	private void verify() {
		btnAccept.setEnabled(false);
		lblverify.setVisible(true);
		
		if(	comboRepair.getSelectionIndex()==-1){
			lblverify.setText(Lang.getString("FMaintain.textRepair.notEmpty"));
			return;
		}

		if (Tool.isEmpty(textRadix.getText())) {
			lblverify.setText(Lang.getString("FMaintain.textRadix.notEmpty"));
			return;
		}

		if (Tool.isEmpty(textEB.getText())) {
			lblverify.setText(Lang.getString("FMaintain.textEB.notEmpty"));
			return;
		}

		lblverify.setText(Tool.EMPTY);
		lblverify.setVisible(false);
		btnAccept.setEnabled(true);
	}

	private void close() {
		if (closing) {

		} else {
			shell.close();
		}
	}

	private ShellAdapter shellAdapter = new ShellAdapter() {

		@Override
		public void shellClosed(ShellEvent arg0) {
			closing = true;
			close();
		}

	};
}