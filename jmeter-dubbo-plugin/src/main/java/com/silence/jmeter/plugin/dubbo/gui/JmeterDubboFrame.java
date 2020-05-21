package com.silence.jmeter.plugin.dubbo.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

	private static final int HEIGHT = 100;
	
	private static final int ARG_HEIGHT =150;

	private static final String EMPTY = "";

	private JLabel samplerTitleLabel;

	private JLabel samplerNameLabel, samplerCommentLabel;

	private JTextField samplerNameText, samplerCommentText;

	private JLabel samplerRegistryProtocolNameLabel, samplerRegistryProtocolAddressLabel;

	private JTextField samplerRegistryProtocolNameText, samplerRegistryProtocolAddressText;

	private final EmptyBorder paddingBorder = new EmptyBorder(0, 10, 10, 10);

	private JTable protocolTable, consumerTable, interfaceTable;

	private JLabel interfaceClassLabel, interfaceMethodLabel, interfaceArgLabel;

	private JTextField interfaceClassText, interfaceMethodText;

	private AbstractSamplerGui samplerGui;

	public JmeterDubboFrame(AbstractSamplerGui samplerGui) {
		this.samplerGui = samplerGui;
		this.init();
	}

	public void init() {
		this.samplerGui.setLayout(new BorderLayout());
		this.samplerTitleLabel = new JLabel(JmeterResUtils.getResString(Resources.SAMPLER_NAME));
		this.samplerTitleLabel.setFont(new Font("宋体", Font.BOLD, 23));
		this.samplerGui.add(this.samplerTitleLabel, BorderLayout.NORTH);

		JPanel samplerBaseInfoPanel = createSamplerBaseInfoPanel();

		JPanel samplerRegistryPanel = createRegistryPanel();

		JPanel samplerProtocolPanel = createProtocolPanel();

		JPanel consumerPanel = createConsumerPanel();

		JPanel interfacePanel = createInterfacePanel();

		JPanel samplerControlJPanel = new VerticalPanel();
		//samplerControlJPanel.setLayout(new GridLayout(5, 1));
		samplerControlJPanel.add(samplerBaseInfoPanel);
		samplerControlJPanel.add(samplerRegistryPanel);
		samplerControlJPanel.add(samplerProtocolPanel);
		samplerControlJPanel.add(consumerPanel);
		samplerControlJPanel.add(interfacePanel);
		
		this.samplerGui.add(samplerControlJPanel, BorderLayout.CENTER);
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
		JPanel samplerProtocolPanel = new VerticalPanel();
		samplerProtocolPanel
				.setBorder(BorderFactory.createTitledBorder(JmeterResUtils.getResString(Resources.PROTOCOL_SETTINGS)));

		Object[] columnNames = new Object[] { JmeterResUtils.getResString(Resources.PARAM_NAME),
				JmeterResUtils.getResString(Resources.PARAM_VALUE) };
		Object[][] rowDatas = new Object[][] { { "name", "dubbo" }, { "port", "2181" } };
		DefaultTableModel defaultTableModel = new DefaultTableModel(rowDatas, columnNames);
		protocolTable = new JTable(defaultTableModel);
		protocolTable.addMouseListener(new JmeterDataGridRightClickEventListener());
		samplerProtocolPanel.add(protocolTable.getTableHeader(), BorderLayout.NORTH);
		JScrollPane jScrollPane = new JScrollPane(protocolTable);
		jScrollPane.setPreferredSize(new Dimension(samplerProtocolPanel.getWidth(), HEIGHT));
		samplerProtocolPanel.add(jScrollPane, BorderLayout.CENTER);
		return samplerProtocolPanel;
	}

	private JPanel createConsumerPanel() {
		JPanel samplerConsumerPanel = new HorizontalPanel();
		samplerConsumerPanel
				.setBorder(BorderFactory.createTitledBorder(JmeterResUtils.getResString(Resources.CONSUMER_SETTINGS)));

		Object[] columnNames = new Object[] { JmeterResUtils.getResString(Resources.PARAM_NAME),
				JmeterResUtils.getResString(Resources.PARAM_VALUE) };
		Object[][] rowDatas = new Object[][] { {} };
		DefaultTableModel defaultTableModel = new DefaultTableModel(rowDatas, columnNames);
		consumerTable = new JTable(defaultTableModel);
		consumerTable.addMouseListener(new JmeterDataGridRightClickEventListener());
		samplerConsumerPanel.add(consumerTable.getTableHeader(), BorderLayout.NORTH);
		JScrollPane jScrollPane = new JScrollPane(consumerTable);
		jScrollPane.setPreferredSize(new Dimension(samplerConsumerPanel.getWidth(), HEIGHT));
		samplerConsumerPanel.add(jScrollPane, BorderLayout.CENTER);
		return samplerConsumerPanel;
	}

	private JPanel createInterfacePanel() {
		JPanel samplerInterfacePanel = new VerticalPanel();
		samplerInterfacePanel
				.setBorder(BorderFactory.createTitledBorder(JmeterResUtils.getResString(Resources.INTERFACE_SETTINGS)));

		JPanel interfaceClassPanel = new HorizontalPanel();
		this.interfaceClassLabel = new JLabel(JmeterResUtils.getResString(Resources.INTERFACE_CLASS));
		this.interfaceClassText = new JTextField();
		interfaceClassPanel.setBorder(paddingBorder);
		interfaceClassPanel.add(this.interfaceClassLabel, BorderLayout.WEST);
		interfaceClassPanel.add(this.interfaceClassText, BorderLayout.CENTER);

		JPanel interfaceMethodPanel = new HorizontalPanel();
		this.interfaceMethodLabel = new JLabel(JmeterResUtils.getResString(Resources.INTERFACE_METHOD));
		this.interfaceMethodText = new JTextField();
		interfaceMethodPanel.setBorder(paddingBorder);
		interfaceMethodPanel.add(this.interfaceMethodLabel, BorderLayout.WEST);
		interfaceMethodPanel.add(this.interfaceMethodText, BorderLayout.CENTER);

		JPanel interfaceArgPanel = new HorizontalPanel();
		this.interfaceArgLabel = new JLabel(JmeterResUtils.getResString(Resources.INTERFACE_ARG));
		
		JPanel argPanel = new VerticalPanel();
		Object[] columnNames = new Object[] { JmeterResUtils.getResString(Resources.PARAM_TYPE),
				JmeterResUtils.getResString(Resources.PARAM_VALUE) };
		Object[][] rowDatas = new Object[][] { {} };
		DefaultTableModel defaultTableModel = new DefaultTableModel(rowDatas, columnNames);
		interfaceTable = new JTable(defaultTableModel);
		interfaceTable.addMouseListener(new JmeterDataGridRightClickEventListener());
		argPanel.add(interfaceTable.getTableHeader(), BorderLayout.NORTH);
		JScrollPane jScrollPane = new JScrollPane(interfaceTable);
		jScrollPane.setPreferredSize(new Dimension(argPanel.getWidth(), ARG_HEIGHT));
		argPanel.add(jScrollPane, BorderLayout.CENTER);
		
		interfaceArgPanel.setBorder(paddingBorder);
		interfaceArgPanel.add(this.interfaceArgLabel, BorderLayout.WEST);
		interfaceArgPanel.add(argPanel, BorderLayout.CENTER);
		

		samplerInterfacePanel.add(interfaceClassPanel);
		samplerInterfacePanel.add(interfaceMethodPanel);
		samplerInterfacePanel.add(interfaceArgPanel);
		return samplerInterfacePanel;
	}
}
