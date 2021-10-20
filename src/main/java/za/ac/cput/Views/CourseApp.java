package za.ac.cput.Views;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.Entity.Course;

import java.io.IOException;
import java.util.ArrayList;

public class CourseApp {

    private static OkHttpClient client = new OkHttpClient();

    private static String run(String URL) throws IOException {
        Request request = new Request.Builder()
                .url(URL)
                .build();

        try(Response response = client.newCall(request).execute()){
            return response.body().string();
        }
    }
    public static ArrayList<Course> getAll(){
        ArrayList cList = new ArrayList();
        try{
            final String URL = "http://localhost:8080/course/getall";
            String responseBody = run(URL);
            JSONArray courses = new JSONArray(responseBody);

            for(int i = 0; i < courses.length(); i++){
                JSONObject course = courses.getJSONObject(i);

                Gson g = new Gson();
                Course c = g.fromJson(course.toString(), Course.class);
                System.out.println(c.toString());
                cList.add(c);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return cList;
    }

    public static void main(String[] args) {
        getAll();
    }
}
