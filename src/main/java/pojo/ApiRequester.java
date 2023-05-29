package pojo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiRequester {
    public static String makeApiRequest(String apiUrl)
    {
        StringBuilder response = new StringBuilder();
        try{

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null)
                {
                    response.append(line);
                }
                reader.close();
            } else {
                System.out.println("Error response code: " + responseCode);
            }
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }
}
