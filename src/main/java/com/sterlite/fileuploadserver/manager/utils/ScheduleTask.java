package com.sterlite.fileuploadserver.manager.utils;

import java.util.Date;
import java.util.TimerTask;

public class ScheduleTask extends TimerTask{

	Date now;
	@Override
	public void run() {
		
		now=new Date();
		System.out.println(now);
	}

	
}
