import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

public class Request {

    String token;

    Request () throws IOException {
        token = getAuthToken();
    }

    //get OAuth2 token
    public String getAuthToken() throws IOException {

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);

        String url = "https://www.mtstv.ru/movies";
        URLConnection connection = new URL(url).openConnection();

        connection.getContent();

        List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();
        String token = "";
        for (HttpCookie cookie : cookies) {
            if (cookie.getName().equals("mts_access_token")) {
                token = cookie.getValue();
            }
        }
        return token;
    }

    //do GET request
    public HttpURLConnection doRequest(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Authorization", "Bearer " + token);
        return connection;
    }

    //get response body
    public String getRequestJsonBody(String url) throws IOException, InterruptedException {

        HttpURLConnection connection = null;

        for (int i = 0; i < 3; i++) {
            connection = doRequest(url);
            int code = connection.getResponseCode();
            if (code != 200) {
                Thread.sleep(5000);
                token = getAuthToken();
            }
            if (code == 200) {
                break;
            }
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        String jsonString = null;
        while ((inputLine = in.readLine()) != null) {
            jsonString = inputLine;
        }
        in.close();
        return jsonString;
    }

}
