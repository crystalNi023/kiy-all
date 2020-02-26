/**
 * 2017年1月18日
 */
package com.kiy.view;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public class FGuide {

	private Shell shell;
	private Composite search;
	private Browser browser;
	private TreeItem item;
	private SashForm formCt;
	private CTabFolder tabTree;

	/**
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
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setText("帮助"); //$NON-NLS-1$
		shell.setSize(690, 580);
		shell.setLayout(new FormLayout());
		{
			// 定义顶部面板
			search = new Composite(shell, SWT.NONE | SWT.BORDER);
			FormData fd_search = new FormData();
			fd_search.bottom = new FormAttachment(0);
			fd_search.left = new FormAttachment(0, 3);
			fd_search.right = new FormAttachment(100, -0);
			fd_search.top = new FormAttachment(0, 3);
			search.setLayoutData(fd_search);

			// 定义树形分隔框
			formCt = new SashForm(shell, SWT.SMOOTH);
			FormData fd_content = new FormData();
			fd_content.top = new FormAttachment(search, 0, SWT.TOP);
			fd_content.bottom = new FormAttachment(100);
			fd_content.left = new FormAttachment(0, 3);
			fd_content.right = new FormAttachment(35, -0);
			formCt.setLayoutData(fd_content);
			// 放上选择卡
			tabTree = new CTabFolder(formCt, SWT.BORDER);
			// 放置树的选项卡的内容
			CTabItem tabTreeZone = new CTabItem(tabTree, SWT.NONE);
			tabTreeZone.setText("Help");

			Tree tree = new Tree(tabTree, SWT.MULTI | SWT.BORDER);
			tabTreeZone.setControl(tree);

			tabTree.setSelection(tabTreeZone);
			// 读取xml文件的方法
			try {
				Document doc = readXml();
				NodeList nl = doc.getElementsByTagName("helps");
				for (int i = 0; i < nl.getLength(); i++) {
					TreeItem treeItem = new TreeItem(tree, SWT.NONE);
					treeItem.setText("帮助");
					list(nl.item(i), treeItem);
				}
			} catch (ParserConfigurationException e) {
				// 暂无处理
			} catch (SAXException e) {
				// 暂无处理
			} catch (IOException e) {
				// 暂无处理
			}
			tree.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					item = (TreeItem) e.item;
					if (item != null) {
						String dir = System.getProperty("user.dir");

						if (item.getData() != null) {
//							System.out.println("item.getData()" + item.getData());
							setBrowser(dir + File.separator + "help" + File.separator + item.getData());
						}

					}
				}
			});

			// 定义浏览器
			browser = new Browser(shell, SWT.BORDER);
			FormData fd_page = new FormData();
			fd_page.left = new FormAttachment(formCt, 2);
			fd_page.right = new FormAttachment(100);
			fd_page.top = new FormAttachment(search, 0, SWT.TOP);
			fd_page.bottom = new FormAttachment(100);
			browser.setLayoutData(fd_page);
		}
	}
	// 读取xml文件
	public Document readXml() throws ParserConfigurationException, SAXException, IOException {
		File f = new File("help/help.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(f);
		return doc;
	}

	// 递归添加树形
	private void list(Node rootNode, TreeItem item) {
		NodeList nodes = rootNode.getChildNodes();
		if (nodes != null) {
			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i).getNodeName() == "#text") {
					continue;
				}
				TreeItem subItem = new TreeItem(item, SWT.None);
				subItem.setText(nodes.item(i).getAttributes().getNamedItem("title").getNodeValue());

				if (nodes.item(i).getAttributes().getNamedItem("url") == null) {

				} else {
					subItem.setData(nodes.item(i).getAttributes().getNamedItem("url").getNodeValue());
					// System.out.println("设置网页到树item里"+nodes.item(i).getAttributes().getNamedItem("url").getNodeValue());
				}

				list(nodes.item(i), subItem);
			}
		}
	}

	private void setBrowser(String url) {
		browser.setUrl(url);
	}
}
