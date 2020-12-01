package com.sterlite.fileuploadserver.manager.controller.customer;

import java.io.File;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sterlite.fileuploadserver.manager.exception.FileMissingException;
import com.sterlite.fileuploadserver.manager.exception.UserIDMissingException;
import com.sterlite.fileuploadserver.manager.model.FileInfo;
import com.sterlite.fileuploadserver.manager.utils.CommonConstants;
import com.sterlite.fileuploadserver.manager.utils.JMXUtility;

@RestController
public class CustomerRestController {

	private static final Logger logger = LogManager.getLogger(CustomerRestController.class);

	@Autowired
	private JMXUtility jmxUtility;

	@Value("${file.upload.location}")
	private String uploadLocation;

	@RequestMapping(value = "/fileupload", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public ResponseEntity fileupload(@RequestParam(value = "file", required = false) MultipartFile inputFile,
			@RequestParam(required = false) String userid) throws UserIDMissingException, FileMissingException {
		logger.info(" method startedfileupload");

		long startTime = new Date().getTime();
		FileInfo fileInfo = new FileInfo();
		HttpHeaders headers = new HttpHeaders();
		
		if (inputFile == null) {
			logger.error(" No file selected hence throwing error");
			throw new FileMissingException(CommonConstants.ZIP_FILE_MISSED);
		}
		
		if (userid == null || userid.isEmpty()) {
			logger.error(" No userid provided hence throwing error");
			throw new UserIDMissingException(CommonConstants.USERID_MISSED);
		}
		
		String originalFilename = inputFile.getOriginalFilename();
		if (!inputFile.isEmpty() && originalFilename.endsWith(CommonConstants.FILE_EXTENSION)) {
			try {
				File destinationFile = new File(uploadLocation + File.separator + originalFilename);
				inputFile.transferTo(destinationFile);
				fileInfo.setFileName(destinationFile.getPath());
				fileInfo.setFileSize(inputFile.getSize());
				headers.add(CommonConstants.FILEUPLOAD_SUCCESS, originalFilename);

				jmxUtility.getFileuploadInstanceStatusDTO().fileuploadSuccessRequest(startTime);
				logger.info("File uploaded Successfully");
				return new ResponseEntity<FileInfo>(fileInfo, headers, HttpStatus.OK);
			} catch (Exception e) {
				jmxUtility.getFileuploadInstanceStatusDTO().fileuploadFailureRequest(startTime);
				logger.error("fileupload method ends with Internal Server Error");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			jmxUtility.getFileuploadInstanceStatusDTO().fileuploadMalformedInputRequest(startTime);
			logger.error("fileupload method ends with NOT_ACCEPTABLE");
			return new ResponseEntity<>("You have chosen non zip file",HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
