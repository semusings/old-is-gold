package pathsala.backend.student;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.core.Relation;
import pathsala.backend.PathsalaResource;
import pathsala.student.StudentParams;
import pathsala.student.StudentVisitor;

@Getter
@Setter
@Relation(value = "student", collectionRelation = "students")
public class StudentResource extends PathsalaResource implements StudentParams, StudentVisitor {

    private String studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String addressLine;
    private String city;
    private String state;
    private String zipCode;
}
