package za.ac.cput.Views;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import za.ac.cput.Entity.Lecturer;

import java.io.IOException;

public class LecturerApp {
    private static OkHttpClient client = new OkHttpClient();

    private static String run(String URL) throws IOException {
        Request request = new Request.Builder().url(URL).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void getAll(){
        try {
            final String URL = "http://localhost:3306/lecture/getall";
            String responseBody = run(URL);
            JSONArray lecturer = new JSONArray(responseBody);

            for(int i=0; i< lecturer.length(); i++){

                JSONArray lecturers = lecturer.getJSONArray(i);

                Gson g= new Gson();
                Lecturer l= g.fromJson(lecturers.toString(), Lecturer.class);
                System.out.println(l.toString());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        System.out.println("Hello World!");
        getAll();
    }
}
