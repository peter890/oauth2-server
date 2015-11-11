/**
 * 
 */
package org.application.oauth.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.application.oauth.init.InitApp;
import org.springframework.context.ApplicationContext;

/**
 * Abstrakcyjny servlet udostêpniaj¹cy ApplicationContext.
 * @author piotrek
 *
 */
public abstract class AbstractServlet extends HttpServlet {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 5293074494981394627L;
	
	/**
	 * Spring ApplicationContext.
	 */
	protected ApplicationContext applicationContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		applicationContext = InitApp.getAppContext();//(ApplicationContext) config.getServletContext().getAttribute("applicationContext");
	}
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		//super.doGet(req, resp);
		doProcess(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		//super.doPost(req, resp);
		doProcess(req, resp);
	}
	
	/**
	 * Agreguje wywo³ania POST i GET.
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 */
	protected void doProcess(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		
	}
}
