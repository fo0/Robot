package com.fo0.robot.utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtils {

	public static void unzip(String sourceFile, String destDir) {
		try {
			ZipFile zipFile = new ZipFile(sourceFile);
			zipFile.extractAll(destDir);
			Utils.sleep(TimeUnit.MILLISECONDS, 500);
		} catch (Exception e) {
			Logger.error("failed to unzip File: " + sourceFile + " to: " + destDir);
		}
	}

	public static void zip(String toZip, String archiveName) {
		try {
			ZipFile zipFile = new ZipFile(archiveName);
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

			ArrayList<File> filesToZip = new ArrayList<File>();
			// determine zipFile or Folder
			if (Paths.get(toZip).toFile().isDirectory()) {
				// directory
				Collection<File> files = FileUtils.listFiles(new File(toZip), null, true);
				filesToZip.addAll(files);
			} else {
				// single file
				filesToZip.add(new File(toZip));
			}

			zipFile.createZipFile(filesToZip, parameters);
			Utils.sleep(TimeUnit.MILLISECONDS, 500);
		} catch (Exception e) {
			Logger.error("failed to zip File: " + toZip + " to: " + archiveName);
		}
	}

}
