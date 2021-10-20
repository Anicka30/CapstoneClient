package za.ac.cput.Factory;
/*lIAM STEWART
219084394
GROUP 21*/
import za.ac.cput.Entity.Classroom;

public class ClassroomFactory {
    public static Classroom createClassroom(String classCode){

        Classroom classroom = new Classroom.ClassBuilder().setClassCode(classCode).build();
        return classroom;
    }
}
