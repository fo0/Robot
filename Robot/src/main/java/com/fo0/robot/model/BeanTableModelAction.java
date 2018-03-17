package com.fo0.robot.model;

import java.util.Map.Entry;

import com.fo0.robot.controller.ControllerChain;
import com.fo0.robot.utils.Logger;

public class BeanTableModelAction extends BeanTableModel<ActionItem> {

	private static final long serialVersionUID = -4091648872922934132L;

	public BeanTableModelAction() {
		super(ActionItem.class);

		addColumn("Type", "type");
		addColumn("Description", "description");
		// addColumn("Value", "value");

		loadActionContextFromController();
	}

	@Override
	public void addRow(ActionItem row) {
		ActionItem tmpRow = getRow(row);

		if (tmpRow != null) {
			Entry<Integer, ActionItem> item = ControllerChain.getChain().getActionItem(tmpRow);
			ControllerChain.getChain().getContext().put(item.getKey(), row);
			updateRow(item.getKey(), row);
		} else {
			ControllerChain.getChain().getContext().put(row);
			super.addRow(row);
		}

	}

	@Override
	public ActionItem updateRow(int idx, ActionItem item) {
		ActionItem tmpRow = getRow(item);

		if (tmpRow != null) {
			// todo: update the bean model
			super.updateRow(idx, item);
			return tmpRow;
		}

		return null;
	}

	@Override
	public void removeRow(ActionItem row, boolean updateModel) {
		super.removeRow(row, updateModel);
		ControllerChain.getChain().removeActionItem(row);
	}

	public ActionItem getRow(ActionItem item) {
		return super.getRow(item);
	}

	public void loadActionContextFromController() {
		try {
			clear();
			ControllerChain.getChain().getContext().getMap().entrySet().stream().forEach(e -> {
				addRow(e.getValue());
			});
		} catch (Exception e2) {
			Logger.error("failed to load items to table " + e2);
			e2.printStackTrace();
		}
	}

}
