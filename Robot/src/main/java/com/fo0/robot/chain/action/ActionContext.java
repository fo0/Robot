package com.fo0.robot.chain.action;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.fo0.robot.model.ActionItem;
import com.fo0.robot.utils.Logger;
import com.fo0.robot.utils.Parser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionContext {

	private int start = 0;
	private int end = 0;

	private int current = 0;

	@Builder.Default
	private Map<Integer, ActionItem> map = new TreeMap<Integer, ActionItem>();

	public ActionItem push(int id, ActionItem item) {
		return map.put(id, item);
	}

	public Entry<Integer, ActionItem> push(ActionItem item) {
		Entry<Integer, ActionItem> tmpItem = map.entrySet().stream().filter(e -> e.getValue().equals(item)).findFirst()
				.orElse(null);

		if (tmpItem != null) {
			return tmpItem;
		}

		int id = determineNextId();
		map.put(id, item);
		return new SimpleEntry<Integer, ActionItem>(id, item);
	}

	public void remove(ActionItem item) {
		Entry<Integer, ActionItem> foundItem = map.entrySet().stream()
				.filter(e -> e.getValue().getId().equals(item.getId())).findFirst().orElse(null);
		map.remove(foundItem.getKey());
		end = map.size();
	}

	public Entry<Integer, ActionItem> pop() {
		ActionItem latest = map.get(current);
		SimpleEntry<Integer, ActionItem> entry = new SimpleEntry<Integer, ActionItem>(current, latest);
		current++;
		end = map.size();
		return entry;
	}

	public Entry<Integer, ActionItem> peek() {
		ActionItem latest = map.get(current);
		return new SimpleEntry<Integer, ActionItem>(current, latest);
	}

	public void reset() {
		start = 0;
		end = map.size();
		current = 0;
	}

	public void clear() {
		reset();
		try {
			map.clear();
		} catch (Exception e) {
			map = new HashMap<>();
		}
	}

	public int getCurrent() {
		return current;
	}

	private int determineNextId() {
		if (map.size() == 0) {
			return 0;
		} else {
			int id = map.entrySet().stream().map(e -> e.getKey()).max(Integer::compareTo).orElse(0);
			return id = id + 1;
		}
	}

	public void save(String path) {
		Logger.info("saving config-file: " + path);
		Parser.write(this, new File(path));
	}

	public void load(String path) {
		ActionContext ctx = Parser.read(new File(path), ActionContext.class);

		if (ctx == null) {
			Logger.error("failed to load context from file: " + path);
		}

		try {
			setCurrent(ctx.getCurrent());
			setEnd(ctx.getEnd());
			setStart(ctx.getStart());
			getMap().clear();
			getMap().putAll(ctx.getMap());

			Logger.info("loading config-file: " + path + ", Success: " + (ctx != null ? "true" : "false"));
		} catch (Exception e) {
			clear();
			Logger.error("loading config-file failed, clear context");
		}

	}
}
