package za.ac.cput.Views;
/*lIAM STEWART
219084394
GROUP 21*/
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import za.ac.cput.Entity.Subject;

import java.io.IOException;
import java.util.ArrayList;

public class SubjectApp {

    private static OkHttpClient client = new OkHttpClient();

    private static String run(String URL) throws IOException {
        Request request = new Request.Builder()
                .url(URL)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static ArrayList<Subject> getAll(){

        ArrayList subjectList = new ArrayList();
        try {
            final String URL = "http://localhost:8080/subject/getall";
            String responseBody = run(URL);
            JSONArray subjects = new JSONArray(responseBody);

            for (int i = 0; i < subjects.length(); i++) {
                JSONObject subject = subjects.getJSONObject(i);

                Gson g = new Gson();
                Subject s = g.fromJson(subject.toString(), Subject.class);
                System.out.println(s.toString());
                subjectList.add(s);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return subjectList;
    }

    public static void main( String[] args )
    {
        getAll();
    }
}
