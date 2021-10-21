package za.ac.cput.Views;


import com.google.gson.Gson;
import okhttp3.*;
import za.ac.cput.Entity.Lecturer;
import za.ac.cput.Factory.LecturerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StoreLecturer extends JFrame implements ActionListener {
public static final MediaType JSON
        = MediaType.get("application/json; charset=utf-8");

private static OkHttpClient client = new OkHttpClient();


    private JLabel lblName;
    private JTextField txtName;
    private JLabel lblSurname;
    private JTextField txtSurname;
    private JLabel lblIdentity;
    private JTextField txtIdentity;
    private JButton btnSave;
    private JButton btnCancel;

public StoreLecturer(){

        super("Create new Lecturer");
        lblName = new JLabel("first name:");
        txtName = new JTextField("Auto-generated-");
        txtName.setEnabled(false);

        lblSurname = new JLabel("surname:");
        txtSurname = new JTextField(30);

        lblIdentity = new JLabel("Identity:");
        txtIdentity = new JTextField(30);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");

}
    public void setGUI() {
        this.setLayout(new GridLayout(8, 2));
        this.add(txtName);
        this.add(lblName);
        this.add(txtSurname);
        this.add(lblSurname);
        this.add(txtIdentity);
        this.add(lblIdentity);
        this.add(btnSave);
        this.add(btnCancel);

        btnSave.addActionListener(this);
        btnCancel.addActionListener(this);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            store(txtName.getText(),txtSurname.getText(),txtIdentity.getText());
        } else if (e.getSource() == btnCancel) {
            this.dispose();
        }

}

    public void store(String Name, String Surname, String Id) {

     try {
         final String URL = "http//localhost:3306/lecturer/create";
         Lecturer lecturer = LecturerFactory.createLecturer("asive", "madladla", "2021");
         Gson g = new Gson();
         String jsonString = g.toJson(lecturer);
         String l = post(URL, jsonString);
         if (l != null)
             JOptionPane.showMessageDialog(null, "Saved successfully");
         else
             JOptionPane.showMessageDialog(null, "Not saved");
     } catch (Exception e) {
         JOptionPane.showMessageDialog(null, e.getMessage());
     }
}

    public String post(final String url, String json) throws IOException
    {
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try(Response response = client.newCall(request).execute())
        {
            return response.body().string();
        }
    }

    public static void main(String[] args) {
        new StoreLecturer().setGUI();
    }


}
