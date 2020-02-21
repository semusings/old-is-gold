package pathsala.serverless.student;

import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import lombok.extern.log4j.Log4j;
import pathsala.student.Student;
import pathsala.student.StudentId;
import pathsala.student.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder.standard;

@Log4j
public class DynamoStudentRepository implements StudentRepository {

    private final DynamoDBMapper mapper = new DynamoDBMapper(standard().build());

    @Override
    public Student save(Student student) {
        StudentData data = new StudentData();
        student.accept(data);
        mapper.save(data);
        return student;
    }

    @Override
    public Optional<Student> getById(StudentId studentId) {
        return Optional.ofNullable(mapper.load(StudentData.class, studentId.getRefNo())).
                map(data -> {
                    Result<Student, Message> rs = Student.create(data);
                    if (rs.isFailure()) {
                        LOG.warn("Data Corrupted : " + rs.getError().getText());
                        return null;
                    }
                    return rs.getValue();
                });
    }

    List<StudentData> getStudents() {
        return new ArrayList<>(mapper.scan(StudentData.class, new DynamoDBScanExpression()));
    }

}
