package com.fo0.robot.client.gui.observer;

import java.awt.Color;

import javax.swing.JLabel;

import com.fo0.robot.chain.ChainStateObserver;
import com.fo0.robot.chain.EState;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class GUIStateObserver implements ChainStateObserver {

	@NonNull
	private JLabel label;

	@Override
	public void finished(EState state) {
		label.setText(state.name().toUpperCase());

		switch (state) {
		case Pending:
		case Processing:
			label.setBackground(Color.ORANGE);
			break;

		case Stopped:
		case Success:
			label.setBackground(Color.GREEN);
			break;

		case Failed:
			label.setBackground(Color.RED);
			break;
		}
	}

}
