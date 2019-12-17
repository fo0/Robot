package com.fo0.robot.test.chain;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.chain.EState;
import com.fo0.robot.chain.action.ChainActions;
import com.fo0.robot.enums.EActionType;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.utils.Logger;

public class ContextBackupTest {

	@Test
	public void saveContext() {
		ChainActions actions = new ChainActions();
		actions.addActionItem(ActionItem.builder().type(EActionType.Simple_Commandline).value("echo lol")
				.description("description example").build());
		actions.start();
		Assert.assertEquals(EState.Success, actions.getState());

		String dir = "test";
		String name = dir + "/test.robot";
		try {
			// check if dir is a folder
			Assert.assertEquals(true, new File(dir).isDirectory());
			FileUtils.cleanDirectory(new File(dir));

			// check if dir is empty
			Assert.assertEquals(0, new File(dir).list().length);

			// save context to file: name
			System.out.println(Paths.get(dir).toAbsolutePath());
			actions.getContext().save(name);

			// check if file exists
			Assert.assertEquals(true, new File(name).exists());

			// check if file has content
			Assert.assertNotEquals(0, FileUtils.readFileToString(new File(name), Charset.defaultCharset()).length());

			// clear tmp again
			FileUtils.cleanDirectory(new File(dir));
		} catch (Exception e) {
			Logger.info("failed to clean or save directory and context");
		}

	}

}
