package cn.wind.com.reader.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScheduleMain {
	private static final Logger logger = LoggerFactory.getLogger(ScheduleMain.class);
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("scheduleConfig.xml");
	}

}
