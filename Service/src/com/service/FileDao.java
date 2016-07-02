package com.service;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
public class FileDao {
	public static List<File> getFileList(String dir) {
		List<File> listFiles = new ArrayList<File>();
		File dirFile = new File(dir);
		// 如果不是目录文件，则直接返回
		if (dirFile.isDirectory()) {
			// 获得文件夹下的文件列表，然后根据文件类型分别处理
			File[] files = dirFile.listFiles();
			if (null != files && files.length > 0) {
				// 根据时间排序
				Arrays.sort(files, new Comparator<File>() {
					public int compare(File f1, File f2) {
						return (int) (f1.lastModified() - f2.lastModified());
					}
                    public boolean equals(Object obj) {
						return true;
					}
				});
				for (File file : files) {
					listFiles.add(file);
				}
			}
		}
		return listFiles;
	}
}
