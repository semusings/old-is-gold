package pathsala.student;

import java.util.Optional;

public interface StudentRepository {

    Student save(Student student);

    Optional<Student> getById(StudentId studentId);

}
