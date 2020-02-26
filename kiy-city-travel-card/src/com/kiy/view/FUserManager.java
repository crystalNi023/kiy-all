package com.kiy.view;

import java.util.Comparator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.kiy.common.DataCache;
import com.kiy.common.User;
import com.kiy.common.Util;
import com.kiy.data.DataOperation;

public class FUserManager {
	private Shell shell;
	private DataCache dataCache;

	private FVTable vt;
	private User user;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void open(User u) {
		this.user = u;
		Display display = Display.getDefault();
		dataCache = DataCache.getInstance();

		createContent();

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void createContent() {
		shell = new Shell(SWT.MIN | SWT.CLOSE);
		shell.setSize(900, 500);
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2, Display.getCurrent().getClientArea().height / 2 - shell.getSize().y / 2);

		Table table = new Table(shell, SWT.VIRTUAL | SWT.MULTI | SWT.FULL_SELECTION);
		table.setBounds(0, 0, 878, 452);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(58);
		tblclmnNewColumn.setText("用户名");

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(56);
		tableColumn.setText("权限");

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(62);
		tableColumn_1.setText("真实姓名");

		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(97);
		tableColumn_2.setText("联系方式");

		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(60);
		tableColumn_3.setText("状态");
		
		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(60);
		tableColumn_4.setText("备注");

		TableColumn tableColumn_5 = new TableColumn(table, SWT.NONE);
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("开始号段");

		TableColumn tableColumn_6 = new TableColumn(table, SWT.NONE);
		tableColumn_6.setWidth(100);
		tableColumn_6.setText("结束号段");

		TableColumn tableColumn_7 = new TableColumn(table, SWT.NONE);
		tableColumn_7.setWidth(73);
		tableColumn_7.setText("制卡总数");

		TableColumn tableColumn_8 = new TableColumn(table, SWT.NONE);
		tableColumn_8.setWidth(109);
		tableColumn_8.setText("创建时间");

		TableColumn tableColumn_9 = new TableColumn(table, SWT.NONE);
		tableColumn_9.setWidth(138);
		tableColumn_9.setText("更新时间");

		vt = new FVTable(table);

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.CASCADE);
		mntmNewItem_1.setText("操作");
		// 操作
		Menu menuActions = new Menu(mntmNewItem_1);
		mntmNewItem_1.setMenu(menuActions);

		MenuItem mntmNewItem = new MenuItem(menuActions, SWT.NONE);
		mntmNewItem.setText("创建用户");
		mntmNewItem.addSelectionListener(new CreaateUser());

		MenuItem menuItem = new MenuItem(menuActions, SWT.NONE);
		menuItem.setText("修改用户");
		menuItem.addSelectionListener(new UpdateUser());

		MenuItem menuItem_1 = new MenuItem(menuActions, SWT.NONE);
		menuItem_1.setText("禁用/正常");
		menuItem_1.addSelectionListener(new UpdateUserEnable());

		MenuItem menuItem_2 = new MenuItem(menuActions, SWT.NONE);
		menuItem_2.setText("修改密码");
		menuItem_2.addSelectionListener(new UpdateUserPassword());

		MenuItem menuItem_3 = new MenuItem(menuActions, SWT.NONE);
		menuItem_3.setText("查看制卡记录");

		MenuItem menuItem_4 = new MenuItem(menuActions, SWT.NONE);
		menuItem_4.setText("查看读卡记录");

		MenuItem menuItem_5 = new MenuItem(menuActions, SWT.NONE);
		menuItem_5.setText("领卡");
		menuItem_5.addSelectionListener(new CreateGetCard());

		Menu menu_1 = new Menu(table);

		table.setMenu(menu_1);

		MenuItem mntmNewItem1 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem1.setText("创建用户");
		mntmNewItem1.addSelectionListener(new CreaateUser());

		MenuItem menuItem1 = new MenuItem(menu_1, SWT.NONE);
		menuItem1.setText("修改用户");
		menuItem1.addSelectionListener(new UpdateUser());

		MenuItem menuItem_11 = new MenuItem(menu_1, SWT.NONE);
		menuItem_11.setText("禁用/正常");
		menuItem_11.addSelectionListener(new UpdateUserEnable());

		MenuItem menuItem_21 = new MenuItem(menu_1, SWT.NONE);
		menuItem_21.setText("修改密码");
		menuItem_21.addSelectionListener(new UpdateUserPassword());

		MenuItem menuItem_31 = new MenuItem(menu_1, SWT.NONE);
		menuItem_31.setText("查看制卡记录");
		menuItem_31.addSelectionListener(new WriteCardQuery());

		MenuItem menuItem_41 = new MenuItem(menu_1, SWT.NONE);
		menuItem_41.setText("查看读卡记录");
		menuItem_41.addSelectionListener(new ReadCardQuery());

		MenuItem menuItem_51 = new MenuItem(menu_1, SWT.NONE);
		menuItem_51.setText("领卡");
		menuItem_51.addSelectionListener(new CreateGetCard());

		List<User> selectUser = DataOperation.selectUser();
		for (User user : selectUser) {
			dataCache.addUser(user);
		}
		vt.beginUpdate();
		vt.add(dataCache.getUsers());
		vt.endUpdate();
	}

	private void refresh() {
		List<User> selectUser = DataOperation.selectUser();
		dataCache.removeAll();
		vt.clear();
		for (User user : selectUser) {
			dataCache.addUser(user);
		}
		vt.beginUpdate();
		vt.add(dataCache.getUsers());
		vt.endUpdate();
	}

	private class CreaateUser extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			FCreateUser fCreateUser = new FCreateUser(shell);
			User open = fCreateUser.open(null);
			if (open != null) {
				refresh();
				vt.refresh();
			}
		}
	}

	private class UpdateUser extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			User selection = vt.getSelection();
			if (selection == null) {
				FPrompt.showInformation(shell, "请选择您要修改的用户。");
			} else {
				FUpdateUser fUpdateUser = new FUpdateUser(shell);
				User open = fUpdateUser.open(selection);
				if (open != null) {
					refresh();
					vt.refresh();
				}
			}

		}
	}

	private class UpdateUserEnable extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			User selection = vt.getSelection();
			if (selection == null) {
				FPrompt.showInformation(shell, "请选择您要禁用/正常的用户。");
			} else {
				boolean setEnableForUser = DataOperation.setEnableForUser(selection);
				if (setEnableForUser) {
					FPrompt.showSuccess(shell, "更新用户成功");
					refresh();
					vt.refresh();
				} else {
					FPrompt.showSuccess(shell, "更新用户失败");
				}
			}
		}
	}

	private class UpdateUserPassword extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			User selection = vt.getSelection();
			if (selection == null) {
				FPrompt.showInformation(shell, "请选择您要修改密码的用户。");
			} else {
				FModifyPassword fModifyPassword = new FModifyPassword(shell);
				User open = fModifyPassword.open(selection);
				if (open != null) {
					refresh();
					vt.refresh();
				}
			}
		}
	}

	private class WriteCardQuery extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			User selection = vt.getSelection();
			if (selection == null) {
				FPrompt.showInformation(shell, "请选择您要查看制卡记录的用户。");
			} else {
				FWriteCardQuery fWriteCardQuery = new FWriteCardQuery();
				User open = fWriteCardQuery.open(selection);
				if (open != null) {
					refresh();
					vt.refresh();
				}
			}
		}
	}

	private class ReadCardQuery extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			User selection = vt.getSelection();
			if (selection == null) {
				FPrompt.showInformation(shell, "请选择您要查看读卡记录的用户。");
			} else {
				FReadCardQuery fReadCardQuery = new FReadCardQuery();
				User open = fReadCardQuery.open(selection);
				if (open != null) {
					refresh();
					vt.refresh();
				}
			}
		}
	}

	private class CreateGetCard extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			User selection = vt.getSelection();
			if (selection == null) {
				FPrompt.showInformation(shell, "请选择领卡的用户。");
			} else {
				FCreateGetCard fCreateGetCard = new FCreateGetCard(shell);
				User open = fCreateGetCard.open(user, selection);
				if (open != null) {
					refresh();
					vt.refresh();
				}
			}
		}
	}

	private class FVTable extends VTable<User> {

		public FVTable(Table t) {
			super(t);
		}

		@Override
		public void row(TableItem tableItem, User user) {
			tableItem.setText(0, user.getUsername());
			tableItem.setText(1, user.getPower().name());
			tableItem.setText(2, user.getRealname());
			tableItem.setText(3, Util.getString(user.getPhone()));
			tableItem.setText(4, user.isEnable() ? "禁用" : "正常");
			tableItem.setText(5, String.valueOf(Util.getString(user.getRemark())));
			tableItem.setText(6, String.valueOf(Util.getString(user.getBeginNumber())));
			tableItem.setText(7, String.valueOf(Util.getString(user.getEndNumber())));
			tableItem.setText(8, String.valueOf(Util.getString(user.getCardsNum())));
			tableItem.setText(9, Util.dateFormat(user.getCreated()));
			tableItem.setText(10, Util.dateFormat(user.getUpdated()));
		}

		@Override
		public void column(TableColumn column, int direction) {
			switch (vt.getColumnIndex(column)) {
				case 0:
					vt.sort(new Comparator<User>() {
						@Override
						public int compare(User a, User b) {
							if (SWT.UP == direction)
								return String.CASE_INSENSITIVE_ORDER.compare(a.getUsername() == null ? "" : a.getUsername(), b.getUsername() == null ? "" : b.getUsername());
							else
								return String.CASE_INSENSITIVE_ORDER.compare(b.getUsername() == null ? "" : b.getUsername(), a.getUsername() == null ? "" : a.getUsername());
						}
					});
					break;
				case 1:
					vt.sort(new Comparator<User>() {
						@Override
						public int compare(User a, User b) {
							if (SWT.UP == direction)
								return Integer.compare(a.getPower().getValue(), b.getPower().getValue());
							else
								return Integer.compare(b.getPower().getValue(), a.getPower().getValue());
						}
					});
					break;
				case 2:
					vt.sort(new Comparator<User>() {
						@Override
						public int compare(User a, User b) {
							if (SWT.UP == direction)
								return String.CASE_INSENSITIVE_ORDER.compare(a.getRealname() == null ? "" : a.getRealname(), b.getRealname() == null ? "" : b.getRealname());
							else
								return String.CASE_INSENSITIVE_ORDER.compare(b.getRealname() == null ? "" : b.getRealname(), a.getRealname() == null ? "" : a.getRealname());
						}
					});
					break;
				case 3:
					vt.sort(new Comparator<User>() {
						@Override
						public int compare(User a, User b) {
							if (SWT.UP == direction)
								return String.CASE_INSENSITIVE_ORDER.compare(a.getPhone() == null ? "" : a.getPhone(), b.getPhone() == null ? "" : b.getPhone());
							else
								return String.CASE_INSENSITIVE_ORDER.compare(b.getPhone() == null ? "" : b.getPhone(), a.getPhone() == null ? "" : a.getPhone());
						}
					});
					break;
				case 4:
					vt.sort(new Comparator<User>() {
						@Override
						public int compare(User a, User b) {
							if (SWT.UP == direction)
								return String.CASE_INSENSITIVE_ORDER.compare(a.isEnable() ? "禁用" : "正常", b.isEnable() ? "禁用" : "正常");
							else
								return String.CASE_INSENSITIVE_ORDER.compare(b.isEnable() ? "禁用" : "正常", a.isEnable() ? "禁用" : "正常");
						}
					});
					break;
				case 5:
					vt.sort(new Comparator<User>() {
						@Override
						public int compare(User a, User b) {
							if (SWT.UP == direction)
								return String.CASE_INSENSITIVE_ORDER.compare(a.getRemark() == null ? "" : a.getRemark(), b.getRemark() == null ? "" : b.getRemark());
							else
								return String.CASE_INSENSITIVE_ORDER.compare(b.getRemark() == null ? "" : b.getRemark(), a.getRemark() == null ? "" : a.getRemark());
						}
					});
					break;
				case 6:
					vt.sort(new Comparator<User>() {
						@Override
						public int compare(User a, User b) {
							if (SWT.UP == direction)
								return Long.compare(a.getBeginNumber(), b.getBeginNumber());
							else
								return Long.compare(b.getBeginNumber(), a.getBeginNumber());
						}
					});
					break;
				case 7:
					vt.sort(new Comparator<User>() {
						@Override
						public int compare(User a, User b) {
							if (SWT.UP == direction)
								return Long.compare(a.getEndNumber(), b.getEndNumber());
							else
								return Long.compare(b.getEndNumber(), a.getEndNumber());
						}
					});
					break;
				case 8:
					vt.sort(new Comparator<User>() {
						@Override
						public int compare(User a, User b) {
							if (SWT.UP == direction)
								return Long.compare(a.getCardsNum(), b.getCardsNum());
							else
								return Long.compare(b.getCardsNum(), a.getCardsNum());
						}
					});
					break;
				case 9:
					vt.sort(new Comparator<User>() {
						@Override
						public int compare(User a, User b) {
							if (SWT.UP == direction)
								return Long.compare(a.getCreated().getTime(), b.getCreated().getTime());
							else
								return Long.compare(b.getCreated().getTime(), a.getCreated().getTime());
						}
					});
					break;
				case 10:
					vt.sort(new Comparator<User>() {
						@Override
						public int compare(User a, User b) {
							if (SWT.UP == direction)
								return Long.compare(a.getUpdated().getTime(), b.getUpdated().getTime());
							else
								return Long.compare(b.getUpdated().getTime(), a.getUpdated().getTime());
						}
					});
					break;
			}
		}
	}
}