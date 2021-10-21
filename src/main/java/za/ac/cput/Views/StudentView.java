package za.ac.cput.Views;

/*StudentView.java
 * gui code
 *@author Anicka Schouw 217284183
 * October 2021
 */

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.Entity.Student;
import za.ac.cput.Factory.StudentFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class StudentView extends JFrame implements ActionListener {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient c = new OkHttpClient();

    ArrayList<Student> l = new ArrayList<>(StudentApp.getAll());

    public ArrayList<String> displayStList(){
        ArrayList<String> showing = new ArrayList<>();
        for (int i = 0; i < l.size(); i++){
            showing.add(l.get(i).getStudentNo());
        }
        return showing;
    }

    private JLabel stInfo;
    private JPanel topPanel;
    private JPanel sidePanel;
    private JPanel side2Panel;
    private JPanel bottomPanel;
    private JLabel stNo, stN, stS,stE;
    private JTextField stNoT, stNT, stST, stET;
    private JButton insertB, deleteB, updateB;

    public StudentView(){
        super("Student Registration Details");
        stInfo = new JLabel("Student Information");
        stNo = new JLabel("Student Number:");
        stN = new JLabel("Student Name:");
        stS = new JLabel("Student Surname:");
        stE = new JLabel("Student Email");
        stNoT = new JTextField(10);
        stNT = new JTextField(10);
        stST = new JTextField(10);
        stET = new JTextField(20);
        insertB = new JButton("Insert");
        deleteB = new JButton("Delete");
        updateB = new JButton("Update");
    }

    public void setGui(){
        topPanel.setLayout(new GridLayout(1,1));
        sidePanel.setLayout(new GridLayout(4,1));
        side2Panel.setLayout(new GridLayout(4,1));
        bottomPanel.setLayout(new GridLayout(1,3));

        topPanel.add(stInfo);

        sidePanel.add(stNo);
        sidePanel.add(stN);
        sidePanel.add(stS);
        sidePanel.add(stE);

        side2Panel.add(stNoT);
        side2Panel.add(stNT);
        side2Panel.add(stST);
        side2Panel.add(stET);

        bottomPanel.add(insertB);
        bottomPanel.add(deleteB);
        bottomPanel.add(updateB);

        insertB.addActionListener(this);
        deleteB.addActionListener(this);
        updateB.addActionListener(this);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(sidePanel, BorderLayout.EAST);
        this.add(side2Panel, BorderLayout.WEST);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
    }

    @Override
    public void actionPerformed(ActionEvent et){
        if(et.getSource()== insertB){
            save(stNoT.getText()
                    ,stNT.getText()
                    ,stST.getText()
                    ,stET.getText());
            stNoT.setText("");
            stNT.setText("");
            stST.setText(" ");
            stET.setText(" ");
            //new StudentView().setGUI();
        }else if (et.getSource()== deleteB){
            String item = stNoT.getText();
            change(item);
            //new StudentView().setGUI();
        }else if(et.getSource() == updateB){
            alter(stNoT.getText(),stNT.getText(),stST.getText(),stET.getText());
            stNoT.setText("");
            stNT.setText("");
            stST.setText(" ");
            stET.setText(" ");
            //new StudentView().setGUI();
        }
    }

    public void save(String studentNo, String stFname, String stLname, String stEmail){
        try {
            final String URL = "http://localhost:8080/student/create";
            Student st = StudentFactory.build(studentNo,stFname,stLname,stEmail);
            Gson g = new Gson();
            String jsonString = g.toJson(st);
            String s = post(URL, jsonString);
            if (s != null)
                JOptionPane.showMessageDialog(null, "Successfully saved");
            else
                JOptionPane.showMessageDialog(null, "Unfortunately cannot save");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public String post(final String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = c.newCall(request).execute()){
            return response.body().string();
        }
    }

    public void change(String deleted){
        try {
            final String URL = "http://localhost:8080/student/delete/"+deleted;

            Gson g = new Gson();
            String jsonString = g.toJson(deleted);
            String p = post(URL, jsonString);
            if (p != null)
                JOptionPane.showMessageDialog(null, "Item has been Deleted");
            else
                JOptionPane.showMessageDialog(null, "Could not delete");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void alter(String studentNo, String stFname, String stLname, String stEmail){
        try {
            final String URL = "http://localhost:8080/student/update";
            Student stu = StudentFactory.build(studentNo,stFname,stLname,stEmail);
            Gson gn = new Gson();
            String jsonString = gn.toJson(stu);
            String p = post(URL, jsonString);
            if (p != null)
                JOptionPane.showMessageDialog(null, "Item has been Updated");
            else
                JOptionPane.showMessageDialog(null, "Unfortantely could not update");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new StudentView().setGUI();
    }

}
