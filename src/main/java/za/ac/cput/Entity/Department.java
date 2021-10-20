package za.ac.cput.Entity;

/* EntityFactory.java
 Department Entity
 Author: Keenan Solomons (219264228)
 Date: 09 June 2021
*/

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Department implements Serializable {

    @Id
    private String depCode;
    private String depName;
    private String officeNo;

    public Department(){}

    private Department(Builder builder){
        this.depCode = builder.depCode;
        this.depName = builder.depName;
        this.officeNo = builder.officeNo;
    }

    public String getDepCode() {
        return depCode;
    }

    public String getDepName() {
        return depName;
    }

    public String getOfficeNo() {
        return officeNo;
    }

    @Override
    public String toString() {
        return "Department{" +
                "depCode='" + depCode + '\'' +
                ", depName='" + depName + '\'' +
                ", officeNo=" + officeNo +
                '}';
    }

    public static class Builder{
        private String depCode;
        private String depName;
        private String officeNo;

        public Builder setDepCode(String depCode) {
            this.depCode = depCode;
            return this;
        }

        public Builder setDepName(String depName) {
            this.depName = depName;
            return this;
        }

        public Builder setOfficeNo(String officeNo) {
            this.officeNo = officeNo;
            return this;
        }
        public Department build(){
            return new Department(this);
        }
        public Builder copy(Department department){
            this.depCode = department.depCode;
            this.depName = department.depName;
            this.officeNo = department.officeNo;

            return this;
        }
    }
}
