/**
 *
 */
package org.application.oauth;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.application.filters.UrlsExcludeBaseFilter;
import org.application.jpa.model.Session;
import org.application.services.CustomerService;
import org.application.services.UserSessionService;
import org.application.services.api.ICustomerService;
import org.application.services.api.IUserSessionService;
import org.config.ConfigProperties;
import org.core.common.enums.CookiesName;
import org.core.common.exceptions.CookieNotFoundException;
import org.core.common.utils.CookieManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author piotrek
 */
public class LoginSSOFilter extends UrlsExcludeBaseFilter {
    private static final Logger logger = LoggerFactory.getLogger(LoginSSOFilter.class);

    /**
     * Nazwa parametru pod jakim przechowywany jest requert autoryzacji.
     */
    private static final String OAUTH_AUTHZ_REQUEST = "OAuthAuthzRequest";

    private ApplicationContext ctx;

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(final FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        this.ctx = ((ApplicationContext) filterConfig.getServletContext().getAttribute(org.springframework.web.context.WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE));
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        verifyOAuthAuthzRequest(req);

        if (isUrlExcluded(req)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            final IUserSessionService sessionService = this.ctx.getBean("userSessionService", UserSessionService.class);
            final Cookie ssidCookie = CookieManager.getCookie(req, CookiesName.SSID);
            final Session session = sessionService.getSessionBySsnId(ssidCookie.getValue());
            if (null == session || !session.isValid()) {
                forwardToLogin(request, response);
                return;
            } else {
                sessionService.updateSessionExpires(session);
                final Cookie cookie = CookieManager.getCookie(req, CookiesName.SSID);
                cookie.setMaxAge(Integer.valueOf(ConfigProperties.SESSION_TIMEOUT.getValue()));
                CookieManager.setCookie((HttpServletResponse) response, cookie);
            }
            if (processOAuthRequestIfExist(req, res, session)) {
                return;
            }

        } catch (final CookieNotFoundException e) {
            forwardToLogin(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    /**
     * Procesuje żądanie autoryzacji jeśli istnieje w sesji.
     *
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @return Zwraca <b>True</b> jeśli nastąpiło procesowanie autoryzacji, w przyciwnym razie <b>False</b>
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    private boolean processOAuthRequestIfExist(final HttpServletRequest req, final HttpServletResponse res, final Session session)
            throws IOException {
        if (req.getSession().getAttribute(OAUTH_AUTHZ_REQUEST) != null) {
            @SuppressWarnings("unchecked") final Map<String, Object> oAuthRequestAttr = (Map<String, Object>) req.getSession().getAttribute(
                    OAUTH_AUTHZ_REQUEST);
            final OAuthResponse resp;
            try {
                if (null != oAuthRequestAttr && oAuthRequestAttr.size() > 0) {
                    final ICustomerService customerService = this.ctx.getBean(CustomerService.class);
                    final String code = customerService.generateAuthorizationCode(
                            (String) oAuthRequestAttr.get(OAuth.OAUTH_CLIENT_ID),
                            (Set<String>) oAuthRequestAttr.get(OAuth.OAUTH_SCOPE),
                            session.getSocialUser().getUser());

                    resp = OAuthASResponse.authorizationResponse(req, HttpServletResponse.SC_FOUND).setCode(code)
                            .location((String) oAuthRequestAttr.get(OAuth.OAUTH_REDIRECT_URI)).buildQueryMessage();
                    res.sendRedirect(resp.getLocationUri());
                    req.getSession().removeAttribute(OAUTH_AUTHZ_REQUEST);
                }
            } catch (final OAuthSystemException e) {
                logger.error("processOAuthRequestIfExist", e);
            }
            return true;
        }
        return false;
    }

    /**
     * Sprawdza czy dany reuest nie jest ządaniem OAuth.
     * Jeśli tak, to zapisuje w sesji jego atrybuty.
     *
     * @param req
     */
    private void verifyOAuthAuthzRequest(final HttpServletRequest req) {
        try {
            final OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(req);
            if (ResponseType.CODE.toString().equals(oauthRequest.getResponseType())) {
                logger.debug("verifyOAuthAuthzRequest|zapisujemy OAuthAuthzRequest");
                final Map<String, Object> oAuthRequestAttr = new HashMap<>();
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
     * Przekierowuje do strony logowania (wybory dostawcy tożsamości).
     *
     * @param request  ServletRequest
     * @param response ServletResponse
     * @throws ServletException
     * @throws IOException
     */
    private void forwardToLogin(final ServletRequest request, final ServletResponse response) throws ServletException,
            IOException {
        logger.debug("forwardToLogin");
        final RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login");
        request.setAttribute("sessionExpired", "Y");
        requestDispatcher.forward(request, response);
    }

}
