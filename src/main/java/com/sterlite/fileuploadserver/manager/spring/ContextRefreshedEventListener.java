package com.sterlite.fileuploadserver.manager.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.sterlite.fileuploadserver.manager.utils.JMXUtility;

@Component("contextRefreshedEventListener")
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private JMXUtility jmxUtility;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		jmxUtility.registerDefaultBeans();
	}

}
