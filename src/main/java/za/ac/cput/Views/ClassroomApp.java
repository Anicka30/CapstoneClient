package za.ac.cput.Views;
/*lIAM STEWART
219084394
GROUP 21*/
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.Entity.Classroom;
import za.ac.cput.Entity.Subject;

import java.io.IOException;
import java.util.ArrayList;

public class ClassroomApp {
    private static OkHttpClient client = new OkHttpClient();

    private static String run(String URL) throws IOException {
        Request request = new Request.Builder()
                .url(URL)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static ArrayList<Classroom> getAll(){
        ArrayList classroomList = new ArrayList();
        try {
            final String URL = "http://localhost:8080/classroom/getall";
            String responseBody = run(URL);
            JSONArray classrooms = new JSONArray(responseBody);

            for (int i = 0; i < classrooms.length(); i++) {
                JSONObject classroom = classrooms.getJSONObject(i);

                Gson g = new Gson();
                Classroom c = g.fromJson(classroom.toString(), Classroom.class);
                System.out.println(c.toString());
                classroomList.add(c);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return classroomList;
    }

    public static void main( String[] args )
    {
        getAll();
    }
}
