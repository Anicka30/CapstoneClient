package za.ac.cput.Views;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.Entity.Department;

import java.io.IOException;
import java.util.ArrayList;

public class DepartmentApp {
    private static OkHttpClient client = new OkHttpClient();

    private static String run(String URL) throws IOException {
        Request request = new Request.Builder()
                .url(URL)
                .build();

        try(Response response = client.newCall(request).execute()){
            return response.body().string();
        }
    }
    public static ArrayList<Department> getAll(){
        ArrayList dList = new ArrayList();
        try{
            final String URL = "http://localhost:8080/department/getall";
            String responseBody = run(URL);
            JSONArray departments = new JSONArray(responseBody);

            for(int i = 0; i < departments.length(); i++){
                JSONObject department = departments.getJSONObject(i);

                Gson g = new Gson();
                Department d = g.fromJson(department.toString(), Department.class);
                System.out.println(d.toString());
                dList.add(d);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return dList;
    }

    public static void main(String[] args) {
        getAll();
    }
}
