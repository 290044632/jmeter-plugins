package com.silence.jmeter.plugin.dubbo.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

import com.silence.jmeter.plugin.dubbo.constants.Resources;
import com.silence.jmeter.plugin.dubbo.util.JmeterResUtils;

public class JmeterDubboFrame {

	private static final String EMPTY = "";

	private JLabel samplerTitleLabel;

	private JLabel samplerNameLabel, samplerCommentLabel;

	private JTextField samplerNameText, samplerCommentText;

	private JLabel samplerRegistryProtocolNameLabel, samplerRegistryProtocolAddressLabel;

	private JTextField samplerRegistryProtocolNameText, samplerRegistryProtocolAddressText;
	
	private final EmptyBorder paddingBorder = new EmptyBorder(0, 10, 10, 10);

	private AbstractSamplerGui samplerGui;

	public JmeterDubboFrame(AbstractSamplerGui samplerGui) {
		this.samplerGui = samplerGui;
		this.init();
	}

	public void init() {
		this.samplerGui.setLayout(new BorderLayout());
		this.samplerTitleLabel = new JLabel(JmeterResUtils.getResString(Resources.SAMPLER_NAME));
		this.samplerGui.add(this.samplerTitleLabel, BorderLayout.NORTH);

		JPanel samplerBaseInfoPanel = createSamplerBaseInfoPanel();

		JPanel samplerRegistryPanel = createRegistryPanel();

		JPanel samplerProtocolPanel = createProtocolPanel();

		JPanel samplerJPanel1 = new VerticalPanel();
		samplerJPanel1.setLayout(new GridLayout(5, 1));
		samplerJPanel1.add(samplerBaseInfoPanel);
		samplerJPanel1.add(samplerRegistryPanel);
		samplerJPanel1.add(samplerProtocolPanel);

		this.samplerGui.add(samplerJPanel1, BorderLayout.CENTER);
	}

	public void refeshTestElement(final TestElement element) {
		element.setName(this.samplerNameText.getText());
		element.setComment(this.samplerCommentText.getText());
	}

	public void configureFrame(final TestElement element) {
		this.samplerNameText.setText(element.getName());
		this.samplerCommentText.setText(element.getComment());
	}

	public void clearGUI() {
		this.samplerNameText.setText(JmeterResUtils.getResString(Resources.SAMPLER_NAME));
		this.samplerCommentText.setText(EMPTY);
	}

	private JPanel createSamplerBaseInfoPanel() {
		JPanel samplerBaseInfoPanel = new HorizontalPanel();
		JPanel samplerNamePanel = new HorizontalPanel();
		this.samplerNameLabel = new JLabel(JmeterResUtils.getResString(Resources.NAME));
		this.samplerNameText = new JTextField(JmeterResUtils.getResString(Resources.SAMPLER_NAME));
		samplerNamePanel.add(this.samplerNameLabel, BorderLayout.WEST);
		samplerNamePanel.add(this.samplerNameText, BorderLayout.CENTER);
		samplerNamePanel.setBorder(paddingBorder);

		JPanel samplerCommentPanel = new HorizontalPanel();
		this.samplerCommentLabel = new JLabel(JmeterResUtils.getResString(Resources.COMMENT));
		this.samplerCommentText = new JTextField();
		samplerCommentPanel.add(this.samplerCommentLabel, BorderLayout.WEST);
		samplerCommentPanel.add(this.samplerCommentText, BorderLayout.CENTER);
		samplerCommentPanel.setBorder(paddingBorder);

		samplerBaseInfoPanel.add(samplerNamePanel, BorderLayout.NORTH);
		samplerBaseInfoPanel.add(samplerCommentPanel, BorderLayout.CENTER);
		return samplerBaseInfoPanel;
	}

	private JPanel createRegistryPanel() {
		JPanel samplerRegistryPanel = new HorizontalPanel();
		samplerRegistryPanel.setBorder(
				BorderFactory.createTitledBorder(JmeterResUtils.getResString(Resources.REGISTRY_PROTOCOL_SETTINGS)));

		JPanel samplerRegistryProtocolPanel = new HorizontalPanel();
		this.samplerRegistryProtocolNameLabel = new JLabel(
				JmeterResUtils.getResString(Resources.REGISTRY_PROTOCOL_NAME));
		this.samplerRegistryProtocolNameText = new JTextField();
		samplerRegistryProtocolPanel.add(this.samplerRegistryProtocolNameLabel, BorderLayout.WEST);
		samplerRegistryProtocolPanel.add(this.samplerRegistryProtocolNameText, BorderLayout.CENTER);
		samplerRegistryProtocolPanel.setBorder(paddingBorder);

		JPanel samplerRegistryAddressPanel = new HorizontalPanel();
		this.samplerRegistryProtocolAddressLabel = new JLabel(
				JmeterResUtils.getResString(Resources.REGISTRY_PROTOCOL_ADDRESS));
		this.samplerRegistryProtocolAddressText = new JTextField();
		samplerRegistryAddressPanel.add(this.samplerRegistryProtocolAddressLabel, BorderLayout.WEST);
		samplerRegistryAddressPanel.add(this.samplerRegistryProtocolAddressText, BorderLayout.CENTER);
		samplerRegistryAddressPanel.setBorder(paddingBorder);

		samplerRegistryPanel.add(samplerRegistryProtocolPanel, BorderLayout.NORTH);
		samplerRegistryPanel.add(samplerRegistryAddressPanel, BorderLayout.CENTER);
		return samplerRegistryPanel;
	}

	private JPanel createProtocolPanel() {
		JPanel samplerProtocolPanel = new HorizontalPanel();
		samplerProtocolPanel
				.setBorder(BorderFactory.createTitledBorder(JmeterResUtils.getResString(Resources.PROTOCOL_SETTINGS)));

		Object[] columnNames = new Object[] { JmeterResUtils.getResString(Resources.PARAM_NAME),
				JmeterResUtils.getResString(Resources.PARAM_VALUE) };
		Object[][] rowDatas = new Object[][] { { "name", "dubbo" }, { "port", "2181" } };
		DefaultTableModel defaultTableModel = new DefaultTableModel(rowDatas, columnNames);
		JTable table = new JTable(defaultTableModel);
		table.addMouseListener(new JmeterDataGridRightClickEventListener());
		samplerProtocolPanel.add(table.getTableHeader(), BorderLayout.NORTH);
		samplerProtocolPanel.add(table, BorderLayout.CENTER);
		return samplerProtocolPanel;
	}
}
