/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class HttpRequest {

    private final static String USER_AGENT = "Updater by CodeHat";

    /**
     * Sends a GET request to the api and returns the current version of the plugin.
     *
     * @param url        The url for the http request.
     * @return           The current version of the plugin.
     * @throws Exception If something went wrong.
     */
    public static String getNewVersion(String url) throws Exception {
        URL urlObj = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
        SSLSocketFactory sslSocketFactory = createSslSocketFactory();
        connection.setSSLSocketFactory(sslSocketFactory);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setReadTimeout(2000);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.toString());

        return (String) json.get("version");
    }

    /**
     * Helper method which bypasses SSL certificates like "Let's Encrypt".
     *
     * @return           A socket factory which isn't checking certificates.
     * @throws Exception On failure.
     */
    private static SSLSocketFactory createSslSocketFactory() throws Exception {
        TrustManager[] byPassTrustManagers = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        }};
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, byPassTrustManagers, new SecureRandom());
        return sslContext.getSocketFactory();
    }
}