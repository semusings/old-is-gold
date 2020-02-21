package pathsala.student;

import lombok.Getter;

@Getter
public class JsonStudentParams implements StudentParams {
    private String studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String addressLine;
    private String city;
    private String state;
    private String zipCode;

}