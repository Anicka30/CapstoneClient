package za.ac.cput.Views;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import za.ac.cput.Entity.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteCourseView extends JFrame implements ActionListener {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JPanel panelNorth, panelSouth, panelCenter;
    private JLabel lblCourseCode, lblCourseName;
    private JTextField txtCourseCode, txtCourseName;
    private JButton btnCreate, btnBack, btnDel, btnView, btnClear, btnUpdate;
    private JComboBox courseCodes;


    ArrayList<Course> list = new ArrayList<>(CourseApp.getAll());
    public ArrayList<String> comboBoxVal(){

        ArrayList<String> listOfCourses = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            listOfCourses.add(list.get(i).getCourseCode());
        }
        return listOfCourses;
    }

    public DeleteCourseView(){
        super("Delete Course");
        panelNorth = new JPanel();
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        lblCourseCode = new JLabel("Course Code: ");
        lblCourseName = new JLabel("Course Name: ");
        txtCourseCode = new JTextField();
        txtCourseName = new JTextField();
        btnCreate = new JButton("Create");
        btnBack = new JButton("Back");
        btnDel = new JButton("Delete");
        btnView = new JButton("View");
        btnClear = new JButton("Clear");
        btnUpdate = new JButton("Update");


        courseCodes = new JComboBox();
        ArrayList<String> courseInDb = comboBoxVal();

        for(String data: courseInDb){
            courseCodes.addItem(data);
            courseCodes.setSelectedIndex(-1);
        }

    }
    public void setDeleteCourseGui(){
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
        panelSouth.add(btnDel);
        panelSouth.add(btnBack);


        btnDel.addActionListener(this);
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
        if(e.getActionCommand().equals("Delete")){
            JOptionPane.showMessageDialog(null, "Can't delete");
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
}
