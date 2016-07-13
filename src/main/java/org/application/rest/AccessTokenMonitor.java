/**
 * 
 */
package org.application.rest;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.springframework.stereotype.Component;

/**
 * @author piotrek
 *
 */
@Component
@Interceptor
public class AccessTokenMonitor {
	@AroundInvoke
	public Object interceptorMethod(InvocationContext ictx) throws Exception {
		return ictx.proceed();
		
	}
}
