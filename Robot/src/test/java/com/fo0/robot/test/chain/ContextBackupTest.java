package com.fo0.robot.test.chain;

import java.io.File;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.chain.EState;
import com.fo0.robot.chain.action.ChainActions;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.utils.Logger;

public class ContextBackupTest {

	@Test
	public void saveContext() {
		ChainActions actions = new ChainActions();
		actions.addActionItem(ActionItem.builder().value("echo lol").description("description example").build());
		actions.start();
		Assert.assertEquals(EState.Success, actions.getState());

		String dir = "test";
		String name = dir + "/test.robot";
		try {
			FileUtils.cleanDirectory(new File(dir));
			System.out.println(Paths.get(dir).toAbsolutePath());
			actions.getContext().save(name);
		} catch (Exception e) {
			Logger.info("failed to clean or save directory and context");
		}
		
		
	}

}
