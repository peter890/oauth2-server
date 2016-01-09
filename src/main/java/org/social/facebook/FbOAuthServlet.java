package org.social.facebook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.application.oauth.servlet.AbstractServlet;
import org.application.services.api.IUserSessionService;

/**
 * Servlet implementation class FbOAuthServlet
 */
public class FbOAuthServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	private String code="";
	
	private IUserSessionService sessionService;
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FbOAuthServlet() {
		super();
	}

	@Override
	public void doProcess(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
//		code = request.getParameter("code");
//		if (code == null || code.equals("")) {
//			throw new RuntimeException(
//					"ERROR: Didn't get code parameter in callback.");
//		}
//		FacebookConnect fbConnect = new FacebookConnect();
//		String accessToken = fbConnect.getAccessToken(code);
//		IOAuthProcessor processor = OAuthProcessorFactory.createProcessorByName(request.getParameter("sysname"));
//		try {
//			processor.process(request, response);
//			SocialUser socialUser = processor.getSocialUserData();
//			ServletOutputStream out = response.getOutputStream();
//			out.println(socialUser.toString());
//			Session session = new Session();
//			session.setSocialUser(socialUser);
//			//session.setUser(socialUser.getUser());
//			sessionService = InitApp.getAppContext().getBean(UserSessionService.class);
//			session = sessionService.createSessionWithNewUser(session);
//			CookieManager.setCookie(response, CookiesName.SSID, session.getSsnId(), true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		String accessToken = processor.getAccessToken();
//		FBGraph fbGraph = new FBGraph(accessToken);
//		String graph = fbGraph.getFBGraph();
//		Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
//		ServletOutputStream out = response.getOutputStream();
//		out.println("<h1>Facebook Login using Java</h1>");
//		out.println("<h2>Application Main Menu</h2>");
//		out.println("<div>Welcome "+fbProfileData.get("first_name"));
//		out.println("<div>Your Email: "+fbProfileData.get("email"));
//		out.println("<div>You are "+fbProfileData.get("gender"));		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
			IOException {
		doProcess(request, response);
	}
}
