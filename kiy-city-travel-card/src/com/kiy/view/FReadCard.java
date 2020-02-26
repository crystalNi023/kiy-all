
package com.kiy.view;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.kiy.common.Card;
import com.kiy.common.DataCache;
import com.kiy.common.Types.Kind;
import com.kiy.common.Util;
import com.kiy.data.Data;
import com.kiy.data.DataOperation;
import com.kiy.driver.CardSerialPort;
import com.kiy.driver.ConfigDriver;
import com.kiy.driver.Executor;
import com.kiy.driver.Log;
import com.kiy.driver.MCard;

public class FReadCard extends Dialog {
	
	private Shell shell;
	private Text number;
	private CCombo port;
	private CLabel info;
	private CLabel infoBig;
	private Text type;
	private Label data;
	
	
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			com.kiy.common.Config.load();
			Executor.initialize();
			Data.initialize();
			Shell shell = new Shell();
			FReadCard window = new FReadCard(shell);
			window.open();
		} catch (Exception e) {
			System.out.println("error");
		}
	}
	
	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public FReadCard(Shell arg0) {
		super(arg0);
		shell = arg0;
	}
	
	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();

		shell.setToolTipText("都市一日游读卡工具");
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
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.SYSTEM_MODAL);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setSize(460, 275);
		shell.setText("都市一日游制卡工具");
		
		CLabel lblNewLabel = new CLabel(shell, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(16, 12, 67, 23);
		lblNewLabel.setText("\u7AEF\u53E3:");
		
		port = new CCombo(shell, SWT.BORDER);
		port.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		port.setEditable(false);
		port.setBounds(85, 12, 129, 21);
		List<String> allPort = CardSerialPort.getAllPort();
		for (String string : allPort) {
			port.add(string);
		}
		port.select(0);
		
		CLabel lblNewLabel_1 = new CLabel(shell, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_1.setBounds(16, 41, 61, 23);
		lblNewLabel_1.setText("\u5361\u53F7:");
		
		number = new Text(shell, SWT.BORDER);
		number.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		number.setBounds(85, 41, 129, 23);
		number.setTextLimit(8);
		number.setEnabled(false);
		
		CLabel lblNewLabel_2 = new CLabel(shell, SWT.NONE);
		lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_2.setBounds(220, 41, 61, 23);
		lblNewLabel_2.setText("\u7C7B\u578B:");
		
		Group groupCount = new Group(shell, SWT.NONE);
		groupCount.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		groupCount.setBounds(16, 66, 412, 125);
		

		type = new Text(shell, SWT.BORDER);
		type.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		type.setTextLimit(8);
		type.setEnabled(false);
		type.setBounds(287, 41, 141, 23);
		
		infoBig = new CLabel(groupCount, SWT.NONE);
		infoBig.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		infoBig.setFont(SWTResourceManager.getFont("微软雅黑", 30, SWT.NORMAL));
		infoBig.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		infoBig.setAlignment(SWT.CENTER);
		infoBig.setBounds(20, 49, 368, 66);
		infoBig.setText("");
		
		Label lblNewLabel_3 = new Label(groupCount, SWT.NONE);
		lblNewLabel_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_3.setBounds(20, 21, 119, 17);
		lblNewLabel_3.setText("第一次刷卡时间:");
		
		data = new Label(groupCount, SWT.NONE);
		data.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		data.setBounds(145, 21, 257, 17);
		
		info = new CLabel(shell, SWT.NONE);
		info.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		info.setBounds(16, 204, 236, 26);
		
		Button readCard = new Button(shell, SWT.NONE);
		readCard.setBounds(348, 203, 80, 27);
		readCard.setText("读卡");
		readCard.addSelectionListener(new ReadSelectionAdapter());
		
		
	}

	private class ReadSelectionAdapter extends SelectionAdapter{

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			//clear();
			
			ConfigDriver.ID_CARD_SERIAL_PORT = port.getText();
			CardSerialPort cardSerialPort = new CardSerialPort();
			cardSerialPort.start();
			
			MCard mCard = new MCard();
			Card card = new Card();
			cardSerialPort.readCard(mCard);
			
			if(mCard.getCardNumber()!=null){
				number.setText(mCard.getCardNumber());
				card.setNumber(Long.valueOf(mCard.getCardNumber()));
				switch (mCard.getType()) {
					case 1:
						int number2 = mCard.getNumber();
						if(number2==1){
							type.setText("一次卡");
							card.setKind(Kind.ONCE);
						}else{
							info.setText("次数卡，次数为"+number2);
						}
						
						break;
					case 2:
						int hour = mCard.getHour();
						data.setText(Util.dateFormat(mCard.getDate()));
						if(hour==24){
							card.setKind(Kind.ONE_DAY);
							type.setText("一日卡");
						}else if(hour==48){
							type.setText("两日卡");
							card.setKind(Kind.TWO_DAY);
						}else{
							info.setText("计时卡，次数为"+hour);
						}
						break;
					default:
						break;
				}
			}
			
			card.setUserId(DataCache.getInstance().getUser().getId());
			info.setText(mCard.getRemark()==null?"":mCard.getRemark());
			cardSerialPort.stop();
			
			if(card.getKind()!=null){
				boolean createWriteCard = DataOperation.createReadCard(card);
				if (createWriteCard) {
					Log.debug("创建读卡记录成功");
				}else{
					Log.debug("创建读卡记录失败");
				}
			}
			
			
		}
		
	}
	
}
