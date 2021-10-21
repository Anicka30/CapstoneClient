package za.ac.cput.Views;

/*StudentApp.java
 *@author Anicka Schouw 217284183
 * October 2021
 */

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import za.ac.cput.Entity.Student;
import java.io.IOException;
import java.util.ArrayList;

public class StudentApp {
    private static OkHttpClient client = new OkHttpClient();

    private static String run(String URL) throws IOException {
        Request request = new Request.Builder()
                .url(URL)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static ArrayList<Student> getAll(){

        ArrayList sl = new ArrayList();
        try {
            final String URL = "http://localhost:8080/subject/getall";
            String responseBody = run(URL);
            JSONArray stu = new JSONArray(responseBody);

            for (int i = 0; i < stu.length(); i++) {
                JSONObject s = stu.getJSONObject(i);

                Gson g = new Gson();
                Student st = g.fromJson(s.toString(), Student.class);
                System.out.println(s.toString());
                sl.add(s);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sl;
    }

    public static void main( String[] args )
    {
        getAll();
    }
}