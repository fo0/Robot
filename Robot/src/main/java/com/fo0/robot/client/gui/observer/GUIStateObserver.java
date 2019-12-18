package com.fo0.robot.client.gui.observer;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.fo0.robot.chain.ChainStateObserver;
import com.fo0.robot.chain.EState;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class GUIStateObserver implements ChainStateObserver {

	@NonNull
	private JLabel label;

	@NonNull
	private JButton btnStop;

	@Override
	public void finished(EState state) {
		label.setText(state.name().toUpperCase());

		switch (state) {
		case Pending:
		case Processing:
			label.setBackground(Color.ORANGE);
			btnStop.setVisible(true);
			break;

		case Stopped:
		case Success:
			btnStop.setVisible(false);
			label.setBackground(Color.GREEN);
			break;

		case Failed:
			btnStop.setVisible(false);
			label.setBackground(Color.RED);
			break;
		}
	}

}
