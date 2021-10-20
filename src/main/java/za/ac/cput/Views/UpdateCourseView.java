package za.ac.cput.Views;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.Entity.Course;
import za.ac.cput.Factory.CourseFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class UpdateCourseView extends JFrame implements ActionListener {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JPanel panelNorth, panelSouth, panelCenter;
    private JLabel lblCourseCode, lblCourseName;
    private JTextField  txtCourseName;
    private JButton  btnBack, btnView, btnUpdate;
    private JComboBox courseCodes;


    ArrayList<Course> list = new ArrayList<>(CourseApp.getAll());
    public ArrayList<String> comboBoxVal(){

        ArrayList<String> listOfCourses = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            listOfCourses.add(list.get(i).getCourseCode());
        }
        return listOfCourses;
    }

    public UpdateCourseView(){
        super("Update Course");
        panelNorth = new JPanel();
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        lblCourseCode = new JLabel("Course Code: ");
        lblCourseName = new JLabel("Course Name: ");
        txtCourseName = new JTextField();
        btnBack = new JButton("Back");
        btnView = new JButton("View");
        btnUpdate = new JButton("Update");


        courseCodes = new JComboBox();
        ArrayList<String> courseInDb = comboBoxVal();

        for(String data: courseInDb){
            courseCodes.addItem(data);
            courseCodes.setSelectedIndex(-1);
        }

    }
    public void setUpdateCourseGui(){
        panelNorth.setLayout(new GridLayout(1,1));
        panelCenter.setLayout(new GridLayout(2,2));
        panelSouth.setLayout(new GridLayout(1,6));

        panelCenter.setBorder(BorderFactory.createEmptyBorder(50,15,50,15));
        panelSouth.setBorder(BorderFactory.createEmptyBorder(0,15,15,15));


        panelCenter.add(lblCourseCode);
        panelCenter.add(courseCodes);
        panelCenter.add(lblCourseName);
        panelCenter.add(txtCourseName);

        panelSouth.add(btnView);
        panelSouth.add(btnUpdate);
        panelSouth.add(btnBack);


        btnUpdate.addActionListener(this);
        btnBack.addActionListener(this);
        btnView.addActionListener(this);

        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(500,250);
        this.setLocationRelativeTo(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Update")){
            update(courseCodes.getSelectedItem().toString(), txtCourseName.getText());
            this.dispose();
            CourseView cv = new CourseView();
            cv.setCourseGui();
        }
        else if(e.getActionCommand().equals("View")){

            int selected = courseCodes.getSelectedIndex();
            String name = list.get(selected).getCourseName();
            txtCourseName.setText(name);
        }
        else if(e.getActionCommand().equals("Back")){
            this.dispose();
            CourseView cv = new CourseView();
            cv.setCourseGui();
        }
    }

    public void update(String courseCode, String courseName){
        try{
            final String URL = "http://localhost:8080/course/update";
            Course course = CourseFactory.build(courseCode, courseName);
            Gson g = new Gson();
            String jsonString = g.toJson(course);
            String c = post(URL, jsonString);
            if(c != null)
                JOptionPane.showMessageDialog(null,"Successfully updated");
            else
                JOptionPane.showMessageDialog(null, "Failed to update");
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public String post(final String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try(Response response = client.newCall(request).execute()){
            return response.body().string();
        }

    }
}
