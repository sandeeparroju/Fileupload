package com.sterlite.fileuploadserver.manager.controller.common;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.sterlite.fileuploadserver.manager.exception.FileMissingException;
import com.sterlite.fileuploadserver.manager.exception.UserIDMissingException;
import com.sterlite.fileuploadserver.manager.utils.CommonConstants;
import com.sterlite.fileuploadserver.manager.utils.JMXUtility;

//@Controller
@ControllerAdvice
public class CustomErrorController implements ErrorController {

	private static final Logger logger = LogManager.getLogger(CustomErrorController.class);

	@Autowired
	private JMXUtility jmxUtility;

	/*
	 * @RequestMapping("/error") public @ResponseBody String
	 * handleError(HttpServletRequest request) {
	 * 
	 * Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE); if
	 * (status != null) { Integer statusCode = Integer.parseInt(status.toString());
	 * if (statusCode == 403) { return "error-403"; } }
	 * 
	 * return "error"; }
	 */

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> handleErrors(Exception e, WebRequest request) {
		String uri = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
		long startTime = new Date().getTime();
		if (uri.contains("fileupload")) {
			jmxUtility.getFileuploadInstanceStatusDTO().fileuploadFailureRequest(startTime);
		} else {
			jmxUtility.getLivelinessInstanceStatusDTO().livelinessFailedRequest(startTime);
		}

		logger.error(e.getMessage());

		if (e.getClass().equals(HttpRequestMethodNotSupportedException.class)) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}

		if (e.getClass().equals(UserIDMissingException.class)) {
			return new ResponseEntity<>(CommonConstants.USERID_MISSED, HttpStatus.BAD_REQUEST);
		}
		if (e.getClass().equals(FileMissingException.class)) {
			return new ResponseEntity<>(CommonConstants.ZIP_FILE_MISSED, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
