package com.sterlite.fileuploadserver.manager.controller.liveliness;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sterlite.fileuploadserver.manager.utils.JMXUtility;

@RestController
public class LivelinessController {

	private static final Logger logger = LogManager.getLogger(LivelinessController.class);

	@Autowired
	private JMXUtility jmxUtility;

	@PostMapping(value = "/liveliness")
	public ResponseEntity<Object> livelinessProbe() {

		long startTime = new Date().getTime();
		logger.info("start LivelinessProbe method");
		try {
			jmxUtility.getLivelinessInstanceStatusDTO().livelinessSuccessRequest(startTime);
			logger.info("end LivelinessProbe method");
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			jmxUtility.getLivelinessInstanceStatusDTO().livelinessFailedRequest(startTime);
			logger.error("end LivelinessProbe method");
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
