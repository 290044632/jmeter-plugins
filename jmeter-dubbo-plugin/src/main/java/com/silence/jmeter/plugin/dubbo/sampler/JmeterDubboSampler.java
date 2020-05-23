package com.silence.jmeter.plugin.dubbo.sampler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.silence.jmeter.plugin.dubbo.constants.ConfigReference;
import com.silence.jmeter.plugin.dubbo.exception.ConfigNullException;
import com.silence.jmeter.plugin.dubbo.model.JmeterDubboInterfaceModel;
import com.silence.jmeter.plugin.dubbo.util.JmeterJSONUtils;
import com.silence.jmeter.plugin.dubbo.util.StringUtils;

public class JmeterDubboSampler extends AbstractSampler implements ConfigReference {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(JmeterDubboSampler.class);

	@Override
	public SampleResult sample(Entry e) {

		SampleResult result = new SampleResult();
		result.setSampleLabel(this.getName());
		try {
			// 注册中心配置
			RegistryConfig registryConfig = this.getRegistryConfig();
			// 协议配置
			// ProtocolConfig protocolConfig = this.getProtocolConfig();
			// 消费者配置
			ConsumerConfig consumerConfig = this.getConsumerConfig();

			JmeterDubboInterfaceModel interfaceModel = getInterfaceModel();
			ReferenceConfig<?> referenceConfig = new ReferenceConfig<>();
			referenceConfig.setRegistry(registryConfig);
			referenceConfig.setConsumer(consumerConfig);
			referenceConfig.setInterface(interfaceModel.getClassName());
			ReferenceConfigCache cache = ReferenceConfigCache.getCache();
			GenericService genericService = (GenericService) cache.get(referenceConfig);
			if (null == genericService) {
				result.setResponseData(
						"未从注册中心" + registryConfig.toString() + "找到接口：" + referenceConfig.toString() + "提供者", UTF_8);
				return result;
			}
			Map<String, Object> argsConfig = interfaceModel.getArgs();
			String[] parameterTypes = null;
			Object[] args = null;
			if (null != argsConfig) {
				logger.info("参数列表：{}", JmeterJSONUtils.toJSONString(argsConfig));
				int size = argsConfig.size();
				List<String> parameterTypeList = new ArrayList<>();
				List<Object> argsList = new ArrayList<>();
				argsConfig.forEach((key, value) -> {
					if (StringUtils.isNotBlank(key)) {
						parameterTypeList.add(key);
						argsList.add(value);
					}
				});
				if (!parameterTypeList.isEmpty()) {
					parameterTypes = new String[size];
					args = new Object[size];
					parameterTypeList.toArray(parameterTypes);
					argsList.toArray(args);
				}
			} else {
				parameterTypes = new String[] {};
				args = new Object[] {};
			}
			Object $invoke = genericService.$invoke(interfaceModel.getMethod(), parameterTypes, args);
			result.setResponseData(JmeterJSONUtils.toJSONString($invoke), UTF_8);
			result.setResponseOK();
		} catch (Exception ex) {
			logger.error("执行异常", ex);
			String message = ex.getMessage();
			result.setResponseData(message, UTF_8);
		}
		return result;
	}

	private RegistryConfig getRegistryConfig() throws ConfigNullException {
		return this.getConfig(REGISTRY_CONFIG, RegistryConfig.class, "请检查注册中心配置");
	}

	@Deprecated
	private ProtocolConfig getProtocolConfig() throws ConfigNullException {
		return this.getConfig(PROTOCOL_CONFIG, ProtocolConfig.class, "请检查协议配置");
	}

	private ConsumerConfig getConsumerConfig() throws ConfigNullException {
		return this.getConfig(CONSUMER_CONFIG, ConsumerConfig.class, "请检查消费者配置");
	}

	private JmeterDubboInterfaceModel getInterfaceModel() throws ConfigNullException {
		String interfaceConfig = this.getPropertyAsString(INTERFACE_CONFIG);
		JmeterDubboInterfaceModel interfaceModel = null;
		if (StringUtils.isNotBlank(interfaceConfig)) {
			interfaceModel = JmeterJSONUtils.toObject(interfaceConfig, JmeterDubboInterfaceModel.class);
			if (null != interfaceModel) {
				String className = interfaceModel.getClassName();
				String method = interfaceModel.getMethod();
				if (StringUtils.isAnyBlank(className, method)) {
					interfaceModel = null;
				}
			}
		}
		if (null == interfaceModel) {
			throw new ConfigNullException("请检查接口配置：" + interfaceConfig);
		}
		return interfaceModel;
	}

	private <T> T getConfig(String key, Class<T> clazz, String errorMsg) throws ConfigNullException {
		T config = null;
		String conifgValue = this.getPropertyAsString(key);
		if (StringUtils.isNotBlank(conifgValue)) {
			config = JmeterJSONUtils.toObject(conifgValue, clazz);
		}
		if (null == config) {
			throw new ConfigNullException(errorMsg + "：" + conifgValue);
		}
		return config;
	}

}
