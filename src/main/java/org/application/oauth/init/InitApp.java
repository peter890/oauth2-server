package org.application.oauth.init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InitApp {
	
	public static  ApplicationContext getAppContext(){
		ApplicationContext context = null;
		if (context == null) {
			context = new ClassPathXmlApplicationContext("config/ApplicationContext.xml");
		}
		return context;
	}
	static{
		InitApp.getAppContext();
	}

}
