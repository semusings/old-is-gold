package pathsala.usecase;

import bhuwanupadhyay.core.ddd.Publisher;
import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import bhuwanupadhyay.core.usecase.DefaultUseCase;
import lombok.RequiredArgsConstructor;
import pathsala.student.Student;
import pathsala.student.StudentId;
import pathsala.student.StudentRepository;

import java.util.Optional;

import static bhuwanupadhyay.core.railway.message.Message.withError;

@RequiredArgsConstructor
public class ApproveStudentUseCase extends DefaultUseCase<String> {

    private final StudentRepository repository;
    private final Publisher publisher;

    @Override
    protected Result<Void, Message> doExecute(String studentId) {
        return StudentId.create(studentId).
                map(repository::getById).
                ensure(Optional::isPresent, withError("Student not registered with id " + studentId)).
                map(Optional::get).
                onSuccess(Student::approve).
                onSuccess(repository::save).
                onSuccess(publisher::publish).
                flatMap(s -> Result.withoutValue());
    }
}
