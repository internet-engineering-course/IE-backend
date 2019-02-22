package client;

import client.models.HttpResponse;
import entities.Project;
import exceptions.DeserializeException;
import utilities.Deserializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class HttpClient {
    private static HttpResponse get(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new HttpResponse(response.toString(), responseCode);
        } catch(Exception e) {
            System.err.println("GET request failed: " + e.getMessage());
        }
        return new HttpResponse("An Error Occurred", 500);
    }

    public static List<Project> fetchAllProjects() {
        String url = "http://142.93.134.194:8000/joboonja/project";
        HttpResponse response = get(url);
        try {
            return Deserializer.deserializeList(response.getResponse(), Project.class);
        } catch (DeserializeException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }
}
