package org.application.oauth.init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.inject.Singleton;

@Singleton
public class InitApp {
    private static ApplicationContext context;

    public static ApplicationContext getAppContext() {
        if (null == context) {
            context = new ClassPathXmlApplicationContext("config/ApplicationContext.xml");
        }
        return context;
    }

    static {
        InitApp.getAppContext();
    }
}