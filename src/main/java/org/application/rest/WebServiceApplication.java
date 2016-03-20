/**
 * 
 */
package org.application.rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author piotrek
 *
 */
public class WebServiceApplication extends ResourceConfig{
	public WebServiceApplication() {
		packages("org.application.rest");
        register(JacksonFeature.class);
	}
}
