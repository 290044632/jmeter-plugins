package com.silence.jmeter.plugin.dubbo.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.silence.jmeter.plugin.dubbo.constants.Resources;
import com.silence.jmeter.plugin.dubbo.model.JmeterDubboInterfaceModel;
import com.silence.jmeter.plugin.dubbo.util.JmeterJSONUtils;
import com.silence.jmeter.plugin.dubbo.util.JmeterResUtils;

public class JmeterDubboFrame {

	private static Logger logger = LoggerFactory.getLogger(JmeterDubboFrame.class);

	private static final Object[][] DEFAULT_PROTOCOL_CONFIG = new Object[][] { { "name", "dubbo" },
			{ "port", "3603" } };

	private static final Object[][] DEFAULT_REGISTRY_CONFIG = new Object[][] { { "protocol", "zookeeper" },
			{ "address", "127.0.0.1:2181" } };

	private static Object[] columnNames1;
	private static Object[] columnNames2;

	private static final String INTERFACE_CONFIG = "interfaceConfig";

	private static final String CONSUMER_CONFIG = "consumerConfig";

	private static final String PROTOCOL_CONFIG = "protocolConfig";

	private static final String REGISTRY_CONFIG = "registryConfig";

	private static final int HEIGHT = 100;

	private static final int ARG_HEIGHT = 150;

	private JLabel samplerTitleLabel;

	private JLabel samplerNameLabel, samplerCommentLabel;

	private JTextField samplerNameText, samplerCommentText;

	private final EmptyBorder paddingBorder = new EmptyBorder(0, 10, 10, 10);

	private JTable protocolTable, consumerTable, interfaceTable, registryTable;

	private JLabel interfaceClassLabel, interfaceMethodLabel, interfaceArgLabel;

	private JTextField interfaceClassText, interfaceMethodText;

	private AbstractSamplerGui samplerGui;

	static {
		columnNames1 = new Object[] { JmeterResUtils.getResString(Resources.PARAM_NAME),
				JmeterResUtils.getResString(Resources.PARAM_VALUE) };
		columnNames2 = new Object[] { JmeterResUtils.getResString(Resources.PARAM_TYPE),
				JmeterResUtils.getResString(Resources.PARAM_VALUE) };
	}

	public JmeterDubboFrame(AbstractSamplerGui samplerGui) {
		this.samplerGui = samplerGui;
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
		element.setProperty(REGISTRY_CONFIG, getConfigString(this.registryTable));
		element.setProperty(PROTOCOL_CONFIG, getConfigString(this.protocolTable));
		element.setProperty(CONSUMER_CONFIG, getConfigString(this.consumerTable));
		element.setProperty(INTERFACE_CONFIG, getInterfaceConfigString(this.interfaceTable));
	}

	public void configureFrame(final TestElement element) {
		this.samplerNameText.setText(element.getName());
		this.samplerCommentText.setText(element.getComment());

		configComponent(this.registryTable, element, REGISTRY_CONFIG, DEFAULT_REGISTRY_CONFIG, columnNames1);
		configComponent(this.protocolTable, element, PROTOCOL_CONFIG, DEFAULT_PROTOCOL_CONFIG, columnNames1);
		configComponent(this.consumerTable, element, CONSUMER_CONFIG, null, columnNames1);
		configInterfaceComponent(this.interfaceTable, element, INTERFACE_CONFIG, null, columnNames2);
	}

	public void clearGUI() {
		this.init();
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

		Object[] columnNames = new Object[] { JmeterResUtils.getResString(Resources.PARAM_NAME),
				JmeterResUtils.getResString(Resources.PARAM_VALUE) };
		Object[][] rowDatas = new Object[][] {};
		DefaultTableModel defaultTableModel = new DefaultTableModel(rowDatas, columnNames);
		registryTable = new JTable(defaultTableModel);
		registryTable.addMouseListener(new JmeterDataGridRightClickEventListener());
		samplerRegistryPanel.add(registryTable.getTableHeader(), BorderLayout.NORTH);
		JScrollPane jScrollPane = new JScrollPane(registryTable);
		jScrollPane.setPreferredSize(new Dimension(samplerRegistryPanel.getWidth(), HEIGHT));
		samplerRegistryPanel.add(jScrollPane, BorderLayout.CENTER);
		return samplerRegistryPanel;
	}

	private JPanel createProtocolPanel() {
		JPanel samplerProtocolPanel = new VerticalPanel();
		samplerProtocolPanel
				.setBorder(BorderFactory.createTitledBorder(JmeterResUtils.getResString(Resources.PROTOCOL_SETTINGS)));

		Object[] columnNames = new Object[] { JmeterResUtils.getResString(Resources.PARAM_NAME),
				JmeterResUtils.getResString(Resources.PARAM_VALUE) };
		Object[][] rowDatas = new Object[][] { {} };
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

	public String getInterfaceConfigString(final JTable table) {
		Map<Object, Object> args = this.getConfig(table);
		String className = this.interfaceClassText.getText();
		String method = this.interfaceMethodText.getText();
		return JmeterJSONUtils.toJSONString(new JmeterDubboInterfaceModel(className, method, args));
	}

	private String getConfigString(final JTable table) {
		return JmeterJSONUtils.toJSONString(this.getConfig(table));
	}

	@SuppressWarnings("unchecked")
	private Map<Object, Object> getConfig(final JTable table) {
		DefaultTableModel registryTableModel = (DefaultTableModel) table.getModel();
		Map<Object, Object> registryConfig = new HashMap<>();
		registryTableModel.getDataVector().forEach(data -> {
			if (null != data) {
				Vector<Object> v = (Vector<Object>) data;
				registryConfig.put(v.get(0), v.get(1));
			}
		});
		return registryConfig;
	}

	private void configInterfaceComponent(final JTable table, final TestElement element, final String key,
			Object[][] defaultValues, Object[] columnNames) {
		String configVaule = element.getPropertyAsString(key);
		if (null != configVaule && !configVaule.isEmpty()) {
			JmeterDubboInterfaceModel interfaceModel = JmeterJSONUtils.toObject(configVaule,
					JmeterDubboInterfaceModel.class);
			if (null != interfaceModel) {
				String className = interfaceModel.getClassName();
				if (null != className) {
					this.interfaceClassText.setText(className);
				}
				String method = interfaceModel.getMethod();
				if (null != method) {
					this.interfaceMethodText.setText(method);
				}
				this.configJTableComponent(interfaceModel.getArgs(), table, defaultValues, columnNames);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void configComponent(final JTable table, final TestElement element, final String key,
			Object[][] defaultValues, Object[] columnNames) {
		String configValue = element.getPropertyAsString(key);
		if (null != configValue && !configValue.isEmpty()) {
			Map map = JmeterJSONUtils.toObject(configValue, Map.class);
			if (null != map && !map.isEmpty()) {
				configJTableComponent(map, table, defaultValues, columnNames);
			}
		}
	}

	private void configJTableComponent(Map<Object, Object> configMap, final JTable table, Object[][] defaultValues,
			Object[] columnNames) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		Object[][] dataVector = null;
		if (null != configMap && !configMap.isEmpty()) {
			List<Object[]> list = new ArrayList<>();
			configMap.forEach((_key, _value) -> {
				list.add(new Object[] { _key, _value });
			});
			if (!list.isEmpty()) {
				dataVector = new Object[list.size()][2];
				for (int i = 0; i < list.size(); i++) {
					dataVector[i] = list.get(i);
				}
			}
		} else {
			if (null != defaultValues) {
				dataVector = defaultValues;
			}
		}
		if (null == dataVector) {
			dataVector = new Object[][] { {} };
		}
		tableModel.setDataVector(dataVector, columnNames);
	}
}
