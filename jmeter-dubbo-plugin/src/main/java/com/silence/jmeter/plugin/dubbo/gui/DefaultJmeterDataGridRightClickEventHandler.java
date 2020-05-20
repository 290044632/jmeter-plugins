package com.silence.jmeter.plugin.dubbo.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.silence.jmeter.plugin.dubbo.constants.Resources;
import com.silence.jmeter.plugin.dubbo.util.JmeterResUtils;

public class DefaultJmeterDataGridRightClickEventHandler implements JmeterDataGridRightClickEventHandler {

	@Override
	public void doAction(MouseEvent e) {
		JPopupMenu popupmenu = new JPopupMenu();
		JMenuItem addMenuItem = new JMenuItem(JmeterResUtils.getResString(Resources.MENU_ITEM_ADD));
		popupmenu.add(addMenuItem);
		addMenuItem.addActionListener(new DefaultJmeterDataGridRightMenuActionListener(e.getSource()));
		popupmenu.addSeparator();
		JMenuItem deleteMenuItem = new JMenuItem(JmeterResUtils.getResString(Resources.MENU_ITEM_DELETE));
		deleteMenuItem.addActionListener(new DefaultJmeterDataGridRightMenuActionListener(e.getSource()));
		popupmenu.add(deleteMenuItem);
		popupmenu.show(e.getComponent(), e.getX(), e.getY());

	}

	class DefaultJmeterDataGridRightMenuActionListener implements ActionListener {

		private JmeterDataGridRightMenuHandler jmeterDataGridRightMenuHandler;

		private Object source;

		public DefaultJmeterDataGridRightMenuActionListener(Object source) {
			this(source, null);
		}

		public DefaultJmeterDataGridRightMenuActionListener(Object source,
				JmeterDataGridRightMenuHandler jmeterDataGridRightMenuHandler) {
			this.source = source;
			if (null == jmeterDataGridRightMenuHandler) {
				this.jmeterDataGridRightMenuHandler = new DefaultJmeterDataGridRightMenuHandler();
			} else {
				this.jmeterDataGridRightMenuHandler = jmeterDataGridRightMenuHandler;
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (this.jmeterDataGridRightMenuHandler != null) {
				this.jmeterDataGridRightMenuHandler.doAction(e, this.source);
			}
		}

	}
}
