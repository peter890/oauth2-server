package org.application.oauth.servlet;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.application.jpa.model.*;
import org.application.services.api.ICustomerService;
import org.application.services.api.IDictionaryService;
import org.config.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class OAuthTokenServlet
 */
public class OAuthTokenServlet extends AbstractServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Inject
    private
    IDictionaryService dictionaryService;
    @Inject
    private
    ICustomerService customerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OAuthTokenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
        this.logger.debug("doPost|START");
        final OAuthTokenRequest oauthRequest;

        final OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
        final boolean addDictionary = false;
        if (addDictionary) {
            addDictionary();
        }

        try {
            oauthRequest = new OAuthTokenRequest(request);
            final String clientId = request.getParameter(OAuth.OAUTH_CLIENT_ID);
            final String clientSecret = request.getParameter(OAuth.OAUTH_CLIENT_SECRET);

            // validateClient(oauthRequest);

            final String authzCode = oauthRequest.getCode();
            final AccessToken token = this.customerService.getAccessToken(clientId, clientSecret, authzCode);

            // some code
            // String accessToken = oauthIssuerImpl.accessToken();
            // String refreshToken = oauthIssuerImpl.refreshToken();

            int tokenLifeTime = Integer.parseInt(ConfigProperties.SESSION_TIMEOUT.getValue());
            tokenLifeTime = tokenLifeTime / 2;

            final OAuthResponse r = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(token.getAccessToken()).setExpiresIn(String.valueOf(tokenLifeTime))
                    .setRefreshToken(token.getRefreshToken()).location(request.getParameter(OAuth.OAUTH_REDIRECT_URI))
                    .buildJSONMessage();

            response.setStatus(r.getResponseStatus());
            final PrintWriter pw = response.getWriter();
            pw.print(r.getBody());
            pw.flush();
            pw.close();
            // if something goes wrong
        } catch (final OAuthProblemException ex) {
            this.logger.error("doPost", ex);

            final OAuthResponse r;
            try {
                r = OAuthResponse.errorResponse(401).error(ex).buildJSONMessage();
                response.setStatus(r.getResponseStatus());

                final PrintWriter pw = response.getWriter();
                pw.print(r.getBody());
                pw.flush();
                pw.close();
            } catch (final OAuthSystemException e) {
                this.logger.error("doPost", e);
            }

            response.sendError(401);
        } catch (final OAuthSystemException e) {
            this.logger.error("doPost", e);
        }
        this.logger.debug("doPost|STOP");
    }

    @Override
    protected void doProcess(final HttpServletRequest request, final HttpServletResponse response) {

    }

    private void addDictionary() {
        final List<DictionaryValue> dictValues = new ArrayList<>();

        final Dictionary dictionary = new Dictionary();
        dictionary.setName("TESTOWY");
        dictionary.setDescription("S�ownik testowy");

        final DictionaryStructure structureCode = new DictionaryStructure();
        structureCode.setDictionary(dictionary);
        structureCode.setName("CODE");

        final DictionaryStructure structureValue = new DictionaryStructure();
        structureValue.setDictionary(dictionary);
        structureValue.setName("VALUE");

        final DictionaryStructure structureMeaning = new DictionaryStructure();
        structureMeaning.setDictionary(dictionary);
        structureMeaning.setName("MEANING");

        final DictionaryStructure structureActive = new DictionaryStructure();
        structureActive.setDictionary(dictionary);
        structureActive.setName("ACTIVE");

        final DictionaryItem item1 = new DictionaryItem();
        item1.setDictionary(dictionary);

        final DictionaryValue val1 = new DictionaryValue();
        val1.setDictionaryItem(item1);
        val1.setDictionaryStructure(structureCode);
        val1.setValue("ZXWS1");
        dictValues.add(val1);

        final DictionaryValue val2 = new DictionaryValue();
        val2.setDictionaryItem(item1);
        val2.setDictionaryStructure(structureValue);
        val2.setValue("1231");
        dictValues.add(val2);

        final DictionaryValue val3 = new DictionaryValue();
        val3.setDictionaryItem(item1);
        val3.setDictionaryStructure(structureMeaning);
        val3.setValue("Tajny kod do kodowania1");
        dictValues.add(val3);

        final DictionaryValue val4 = new DictionaryValue();
        val4.setDictionaryItem(item1);
        val4.setDictionaryStructure(structureActive);
        val4.setValue("Y1");
        dictValues.add(val4);

        final DictionaryItem item2 = new DictionaryItem();
        item2.setDictionary(dictionary);

        final DictionaryValue val12 = new DictionaryValue();
        val12.setDictionaryItem(item2);
        val12.setDictionaryStructure(structureCode);
        val12.setValue("ZXWS2");
        dictValues.add(val12);

        final DictionaryValue val22 = new DictionaryValue();
        val22.setDictionaryItem(item2);
        val22.setDictionaryStructure(structureValue);
        val22.setValue("2");
        dictValues.add(val22);

        final DictionaryValue val32 = new DictionaryValue();
        val32.setDictionaryItem(item2);
        val32.setDictionaryStructure(structureMeaning);
        val32.setValue("Tajny kod do kodowania2");
        dictValues.add(val32);

        final DictionaryValue val42 = new DictionaryValue();
        val42.setDictionaryItem(item2);
        val42.setDictionaryStructure(structureActive);
        val42.setValue("Y2");
        dictValues.add(val42);

        this.dictionaryService.addDictionary(dictValues);
    }

}
