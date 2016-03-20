package org.application.oauth.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.application.jpa.model.AccessToken;
import org.application.jpa.model.Dictionary;
import org.application.jpa.model.DictionaryItem;
import org.application.jpa.model.DictionaryStructure;
import org.application.jpa.model.DictionaryValue;
import org.application.services.api.ICustomerService;
import org.application.services.api.IDictionaryService;
import org.config.Configuration;
import org.config.Configuration.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class OAuthTokenServlet
 */
public class OAuthTokenServlet extends AbstractServlet {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Inject
	IDictionaryService dictionaryService;
	@Inject
	ICustomerService customerService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OAuthTokenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		logger.debug("doPost|START");
		OAuthTokenRequest oauthRequest = null;

		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		boolean addDictionary = false;
		if(addDictionary) {
			addDictionary();
		}

		try {
			oauthRequest = new OAuthTokenRequest(request);
			String clientId = request.getParameter(OAuth.OAUTH_CLIENT_ID);
			String clientSecret = request.getParameter(OAuth.OAUTH_CLIENT_SECRET);

			// validateClient(oauthRequest);

			String authzCode = oauthRequest.getCode();
			AccessToken token = customerService.getAccessToken(clientId, clientSecret, authzCode);

			// some code
			//String accessToken = oauthIssuerImpl.accessToken();
			//String refreshToken = oauthIssuerImpl.refreshToken();

			int tokenLifeTime = Integer.parseInt(Configuration.getParameterValue(Parameter.SessionTimeout));
			tokenLifeTime = tokenLifeTime / 2;
			
			OAuthResponse r = OAuthASResponse
					.tokenResponse(HttpServletResponse.SC_OK)
					.setAccessToken(token.getAccessToken()).setExpiresIn(String.valueOf(tokenLifeTime))
					.setRefreshToken(token.getRefreshToken()).location(request.getParameter(OAuth.OAUTH_REDIRECT_URI)).buildJSONMessage();

			response.setStatus(r.getResponseStatus());
			PrintWriter pw = response.getWriter();
			pw.print(r.getBody());
			pw.flush();
			pw.close();
			// if something goes wrong
		} catch (OAuthProblemException ex) {
			logger.error("doPost", ex);

			OAuthResponse r;
			try {
				r = OAuthResponse.errorResponse(401).error(ex)
						.buildJSONMessage();
				response.setStatus(r.getResponseStatus());

				PrintWriter pw = response.getWriter();
				pw.print(r.getBody());
				pw.flush();
				pw.close();
			} catch (OAuthSystemException e) {
				logger.error("doPost", e);
			}

			response.sendError(401);
		} catch (OAuthSystemException e) {
			logger.error("doPost", e);
		}
		logger.debug("doPost|STOP");
	}
	
	private void addDictionary() {
		List<DictionaryValue> dictValues = new ArrayList<DictionaryValue>();
		
		Dictionary dictionary = new Dictionary();
		dictionary.setName("TESTOWY");
		dictionary.setDescription("S³ownik testowy");
		
		DictionaryStructure structureCode = new DictionaryStructure();
		structureCode.setDictionary(dictionary);
		structureCode.setName("CODE");
		
		DictionaryStructure structureValue = new DictionaryStructure();
		structureValue.setDictionary(dictionary);
		structureValue.setName("VALUE");
		
		DictionaryStructure structureMeaning = new DictionaryStructure();
		structureMeaning.setDictionary(dictionary);
		structureMeaning.setName("MEANING");
		
		DictionaryStructure structureActive = new DictionaryStructure();
		structureActive.setDictionary(dictionary);
		structureActive.setName("ACTIVE");
		
		DictionaryItem item1 = new DictionaryItem();
		item1.setDictionary(dictionary);
		
		DictionaryValue val1 = new DictionaryValue();
		val1.setDictionaryItem(item1);
		val1.setDictionaryStructure(structureCode);
		val1.setValue("ZXWS1");
		dictValues.add(val1);
		
		DictionaryValue val2 = new DictionaryValue();
		val2.setDictionaryItem(item1);
		val2.setDictionaryStructure(structureValue);
		val2.setValue("1231");
		dictValues.add(val2);
		
		DictionaryValue val3 = new DictionaryValue();
		val3.setDictionaryItem(item1);
		val3.setDictionaryStructure(structureMeaning);
		val3.setValue("Tajny kod do kodowania1");
		dictValues.add(val3);
		
		DictionaryValue val4 = new DictionaryValue();
		val4.setDictionaryItem(item1);
		val4.setDictionaryStructure(structureActive);
		val4.setValue("Y1");
		dictValues.add(val4);
		
		DictionaryItem item2 = new DictionaryItem();
		item2.setDictionary(dictionary);
		
		DictionaryValue val12 = new DictionaryValue();
		val12.setDictionaryItem(item2);
		val12.setDictionaryStructure(structureCode);
		val12.setValue("ZXWS2");
		dictValues.add(val12);
		
		DictionaryValue val22 = new DictionaryValue();
		val22.setDictionaryItem(item2);
		val22.setDictionaryStructure(structureValue);
		val22.setValue("2");
		dictValues.add(val22);
		
		DictionaryValue val32 = new DictionaryValue();
		val32.setDictionaryItem(item2);
		val32.setDictionaryStructure(structureMeaning);
		val32.setValue("Tajny kod do kodowania2");
		dictValues.add(val32);
		
		DictionaryValue val42 = new DictionaryValue();
		val42.setDictionaryItem(item2);
		val42.setDictionaryStructure(structureActive);
		val42.setValue("Y2");
		dictValues.add(val42);
		

		dictionaryService.addDictionary(dictValues);
	}

}
