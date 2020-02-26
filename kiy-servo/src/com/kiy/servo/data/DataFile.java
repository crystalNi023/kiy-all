/**
 * 2017年2月16日
 */
package com.kiy.servo.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.kiy.servo.Config;
import com.kiy.servo.Log;

/**
 * 将内存对象保存为文件/从文件载入对象到内存
 * 
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class DataFile {

	public static final String DATA_FILENAME = "servo.data";

	private String filename;

	public DataFile() {
		if (filename == null || filename.length() == 0)
			filename = DATA_FILENAME;
	}

	public Object load() {
		String path = Config.getWorkPath();
		File file = new File(path + File.separatorChar + filename);

		if (file.exists()) {
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));) {
				return in.readObject();
			} catch (IOException | ClassNotFoundException ex) {
				Log.error(ex);
				// 无需将此异常抛出，如果数据文件加载失败可以从数据库恢复
				// 如没有数据库的情况，抛出此异常也无意义，返回null即可
			}
		}
		return null;
	}

	public void save(Object o) {
		if (o == null)
			return;

		String path = Config.getWorkPath();
		File temp = new File(path + File.separatorChar + filename + ".tmp");

		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(temp));) {
			out.writeObject(o);
			out.flush();
		} catch (IOException ex) {
			Log.error(ex);
			// 无需将此异常抛出，如果数据文件保存失败可以从数据库恢复
			return;
		}

		if (temp.exists()) {
			File original = new File(path + File.separatorChar + filename);
			if (original.exists()) {
				File backup = new File(path + File.separatorChar + filename + ".bak");
				backup.delete();
				original.renameTo(backup);
				// Log.debug(original.getAbsolutePath());
			}
			temp.renameTo(original);
		}
	}
}