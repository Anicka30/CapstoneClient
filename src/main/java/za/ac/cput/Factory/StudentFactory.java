package za.ac.cput.Factory;

import za.ac.cput.Entity.Student;

/*StudentFactory.java
 * Student Factory code
 * @author Anicka Schouw 217284183
 * October2021
 */
public class StudentFactory {

    public static Student build(String studentNo, String stFname, String stLname, String stEmail){

        if(stFname.isEmpty() && stLname.isEmpty()){
            return null;
        }

        return new Student.StudentBuilder().setStudentNo(studentNo)
                .setStFname(stFname).setStLname(stLname).setStEmail(stEmail).build();
    }

}
