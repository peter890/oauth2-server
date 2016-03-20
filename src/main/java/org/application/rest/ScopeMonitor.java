/**
 * 
 */
package org.application.rest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author piotrek
 *
 */
@Component
@Aspect
public class ScopeMonitor {
	@Before("execution(@org.application.rest.AccessTokenScope * *(..))")
	public void checkScope(final JoinPoint  point) {
		System.out.println("dupa dupa");
	}
}
