/**
 *
 */
package org.social.facebook;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author piotrek
 */
public class FBGraph {
    private final String accessToken;

    public FBGraph(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getFBGraph() {
        final String graph;
        try {
            final String g = "https://graph.facebook.com/v2.4/me?fields=id%2Cfirst_name%2Clast_name%2Cemail%2Cgender&" + this.accessToken;
            final URL u = new URL(g);
            final URLConnection c = u.openConnection();
            final BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String inputLine;
            final StringBuffer b = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                b.append(inputLine + "\n");
            }
            in.close();
            graph = b.toString();
            System.out.println(graph);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in getting FB graph data. " + e);
        }
        return graph;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Map<String, String> getGraphData(final String fbGraph) {
        final Map fbProfile = new HashMap();
        try {
            final JSONObject json = new JSONObject(fbGraph);
            fbProfile.put("id", json.getString("id"));
            fbProfile.put("first_name", json.getString("first_name"));
            if (json.has("email")) {
                fbProfile.put("email", json.getString("email"));
            }
            if (json.has("gender")) {
                fbProfile.put("gender", json.getString("gender"));
            }
        } catch (final JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in parsing FB graph data. " + e);
        }
        return fbProfile;
    }
}
