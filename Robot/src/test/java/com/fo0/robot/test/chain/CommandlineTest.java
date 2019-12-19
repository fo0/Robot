package com.fo0.robot.test.chain;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import com.fo0.robot.chain.EState;
import com.fo0.robot.chain.action.ChainActions;
import com.fo0.robot.enums.EActionType;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.utils.CONSTANTS;
import com.fo0.robot.utils.Utils;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

public class CommandlineTest {

	private String testDir = "test";
	private String file = "script.sh";

	@Test
	public void basic() {
		createDir();
		createFile();
		CONSTANTS.DEBUG = true;
		ChainActions actions = new ChainActions();
		actions.addActionItem(ActionItem.builder().type(EActionType.Commandline).value(createCmds())
				.description("description example").build());
		actions.start();
		Assert.assertEquals(EState.Success, actions.getState());
	}

	@Test
	public void noWaitFail() {
		createDir();
		createFile();
		CONSTANTS.DEBUG = true;

		Stopwatch watch = Stopwatch.createStarted();
		ChainActions actions = new ChainActions();
		actions.addActionItem(ActionItem.builder().type(EActionType.Commandline).value(createNoSleepCmds())
				.description("description example").build());
		actions.start();
		watch.stop();

		Utils.sleep(100);
		
		Assert.assertEquals(EState.Success, actions.getState());
		Assert.assertTrue(watch.elapsed(TimeUnit.MILLISECONDS) < 250);
	}

	@Test
	public void waitSuccess() {
		createDir();
		createFile();
		CONSTANTS.DEBUG = true;

		Stopwatch watch = Stopwatch.createStarted();
		ChainActions actions = new ChainActions();
		actions.addActionItem(ActionItem.builder().type(EActionType.Commandline).value(createSleepCmds())
				.description("description example").build());
		actions.start();
		watch.stop();

		Assert.assertEquals(EState.Success, actions.getState());
		Assert.assertTrue(watch.elapsed(TimeUnit.MILLISECONDS) > 2000);
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

	private String createNoSleepCmds() {
		String WAIT = "$WAIT(false)";
		String HOME = "$HOME(" + testDir + ")";
		String CMDS = "$CMDS(sleep,2)";
		return WAIT + " " + HOME + " " + CMDS;
	}

	private String createSleepCmds() {
		String WAIT = "$WAIT(true)";
		String HOME = "$HOME(" + testDir + ")";
		String CMDS = "$CMDS(sleep,2)";
		return WAIT + " " + HOME + " " + CMDS;
	}

	private String createCmds() {
		String HOME = "$HOME(" + testDir + ")";
		String CMDS = "$CMDS(/bin/sh," + file + ")";
		return HOME + " " + CMDS;
	}

}
