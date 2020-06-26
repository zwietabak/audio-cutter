import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class AudioCutter {
    private String accessToken;

    public AudioCutter() {
        this.accessToken = "";
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void getPlaylistData(String accessToken, String playlistId) throws Exception {
        URL urlForGetRequest = new URL("https://api.spotify.com/v1/playlists/" + playlistId + "/tracks");
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        conection.setRequestProperty("Authorization", "Bearer " + accessToken);
        conection.setRequestProperty("Content-Type", "application/json");
        int responseCode = conection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            // print result
            // System.out.println("JSON String Result " + response.toString());

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray playlistItems = jsonObject.getJSONArray("items");
            for (int i = 0; i < playlistItems.length(); i++) {
                String songName = playlistItems.getJSONObject(i).getJSONObject("track").getString("name");
                String artistName = playlistItems.getJSONObject(i).getJSONObject("track").getJSONArray("artists").getJSONObject(0).getString("name");
                int songDuration = playlistItems.getJSONObject(i).getJSONObject("track").getInt("duration_ms");
                System.out.println("Tracknumber: " + i);
                System.out.println("Artist: " + artistName);
                System.out.println("Songname: " + songName);
                System.out.println("Duration (sec): " + (songDuration / 1000));
                System.out.println("Duration (min:sec): " + ((songDuration / 1000) / 60) + ":" + ((songDuration / 1000) % 60));
                System.out.println("-----------------------------------");
            }
        } else {
            System.out.println("GET REQUEST DID NOT WORK!");
            System.out.println(responseCode);
        }
    }

    public void authorizeApp(String clientId) throws Exception {
        URL urlForGetRequest = new URL("https://accounts.spotify" +
                ".com/authorize?client_id=" + clientId + "&response_type=code&redirect_uri=http" +
                "://localhost:8888/callback&scope=user-read-private%20user-read-email&state=34fFs29kd09");

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI("https://accounts.spotify" +
            ".com/authorize?client_id=" + clientId + "&response_type=code&redirect_uri=http" +
                    "://localhost:8888/callback&scope=user-read-private%20user-read-email&state=34fFs29kd09"));
        }
    }

    public void getApiAccessToken(String clientId, String clientSecret, String clientCode) throws  Exception {
        URL urlForGetRequest = new URL("https://accounts.spotify.com/api/token");
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("POST");
        String authData = clientId + ":" + clientSecret;
        String authDataEncoded = Base64.encode(authData.getBytes());
        String postDataParameters = "grant_type=authorization_code&code=" + clientCode + "&redirect_uri=http://localhost:8888/callback";
        byte[] postData = postDataParameters.getBytes();
        conection.setRequestProperty("Authorization", "Basic " + authDataEncoded);
        conection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conection.setDoOutput(true);
        try {
            DataOutputStream wr = new DataOutputStream(conection.getOutputStream());
            wr.write(postData);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            // print result
            // System.out.println("JSON String Result " + response.toString());
            JSONObject jsonObject = new JSONObject(response.toString());
            String accessToken =  jsonObject.getString("access_token");
            this.setAccessToken(accessToken);
            System.out.println("Token succesfully received!");
            // System.out.println("Token: " + accessToken);
        } else {
            System.out.println("POST NOT WORKED");
            System.out.println(responseCode);
        }
    }
}
