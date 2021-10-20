package za.ac.cput.Views;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.Entity.Course;
import za.ac.cput.Factory.CourseFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class CreateCourseView extends JFrame implements ActionListener {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JPanel panelNorth, panelSouth, panelCenter;
    private JLabel lblCourseCode, lblCourseName;
    private JTextField txtCourseCode, txtCourseName;
    private JButton btnCreate, btnBack, btnDel, btnUpdate, btnClear;

    public CreateCourseView(){
        super("Create Course");
        panelNorth = new JPanel();
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        lblCourseCode = new JLabel("Course Code: ");
        lblCourseName = new JLabel("Course Name: ");
        txtCourseCode = new JTextField();
        txtCourseName = new JTextField();
        btnCreate = new JButton("Save");
        btnBack = new JButton("Back");
        //btnDel = new JButton("Delete");
        //btnUpdate = new JButton("Update");
        btnClear = new JButton("Clear");
    }
    public void setCreateCourseGui(){
        panelNorth.setLayout(new GridLayout(1,1));
        panelCenter.setLayout(new GridLayout(2,2));
        panelSouth.setLayout(new GridLayout(1,2));

        panelCenter.setBorder(BorderFactory.createEmptyBorder(50,15,50,15));
        panelSouth.setBorder(BorderFactory.createEmptyBorder(0,15,15,15));


        panelCenter.add(lblCourseCode);
        panelCenter.add(txtCourseCode);
        panelCenter.add(lblCourseName);
        panelCenter.add(txtCourseName);

        panelSouth.add(btnCreate);
        panelSouth.add(btnClear);
        panelSouth.add(btnBack);

        btnCreate.addActionListener(this);
        btnBack.addActionListener(this);
        btnClear.addActionListener(this);

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
        if(e.getActionCommand().equals("Save")){
            if(txtCourseCode.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter the necessary code");
                return;
            }
            else if(txtCourseName.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter the necessary name");
                return;
            }
            createCourse(txtCourseCode.getText(), txtCourseName.getText());
            this.dispose();
            CourseView cv = new CourseView();
            cv.setCourseGui();

        }
        else if(e.getActionCommand().equals("Clear")){
            txtCourseCode.setText("");
            txtCourseName.setText("");
        }
        else if(e.getActionCommand().equals("Back")){
            this.dispose();
            CourseView cv = new CourseView();
            cv.setCourseGui();
        }
    }

    public void createCourse(String courseCode, String courseName){
        try{
            final String URL = "http://localhost:8080/course/create";
            Course course = CourseFactory.build(courseCode, courseName);
            Gson g = new Gson();
            String jsonString = g.toJson(course);
            String c = post(URL, jsonString);
            if(c != null)
                JOptionPane.showMessageDialog(null, "Successfully saved");
            else
                JOptionPane.showMessageDialog(null, "Sorry, could not store");
        }catch(Exception e){
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

    public static void main(String[] args) {
        new CreateCourseView().setCreateCourseGui();
    }
}
