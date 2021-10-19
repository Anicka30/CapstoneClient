package za.ac.cput.Entity;

import java.io.Serializable;

public class Classroom implements Serializable {
    private String classCode;

    private Classroom(ClassBuilder builder){

        this.classCode = builder.classCode;
    }

    public String getClassCode() {
        return classCode;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "classCode='" + classCode + '\'' +
                '}';
    }

    public static class ClassBuilder{
        private String classCode;

        public ClassBuilder setClassCode(String classCode){
            this.classCode = classCode;
            return this;
        }
        public Classroom.ClassBuilder copy(Classroom classroom){
            this.classCode = classroom.classCode;
            return this;
        }

        public Classroom build(){
            return new Classroom(this);
        }

    }
}
