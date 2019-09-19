/**
 *
 */
package org.application.oauth.servlet;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstrakcyjny servlet udostępniający ApplicationContext.
 *
 * @author piotrek
 */
public abstract class AbstractServlet extends HttpServlet {
    /**
     * UID
     */
    private static final long serialVersionUID = 5293074494981394627L;

    /**
     * Spring ApplicationContext.
     */
    private AutowireCapableBeanFactory ctx;

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        this.ctx = ((ApplicationContext) getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE)).getAutowireCapableBeanFactory();
        //The following line does the magic
        this.ctx.autowireBean(this);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    /**
     * Agreguje wywołania POST i GET.
     * nse
     */
    abstract protected void doProcess(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException;
}
