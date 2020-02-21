package pathsala.backend.student.data;

import lombok.Getter;
import lombok.Setter;
import pathsala.student.StudentParams;
import pathsala.student.StudentVisitor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Student")
@Entity
@Getter
@Setter
public class StudentEntity implements StudentVisitor, StudentParams {

    @Id
    private String studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String addressLine;
    private String city;
    private String state;
    private String zipCode;

}
