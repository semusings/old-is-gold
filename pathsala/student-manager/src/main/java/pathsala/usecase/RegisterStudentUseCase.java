package pathsala.usecase;

import bhuwanupadhyay.core.ddd.Publisher;
import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import bhuwanupadhyay.core.usecase.DefaultUseCase;
import lombok.RequiredArgsConstructor;
import pathsala.student.Student;
import pathsala.student.StudentParams;
import pathsala.student.StudentRepository;

@RequiredArgsConstructor
public class RegisterStudentUseCase extends DefaultUseCase<StudentParams> {

    private final StudentRepository repository;
    private final Publisher publisher;

    @Override
    protected Result<Void, Message> doExecute(StudentParams request) {
        return Student.create(request).
                onSuccess(Student::register).
                onSuccess(repository::save).
                onSuccess(publisher::publish).
                flatMap(s -> Result.withoutValue());
    }
}
