package za.ac.cput.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Navigation extends JFrame implements ActionListener {
    private JButton departnment, course, subject, classroom, lecturer, student;
    private JPanel panelNorth, panelCenter, panelSouth;

    public Navigation(){
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        departnment = new JButton("Department");
        course = new JButton("Course");
        subject = new JButton("Subject");
        classroom = new JButton("Classroom");
        lecturer = new JButton("Lecturer");
        student = new JButton("Student");
    }

    public void setGUI(){

        panelNorth.setLayout(new GridLayout(1,1));
        panelCenter.setLayout(new GridLayout(6,1,3,4));
        panelSouth.setLayout(new GridLayout(1,1));

        panelCenter.setBorder(BorderFactory.createEmptyBorder(50,15,50,15));
        panelSouth.setBorder(BorderFactory.createEmptyBorder(0,15,25,15));

        panelCenter.add(departnment);
        panelCenter.add(course);
        panelCenter.add(subject);
        panelCenter.add(classroom);
        panelCenter.add(lecturer);
        panelCenter.add(student);

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        departnment.addActionListener(this);
        course.addActionListener(this);
        subject.addActionListener(this);
        classroom.addActionListener(this);
        lecturer.addActionListener(this);
        student.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(500,400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == departnment)
        {
            new DepartmentView().setDepartmentGui();
        }
        else if (e.getSource() == course)
        {
            new CourseView().setCourseGui();
        }
        else if (e.getSource() == subject)
        {
            new SubjectView().setGUI();
        }
        else if (e.getSource() == classroom)
        {
            new ClassroomView().setGUI();
        }
        else if (e.getSource() == lecturer)
        {
            new StoreLecturer().setGUI();
        }
        else if (e.getSource() == student)
        {
            //JOptionPane.showMessageDialog(null, "Currently not attatched");
            new StudentView().setGui();
        }
    }

    public static void main(String[] args) {
        new Navigation().setGUI();
    }
}
