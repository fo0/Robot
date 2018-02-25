package com.fo0.robot.chain.action;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.fo0.robot.chain.ChainCommand;
import com.fo0.robot.chain.EChainResponse;
import com.fo0.robot.enums.EActionType;
import com.fo0.robot.listener.DispatchListener;
import com.fo0.robot.listener.ValueChangeListener;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.model.Host;
import com.fo0.robot.model.KeyValue;
import com.fo0.robot.utils.CONSTANTS;
import com.fo0.robot.utils.Commander;
import com.fo0.robot.utils.Logger;
import com.fo0.robot.utils.SSHClient;
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
		EActionType type = item.getValue().getType();
		switch (type) {
		case Commandline:
			Commander commander = new Commander(log -> {
				ctx.addToLogPlain(type, log);
			});

			commander.execute(true, System.getProperty("user.dir"), item.getValue().getValue());

			if (commander == null || commander.isError()) {
				ctx.addToLog(type, "error at commander: " + item.getKey());
				return EChainResponse.Failed;
			}
			break;

		case Download:
			List<KeyValue> downloads = item.getValue().parsedValue();
			KeyValue url = downloads.stream().filter(e -> e.getKey().equals(CONSTANTS.SOURCE)).findFirst().orElse(null);
			KeyValue path = downloads.stream().filter(e -> e.getKey().equals(CONSTANTS.DESTINATION)).findFirst().orElse(
					KeyValue.builder().key(CONSTANTS.DESTINATION).value(FilenameUtils.getName(url.getValue())).build());

			ctx.addToLog(type, "SRC: " + url);
			ctx.addToLog(type, "DST: " + path);

			// create file
			File file = new File(Paths.get(path.getValue()).toAbsolutePath().toString());

			if (file.exists()) {
				file.delete();
			}

			file.createNewFile();

			FileUtils.copyInputStreamToFile(new URL(url.getValue()).openStream(), file);
			ctx.addToLog(type, "Finished Download: Success");
			break;

		case Zip:
			List<KeyValue> zipList = item.getValue().parsedValue();
			KeyValue zipSrc = zipList.stream().filter(e -> e.getKey().equals(CONSTANTS.SOURCE)).findFirst()
					.orElse(null);
			KeyValue zipDest = zipList.stream().filter(e -> e.getKey().equals(CONSTANTS.DESTINATION)).findFirst()
					.orElse(KeyValue.builder().build());

			ctx.addToLog(type, "SRC: " + zipSrc);
			ctx.addToLog(type, "DST: " + zipDest);

			ZipUtils.zip(zipSrc.getValue(), zipDest.getValue());
			break;

		case Unzip:
			List<KeyValue> unzipList = item.getValue().parsedValue();
			KeyValue unzipSrc = unzipList.stream().filter(e -> e.getKey().equals(CONSTANTS.SOURCE)).findFirst()
					.orElse(null);
			KeyValue unzipDst = unzipList.stream().filter(e -> e.getKey().equals(CONSTANTS.DESTINATION)).findFirst()
					.orElse(KeyValue.builder().build());

			ctx.addToLog(type, "SRC: " + unzipSrc);
			ctx.addToLog(type, "DST: " + unzipDst);

			ZipUtils.unzip(unzipSrc.getValue(), unzipDst.getValue());
			break;

		case SSH:
			List<KeyValue> sshList = item.getValue().parsedValue();
			KeyValue sshHost = sshList.stream().filter(e -> e.getKey().equals(CONSTANTS.HOST)).findFirst().orElse(null);
			KeyValue sshPort = sshList.stream().filter(e -> e.getKey().equals(CONSTANTS.PORT)).findFirst().orElse(null);
			KeyValue sshUser = sshList.stream().filter(e -> e.getKey().equals(CONSTANTS.USER)).findFirst().orElse(null);
			KeyValue sshPassword = sshList.stream().filter(e -> e.getKey().equals(CONSTANTS.PASSWORD)).findFirst()
					.orElse(null);
			KeyValue sshCmd = sshList.stream().filter(e -> e.getKey().equals(CONSTANTS.CMD)).findFirst().orElse(null);

			ctx.addToLog(type, "HOST: " + sshHost.getValue());
			ctx.addToLog(type, "User: " + sshUser.getValue());
			ctx.addToLog(type, "Password: " + StringUtils.join(
					IntStream.range(0, sshPassword.getValue().length()).mapToObj(e -> "*").toArray(String[]::new)));
			ctx.addToLog(type, "PORT: " + sshPort.getValue());
			ctx.addToLog(type, "CMD: " + sshCmd.getValue());

			SSHClient sshClient = new SSHClient(
					Host.builder().address(sshHost.getValue()).port(Integer.parseInt(sshPort.getValue()))
							.username(sshUser.getValue()).password(sshPassword.getValue()).build());

			sshClient.connect();
			if (!sshClient.test()) {
				ctx.addToLog(type, "failed to connect to Host");
				return EChainResponse.Failed;
			}

			sshClient.command(sshCmd.getValue(), null, out -> {
				ctx.addToLogPlain(type, out);
			}, error -> {
				ctx.addToLogPlain(type, error);
			});
			break;

		default:
			ctx.addToLog(type, "Currently not implemented, you may check for updates");
			break;
		}

		return EChainResponse.Continue;
	}

}
