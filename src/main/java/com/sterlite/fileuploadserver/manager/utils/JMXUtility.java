package com.sterlite.fileuploadserver.manager.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.management.ObjectName;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.stereotype.Component;

import com.sterlite.fileuploadserver.manager.dto.FileuploadInstanceStatusDTO;
import com.sterlite.fileuploadserver.manager.dto.LivelinessInstanceStatusDTO;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;

@Component
public class JMXUtility {

	private final String MODULE = "[JMXUtility]";

	@Autowired
	private MBeanExporter exporter;

	private static final Logger logger = LogManager.getLogger(JMXUtility.class);

	private final JmxMeterRegistry jmxMeterRegistry = new JmxMeterRegistry(JmxConfig.DEFAULT, Clock.SYSTEM);
	private final MeterRegistry meterRegistry = new CompositeMeterRegistry(Clock.SYSTEM,
			Arrays.asList(new MeterRegistry[] { jmxMeterRegistry }));

	private final FileuploadInstanceStatusDTO fileuploadInstanceStatusDTO = new FileuploadInstanceStatusDTO(this);
	private final LivelinessInstanceStatusDTO livelinessInstanceStatusDTO = new LivelinessInstanceStatusDTO(this);

	public FileuploadInstanceStatusDTO getFileuploadInstanceStatusDTO() {
		return fileuploadInstanceStatusDTO;
	}

	public LivelinessInstanceStatusDTO getLivelinessInstanceStatusDTO() {
		return livelinessInstanceStatusDTO;
	}

	public void registerDefaultBeans() {
		try {
			getFileuploadInstanceStatusDTO().startFileuploadCounter();
			getLivelinessInstanceStatusDTO().startLivelinessCounter();

			exporter.registerManagedResource(getFileuploadInstanceStatusDTO(),
					new ObjectName(CommonConstants.JMX_PREFIX + ":type=FileuploadInfo"));
			exporter.registerManagedResource(getLivelinessInstanceStatusDTO(),
					new ObjectName(CommonConstants.JMX_PREFIX + ":type=LivelinessInfo"));
		} catch (Exception ex) {
			logger.error(MODULE
					+ "[onApplicationEvent][registerRequestCounter]Exception registering metrics for process service"
					+ " #Ex:" + ex, ex);
		}
	}

	public void unregisterDefaultBeans() {
		try {
			getFileuploadInstanceStatusDTO().stopFileuploadCounter();
			getLivelinessInstanceStatusDTO().stopLivelinessCounter();
		} catch (Exception ex) {
			logger.error(MODULE
					+ "[onApplicationEvent][registerRequestCounter]Exception registering metrics for process service"
					+ " #Ex:" + ex, ex);
		}
		try {
			getFileuploadInstanceStatusDTO().stopFileuploadCounter();
			getLivelinessInstanceStatusDTO().stopLivelinessCounter();
		} catch (Exception ex) {
			logger.error(MODULE
					+ "[onApplicationEvent][registerRequestCounter]Exception registering metrics for process service"
					+ " #Ex:" + ex, ex);
		}
		try {
			meterRegistry.close();
		} catch (Exception ex) {
			logger.error(MODULE
					+ "[onApplicationEvent][registerRequestCounter]Exception registering metrics for process service"
					+ " #Ex:" + ex, ex);
		}
	}

	public Timer getMeterTimer(String[] tagInfo) {
		return meterRegistry.timer(generateTagName(tagInfo), generateTag(tagInfo));
	}

	public Counter getMeterCounter(String[] tagInfo) {
		return meterRegistry.counter(generateTagName(tagInfo), generateTag(tagInfo));
	}

	public void removeMeter(Meter meter) {
		try {
			meter.close();
		} catch (Exception e) {
		}
		try {
			meterRegistry.remove(meter);
		} catch (Exception e) {
		}
	}

	private String[] generateTag(String[] tagInfo) {
		return new String[0];
	}

	private String generateTagName(String[] tagInfo) {
		if (tagInfo == null || tagInfo.length == 0)
			return "";
		return Arrays.<CharSequence>asList((CharSequence[]) tagInfo).stream().collect(Collectors.joining("_"));
	}

}