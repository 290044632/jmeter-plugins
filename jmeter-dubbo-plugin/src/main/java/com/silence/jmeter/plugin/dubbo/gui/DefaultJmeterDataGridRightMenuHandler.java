package com.silence.jmeter.plugin.dubbo.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultJmeterDataGridRightMenuHandler implements JmeterDataGridRightMenuHandler {

	private static Logger logger = LoggerFactory.getLogger(DefaultJmeterDataGridRightMenuHandler.class);

	@Override
	public void doAction(ActionEvent e, Object source) {
		Object source2 = e.getSource();
		if (source2 instanceof JMenuItem) {
			Container menu = ((JMenuItem) source2).getParent();
			Component addMenuItem = menu.getComponent(0);
			JTable table = ((JTable) source);
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			if (source2 == addMenuItem) {
				model.addRow(new Object[] {});
			} else if (source2 == menu.getComponent(2)) {
				int row = table.getSelectedRow();
				int rowCount = table.getRowCount();
				if (row != -1 && rowCount > 1) {
					model.removeRow(row);
				}
				logger.info("totalCount:{}， row:{}", rowCount, row);
			}
		}
	}

}
