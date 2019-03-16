package ir.ac.ut.joboonja.client;

import ir.ac.ut.joboonja.client.models.HttpResponse;
import ir.ac.ut.joboonja.entities.Project;
import ir.ac.ut.joboonja.entities.Skill;
import ir.ac.ut.joboonja.exceptions.SerializeException;
import ir.ac.ut.joboonja.utilities.Deserializer;

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
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),  "UTF-8"));
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
        } catch (SerializeException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    public static List<Skill> fetchAllSkills() {
        String url = "http://142.93.134.194:8000/joboonja/skill";
        HttpResponse response = get(url);
        try {
            return Deserializer.deserializeList(response.getResponse(), Skill.class);
        } catch (SerializeException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }
}
