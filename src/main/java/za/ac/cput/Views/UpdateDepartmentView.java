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
import java.util.ArrayList;

public class UpdateDepartmentView extends JFrame implements ActionListener {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JPanel panelNorth, panelSouth, panelCenter;
    private JLabel lblDepCode, lblDepName, lblOfficeNo;
    private JTextField  txtDepName, txtOfficeNo;
    private JButton  btnBack, btnView, btnUpdate;
    private JComboBox depCodes;


    ArrayList<Department> list = new ArrayList<>(DepartmentApp.getAll());
    public ArrayList<String> comboBoxValue(){

        ArrayList<String> listOfDep = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            listOfDep.add(list.get(i).getDepCode());
        }
        return listOfDep;
    }

    public UpdateDepartmentView(){
        super("Update Department");
        panelNorth = new JPanel();
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        lblDepCode = new JLabel("Department Code: ");
        lblDepName = new JLabel("Department Name: ");
        txtDepName = new JTextField();
        lblOfficeNo = new JLabel("Office No: ");
        txtOfficeNo = new JTextField();
        btnBack = new JButton("Back");
        btnView = new JButton("View");
        btnUpdate = new JButton("Update");


        depCodes = new JComboBox();
        ArrayList<String> depInDb = comboBoxValue();

        for(String data: depInDb){
            depCodes.addItem(data);
            depCodes.setSelectedIndex(-1);
        }

    }
    public void setUpdateDepartmentGui(){
        panelNorth.setLayout(new GridLayout(1,1));
        panelCenter.setLayout(new GridLayout(3,2));
        panelSouth.setLayout(new GridLayout(1,6));

        panelCenter.setBorder(BorderFactory.createEmptyBorder(50,15,50,15));
        panelSouth.setBorder(BorderFactory.createEmptyBorder(0,15,15,15));


        panelCenter.add(lblDepCode);
        panelCenter.add(depCodes);
        panelCenter.add(lblDepName);
        panelCenter.add(txtDepName);
        panelCenter.add(lblOfficeNo);
        panelCenter.add(txtOfficeNo);

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
            update(depCodes.getSelectedItem().toString(), txtDepName.getText(), txtOfficeNo.getText());
            this.dispose();
            DepartmentView dv = new DepartmentView();
            dv.setDepartmentGui();
        }
        else if(e.getActionCommand().equals("View")){

            int a = depCodes.getSelectedIndex();
            String name = list.get(a).getDepName();
            String num = list.get(a).getOfficeNo();
            txtDepName.setText(name);
            txtOfficeNo.setText(num);
        }
        else if(e.getActionCommand().equals("Back")){
            this.dispose();
            DepartmentView dv = new DepartmentView();
            dv.setDepartmentGui();
        }
    }

    public void update(String depCode, String depName, String officeNo){
        try{
            final String URL = "http://localhost:8080/department/update";
            Department dep = DepartmentFactory.build(depCode, depName, officeNo);
            Gson g = new Gson();
            String jsonString = g.toJson(dep);
            String d = post(URL, jsonString);
            if(d != null)
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
