/**
 *
 */
package org.application.oauth.servlet;

import org.application.jpa.model.Customer;
import org.application.services.api.ICustomerService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author piotrek
 */
public class LogoutActionServlet extends AbstractServlet {
    /**
     * Serwis odpowiedzialny za encje Customer
     */
    @Inject
    private ICustomerService customerService;


    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        doProcess(req, resp);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        doProcess(req, resp);
    }

    @Override
    protected void doProcess(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final List<Customer> customers = this.customerService.getAllCustomers();
        final PrintWriter out = response.getWriter();
        out.println("<html><body>");
        for (final Customer customer : customers) {
//            out.println("<iframe src='" + customer.getLogoutUrl() + "'/>");
            out.println("<object data='" + customer.getLogoutUrl() + "' type=\"text/html\" width=\"320\" height=\"240\"></object>");
        }
        //out.println("<object data=\"http://www.google.com/\" type=\"text/html\" width=\"320\" height=\"240\"></object>");
        out.println("</html></body>"); //' style='display:none'
    }
}
