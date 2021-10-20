package za.ac.cput.Views;

import okhttp3.*;
import za.ac.cput.Entity.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CourseView extends JFrame implements ActionListener {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JPanel panelNorth, panelSouth, panelCenter;
    private JLabel lblCourseCode, lblCourseName;
    private JTextField txtCourseCode, txtCourseName;
    private JButton btnCreate, btnBack, btnDel, btnView, btnClear, btnUpdate;
    private JComboBox courseCodes;
    private JTable courseTable;
    private JScrollPane pane;


    ArrayList<Course> list = new ArrayList<>(CourseApp.getAll());
    public ArrayList<String> comboBoxVal(){

        ArrayList<String> listOfCourses = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            listOfCourses.add(list.get(i).getCourseCode());
        }
        return listOfCourses;
    }

    public CourseView(){
        super("Course");
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
        btnUpdate = new JButton("Update");
        courseTable = new JTable();


        courseCodes = new JComboBox();
        ArrayList<String> courseInDb = comboBoxVal();

        for(String data: courseInDb){
            courseCodes.addItem(data);
            courseCodes.setSelectedIndex(-1);
            }

    }
    public void setCourseGui(){
        panelNorth.setLayout(new GridLayout(1,1));
        panelCenter.setLayout(new GridLayout(2,2));
        panelSouth.setLayout(new GridLayout(1,6));

        panelCenter.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));
        panelSouth.setBorder(BorderFactory.createEmptyBorder(0,15,15,15));


        Object[] column = {"Course Code", "Course Name"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], column);


        ArrayList<Course> cAL = CourseApp.getAll();
        for(Course course: cAL){
            Object[] ob = new Object[2];
            ob[0] = course.getCourseCode();
            ob[1] = course.getCourseName();

            model.addRow(ob);
        }
        courseTable = new JTable(model);
        pane = new JScrollPane(courseTable);
        pane.setSize(400,175);

        panelCenter.add(pane);

        panelSouth.add(btnCreate);
        panelSouth.add(btnUpdate);
        panelSouth.add(btnDel);
        panelSouth.add(btnBack);


        btnCreate.addActionListener(this);
        btnBack.addActionListener(this);
        btnDel.addActionListener(this);
        btnUpdate.addActionListener(this);

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

        if(e.getActionCommand().equals("Create")){
            this.dispose();
            CreateCourseView ccv = new CreateCourseView();
            ccv.setCreateCourseGui();
        }
        else if(e.getActionCommand().equals("Delete")){
            this.dispose();
            DeleteCourseView dcv = new DeleteCourseView();
            dcv.setDeleteCourseGui();
        }
        else if(e.getActionCommand().equals("Update")){
            this.dispose();
            UpdateCourseView ucv = new UpdateCourseView();
            ucv.setUpdateCourseGui();
        }
        else if(e.getActionCommand().equals("Back")){
            this.dispose();
        }
    }



    public static void main(String[] args) {
        new CourseView().setCourseGui();
    }
}
