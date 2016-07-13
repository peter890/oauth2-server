/**
 * 
 */
package org.application.oauth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.application.jpa.model.Customer;
import org.application.services.api.ICustomerService;

/**
 * @author piotrek
 *
 */
public class LogoutActionServlet extends AbstractServlet {
	/**
	 * Serwis odpowiedzialny za encjê Customer
	 */
	@Inject
	private ICustomerService customerService;
	
	protected void process(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		List<Customer> customers =  customerService.getAllCustomers();
		PrintWriter out = resp.getWriter();
		out.println("<html><body>");
		for (Customer customer : customers) {
			out.println("<iframe src='" + customer.getLogoutUrl() + "' style='display:none'/>");
		}
		out.println("</html></body>");
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		process(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
		process(req, resp);
	}
}
