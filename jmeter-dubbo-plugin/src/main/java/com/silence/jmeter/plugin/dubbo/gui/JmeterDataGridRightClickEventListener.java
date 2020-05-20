package com.silence.jmeter.plugin.dubbo.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JmeterDataGridRightClickEventListener implements MouseListener {

	private JmeterDataGridRightClickEventHandler jmeterDataGridRightClickEventHandler;

	public JmeterDataGridRightClickEventListener() {
		this(null);
	}

	public JmeterDataGridRightClickEventListener(
			JmeterDataGridRightClickEventHandler jmeterDataGridRightClickEventHandler) {
		if (null == jmeterDataGridRightClickEventHandler) {
			this.jmeterDataGridRightClickEventHandler = new DefaultJmeterDataGridRightClickEventHandler();
		} else {
			this.jmeterDataGridRightClickEventHandler = jmeterDataGridRightClickEventHandler;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (this.jmeterDataGridRightClickEventHandler != null) {
				this.jmeterDataGridRightClickEventHandler.doAction(e);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
