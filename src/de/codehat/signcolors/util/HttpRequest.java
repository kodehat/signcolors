package de.codehat.signcolors.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpRequest
 * @author CodeHat
 */

public class HttpRequest {

	private final static String USER_AGENT = "Updater by CodeHat";

	/**
	 * Sends a GET request to the given URL.
	 * @param url The URL to send the request.
	 * @return The result of the GET request.
	 * @throws Exception Something went wrong.
	 */
	public static String sendGet(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setReadTimeout(2000);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}
}