package com.fo0.robot.test.chain;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZipTest {

	private static final ArchiveFormat TYPE = ArchiveFormat.ZIP;
	private static String toCompressFile = "files/demo-cli.gif";

	private static String SOURCE_DIR = "files";
	private static String DEST_DIR = "test";

	private static String ARCHIVE_NAME = "test.zip";
	private static String ARCHIVE_PATH = DEST_DIR + "/" + ARCHIVE_NAME;

	@BeforeClass
	public static void before() {
		cleanup();
	}

	@AfterClass
	public static void after() {
		cleanup();
	}

	@Test
	public void compress() {
		try {
			Archiver zipArchive = ArchiverFactory.createArchiver(TYPE);
			zipArchive.create(ARCHIVE_NAME, new File(DEST_DIR), new File(toCompressFile));
			Assert.assertEquals(true, new File(ARCHIVE_PATH).exists());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void decompress() {
		try {
			Archiver zipArchive = ArchiverFactory.createArchiver(TYPE);
			zipArchive.extract(new File(ARCHIVE_PATH), new File(DEST_DIR));
			Assert.assertEquals(true, new File(toCompressFile).exists());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void cleanup() {
		try {
			FileUtils.cleanDirectory(new File("test/"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
