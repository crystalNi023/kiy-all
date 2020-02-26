package com.kiy.view;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.kiy.common.DataCache;
import com.kiy.common.ReadCard;
import com.kiy.common.User;
import com.kiy.common.Util;
import com.kiy.data.DataOperation;
import org.eclipse.wb.swt.SWTResourceManager;

public class FReadCardQuery {
	private Shell shell;
	private DataCache dataCache;
	private FVTable vt;
	private DateTime startDay;
	private DateTime startTime;
	private DateTime endDay;
	private DateTime endTime;
	private Date beginTimestamp;
	private Date endTimestamp;
	private Button queryButton;
	private CLabel sumLabel;
	
	private User user;
	private Long userId;

	/**
	 * @wbp.parser.entryPoint
	 */

	public User open(User u) {
		this.user = u;
		dataCache = DataCache.getInstance();
		try {
			createContent();
		} catch (ParseException e) {
		}
		shell.open();
		shell.layout();
		return user;
	}

	public void createContent() throws ParseException {
		shell = new Shell(SWT.MIN|SWT.CLOSE);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setText("读卡记录查询");
		shell.setSize(900, 508);
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2, Display.getCurrent().getClientArea().height / 2 - shell.getSize().y / 2);

		Table table = new Table(shell, SWT.VIRTUAL | SWT.MULTI | SWT.FULL_SELECTION);
		table.setBounds(0, 57, 884, 397);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(91);
		tblclmnNewColumn.setText("用户名");

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("真实姓名");

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(122);
		tableColumn_1.setText("卡号");

		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(97);
		tableColumn_2.setText("类型");

		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(160);
		tableColumn_3.setText("备注");

		TableColumn tableColumn_6 = new TableColumn(table, SWT.NONE);
		tableColumn_6.setWidth(165);
		tableColumn_6.setText("创建时间");

		TableColumn tableColumn_7 = new TableColumn(table, SWT.NONE);
		tableColumn_7.setWidth(157);
		tableColumn_7.setText("更新时间");

		vt = new FVTable(table);

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setBounds(16, 14, 852, 35);

		Label start = new Label(composite, SWT.NONE);
		start.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		start.setText("开始时间：");
		start.setBounds(16, 8, 70, 17);

		startDay = new DateTime(composite, SWT.BORDER);
		startDay.setBounds(94, 5, 110, 24);

		// 设置当前选中时间为当前时间前一天
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		startDay.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

		startTime = new DateTime(composite, SWT.BORDER | SWT.TIME);
		startTime.setBounds(212, 5, 110, 24);

		Label endLable = new Label(composite, SWT.NONE);
		endLable.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		endLable.setText("结束时间：");
		endLable.setBounds(364, 8, 70, 17);

		endDay = new DateTime(composite, SWT.BORDER);
		endDay.setBounds(442, 5, 110, 24);

		endTime = new DateTime(composite, SWT.BORDER | SWT.TIME);
		endTime.setBounds(560, 5, 110, 24);

		queryButton = new Button(composite, SWT.NONE);
		queryButton.setText("查询");
		queryButton.setBounds(724, 3, 80, 26);
		queryButton.addSelectionListener(new ReadCardQueryAdapter());

		CLabel lblNewLabel = new CLabel(shell, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(0, 454, 29, 16);
		lblNewLabel.setText("共");

		sumLabel = new CLabel(shell, SWT.NONE);
		sumLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		sumLabel.setAlignment(SWT.CENTER);
		sumLabel.setText("0");
		sumLabel.setBounds(16, 454, 59, 16);

		CLabel label_1 = new CLabel(shell, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setText("条");
		label_1.setBounds(74, 454, 19, 16);
		
		if (user.getId() != 0) {
			userId = user.getId();
		}else {
			userId = null;
		}
		List<ReadCard> selectReadCard = DataOperation.selectReadCard(userId, Util.getDateReduceOne(), new Date());
		for (ReadCard readCard : selectReadCard) {
			dataCache.addReadCard(readCard);
		}
		vt.beginUpdate();
		vt.add(dataCache.getReadCard());
		vt.endUpdate();
	}

	private void refresh() {
		List<ReadCard> selectReadCard1 = null;
		selectReadCard1 = DataOperation.selectReadCard(userId, beginTimestamp, endTimestamp);
		dataCache.removeAll();
		vt.clear();
		for (ReadCard readCard : selectReadCard1) {
			dataCache.addReadCard(readCard);
		}
		vt.beginUpdate();
		vt.add(dataCache.getReadCard());
		vt.endUpdate();
	}

	private class FVTable extends VTable<ReadCard> {

		public FVTable(Table t) {
			super(t);
		}

		@Override
		public void row(TableItem tableItem, ReadCard readCard) {
			tableItem.setText(0, readCard.getUsername());
			tableItem.setText(1, readCard.getRealname());
			tableItem.setText(2, Long.toString(readCard.getNumber()));
			tableItem.setText(3, readCard.getKind().name());
			tableItem.setText(4, readCard.getRemark());
			tableItem.setText(5, Util.dateFormat(readCard.getCreated()));
			tableItem.setText(6, Util.dateFormat(readCard.getUpdated()));
			sumLabel.setText(Integer.toString((this.getCount())));
		}

		@Override
		public void column(TableColumn column, int direction) {
			switch (vt.getColumnIndex(column)) {
				case 0:
					vt.sort(new Comparator<ReadCard>() {
						@Override
						public int compare(ReadCard a, ReadCard b) {
							if (SWT.UP == direction)
								return String.CASE_INSENSITIVE_ORDER.compare(a.getUsername() == null ? "" : a.getUsername(), b.getUsername() == null ? "" : b.getUsername());
							else
								return String.CASE_INSENSITIVE_ORDER.compare(b.getUsername() == null ? "" : b.getUsername(), a.getUsername() == null ? "" : a.getUsername());
						}
					});
					break;
				case 1:
					vt.sort(new Comparator<ReadCard>() {
						@Override
						public int compare(ReadCard a, ReadCard b) {
							if (SWT.UP == direction)
								return String.CASE_INSENSITIVE_ORDER.compare(a.getRealname() == null ? "" : a.getRealname(), b.getRealname() == null ? "" : b.getRealname());
							else
								return String.CASE_INSENSITIVE_ORDER.compare(b.getRealname() == null ? "" : b.getRealname(), a.getRealname() == null ? "" : a.getRealname());
						}
					});
					break;
				case 2:
					vt.sort(new Comparator<ReadCard>() {
						@Override
						public int compare(ReadCard a, ReadCard b) {
							if (SWT.UP == direction)
								return Long.compare(a.getNumber(), b.getNumber());
							else
								return Long.compare(b.getNumber(), a.getNumber());
						}
					});
					break;
				case 3:
					vt.sort(new Comparator<ReadCard>() {
						@Override
						public int compare(ReadCard a, ReadCard b) {
							if (SWT.UP == direction)
								return Integer.compare(a.getKind().getValue(), b.getKind().getValue());
							else
								return Integer.compare(b.getKind().getValue(), a.getKind().getValue());
						}
					});
					break;
				case 4:
					vt.sort(new Comparator<ReadCard>() {
						@Override
						public int compare(ReadCard a, ReadCard b) {
							if (SWT.UP == direction)
								return String.CASE_INSENSITIVE_ORDER.compare(a.getRemark() == null ? "" : a.getRemark(), b.getRemark() == null ? "" : b.getRemark());
							else
								return String.CASE_INSENSITIVE_ORDER.compare(b.getRemark() == null ? "" : b.getRemark(), a.getRemark() == null ? "" : a.getRemark());

						}
					});
					break;
				case 5:
					vt.sort(new Comparator<ReadCard>() {
						@Override
						public int compare(ReadCard a, ReadCard b) {
							if (SWT.UP == direction)
								return Long.compare(a.getCreated().getTime(), b.getCreated().getTime());
							else
								return Long.compare(b.getCreated().getTime(), a.getCreated().getTime());
						}
					});
					break;
				case 6:
					vt.sort(new Comparator<ReadCard>() {
						@Override
						public int compare(ReadCard a, ReadCard b) {
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

	private class ReadCardQueryAdapter extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			int sYear = startDay.getYear();
			int sMonth = startDay.getMonth() + 1;
			int sDay = startDay.getDay();
			int sHours = startTime.getHours();
			int sMinutes = startTime.getMinutes();
			int sSeconds = startTime.getSeconds();
			int eYear = endDay.getYear();
			int eMonth = endDay.getMonth() + 1;
			int eDay = endDay.getDay();
			int eHours = endTime.getHours();
			int eMinutes = endTime.getMinutes();
			int eSeconds = endTime.getSeconds();

			// 聚合年月日时分秒
			String startDate = sYear + "-" + sMonth + "-" + sDay + " " + sHours + ":" + sMinutes + ":" + sSeconds;
			String endDate = eYear + "-" + eMonth + "-" + eDay + " " + eHours + ":" + eMinutes + ":" + eSeconds;
			try {
				beginTimestamp = Util.parse(startDate);
				endTimestamp = Util.parse(endDate);
			} catch (ParseException e1) {
				return;
			}
			refresh();
			sumLabel.setText("0");
			vt.refresh();
		}

	}
}