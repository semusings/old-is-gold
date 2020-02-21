package pathsala.student;

import bhuwanupadhyay.core.data.Visitor;

public interface StudentVisitor extends Visitor {

    void setStudentId(String studentId);

    void setFirstName(String firstName);

    void setMiddleName(String middleName);

    void setLastName(String lastName);

    void setAddressLine(String addressLine);

    void setCity(String city);

    void setState(String state);

    void setZipCode(String zipCode);
}
