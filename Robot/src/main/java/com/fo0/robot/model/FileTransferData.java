package com.fo0.robot.model;

import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.fo0.robot.utils.Utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileTransferData {

	private String localPath;
	private String remotePath;

	private boolean success = false;

	private long size = 0;

	@Builder.Default
	private long started = System.currentTimeMillis();
	private long finished = 0;

	public String info() {
		return String.format("Duration(s): %d, Speed: %s, File: %s, Size: %s",
				TimeUnit.MILLISECONDS.toSeconds(finished - started),
				Utils.humanReadableBandwith(finished - started, size), FilenameUtils.getName(localPath),
				FileUtils.byteCountToDisplaySize(size));
	}
}
