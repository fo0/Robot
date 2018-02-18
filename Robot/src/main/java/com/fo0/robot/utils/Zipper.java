package com.fo0.robot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

	public static boolean zipDir(String dirName, String nameZipFile) {
		try {
			ZipOutputStream zip = null;
			FileOutputStream fW = null;
			fW = new FileOutputStream(nameZipFile);
			zip = new ZipOutputStream(fW);
			addFolderToZip("", dirName, zip);
			zip.close();
			fW.close();
			return true;
		} catch (Exception e) {
			Logger.error("failed to zip folder: " + dirName + " to file: " + nameZipFile + " " + e);
		}
		return false;
	}

	private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws IOException {
		File folder = new File(srcFolder);
		if (folder.list().length == 0)
			addFileToZip(path, srcFolder, zip, true);
		else
			for (String fileName : folder.list())
				if (path.isEmpty())
					addFileToZip(folder.getName(), srcFolder + OSUtils.FILE_SEPARATOR + fileName, zip, false);
				else
					addFileToZip(path + OSUtils.FILE_SEPARATOR + folder.getName(),
							srcFolder + OSUtils.FILE_SEPARATOR + fileName, zip, false);
	}

	private static void addFileToZip(String path, String srcFile, ZipOutputStream zip, boolean flag)
			throws IOException {
		File folder = new File(srcFile);
		if (flag)
			zip.putNextEntry(new ZipEntry(path + OSUtils.FILE_SEPARATOR + folder.getName() + OSUtils.FILE_SEPARATOR));
		else if (folder.isDirectory())
			addFolderToZip(path, srcFile, zip);
		else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + OSUtils.FILE_SEPARATOR + folder.getName()));
			while ((len = in.read(buf)) > 0)
				zip.write(buf, 0, len);
			in.close();
		}
	}
}
