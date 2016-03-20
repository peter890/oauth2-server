/**
 * 
 */
package org.application.oauth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.application.jpa.model.Session;
import org.application.services.CustomerService;
import org.application.services.UserSessionService;
import org.application.services.api.ICustomerService;
import org.application.services.api.IUserSessionService;
import org.config.Configuration;
import org.config.Configuration.Parameter;
import org.core.common.enums.CookiesName;
import org.core.common.exceptions.CookieNotFoundException;
import org.core.common.utils.CookieManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author piotrek
 *
 */
public class LoginSSOFilter implements Filter {
	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(LoginSSOFilter.class);
	
	/**
	 * Nazwa parametru pod jakim przechowywany jest requert autoryzacji.
	 */
	private static final String OAUTH_AUTHZ_REQUEST = "OAuthAuthzRequest";
	
	private List<String> excludedUrls = new ArrayList<String>();
	private IUserSessionService sessionService;
	private ICustomerService customerService;
	private ApplicationContext ctx;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(final FilterConfig filterConfig) throws ServletException {
		logger.debug("init");
		String excludedUrlsString = filterConfig.getInitParameter("EXCLUDE_URLS");
		if (null != excludedUrlsString) {
			String[] urlsArray = excludedUrlsString.split(";");
			excludedUrls = Arrays.asList(urlsArray);
		}
		ctx = ((ApplicationContext) filterConfig.getServletContext().getAttribute(org.springframework.web.context.WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		verifyOAuthAuthzRequest(req);

		String path = req.getRequestURI();
//		if (!path.startsWith("/server")) {
//			chain.doFilter(request, response);
//			return;
//		}
		path = path.replaceFirst(req.getContextPath(), "");
		boolean excluded = false;
		if (excludedUrls.contains(path)) {
			excluded = true;
		} else {
			for (String url : excludedUrls) {
				if (url.endsWith("*") && path.startsWith(url.replace("*", ""))) {
					if ((excluded = path.startsWith(url.replace("*", ""))) == true) {
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
			sessionService = ctx.getBean("userSessionService", UserSessionService.class);
			Cookie ssidCookie = CookieManager.getCookie(req, CookiesName.SSID);
			Session session = sessionService.getSessionBySsnId(ssidCookie.getValue());
			if (null == session || !session.isValid()) {
				forwardToLogin(request, response);
				return;
			} else {
				sessionService.updateSessionExpires(session);
				Cookie cookie = CookieManager.getCookie(req, CookiesName.SSID);
				cookie.setMaxAge(Integer.valueOf(Configuration.getParameterValue(
						Parameter.SessionTimeout)));
				CookieManager.setCookie((HttpServletResponse) response, cookie);
			}
			if(processOAuthRequestIfExist(req, res, session)) {
				return;
			}

		} catch (CookieNotFoundException e) {
			forwardToLogin(request, response);
			return;
		}
		chain.doFilter(request, response);
	}
	
	/**
	 * Procesuje ¿¹danie autoryzacji jeœli istnieje w sesji.
	 * 
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @return Zwraca <b>True</b> jeœli nast¹pi³o procesowanie autoryzacji, w przyciwnym razie <b>False</b>
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private boolean processOAuthRequestIfExist(final HttpServletRequest req, final HttpServletResponse res, final Session session)
			throws IOException {
		if (req.getSession().getAttribute(OAUTH_AUTHZ_REQUEST) != null) {
			@SuppressWarnings("unchecked")
			Map<String, Object> oAuthRequestAttr = (Map<String, Object>) req.getSession().getAttribute(
					OAUTH_AUTHZ_REQUEST);
			OAuthResponse resp = null;
			try {
				if (null != oAuthRequestAttr && oAuthRequestAttr.size() > 0) {
					customerService = ctx.getBean(CustomerService.class);
					String code = customerService.generateAuthorizationCode(
							(String) oAuthRequestAttr.get(OAuth.OAUTH_CLIENT_ID),
							(Set<String>) oAuthRequestAttr.get(OAuth.OAUTH_SCOPE),
							session.getSocialUser().getUser());

					resp = OAuthASResponse.authorizationResponse(req, HttpServletResponse.SC_FOUND).setCode(code)
							.location((String) oAuthRequestAttr.get(OAuth.OAUTH_REDIRECT_URI)).buildQueryMessage();
					res.sendRedirect(resp.getLocationUri());
					req.getSession().removeAttribute(OAUTH_AUTHZ_REQUEST);
				}
			} catch (OAuthSystemException e) {
				logger.error("processOAuthRequestIfExist", e);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Sprawdza czy dany reuest nie jest z¹daniem OAuth.
	 * Jeœli tak, to zapisuje w sesji jego atrybuty.
	 * @param req
	 */
	private void verifyOAuthAuthzRequest(final HttpServletRequest req) {
		try {
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(req);
			if (ResponseType.CODE.toString().equals(oauthRequest.getResponseType())) {
				logger.debug("verifyOAuthAuthzRequest|zapisujemy OAuthAuthzRequest");
				Map<String, Object> oAuthRequestAttr = new HashMap<String, Object>();
				oAuthRequestAttr.put(OAuth.OAUTH_CLIENT_ID, oauthRequest.getClientId());
				oAuthRequestAttr.put(OAuth.OAUTH_CLIENT_SECRET, oauthRequest.getClientSecret());
				oAuthRequestAttr.put(OAuth.OAUTH_REDIRECT_URI, oauthRequest.getRedirectURI());
				oAuthRequestAttr.put(OAuth.OAUTH_SCOPE, oauthRequest.getScopes());
				
				req.getSession().setAttribute(OAUTH_AUTHZ_REQUEST, oAuthRequestAttr);
			}

		} catch (final Exception e) {
			//logger.error("verifyOAuthAuthzRequest", e);
		}
	}
	/**
	 * Przekierowuje do strony logowania (wybory dostawcy to¿samoœci).
	 * 
	 * @param request ServletRequest
	 * @param response ServletResponse
	 * @throws ServletException 
	 * @throws IOException
	 */
	private void forwardToLogin(final ServletRequest request, final ServletResponse response) throws ServletException,
			IOException {
		logger.debug("forwardToLogin");
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
