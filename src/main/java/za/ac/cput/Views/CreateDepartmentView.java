package za.ac.cput.Views;

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.Entity.Department;
import za.ac.cput.Factory.DepartmentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateDepartmentView extends JFrame implements ActionListener {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JPanel panelNorth, panelSouth, panelCenter;
    private JLabel lblDepCode, lblDepName, lblOfficeNo;
    private JTextField txtDepCode, txtDepName, txtOfficeNo;
    private JButton btnCreateDep, btnBack, btnClearDep;

    public CreateDepartmentView() {
        super("Create Department");
        panelNorth = new JPanel();
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        lblDepCode = new JLabel("Department Code: ");
        lblDepName = new JLabel("Department Name: ");
        lblOfficeNo = new JLabel("Office Number: ");
        txtDepCode = new JTextField();
        txtDepName = new JTextField();
        txtOfficeNo = new JTextField();
        btnCreateDep = new JButton("Save");
        btnBack = new JButton("Back");
        btnClearDep = new JButton("Clear");
    }

    public void setCreateDepartmentGui() {
        panelNorth.setLayout(new GridLayout(1, 1));
        panelCenter.setLayout(new GridLayout(3, 2));
        panelSouth.setLayout(new GridLayout(1, 3));

        panelCenter.setBorder(BorderFactory.createEmptyBorder(40, 15, 50, 15));
        panelSouth.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        panelCenter.add(lblDepCode);
        panelCenter.add(txtDepCode);
        panelCenter.add(lblDepName);
        panelCenter.add(txtDepName);
        panelCenter.add(lblOfficeNo);
        panelCenter.add(txtOfficeNo);

        panelSouth.add(btnCreateDep);
        panelSouth.add(btnClearDep);
        panelSouth.add(btnBack);


        btnCreateDep.addActionListener(this);
        btnClearDep.addActionListener(this);
        btnBack.addActionListener(this);

        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setSize(500, 250);
        this.setLocationRelativeTo(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save")) {
            if(txtDepCode.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter the necessary code");
                return;
            }
            else if(txtDepName.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter the necessary name");
                return;
            }
            else if(txtOfficeNo.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter the office number");
                return;
            }
            createDep(txtDepCode.getText(), txtDepName.getText(), txtOfficeNo.getText());
            this.dispose();
            DepartmentView dp = new DepartmentView();
            dp.setDepartmentGui();
        }

        else if (e.getActionCommand().equals("Clear")) {
            txtDepCode.setText("");
            txtDepName.setText("");
            txtOfficeNo.setText("");
        }

        else if (e.getActionCommand().equals("Back")) {
            this.dispose();
            DepartmentView dv = new DepartmentView();
            dv.setDepartmentGui();
        }
    }
    public void createDep(String depCode, String depName, String officeNo){
        try{
            final String URL = "http://localhost:8080/department/create";
            Department dep = DepartmentFactory.build(depCode, depName, officeNo);
            Gson g = new Gson();
            String jsonString = g.toJson(dep);
            String d = post(URL, jsonString);
            if(d != null)
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
}
