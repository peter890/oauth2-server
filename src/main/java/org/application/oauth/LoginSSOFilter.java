/**
 * 
 */
package org.application.oauth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.application.jpa.model.Session;
import org.application.oauth.init.InitApp;
import org.application.services.UserSessionService;
import org.application.services.api.IUserSessionService;
import org.config.Configuration;
import org.config.Configuration.Parameter;
import org.core.common.enums.CookiesName;
import org.core.common.exceptions.CookieNotFoundException;
import org.core.common.utils.CookieManager;
import org.springframework.util.StringUtils;

/**
 * @author piotrek
 *
 */
public class LoginSSOFilter implements Filter {
	private List<String> excludedUrls = new ArrayList<String>();
	private IUserSessionService sessionService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(final FilterConfig filterConfig) throws ServletException {
		

		String excludedUrlsString = filterConfig.getInitParameter("EXCLUDE_URLS");
		if (null != excludedUrlsString) {
			String[] urlsArray = excludedUrlsString.split(";");
			excludedUrls = Arrays.asList(urlsArray);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		String[] array = InitApp.getAppContext().getBeanDefinitionNames();
		System.out.println(StringUtils.arrayToCommaDelimitedString(array));
		sessionService = InitApp.getAppContext().getBean("userSessionService", UserSessionService.class);
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI();

		path = path.replaceFirst(req.getContextPath(), "");
		boolean excluded = false;
		if (excludedUrls.contains(path)) {
			excluded = true;
		} else {
			for (String url : excludedUrls) {
				if (url.endsWith("*") && path.startsWith(url.replace("*", ""))) {
					if((excluded = path.startsWith(url.replace("*", ""))) == true) {
						break;
					}
					
				}
			}
		}

		if (excluded) {
			chain.doFilter(request, response);
			return;
		}
		try {
			Cookie ssidCookie = CookieManager.getCookie(req, CookiesName.SSID);
			Session session = sessionService.getSessionBySsnId(ssidCookie.getValue());
			if (null == session || !session.isValid()) {
				forwardToLogin(request, response);
				return;
			}
			else {
				sessionService.updateSessionExpires(session);
				Cookie cookie = CookieManager.getCookie(req, CookiesName.SSID);
				cookie.setMaxAge(Integer.valueOf(Configuration.getConfiguration().getParameterValue(Parameter.SessionTimeout)));
				CookieManager.setCookie((HttpServletResponse) response, cookie);
			}

		} catch (CookieNotFoundException e) {
			forwardToLogin(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	private void forwardToLogin(final ServletRequest request, final ServletResponse response) throws ServletException,
			IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login");
		request.setAttribute("sessionExpired", "Y");
		requestDispatcher.forward(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
