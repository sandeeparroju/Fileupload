package com.sterlite.fileuploadserver.manager.dto;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.sterlite.fileuploadserver.manager.utils.JMXUtility;

@ManagedResource
public class LivelinessInstanceStatusDTO {

	private AtomicLong liveliness_Success_Counter = new AtomicLong(0);
	private AtomicLong liveliness_Failure_Counter = new AtomicLong(0);

	private io.micrometer.core.instrument.Timer liveliness_Success_Timer;
	private io.micrometer.core.instrument.Timer liveliness_Failure_Timer;

	private JMXUtility jmxUtility;

	public LivelinessInstanceStatusDTO(JMXUtility jmxUtility) {
		this.jmxUtility = jmxUtility;
	}

	public void startLivelinessCounter() {
		liveliness_Success_Timer = jmxUtility.getMeterTimer(new String[] { "liveness", "result", "success" });
		liveliness_Failure_Timer = jmxUtility.getMeterTimer(new String[] { "liveness", "result", "fail" });
	}

	public void stopLivelinessCounter() {
		jmxUtility.removeMeter(liveliness_Success_Timer);
		jmxUtility.removeMeter(liveliness_Failure_Timer);
	}

	@ManagedAttribute(description = "Liveliness Success counters")
	public long getLiveliness_SuccessCounter() {
		return liveliness_Success_Counter.get();
	}

	@ManagedAttribute(description = "Liveliness Fialure counters")
	public long getLiveliness_FailureCounter() {
		return liveliness_Failure_Counter.get();
	}

	@ManagedAttribute(description = "Liveliness response counters")
	public long getLiveliness_ResponseCounter() {
		return liveliness_Success_Counter.get() + liveliness_Failure_Counter.get();
	}

	@ManagedAttribute(description = "Liveliness Total Request counters")
	public long getLiveliness_TotalRequestsCounters() {
		return liveliness_Success_Counter.get() + liveliness_Failure_Counter.get();
	}
	
	public void livelinessSuccessRequest(long startTime) {
		try {
			liveliness_Success_Counter.incrementAndGet();
			liveliness_Success_Timer.record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
		} catch (Exception e) {
		}
	}

	public void livelinessFailedRequest(long startTime) {
		try {
			liveliness_Failure_Counter.incrementAndGet();
			liveliness_Failure_Timer.record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
		} catch (Exception e) {
		}
	}

}
