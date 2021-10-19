package za.ac.cput.Factory;

import za.ac.cput.Entity.Classroom;

public class ClassroomFactory {
    public static Classroom createClassroom(String classCode){

        Classroom classroom = new Classroom.ClassBuilder().setClassCode(classCode).build();
        return classroom;
    }
}
