package com.sterlite.fileuploadserver.manager.dto;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.sterlite.fileuploadserver.manager.utils.JMXUtility;

@ManagedResource
public class FileuploadInstanceStatusDTO {
	
	private AtomicLong fileupload_Success_Counter = new AtomicLong(0);
	private AtomicLong fileupload_Failure_Counter = new AtomicLong(0);
	private AtomicLong fileupload_Malformed_Counter = new AtomicLong(0);

	private io.micrometer.core.instrument.Timer fileupload_Success_Timer;
	private io.micrometer.core.instrument.Timer fileupload_Failure_Timer;
	private io.micrometer.core.instrument.Timer fileupload_Malformed_Timer;

	private JMXUtility jmxUtility;

	public FileuploadInstanceStatusDTO(JMXUtility jmxUtility) {
		this.jmxUtility = jmxUtility;
	}

	public void startFileuploadCounter() {
		fileupload_Success_Timer = jmxUtility.getMeterTimer(new String[] { "fileupload", "result", "success" });
		fileupload_Failure_Timer = jmxUtility.getMeterTimer(new String[] { "fileupload", "result", "fail" });
		fileupload_Malformed_Timer = jmxUtility.getMeterTimer(new String[] { "fileupload", "result", "malformed" });
	}

	public void stopFileuploadCounter() {
		jmxUtility.removeMeter(fileupload_Success_Timer);
		jmxUtility.removeMeter(fileupload_Failure_Timer);
		jmxUtility.removeMeter(fileupload_Malformed_Timer);
	}

	@ManagedAttribute(description = "Fileupload Success counters")
	public long getFileupload_SuccessCounter() {
		return fileupload_Success_Counter.get();
	}

	@ManagedAttribute(description = "Fileupload Failure counters")
	public long getFileupload_FailureCounter() {
		return fileupload_Failure_Counter.get();
	}

	@ManagedAttribute(description = "Fileupload Response counters")
	public long getFileupload_ResponseCounter() {
		return fileupload_Failure_Counter.get() + fileupload_Success_Counter.get() + fileupload_Malformed_Counter.get();
	}

	@ManagedAttribute(description = "Fileupload Toatal Request counters")
	public long getFileupload_TotalRequestsCounter() {
		return fileupload_Failure_Counter.get() + fileupload_Success_Counter.get() + fileupload_Malformed_Counter.get();
	}
	
	@ManagedAttribute(description = "Fileupload Malformed requests")
	public long getFileupload_MalformedInputCounter() {
		return fileupload_Malformed_Counter.get();
	}

	public void fileuploadSuccessRequest(long startTime) {
		try {
			fileupload_Success_Counter.incrementAndGet();
			fileupload_Success_Timer.record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
		} catch (Exception e) {
		}
	}

	public void fileuploadFailureRequest(long startTime) {
		try {
			fileupload_Failure_Counter.incrementAndGet();
			fileupload_Failure_Timer.record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
		} catch (Exception e) {
		}
	}
	
	public void fileuploadMalformedInputRequest(long startTime) {
		try {
			fileupload_Malformed_Counter.incrementAndGet();
			fileupload_Malformed_Timer.record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
		} catch (Exception e) {
		}
	}
}