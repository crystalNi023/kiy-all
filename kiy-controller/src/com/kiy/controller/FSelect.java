package com.kiy.controller;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TreeItem;

import com.kiy.common.Types.Kind;
import com.kiy.common.Types.Link;
import com.kiy.common.Types.Vendor;
import com.kiy.resources.Lang;

public class FSelect extends Dialog {
	private Shell shell;
	private String tag;

	public static final String VENDOR = "Vendor";
	public static final String KIND = "Kind";
	public static final String LINK = "Link";
	private Button btnEnsure;
	private Button btnReverse;
	private Button btnAll;
	private Tree tree;

	public boolean ensure = false;

	private Set<Vendor> selectVendor;
	private Set<Vendor> selectedVendors;

	private Set<Kind> selectKind;
	private Set<Kind> selectedKinds;

	private Set<Link> selectLink;
	private Set<Link> selectedLinks;

	public FSelect(Shell arg0, String t) {
		super(arg0);
		tag = t;
	}

	public void open() {
		createContent();

		shell.open();
		shell.layout();
		F.center(getParent(), shell);

		btnReverse = new Button(shell, SWT.NONE);
		btnReverse.setBounds(107, 291, 80, 27);
		btnReverse.setText(Lang.getString("UnAllSelect"));
		btnReverse.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				super.widgetSelected(arg0);
				TreeItem[] items = tree.getItems();
				for (TreeItem item : items) {
					item.setChecked(!item.getChecked());
				}
			}

		});

		btnEnsure = new Button(shell, SWT.NONE);
		btnEnsure.setBounds(190, 291, 80, 27);
		btnEnsure.setText(Lang.getString("Ensure.text"));
		btnEnsure.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TreeItem[] items = tree.getItems();
				switch (tag) {
				case VENDOR:
					for (TreeItem treeItem : items) {
						if (treeItem.getChecked()) {
							selectVendor.add((Vendor) treeItem.getData());
						}
					}
					break;
				case KIND:
					for (TreeItem treeItem : items) {
						if (treeItem.getChecked()) {
							selectKind.add((Kind) treeItem.getData());
						}
					}
					break;
				case LINK:
					for (TreeItem treeItem : items) {
						if (treeItem.getChecked()) {
							selectLink.add((Link) treeItem.getData());
						}
					}
					break;

				default:
					break;
				}

				ensure = true;
				shell.close();
			}

		});

		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void createContent() {
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE | SWT.SYSTEM_MODAL);
		shell.setSize(295, 369);

		tree = new Tree(shell, SWT.BORDER | SWT.CHECK);
		tree.setBounds(16, 16, 254, 267);

		switch (tag) {
		case VENDOR:
			shell.setText(Lang.getString("FSelect.Vendor"));
			selectVendor = new HashSet<>();
			Vendor[] values = Vendor.values();
			for (Vendor vendor : values) {
				TreeItem treeItem = new TreeItem(tree, SWT.NONE);
				treeItem.setData(vendor);
				treeItem.setText(Lang.getString("Vendor." + vendor.name()));
			}
			break;
		case KIND:
			shell.setText(Lang.getString("FSelect.Kind"));
			selectKind = new HashSet<>();
			Kind[] values2 = Kind.values();
			for (Kind kind : values2) {
				TreeItem treeItem = new TreeItem(tree, SWT.NONE);
				treeItem.setData(kind);
				treeItem.setText(Lang.getString("Kind." + kind.name()));
			}
			break;
		case LINK:
			shell.setText(Lang.getString("FSelect.Link"));
			selectLink = new HashSet<>();
			Link[] values3 = Link.values();
			for (Link link : values3) {
				TreeItem treeItem = new TreeItem(tree, SWT.NONE);
				treeItem.setData(link);
				treeItem.setText(Lang.getString("Link." + link.name()));
			}
			break;
		default:
			break;
		}

		shell.addShellListener(new ShellAdapter() {

			@Override
			public void shellActivated(ShellEvent arg0) {
				make();
			}

		});

		btnAll = new Button(shell, SWT.NONE);
		btnAll.setBounds(19, 291, 80, 27);
		btnAll.setText(Lang.getString("FOwnerParent.ButtonSelectAll.text"));
		btnAll.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				super.widgetSelected(arg0);
				TreeItem[] items = tree.getItems();
				for (TreeItem treeItem : items) {
					treeItem.setChecked(true);
				}

			}

		});

	}

	private void make() {
		switch (tag) {
		case VENDOR:
			if (selectedVendors == null) {
				return;
			} else {
				for (Vendor vendor : selectedVendors) {
					for (TreeItem treeItem : tree.getItems()) {
						if (treeItem.getData() != null && treeItem.getData() instanceof Vendor) {
							if (vendor == (Vendor) treeItem.getData()) {
								treeItem.setChecked(true);
							}
						}
					}
				}
			}
			break;
		case KIND:
			if (selectedKinds == null) {
				return;
			} else {
				for (Kind kind : selectedKinds) {
					for (TreeItem treeItem : tree.getItems()) {
						if (treeItem.getData() != null && treeItem.getData() instanceof Kind) {
							if (kind == (Kind) treeItem.getData()) {
								treeItem.setChecked(true);
							}
						}
					}
				}
			}
			break;

		case LINK:
			if (selectedLinks == null) {
				return;
			} else {
				for (Link link : selectedLinks) {
					for (TreeItem treeItem : tree.getItems()) {
						if (treeItem.getData() != null && treeItem.getData() instanceof Link) {
							if (link == (Link) treeItem.getData()) {
								treeItem.setChecked(true);
							}
						}
					}
				}
			}
			break;

		default:
			break;
		}

	}

	public Set<Vendor> getSelectVendor() {
		return selectVendor;
	}

	public Set<Kind> getSelectKind() {
		return selectKind;
	}

	public Set<Link> getSelectLink() {
		return selectLink;
	}

	public void setSelectedVendors(Set<Vendor> selectedVendors) {
		this.selectedVendors = selectedVendors;
	}

	public void setSelectedKinds(Set<Kind> selectedKinds) {
		this.selectedKinds = selectedKinds;
	}

	public void setSelectedLinks(Set<Link> selectedLinks) {
		this.selectedLinks = selectedLinks;
	}

}
