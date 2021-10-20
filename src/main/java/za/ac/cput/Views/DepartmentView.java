package za.ac.cput.Views;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import za.ac.cput.Entity.Course;
import za.ac.cput.Entity.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DepartmentView extends JFrame implements ActionListener {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private static OkHttpClient client = new OkHttpClient();

    private JPanel panelNorth, panelSouth, panelCenter;
    private JButton btnCreate, btnBack, btnDel, btnUpdate;
    private JComboBox depCodes;
    private JTable depTable;
    private JScrollPane pane;


    ArrayList<Department> list = new ArrayList<>(DepartmentApp.getAll());
    public ArrayList<String> comboBoxVal(){

        ArrayList<String> listOfDep = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            listOfDep.add(list.get(i).getDepCode());
        }
        return listOfDep;
    }

    public DepartmentView(){
        super("Department");
        panelNorth = new JPanel();
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        btnCreate = new JButton("Create");
        btnBack = new JButton("Back");
        btnDel = new JButton("Delete");
        btnUpdate = new JButton("Update");
        depTable = new JTable();


        depCodes = new JComboBox();
        ArrayList<String> depInDb = comboBoxVal();

        for(String data: depInDb){
            depCodes.addItem(data);
            depCodes.setSelectedIndex(-1);
        }

    }
    public void setDepartmentGui(){
        panelNorth.setLayout(new GridLayout(1,1));
        panelCenter.setLayout(new GridLayout(2,2));
        panelSouth.setLayout(new GridLayout(1,6));

        panelCenter.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));
        panelSouth.setBorder(BorderFactory.createEmptyBorder(0,15,15,15));


        Object[] column = {"Department Code", "Department Name", "Office Number"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], column);


        ArrayList<Department> dAL = DepartmentApp.getAll();
        for(Department department: dAL){
            Object[] ob = new Object[3];
            ob[0] = department.getDepCode();
            ob[1] = department.getDepName();
            ob[2] = department.getOfficeNo();

            model.addRow(ob);
        }
        depTable = new JTable(model);
        pane = new JScrollPane(depTable);
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
            CreateDepartmentView cdv = new CreateDepartmentView();
            cdv.setCreateDepartmentGui();
        }
        else if(e.getActionCommand().equals("Delete")){
            JOptionPane.showMessageDialog(null, "No delete method");
        }
        else if(e.getActionCommand().equals("Update")){
            this.dispose();
            UpdateDepartmentView udv = new UpdateDepartmentView();
            udv.setUpdateDepartmentGui();
        }
        else if(e.getActionCommand().equals("Back")){
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new DepartmentView().setDepartmentGui();
    }

}
