package com.kiy.cloud.notice;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 邮件模板类，支持循环，需要替换的字符格式｛name｝，全部小写
 * 支持循环｛each}XXXXXXX{name*}{end}之间的内容将循环构建。循环不能嵌套和交叉否则会出现意外结果。
 * 邮件模版对象可重复使用，make方法多线程安全。
 * 
 * @author 张希
 *
 */
public final class MailTemplate {

	public final String EACH = "each";// 循环开始标志，小写
	public final String END = "end";// 循环结束标志，小写

	private File file;
	private String[] templates;
	private String[] names;

	public MailTemplate(String filename) throws FileNotFoundException {
		file = new File(filename);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		// 初始化为 0长度数组
		names = new String[0];
		templates = new String[0];
	}

	public final String make(Properties properties) {
		String name = null;
		String value = null;
		StringBuilder builder = new StringBuilder();
		for (int index = 0; index < templates.length; index++) {
			builder.append(templates[index]);
			if (index < names.length) {
				name = names[index];
				if (EACH.equalsIgnoreCase(name)) {
					// 处理循环
					int each = 0;// 记录循环次数
					int each_index = 0;
					while (each >= 0) {
						each_index = index + 1;
						while (!name.equalsIgnoreCase(END) && each_index < templates.length) {
							name = names[each_index];
							if (name.endsWith("*")) {
								// 替换*为序号
								name = name.replace("*", Integer.toString(each));
								value = properties.getProperty(name);
								if (value == null)
									each = -10;// 没有更多值
							} else if (END.equalsIgnoreCase(name)) {
								value = "";
							} else {
								value = properties.getProperty(name);
							}
							// 判断是否成功获取到值
							if (each >= 0) {
								builder.append(templates[each_index]);
								builder.append(value);
								each_index++;
							} else {
								break;
							}
						}
						each++;
						name = "";
					}
					// 更新index,跳过循环段
					index += each_index;
					// 循环完成
				} else {
					builder.append(properties.getProperty(name));
				}
			}
		}
		return builder.toString();
	}

	/*
	 * 从文件载入邮件模版 A{XXX}B{XXX}C {XXX}A{XXX}BC A{XXX}B{XXX}C{XXX}
	 */

	public final void load() throws IOException {
		char c = 0;
		int value = 0;
		boolean name = false;
		ArrayList<String> _names = new ArrayList<String>();
		ArrayList<String> _templates = new ArrayList<String>();
		StringBuilder builder_template = new StringBuilder();
		StringBuilder builder_name = new StringBuilder();

		InputStream input = null;
		InputStreamReader reader = null;
		try {
			input = new FileInputStream(file);
			reader = new InputStreamReader(input, "utf-8");
			while ((value = reader.read()) > -1) {
				c = (char) value;

				if (name) {
					if (c == '}') {
						name = false;
						_names.add(builder_name.toString());
						builder_name.delete(0, builder_name.length());
						continue;
					}
					builder_name.append(c);
				} else {
					if (c == '{') {
						name = true;
						// 保存模版字符,并清空构造对象
						_templates.add(builder_template.toString());
						builder_template.delete(0, builder_template.length());
						continue;
					}
					builder_template.append(c);
				}
			}
			// 将剩余部分添加到模版
			_templates.add(builder_template.toString());
			// 成员数组化，重复利用提高效率
			templates = _templates.toArray(templates);
			names = _names.toArray(names);
		} finally {
			reader.close();
			input.close();
		}
	}
}