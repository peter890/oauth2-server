/**
 * 
 */
package org.application.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author piotrek
 *
 */
@Component
@Aspect
public class ScopeMonitor {
	//@Before("execution(* *(@AccessToken (*)))")
	public Response checkScope(final JoinPoint point) {
		String accessToken = "";
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method m = signature.getMethod();
	    Annotation[][] a = m.getParameterAnnotations();
	    for(int i = 0; i < a.length; i++) {
	        if (a[i].length > 0) {
	            // this param has annotation(s)
	        	for (Annotation an : a[i]) {
	        		if (an instanceof AccessToken) {
	        			if(point.getArgs()[i] instanceof String) {
	        				accessToken = (String) point.getArgs()[i];
	        			}
	        		}
	        	}
	        }
	    }
	    System.out.print(accessToken);
	    return Response.status(401).build();
	}
	
	@AroundInvoke
	public Object interceptorMethod(InvocationContext ictx) throws Exception {
		return ictx.proceed();
		
	}
}
