package org.application.oauth.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.application.oauth.init.InitApp;
import org.springframework.context.ApplicationContext;

/**
 * Servlet implementation class OAuthServer
 */
public class OAuthServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	@SuppressWarnings("unchecked")
	public OAuthServer() {
		super();
		ApplicationContext context =  InitApp.getAppContext();
		//ICustomerDAO<Customer> customer =  (ICustomerDAO<Customer>) context.getBean("customerDAO");
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		try {
			// dynamically recognize an OAuth profile based on request
			// characteristic (params,
			// method, content type etc.), perform validation
			HttpServletRequest req = request;
			HttpServletResponse res = response;
			Cookie[] cookies = req.getCookies();
			String cookieName = "SSOID";
			res.addCookie(new Cookie(cookieName, req.getSession().getId()+"OAuth"));
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
			OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(
					new MD5Generator());

			// validateRedirectionURI(oauthRequest)

			// build OAuth response
			OAuthResponse resp = OAuthASResponse
					.authorizationResponse(request,
							HttpServletResponse.SC_FOUND)
					.setCode(oauthIssuerImpl.authorizationCode())
					.location(oauthRequest.getRedirectURI())
					.buildQueryMessage();

			response.sendRedirect(resp.getLocationUri());

			// if something goes wrong
		} catch (OAuthProblemException ex) {
			OAuthResponse resp;
			try {
				resp = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_FOUND).error(ex)
						.location(ex.getRedirectUri()).buildQueryMessage();
				response.sendRedirect(resp.getLocationUri());
			} catch (OAuthSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
