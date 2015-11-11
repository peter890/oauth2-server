/**
 * 
 */
package org.application.oauth.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author piotrek
 *
 */
public class SpringApplicationContextListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(final ServletContextEvent sce) {
		sce.getServletContext().setAttribute("applicationContext", InitApp.getAppContext());
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(final ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
