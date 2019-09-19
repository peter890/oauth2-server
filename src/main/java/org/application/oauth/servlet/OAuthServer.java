package org.application.oauth.servlet;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.application.oauth.init.InitApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class OAuthServer
 */
public class OAuthServer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @see HttpServlet#HttpServlet()
     */
    @SuppressWarnings("unchecked")
    public OAuthServer() {
        super();
        final ApplicationContext context = InitApp.getAppContext();
        //ICustomerDAO<Customer> customer =  (ICustomerDAO<Customer>) context.getBean("customerDAO");

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response) throws ServletException,
            IOException {
        this.logger.debug("doGet|START");
        try {
            // dynamically recognize an OAuth profile based on request
            // characteristic (params,
            // method, content type etc.), perform validation
            //HttpServletResponse res = response;
//			Cookie[] cookies = req.getCookies();
//			String cookieName = "SSOID";
            //res.addCookie(new Cookie(cookieName, req.getSession().getId()+"OAuth"));
            final OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            final String scope = oauthRequest.getParam(OAuth.OAUTH_SCOPE);
            final OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(
                    new MD5Generator());

            // validateRedirectionURI(oauthRequest)

            // build OAuth response
            final OAuthResponse resp = OAuthASResponse
                    .authorizationResponse(request,
                            HttpServletResponse.SC_FOUND)
                    .setCode(oauthIssuerImpl.authorizationCode())
                    .location(oauthRequest.getRedirectURI())
                    .buildQueryMessage();
            this.logger.error("doGet|redirectTo: {}", resp.getLocationUri());

            response.sendRedirect(response.encodeRedirectURL(resp.getLocationUri()));

            // if something goes wrong
        } catch (final OAuthProblemException ex) {
            this.logger.error("doGet", ex);
            final OAuthResponse resp;
            try {
                resp = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_FOUND).error(ex)
                        .location(ex.getRedirectUri()).buildQueryMessage();
                response.sendRedirect(resp.getLocationUri());
            } catch (final OAuthSystemException e) {
                this.logger.error("doGet", e);
            }

        } catch (final OAuthSystemException e) {
            this.logger.error("doGet", e);
        }
        this.logger.debug("doGet|STOP");
    }

}
