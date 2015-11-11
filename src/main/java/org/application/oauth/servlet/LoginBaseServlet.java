/**
 * 
 */
package org.application.oauth.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.application.jpa.model.Session;
import org.application.jpa.model.SocialUser;
import org.application.oauth.init.InitApp;
import org.application.services.UserSessionService;
import org.application.services.api.IUserSessionService;
import org.config.Configuration;
import org.config.Configuration.Parameter;
import org.core.common.enums.CookiesName;
import org.core.common.exceptions.CookieNotFoundException;
import org.core.common.utils.CookieManager;
import org.social.OAuthProcessorFactory;
import org.social.api.IOAuthProcessor;

/**
 * @author piotrek
 *
 */
public class LoginBaseServlet extends AbstractServlet {
	private IUserSessionService sessionService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		sessionService = InitApp.getAppContext().getBean(UserSessionService.class);
	}

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.application.oauth.servlet.AbstractServlet#doProcess(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doProcess(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		super.doProcess(request, response);
		String sessionExpiredRedirect = (String) request.getAttribute("sessionExpired");
		// if (null != sessionExpiredRedirect &&
		// "Y".equals(sessionExpiredRedirect)) {
		//
		// }
		IOAuthProcessor processor = OAuthProcessorFactory.createProcessorByName(request.getParameter("sysname"));
		if (processor != null) {
			try {
				processor.process(request, response);
				SocialUser socialUser = processor.getSocialUserData();
				ServletOutputStream out = response.getOutputStream();
				out.println(socialUser.toString());
				Session session = new Session();
				session.setSocialUser(socialUser);

				sessionService = InitApp.getAppContext().getBean(UserSessionService.class);
				session = sessionService.createSessionWithNewUser(session);

				Cookie cookie = new Cookie(CookiesName.SSID.getValue(), session.getSsnId());
				cookie.setHttpOnly(true);
				cookie.setMaxAge(Integer.valueOf(Configuration.getConfiguration().getParameterValue(
						Parameter.SessionTimeout)));
				CookieManager.setCookie(response, cookie);
				response.sendRedirect("index.jsp");
			} catch (Exception e) {
				e.printStackTrace();
				redirectToLoginPage(request, response);
			}
		} else if (isUserLoggedIn(request, response)) {
			response.sendRedirect("index.jsp");
		} else {
			redirectToLoginPage(request, response);
			// Sprawdzamy czy istnieje ciasteczko, jeœli tak to na jego
			// podstawie mo¿emy wy³uskaæ z sesji DB dostawcê to¿samoœci.
			// jeœlie nie, to:
			// przekierowanie na stronê logowania
			// mo¿liwoœæ zalogowania za pomoc¹ FB i inne.
		}
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean isUserLoggedIn(final HttpServletRequest request, final HttpServletResponse response) {
		boolean isUserLoggedIn = false;
		try {
			Cookie ssidCookie = CookieManager.getCookie(request, CookiesName.SSID);
			Session session = sessionService.getSessionBySsnId(ssidCookie.getValue());
			if (null != session && session.isValid()) {
				isUserLoggedIn = true;
				sessionService.updateSessionExpires(session);
				Cookie cookie = CookieManager.getCookie(request, CookiesName.SSID);
				cookie.setMaxAge(Integer.valueOf(Configuration.getConfiguration().getParameterValue(
						Parameter.SessionTimeout)));
				CookieManager.setCookie(response, cookie);

				try {
					ServletOutputStream out = response.getOutputStream();
					out.println(session.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (CookieNotFoundException e) {
			isUserLoggedIn = false;
		}

		return isUserLoggedIn;
	}

	private void redirectToLoginPage(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			response.sendRedirect("login.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
