package com.sterlite.fileuploadserver.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.sterlite.fileuploadserver.manager.utils.JMXUtility;
 

@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
public class FileuploadServerApplication {
	
	@Autowired
	private JMXUtility jmxUtility;
	
	public static void main(String[] args) {
		SpringApplication.run(FileuploadServerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void addShutDownHook() {
		Thread unregisterBeansHook = new Thread(() -> jmxUtility.unregisterDefaultBeans());
		Runtime.getRuntime().addShutdownHook(unregisterBeansHook);
	}
}