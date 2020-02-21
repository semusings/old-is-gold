package pathsala.backend.student.data;

import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pathsala.student.Student;
import pathsala.student.StudentId;
import pathsala.student.StudentRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class PostgreSQLStudentRepository implements StudentRepository {

    private final DataLayerStudentRepository repository;

    @Override
    public Student save(Student student) {
        StudentEntity entity = new StudentEntity();
        student.accept(entity);
        repository.save(entity);
        return student;
    }

    @Override
    public Optional<Student> getById(StudentId studentId) {
        return repository.findById(studentId.getRefNo())
                .map(entity -> {
                    Result<Student, Message> result = Student.create(entity);
                    return result.isSuccess() ? result.getValue() : null;
                });
    }

    public List<Student> getStudents() {
        return ((List<StudentEntity>) repository.findAll())
                .stream()
                .map(entity -> Student.create(entity).getValue())
                .collect(toList());
    }
}
