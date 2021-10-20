package za.ac.cput.Views;

/*lIAM STEWART
219084394
GROUP 21*/

import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.Entity.Classroom;
import za.ac.cput.Entity.Subject;
import za.ac.cput.Factory.ClassroomFactory;
import za.ac.cput.Factory.SubjectFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class ClassroomView extends JFrame implements ActionListener {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    ArrayList<Classroom> list = new ArrayList<>(ClassroomApp.getAll());
    private JLabel classCode,addClass;
    private JButton addBtn, clearBtn, updateBtn, deleteBtn, loadBtn, btnCancel;
    private JPanel panelNorth, panelCenter, panelSouth;
    private JComboBox classCodeBox;
    private JTextField txtClassCode;

    public ArrayList<String> loadClassroomList(){
        ArrayList<String> loadedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            loadedList.add(list.get(i).getClassCode());
        }
        return loadedList;
    }

    public void Clear(){
        classCodeBox.setSelectedItem(null);
        txtClassCode.setText("");
    }

    public ClassroomView(){
        super("Classroom Information");
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();
        classCode = new JLabel("Classroom Codes",SwingConstants.LEFT);
        addClass = new JLabel("Add Classroom",SwingConstants.LEFT);

        classCodeBox = new JComboBox(new Vector(loadClassroomList()));

        txtClassCode = new JTextField();

        addBtn = new JButton("Add");
        clearBtn = new JButton("Clear");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");
        btnCancel = new JButton("Cancel");
    }

    public void setGUI(){
        panelNorth.setLayout(new GridLayout(1,1));
        panelCenter.setLayout(new GridLayout(2,2,3,4));
        panelSouth.setLayout(new GridLayout(1,6));

        panelCenter.setBorder(BorderFactory.createEmptyBorder(50,15,50,15));
        panelSouth.setBorder(BorderFactory.createEmptyBorder(0,15,25,15));

        panelCenter.add(classCode);
        panelCenter.add(classCodeBox);
        panelCenter.add(addClass);
        panelCenter.add(txtClassCode);



        panelSouth.add(addBtn);
        panelSouth.add(clearBtn);
        panelSouth.add(updateBtn);
        panelSouth.add(deleteBtn);
        panelSouth.add(loadBtn);
        panelSouth.add(btnCancel);

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        addBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        btnCancel.addActionListener(this);
        deleteBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        updateBtn.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            store(txtClassCode.getText());
            this.dispose();
            new ClassroomView().setGUI();
        }else if(e.getSource() == btnCancel){
            System.exit(0);
        }else if (e.getSource() == clearBtn){
            Clear();
        }else if (e.getSource() == deleteBtn) {
            int selected = classCodeBox.getSelectedIndex();
            String delete = list.get(selected).getClassCode();
            remove(delete);
        }else if (e.getSource() == loadBtn){
            int selected = classCodeBox.getSelectedIndex();
            String code = list.get(selected).getClassCode();
            txtClassCode.setText(code);
        }
    }

    public void store(String classCode){
        try {
            final String URL = "http://localhost:8080/classroom/create";
            Classroom classroom = ClassroomFactory.createClassroom(classCode);
            Gson g = new Gson();
            String jsonString = g.toJson(classroom);
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
            final String URL = "http://localhost:8080/classroom/delete/"+deleted;

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



    public static void main(String[] args) {
        new ClassroomView().setGUI();
    }
}
