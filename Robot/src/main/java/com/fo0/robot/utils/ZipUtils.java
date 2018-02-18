package com.fo0.robot.utils;

import java.util.concurrent.TimeUnit;

import net.lingala.zip4j.core.ZipFile;

public class ZipUtils {

	public static void unzipFile(String sourceFile, String destDir) {
		try {
			ZipFile zipFile = new ZipFile(sourceFile);
			zipFile.extractAll(destDir);
			Utils.sleep(TimeUnit.MILLISECONDS, 500);
		} catch (Exception e) {
			Logger.error("failed to unzip File: " + sourceFile + " to: " + destDir);
		}
	}

	public static void zipFolder(String toZip, String archiveName) {
		try {
			Zipper.zipDir(toZip, archiveName);
			Utils.sleep(TimeUnit.MILLISECONDS, 500);
		} catch (Exception e) {
			Logger.error("failed to unzip File: " + toZip + " to: " + archiveName);
		}
	}

}
