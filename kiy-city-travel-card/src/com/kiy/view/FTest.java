
package com.kiy.view;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Spinner;

import com.kiy.common.Util;
import com.kiy.driver.MCard;
import com.kiy.driver.CardSerialPort;
import com.kiy.driver.Config;
import com.kiy.driver.Executor;



public class FTest{
	
	private Shell shell;
	private Text number;
	private Text time;
	private CCombo port;
	private CCombo type;
	private Spinner count;
	private Label date;
	private CLabel info;
	
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Executor.initialize();
			FTest window = new FTest();
			window.open();
		} catch (Exception e) {
		}
	}
	
	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public FTest() {
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public void open() {
		Display.setAppName("制卡工具");
		Display display = Display.getDefault();
		createContents();

		shell.setToolTipText("都市一日有制卡工具");
		
		shell.open();
		shell.layout();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell();
		shell.setSize(460, 275);
		shell.setText("都市一日有制卡工具");
		
		CLabel lblNewLabel = new CLabel(shell, SWT.NONE);
		lblNewLabel.setBounds(16, 12, 67, 23);
		lblNewLabel.setText("\u7AEF\u53E3:");
		
		port = new CCombo(shell, SWT.BORDER);
		port.setEditable(false);
		port.setBounds(85, 12, 108, 21);
		List<String> allPort = CardSerialPort.getAllPort();
		for (String string : allPort) {
			port.add(string);
		}
		port.select(0);
		
		CLabel lblNewLabel_1 = new CLabel(shell, SWT.NONE);
		lblNewLabel_1.setBounds(16, 41, 61, 23);
		lblNewLabel_1.setText("\u5361\u53F7:");
		
		number = new Text(shell, SWT.BORDER);
		number.setBounds(85, 41, 108, 23);
		number.setTextLimit(8);
		
		CLabel lblNewLabel_2 = new CLabel(shell, SWT.NONE);
		lblNewLabel_2.setBounds(220, 41, 61, 23);
		lblNewLabel_2.setText("\u7C7B\u578B:");
		
		type = new CCombo(shell, SWT.BORDER);
		type.setEditable(false);
		type.setBounds(289, 41, 139, 21);
		type.add("次数卡");
		type.add("计时卡");
		type.select(0);
		type.addSelectionListener(new TypeSelectionAdapter());
		
		Group groupCount = new Group(shell, SWT.NONE);
		groupCount.setBounds(16, 66, 412, 125);
		
		CLabel lblNewLabel_3 = new CLabel(groupCount, SWT.NONE);
		lblNewLabel_3.setBounds(16, 16, 47, 23);
		lblNewLabel_3.setText("\u6B21\u6570:");
		
		count = new Spinner(groupCount, SWT.BORDER);
		count.setMaximum(65535);
		count.setMinimum(0);
		count.setBounds(68, 16, 110, 23);
		
		CLabel label = new CLabel(groupCount, SWT.NONE);
		label.setText("\u6709\u6548\u65F6\u95F4:");
		label.setBounds(205, 16, 63, 23);
		
		time = new Text(groupCount, SWT.BORDER);
		time.setBounds(276, 16, 120, 23);
		
		Label lblNewLabel_4 = new Label(groupCount, SWT.NONE);
		lblNewLabel_4.setBounds(16, 57, 99, 17);
		lblNewLabel_4.setText("\u8BA1\u65F6\u5361\u5237\u5361\u65F6\u95F4:");
		
		date = new Label(groupCount, SWT.NONE);
		date.setBounds(123, 57, 273, 17);
		
		isCount();
		
		info = new CLabel(shell, SWT.NONE);
		info.setBounds(16, 204, 236, 26);
		
		Button read = new Button(shell, SWT.NONE);
		read.setBounds(260, 203, 80, 27);
		read.setText("\u8BFB\u5361");
		read.addSelectionListener(new ReadSelectionAdapter());
		
		Button writeCard = new Button(shell, SWT.NONE);
		writeCard.setBounds(348, 203, 80, 27);
		writeCard.setText("\u5236\u5361");
		writeCard.addSelectionListener(new WriteSelectionAdapter());
	}
	
	private void clear(){
		number.setText("");
		count.setSelection(0);
		time.setText("");
		info.setText("");
	}
	
	private void isCount(){
		time.setEnabled(false);
		count.setEnabled(true);
	}
	
	private void isTime(){
		time.setEnabled(true);
		count.setEnabled(false);
	}
	
	
	private class TypeSelectionAdapter extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			if(type.getText().equals("次数卡")){
				isCount();
			}else if(type.getText().equals("计时卡")){
				isTime();
			}
		}
		
	}
	
	private class ReadSelectionAdapter extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			clear();
			
			Config.ID_CARD_SERIAL_PORT = port.getText();
			CardSerialPort cardSerialPort = new CardSerialPort();
			cardSerialPort.start();
			MCard card = new MCard();
			cardSerialPort.readCard(card);
			if(card.getCardNumber()!=null){
				number.setText(card.getCardNumber());
				if(card.getType()==MCard.TIME_TYPE){
					isTime();
					time.setText(String.valueOf(card.getHour()));
					date.setText(Util.dateFormat(card.getDate()));
					type.select(1);
				}else{
					isCount();
					count.setSelection(card.getNumber());
					type.select(0);
				}
			}
			info.setText(card.getRemark()==null?"":card.getRemark());
			cardSerialPort.stop();
		}
		
	}
	
	private class WriteSelectionAdapter extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
			info.setText("");
			
			Config.ID_CARD_SERIAL_PORT = port.getText();
			CardSerialPort cardSerialPort = new CardSerialPort();
			cardSerialPort.start();
			MCard card = new MCard();
			
			card.setCardNumber(number.getText()==null?"":number.getText());
			if(type.getText().equals("次数卡")){
				card.setType(MCard.NUMBER_TYPE);
				card.setNumber(Integer.valueOf(count.getText()));
			}else{
				card.setType(MCard.TIME_TYPE);
				card.setHour(Integer.valueOf(time.getText()));
			}
			cardSerialPort.writeCard(card);
		
			info.setText(card.getRemark()==null?"":card.getRemark());
			cardSerialPort.stop();
		}
		
	}
	
	
}