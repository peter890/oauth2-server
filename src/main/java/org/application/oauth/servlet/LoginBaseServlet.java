/**
 *
 */
package org.application.oauth.servlet;

import org.application.jpa.model.Session;
import org.application.jpa.model.SocialUser;
import org.application.services.api.IUserSessionService;
import org.config.ConfigProperties;
import org.core.common.enums.CookiesName;
import org.core.common.exceptions.CookieNotFoundException;
import org.core.common.utils.CookieManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.api.IOAuthProcessor;
import org.social.api.IOAuthProcessorFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author piotrek
 */
public class LoginBaseServlet extends AbstractServlet {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginBaseServlet.class);
    @Inject
    private IUserSessionService sessionService;

    @Inject
    private IOAuthProcessorFactory processorFactory;

    /**
     * (non-Javadoc)
     *
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
        super.init();
        //sessionService = InitApp.getAppContext().getBean(UserSessionService.class);
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
            throws IOException {
        logger.debug("doProcess: {}", "START");
        final String sessionExpiredRedirect = (String) request.getAttribute("sessionExpired");
        final String sysname = request.getParameter("sysname");
        // if (null != sessionExpiredRedirect &&
        // "Y".equals(sessionExpiredRedirect)) {
        //
        // }
        logger.debug("doProcess|sesionExpired: {}", sessionExpiredRedirect);
        logger.debug("doProcess|sysname: {}", sysname);
        final IOAuthProcessor processor;// = processorFactory.createProcessorByName(request.getParameter("sysname"));//OAuthProcessorFactory.createProcessorByName(request.getParameter("sysname"));
        if ((null != sysname) && (processor = this.processorFactory.createProcessorByName(sysname)) != null) {
            try {
                processor.process(request, response);
                final SocialUser socialUser = processor.getSocialUserData();
                final ServletOutputStream out = response.getOutputStream();
                out.println(socialUser.toString());
                Session session = new Session();
                session.setSocialUser(socialUser);

                //sessionService = InitApp.getAppContext().getBean(UserSessionService.class);
                session = this.sessionService.createSessionWithNewUser(session);

                final Cookie cookie = new Cookie(CookiesName.SSID.getValue(), session.getSsnId());
                cookie.setHttpOnly(true);
                cookie.setMaxAge(Integer.valueOf(ConfigProperties.SESSION_TIMEOUT.getValue()));
                CookieManager.setCookie(response, cookie);
                response.sendRedirect("index.jsp");
            } catch (final Exception e) {
                e.printStackTrace();
                redirectToLoginPage(request, response);
            }
        } else if (isUserLoggedIn(request, response)) {
            response.sendRedirect("index.jsp");
        } else {
            redirectToLoginPage(request, response);
            // Sprawdzamy czy istnieje ciasteczko, je�li tak to na jego
            // podstawie mo�emy wy�uska� z sesji DB dostawc� to�samo�ci.
            // je�lie nie, to:
            // przekierowanie na stron� logowania
            // mo�liwo�� zalogowania za pomoc� FB i inne.
        }
    }

    private boolean isUserLoggedIn(final HttpServletRequest request, final HttpServletResponse response) {
        boolean isUserLoggedIn = false;
        try {
            final Cookie ssidCookie = CookieManager.getCookie(request, CookiesName.SSID);
            final Session session = this.sessionService.getSessionBySsnId(ssidCookie.getValue());
            if (null != session && session.isValid()) {
                isUserLoggedIn = true;
                this.sessionService.updateSessionExpires(session);
                final Cookie cookie = CookieManager.getCookie(request, CookiesName.SSID);
                cookie.setMaxAge(Integer.valueOf(ConfigProperties.SESSION_TIMEOUT.getValue()));
                CookieManager.setCookie(response, cookie);

                try {
                    final ServletOutputStream out = response.getOutputStream();
                    out.println(session.toString());
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (final CookieNotFoundException e) {
            isUserLoggedIn = false;
        }

        return isUserLoggedIn;
    }

    private void redirectToLoginPage(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            //response.sendRedirect("login.jsp");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

}
