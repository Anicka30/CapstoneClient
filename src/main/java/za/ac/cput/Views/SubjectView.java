package za.ac.cput.Views;
/*lIAM STEWART
219084394
GROUP 21*/
import com.google.gson.Gson;
import okhttp3.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import za.ac.cput.Entity.Subject;
import za.ac.cput.Factory.SubjectFactory;

public class SubjectView extends JFrame implements ActionListener {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    ArrayList<Subject> list = new ArrayList<>(SubjectApp.getAll());
    private JLabel addSubCode, subjectCode, subjectName, subjectTime, subjectDate;
    private JButton addBtn, clearBtn, updateBtn, deleteBtn, loadBtn, btnCancel;
    private JPanel panelNorth, panelCenter, panelSouth;
    private JComboBox sbCodeBox;
    private JTextField txtSubCode, txtSubName, txtSubDate, txtSubTime;

    public ArrayList<String> loadSubjectList(){
        ArrayList<String> loadedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            loadedList.add(list.get(i).getSubjectCode());
        }
        return loadedList;
    }

    public void Clear(){
        sbCodeBox.setSelectedItem(null);
        txtSubCode.setText("");
        txtSubName.setText("");
        txtSubDate.setText("");
        txtSubTime.setText("");
    }



    public SubjectView(){
        super("Subject Information");
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
        addSubCode = new JLabel("Add Subject Code",SwingConstants.LEFT);
        subjectCode = new JLabel("Subject Code",SwingConstants.LEFT);
        subjectName = new JLabel("Subject Name",SwingConstants.LEFT);
        subjectDate = new JLabel("Subject Date",SwingConstants.LEFT);
        subjectTime = new JLabel("Subject Time",SwingConstants.LEFT);
        sbCodeBox = new JComboBox(new Vector(loadSubjectList()));

        txtSubCode = new JTextField();
        txtSubName = new JTextField();
        txtSubDate = new JTextField();
        txtSubDate.setColumns(10);
        txtSubTime = new JTextField();

        addBtn = new JButton("Add");
        clearBtn = new JButton("Clear");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");
        btnCancel = new JButton("Cancel");
    }

    public void setGUI(){
        sbCodeBox.setSelectedItem(null);
        panelNorth.setLayout(new GridLayout(1,1));
        panelCenter.setLayout(new GridLayout(5,2,3,4));
        panelSouth.setLayout(new GridLayout(1,6));

        panelCenter.setBorder(BorderFactory.createEmptyBorder(50,15,50,15));
        panelSouth.setBorder(BorderFactory.createEmptyBorder(0,15,25,15));

        panelCenter.add(subjectCode);
        panelCenter.add(sbCodeBox);
        panelCenter.add(addSubCode);
        panelCenter.add(txtSubCode);
        panelCenter.add(subjectName);
        panelCenter.add(txtSubName);
        panelCenter.add(subjectDate);
        panelCenter.add(txtSubDate);
        panelCenter.add(subjectTime);
        panelCenter.add(txtSubTime);

        panelSouth.add(addBtn);
        panelSouth.add(clearBtn);
        panelSouth.add(updateBtn);
        panelSouth.add(deleteBtn);
        panelSouth.add(loadBtn);
        panelSouth.add(btnCancel);
        addBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        btnCancel.addActionListener(this);
        deleteBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        updateBtn.addActionListener(this);

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn){
            store(txtSubCode.getText(),txtSubName.getText(),txtSubDate.getText(),txtSubTime.getText());
            this.dispose();
            new SubjectView().setGUI();
        }else if (e.getSource() == btnCancel){
            System.exit(0);
        }else if (e.getSource() == clearBtn){
            Clear();
        }else if (e.getSource() == deleteBtn) {
            int selected = sbCodeBox.getSelectedIndex();
            String delete = list.get(selected).getSubjectCode();
            remove(delete);
        }else if (e.getSource() == loadBtn){
            int selected = sbCodeBox.getSelectedIndex();
            String code = list.get(selected).getSubjectCode();
            String name = list.get(selected).getSubjectName();
            String date = list.get(selected).getDate();
            String time = list.get(selected).getTime();
            txtSubCode.setText(code);
            txtSubName.setText(name);
            txtSubDate.setText(date);
            txtSubTime.setText(time);
        }else if (e.getSource() == updateBtn){
            update(sbCodeBox.getSelectedItem().toString(),txtSubName.getText(),txtSubDate.getText(),txtSubTime.getText());
            this.dispose();
            new SubjectView().setGUI();
        }
    }

    public void store(String subjectCode, String subjectName, String subjectDate, String subjectTime){
        try {
            final String URL = "http://localhost:8080/subject/create";
            Subject subject = SubjectFactory.createSubject(subjectCode,subjectName,subjectDate,subjectTime);
            Gson g = new Gson();
            String jsonString = g.toJson(subject);
            String s = post(URL, jsonString);
            if (s != null)
                JOptionPane.showMessageDialog(null, "Saved");
            else
                JOptionPane.showMessageDialog(null, "Sorry could not save");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public String post(final String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()){
            return response.body().string();
        }
    }

    public void remove(String deleted){
        try {
            final String URL = "http://localhost:8080/subject/delete/"+deleted;

            Gson g = new Gson();
            String jsonString = g.toJson(deleted);
            String p = post(URL, jsonString);
            if (p != null)
                JOptionPane.showMessageDialog(null, "Deleted");
            else
                JOptionPane.showMessageDialog(null, "Could not delete");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void update(String code, String name, String date, String time){
        try {
            final String URL = "http://localhost:8080/subject/update";
            Subject s = SubjectFactory.createSubject(code,name,date,time);
            Gson g = new Gson();
            String jsonString = g.toJson(s);
            String p = post(URL, jsonString);
            if (p != null)
                JOptionPane.showMessageDialog(null, "Updated");
            else
                JOptionPane.showMessageDialog(null, "Could not update");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void main(String[] args) {
        new SubjectView().setGUI();
    }
}
