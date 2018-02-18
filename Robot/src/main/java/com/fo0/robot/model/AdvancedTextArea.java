package com.fo0.robot.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

public class AdvancedTextArea extends JTextArea implements FocusListener {

	private static final long serialVersionUID = -7538285323018403910L;

	private String hint;

	public AdvancedTextArea() {
		this("");
	}

	public AdvancedTextArea(final String hint) {
		setHint(hint);
		super.addFocusListener(this);
	}

	public void setHint(String hint) {
		this.hint = hint;
		setUI(new HintTexAreaUI(hint, true));
	}

	public void focusGained(FocusEvent e) {
		if (this.getText().length() == 0) {
			super.setText("");
		}
	}

	public void focusLost(FocusEvent e) {
		if (this.getText().length() == 0) {
			setHint(hint);
		}
	}

	public String getText() {
		String typed = super.getText();
		return typed.equals(hint) ? "" : typed;
	}
}

class HintTexAreaUI extends javax.swing.plaf.basic.BasicTextAreaUI implements FocusListener {

	private String hint;
	private boolean hideOnFocus;
	private Color color;
	private boolean wordwrap;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		repaint();
	}

	private void repaint() {
		if (getComponent() != null) {
			getComponent().repaint();
		}
	}

	public boolean isHideOnFocus() {
		return hideOnFocus;
	}

	public void setHideOnFocus(boolean hideOnFocus) {
		this.hideOnFocus = hideOnFocus;
		repaint();
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
		repaint();
	}

	public HintTexAreaUI(String hint) {
		this(hint, false);
	}

	public HintTexAreaUI(String hint, boolean hideOnFocus) {
		this(hint, hideOnFocus, null);
	}

	public HintTexAreaUI(String hint, boolean hideOnFocus, Color color) {
		this.hint = hint;
		this.hideOnFocus = hideOnFocus;
		this.color = color;

	}

	protected void paintSafely(Graphics g) {
		super.paintSafely(g);
		JTextComponent comp = getComponent();
		if (hint != null && comp.getText().length() == 0 && (!(hideOnFocus && comp.hasFocus()))) {
			if (color != null) {
				g.setColor(color);
			} else {
				g.setColor(Color.gray);
			}
			int padding = (comp.getHeight() - comp.getFont().getSize()) / 2;
			drawStrings(hint.split("\\n"), 5, comp.getHeight() - padding - 1, g);
		}
	}

	public void drawStrings(String ln[], int x, int y, Graphics g) {
		int h = g.getFont().getSize();
		for (int i = 0; i < ln.length; i++) {
			g.drawString(ln[i], x, y + (h * i));
		}
	}

	public void focusGained(FocusEvent e) {
		if (hideOnFocus)
			repaint();

	}

	public void focusLost(FocusEvent e) {
		if (hideOnFocus)
			repaint();
	}

	protected void installListeners() {
		super.installListeners();
		getComponent().addFocusListener(this);
	}

	protected void uninstallListeners() {
		super.uninstallListeners();
		getComponent().removeFocusListener(this);
	}
}
