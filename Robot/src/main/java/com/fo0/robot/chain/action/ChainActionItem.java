package com.fo0.robot.chain.action;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fo0.robot.chain.ChainCommand;
import com.fo0.robot.chain.EChainResponse;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.model.KeyValue;
import com.fo0.robot.utils.Commander;
import com.fo0.robot.utils.Logger;
import com.google.common.collect.Lists;

import lombok.Builder;

@Builder
public class ChainActionItem implements ChainCommand<ActionContext> {

	@Override
	public EChainResponse command(ActionContext ctx) throws Exception {
		// get latest action
		Entry<Integer, ActionItem> item = ctx.pop();

		// info
		Logger.info("popped action: " + item.getKey() + ", " + item.getValue());

		switch (item.getValue().getType()) {
		case Commandline:
			Commander commander = new Commander();
			commander.execute(true, System.getProperty("user.dir"), item.getValue().getValue());

			if (commander == null || commander.isError()) {
				Logger.error("found errors in commander: " + item.getKey());
				return EChainResponse.Failed;
			}
			break;

		case Download:
			List<KeyValue> downloads = item.getValue().parsedValue();
			KeyValue url = null;
			KeyValue path = null;

			url = downloads.stream().filter(e -> e.getKey().equals("URL")).findFirst().orElse(null);
			path = downloads.stream().filter(e -> e.getKey().equals("PATH")).findFirst()
					.orElse(KeyValue.builder().key("PATH").value(FilenameUtils.getName(url.getValue())).build());

			Logger.info("URL: " + url.getValue());
			Logger.info("Path: " + path.getValue());

			// create file
			File file = new File(Paths.get(path.getValue()).toAbsolutePath().toString());

			if (file.exists()) {
				file.delete();
			}

			file.createNewFile();

			FileUtils.copyInputStreamToFile(new URL(url.getValue()).openStream(), file);
			break;

		default:
			break;
		}

		return EChainResponse.Continue;
	}

}
