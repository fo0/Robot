package com.fo0.robot.test.chain;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.chain.EState;
import com.fo0.robot.chain.action.ChainActions;
import com.fo0.robot.enums.EActionType;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.utils.CONSTANTS;
import com.google.common.collect.Lists;

public class CommandlineTest {

	private String testDir = "test";
	private String file = "script.sh";

	@Test
	public void successTest() {
		createDir();
		createFile();
		CONSTANTS.DEBUG = true;
		ChainActions actions = new ChainActions();
		actions.addActionItem(ActionItem.builder().type(EActionType.Commandline).value(createCmds())
				.description("description example").build());
		actions.start();
		Assert.assertEquals(EState.Success, actions.getState());
	}

	private void createFile() {
		try {
			FileUtils.writeLines(new File(testDir + "/" + file), Lists.newArrayList("echo test"));
		} catch (IOException e) {
		}
	}

	private boolean createDir() {
		return new File(testDir).mkdirs();
	}

	private String createCmds() {
		String HOME = "$HOME(" + testDir + ")";
		String CMDS = "$CMDS(/bin/sh," + file + ")";
		return HOME + " " + CMDS;
	}

}
