package pathsala.student;

/*
  Class Template
  ------------------------------------------
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
 ----------------------------------------------
 */
public interface StudentParams {
    String getStudentId();

    String getFirstName();

    String getMiddleName();

    String getLastName();

    String getAddressLine();

    String getCity();

    String getState();

    String getZipCode();

}
