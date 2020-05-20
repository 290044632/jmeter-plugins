package com.silence.jmeter.plugin.dubbo.gui;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

import com.silence.jmeter.plugin.dubbo.constants.Resources;
import com.silence.jmeter.plugin.dubbo.sampler.JmeterDubboSampler;
import com.silence.jmeter.plugin.dubbo.util.JmeterResUtils;

public class JmeterDubboGui extends AbstractSamplerGui {

	private static final long serialVersionUID = 1L;

	private JmeterDubboFrame jmeterDubboFrame;

	public JmeterDubboGui() {
		this.jmeterDubboFrame = new JmeterDubboFrame(this);
	}

	@Override
	public String getLabelResource() {
		return null;
	}

	@Override
	public String getStaticLabel() {
		return JmeterResUtils.getResString(Resources.SAMPLER_NAME);
	}

	@Override
	public TestElement createTestElement() {
		JmeterDubboSampler sampler = new JmeterDubboSampler();
		this.modifyTestElement(sampler);
		return sampler;
	}

	@Override
	public void modifyTestElement(TestElement element) {
		element.clear();
		element.setProperty(TestElement.GUI_CLASS, this.getClass().getName());
		this.jmeterDubboFrame.refeshTestElement(element);
	}

	@Override
	public void configure(TestElement element) {
		super.configure(element);
		this.jmeterDubboFrame.configureFrame(element);
	}

	@Override
	public void clearGui() {
		super.clearGui();
		this.jmeterDubboFrame.clearGUI();
	}

}
