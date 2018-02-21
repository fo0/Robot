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
import com.fo0.robot.listener.DispatchListener;
import com.fo0.robot.listener.ValueChangeListener;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.model.KeyValue;
import com.fo0.robot.utils.CONSTANTS;
import com.fo0.robot.utils.Commander;
import com.fo0.robot.utils.Logger;
import com.fo0.robot.utils.ZipUtils;
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
			Commander commander = new Commander(log -> {
				ctx.addToLog(log);
			});

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

			url = downloads.stream().filter(e -> e.getKey().equals(CONSTANTS.SOURCE)).findFirst().orElse(null);
			path = downloads.stream().filter(e -> e.getKey().equals(CONSTANTS.DESTINATION)).findFirst().orElse(
					KeyValue.builder().key(CONSTANTS.DESTINATION).value(FilenameUtils.getName(url.getValue())).build());

			ctx.addToLog("SRC: " + url);
			ctx.addToLog("DST: " + path);

			// create file
			File file = new File(Paths.get(path.getValue()).toAbsolutePath().toString());

			if (file.exists()) {
				file.delete();
			}

			file.createNewFile();

			FileUtils.copyInputStreamToFile(new URL(url.getValue()).openStream(), file);
			ctx.addToLog("Finished Download: Success");
			break;

		case Zip:
			List<KeyValue> zipList = item.getValue().parsedValue();
			KeyValue zipSrc = null;
			KeyValue zipDest = null;

			zipSrc = zipList.stream().filter(e -> e.getKey().equals(CONSTANTS.SOURCE)).findFirst().orElse(null);
			zipDest = zipList.stream().filter(e -> e.getKey().equals(CONSTANTS.DESTINATION)).findFirst()
					.orElse(KeyValue.builder().build());

			ZipUtils.zip(zipSrc.getValue(), zipDest.getValue());
			break;

		case Unzip:
			List<KeyValue> unzipList = item.getValue().parsedValue();
			KeyValue unzipSrc = null;
			KeyValue unzipDst = null;

			unzipSrc = unzipList.stream().filter(e -> e.getKey().equals(CONSTANTS.SOURCE)).findFirst().orElse(null);
			unzipDst = unzipList.stream().filter(e -> e.getKey().equals(CONSTANTS.DESTINATION)).findFirst()
					.orElse(KeyValue.builder().build());

			ZipUtils.unzip(unzipSrc.getValue(), unzipDst.getValue());
			break;

		default:
			break;
		}

		return EChainResponse.Continue;
	}

}
