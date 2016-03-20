package org.application.oauth.init;

import javax.inject.Singleton;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Singleton
public class InitApp {

	public static ApplicationContext getAppContext() {
		ApplicationContext context = null;
		if (context == null) {
			context = new ClassPathXmlApplicationContext("config/ApplicationContext.xml");
		}
		return context;
	}

	static {
		InitApp.getAppContext();
	}
}
