package com.kiy.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.kiy.common.Config;
import com.kiy.common.DataCache;
import com.kiy.common.Types.Power;
import com.kiy.common.User;
import com.kiy.data.Data;
import com.kiy.data.DataOperation;
import com.kiy.driver.Executor;

public class FMain {

	private Shell shell;
	private Text accountText;
	private Text passwordText;
	private Label remarkText;
	private Composite composite;
	private Composite managerComposite;
	private Button btnManager;
	private Button btnReadRecord;
	private Button btnReadCard;
	private Button btnWriteCard;
	private Button btnGetRecord;
	private Button btnWriteRecord;
	private User u = new User();
	private User user;
	private Label loginLabel;
	private Label out;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		try {
			Config.load();
			Executor.initialize();
			Data.initialize();
			FMain window = new FMain();
			window.open();
		} catch (Exception e) {
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 * @wbp.parser.entryPoint
	 */
	public FMain() {
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
		Display.setAppName("制卡工具");
		shell = new Shell(SWT.MIN | SWT.CLOSE);
		shell.setSize(490, 358);
		shell.setText("都市一日游卡片管理平台");

		shell.setToolTipText("都市一日游卡片管理平台");
		composite = new Composite(shell, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		composite.setBounds(0, 0, 484, 330);

		CLabel lblNewLabel = new CLabel(composite, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(88, 152, 67, 23);
		lblNewLabel.setText("账户:");

		accountText = new Text(composite, SWT.BORDER);
		accountText.setBounds(163, 152, 241, 23);

		CLabel label = new CLabel(composite, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setText("密码:");
		label.setBounds(88, 198, 67, 23);

		passwordText = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		passwordText.setBounds(163, 198, 241, 23);

		Label label_2 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setBounds(0, 240, 484, 2);

		remarkText = new Label(composite, SWT.NONE);
		remarkText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		remarkText.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		remarkText.setBounds(163, 127, 241, 17);

		managerComposite = new Composite(shell, SWT.NONE);
		managerComposite.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		managerComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		managerComposite.setBounds(0, 0, 481, 330);

		btnManager = new Button(managerComposite, SWT.NONE);
		btnManager.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.BOLD));
		btnManager.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/login.png"));
		btnManager.setBounds(28, 46, 120, 120);
		btnManager.addMouseListener(new BtnManagerMouseListener());
		btnManager.addSelectionListener(new UserManagerSelectionAdapter());

		btnWriteRecord = new Button(managerComposite, SWT.NONE);
		btnWriteRecord.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/card_record.png"));
		btnWriteRecord.setBounds(176, 46, 120, 120);
		btnWriteRecord.addMouseListener(new BtnWriteRecordMouseListener());
		btnWriteRecord.addSelectionListener(new WriteCardQuerySelectionAdapter());

		btnReadRecord = new Button(managerComposite, SWT.NONE);
		btnReadRecord.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/readcard_-record.png"));
		btnReadRecord.setBounds(328, 46, 120, 120);
		btnReadRecord.addMouseListener(new BtnReadRecordMouseListener());
		btnReadRecord.addSelectionListener(new ReadCardQuerySelectionAdapter());

		btnGetRecord = new Button(managerComposite, SWT.NONE);
		btnGetRecord.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/collarcard_record.png"));
		btnGetRecord.setBounds(28, 188, 120, 120);
		btnGetRecord.addMouseListener(new BtnGetRecordMouseListener());
		btnGetRecord.addSelectionListener(new GetCardQuerySelectionAdapter());

		btnWriteCard = new Button(managerComposite, SWT.NONE);
		btnWriteCard.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/fabrication.png"));
		btnWriteCard.setBounds(176, 188, 120, 120);
		btnWriteCard.addMouseListener(new BtnWriteCardMouseListener());
		btnWriteCard.addSelectionListener(new WriteCardSelectionAdapter());

		btnReadCard = new Button(managerComposite, SWT.NONE);
		btnReadCard.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/readcard.png"));
		btnReadCard.setBounds(328, 188, 120, 120);
		btnReadCard.addMouseListener(new BtnReadCardMouseListener());
		btnReadCard.addSelectionListener(new ReadCardSelectionAdapter());

		composite.setVisible(true);
		composite.setEnabled(true);

		Label label_1 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setBounds(0, 115, 484, 2);

		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/logo.png"));
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_1.setBounds(34, 16, 376, 60);

		loginLabel = new Label(composite, SWT.NONE);
		loginLabel.setForeground(SWTResourceManager.getColor(255, 255, 255));
		loginLabel.setFont(SWTResourceManager.getFont("微软雅黑", 13, SWT.BOLD));
		loginLabel.setText("登  录");
		loginLabel.addFocusListener(new LoginFocusAdater());
		loginLabel.addMouseListener(new LoginMouseAdapter());
		loginLabel.setAlignment(SWT.CENTER);
		loginLabel.setBackground(SWTResourceManager.getColor(51, 153, 255));
		loginLabel.setBounds(88, 263, 314, 34);

		managerComposite.setVisible(false);
		managerComposite.setEnabled(false);

		Label help = new Label(managerComposite, SWT.NONE);
		help.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/help.png"));
		help.setBackground(SWTResourceManager.getColor(255, 255, 255));
		help.setToolTipText("帮助");
		help.setBounds(379, 2, 37, 31);
		help.addMouseListener(new HelpMouseListener());

		out = new Label(managerComposite, SWT.NONE);
		out.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/out.png"));
		out.setBackground(SWTResourceManager.getColor(255, 255, 255));
		out.setToolTipText("退出");
		out.setBounds(420, 2, 45, 31);
		out.addMouseListener(new OutMouseListener());

	}

	private class WriteCardSelectionAdapter extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			FWriteCard fWriteCard = new FWriteCard(shell);
			fWriteCard.open();
		}

	}

	private class ReadCardSelectionAdapter extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			FReadCard fReadCard = new FReadCard(shell);
			fReadCard.open();
		}

	}

	private class UserManagerSelectionAdapter extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			FUserManager fUserManager = new FUserManager();
			fUserManager.open(user);
		}

	}

	private class WriteCardQuerySelectionAdapter extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			FWriteCardQuery fWriteCardQuery = new FWriteCardQuery();
			fWriteCardQuery.open(u);
		}
	}

	private class ReadCardQuerySelectionAdapter extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			FReadCardQuery fReadCardQuery = new FReadCardQuery();
			fReadCardQuery.open(u);
		}
	}

	private class GetCardQuerySelectionAdapter extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			FGetCardQuery fGetCardQuery = new FGetCardQuery();
			fGetCardQuery.open();
		}
	}

	private class BtnManagerMouseListener implements MouseListener {
		@Override
		public void mouseUp(MouseEvent arg0) {
			btnManager.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/login.png"));
		}

		@Override
		public void mouseDown(MouseEvent arg0) {
			btnManager.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/login_choose.png"));
		}

		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
		}

	}

	private class BtnWriteRecordMouseListener implements MouseListener {
		@Override
		public void mouseUp(MouseEvent arg0) {
			btnWriteRecord.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/card_record.png"));
		}

		@Override
		public void mouseDown(MouseEvent arg0) {
			btnWriteRecord.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/card_record_choose.png"));
		}

		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
		}

	}

	private class BtnReadRecordMouseListener implements MouseListener {
		@Override
		public void mouseUp(MouseEvent arg0) {
			btnReadRecord.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/readcard_-record.png"));
		}

		@Override
		public void mouseDown(MouseEvent arg0) {
			btnReadRecord.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/readcard_-record_choose.png"));
		}

		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
		}

	}

	private class BtnGetRecordMouseListener implements MouseListener {
		@Override
		public void mouseUp(MouseEvent arg0) {
			btnGetRecord.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/collarcard_record.png"));
		}

		@Override
		public void mouseDown(MouseEvent arg0) {
			btnGetRecord.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/collarcard_record_choose.png"));
		}

		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
		}
	}
	
	private class OutMouseListener implements MouseListener{

		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
		}

		@Override
		public void mouseDown(MouseEvent arg0) {
		}

		@Override
		public void mouseUp(MouseEvent arg0) {
			DataCache instance = DataCache.getInstance();
			instance.removeAll();
			accountText.setText("");
			passwordText.setText("");
			remarkText.setText("");
			composite.setVisible(true);
			composite.setEnabled(true);
			managerComposite.setVisible(false);
			managerComposite.setEnabled(false);
		}
		
	}

	private class BtnWriteCardMouseListener implements MouseListener {
		@Override
		public void mouseUp(MouseEvent arg0) {
			btnWriteCard.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/fabrication.png"));
		}

		@Override
		public void mouseDown(MouseEvent arg0) {
			btnWriteCard.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/fabrication_choose.png"));
		}

		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
		}

	}

	private class BtnReadCardMouseListener implements MouseListener {
		@Override
		public void mouseUp(MouseEvent arg0) {
			btnReadCard.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/readcard.png"));
		}

		@Override
		public void mouseDown(MouseEvent arg0) {
			btnReadCard.setImage(SWTResourceManager.getImage(FMain.class, "/com/kiy/resources/readcard_choose.png"));
		}

		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
		}

	}

	private class HelpMouseListener implements MouseListener {

		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
		}

		@Override
		public void mouseDown(MouseEvent arg0) {
		}

		@Override
		public void mouseUp(MouseEvent arg0) {
			FGuide fGuide = new FGuide();
			fGuide.open();
		}

	}

	private class LoginMouseAdapter extends MouseAdapter {

		@Override
		public void mouseDoubleClick(MouseEvent arg0) {
			user = DataOperation.login(accountText.getText(), F.MD5(passwordText.getText()));
			if (user != null) {
				DataCache instance = DataCache.getInstance();
				instance.setUser(user);

				composite.setVisible(false);
				composite.setEnabled(false);
				managerComposite.setVisible(false);
				managerComposite.setEnabled(false);

				Power power = user.getPower();
				switch (power) {
					case ADMINISTRATOR:
						btnManager.setEnabled(true);
						btnWriteRecord.setEnabled(true);
						btnReadRecord.setEnabled(true);
						btnGetRecord.setEnabled(true);
						btnWriteCard.setEnabled(true);
						btnReadCard.setEnabled(true);
						break;
					case WRITE_CARD:
						btnManager.setEnabled(false);
						btnWriteRecord.setEnabled(false);
						btnReadRecord.setEnabled(false);
						btnGetRecord.setEnabled(false);
						btnWriteCard.setEnabled(true);
						btnReadCard.setEnabled(false);
						break;
					case READ_CARD:
						btnManager.setEnabled(false);
						btnWriteRecord.setEnabled(false);
						btnReadRecord.setEnabled(false);
						btnGetRecord.setEnabled(false);
						btnWriteCard.setEnabled(false);
						btnReadCard.setEnabled(true);
						break;
					case READ_WRITE_CARD:
						btnManager.setEnabled(false);
						btnWriteRecord.setEnabled(false);
						btnReadRecord.setEnabled(false);
						btnGetRecord.setEnabled(false);
						btnWriteCard.setEnabled(true);
						btnReadCard.setEnabled(true);
						break;
					default:
						break;
				}

				/**
				 * 判断权限
				 */

			} else {
				/* 登录失败 */
				remarkText.setText("用户名或密码有误");
				remarkText.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			}
		}

		@Override
		public void mouseDown(MouseEvent arg0) {
			loginLabel.setBackground(SWTResourceManager.getColor(22, 122, 255));
			user = DataOperation.login(accountText.getText(), F.MD5(passwordText.getText()));
			if (user != null) {
				DataCache instance = DataCache.getInstance();
				instance.setUser(user);

				composite.setVisible(false);
				composite.setEnabled(false);
				managerComposite.setVisible(true);
				managerComposite.setEnabled(true);

				Power power = user.getPower();
				switch (power) {
					case ADMINISTRATOR:
						btnManager.setEnabled(true);
						btnWriteRecord.setEnabled(true);
						btnReadRecord.setEnabled(true);
						btnGetRecord.setEnabled(true);
						btnWriteCard.setEnabled(true);
						btnReadCard.setEnabled(true);
						break;
					case WRITE_CARD:
						btnManager.setEnabled(false);
						btnWriteRecord.setEnabled(false);
						btnReadRecord.setEnabled(false);
						btnGetRecord.setEnabled(false);
						btnWriteCard.setEnabled(true);
						btnReadCard.setEnabled(false);
						break;
					case READ_CARD:
						btnManager.setEnabled(false);
						btnWriteRecord.setEnabled(false);
						btnReadRecord.setEnabled(false);
						btnGetRecord.setEnabled(false);
						btnWriteCard.setEnabled(false);
						btnReadCard.setEnabled(true);
						break;
					case READ_WRITE_CARD:
						btnManager.setEnabled(false);
						btnWriteRecord.setEnabled(false);
						btnReadRecord.setEnabled(false);
						btnGetRecord.setEnabled(false);
						btnWriteCard.setEnabled(true);
						btnReadCard.setEnabled(true);
						break;
					default:
						break;
				}

				/**
				 * 判断权限
				 */

			} else {
				/* 登录失败 */
				remarkText.setText("用户名或密码有误");
				remarkText.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			}
		}

		@Override
		public void mouseUp(MouseEvent arg0) {
			loginLabel.setBackground(SWTResourceManager.getColor(51, 153, 255));
		}

	}

	private class LoginFocusAdater extends FocusAdapter {

		@Override
		public void focusGained(FocusEvent arg0) {
			loginLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			loginLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		}

	}
}
