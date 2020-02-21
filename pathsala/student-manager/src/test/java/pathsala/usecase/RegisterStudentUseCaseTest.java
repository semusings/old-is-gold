package pathsala.usecase;

import bhuwanupadhyay.core.ddd.DomainEvent;
import bhuwanupadhyay.core.ddd.PublisherAdapter;
import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pathsala.student.*;
import pathsala.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterStudentUseCaseTest {

    private RegisterStudentUseCase useCase;

    private List<DomainEvent> domainEvents;

    @BeforeEach
    void setUp() {
        this.domainEvents = new ArrayList<>();
        StudentRepository repository = new StudentRepository() {
            private Student student;

            @Override
            public Student save(Student student) {
                this.student = student;
                return student;
            }

            @Override
            public Optional<Student> getById(StudentId studentId) {
                return Optional.ofNullable(student);
            }

        };

        PublisherAdapter publisher = new PublisherAdapter() {
            @Override
            protected void publish(DomainEvent event) {
                domainEvents.add(event);
            }
        };

        useCase = new RegisterStudentUseCase(repository, publisher);
    }

    @Test
    void can_register_student_when_given_valid_student_params() {
        Result<Void, Message> result = useCase.execute(params("Valid__StudentParams.json"));
        assertTrue(result.isSuccess());
        assertEquals(1, domainEvents.size());
        assertTrue(domainEvents.get(0) instanceof StudentRegistered);
        assertEquals("12345", ((StudentRegistered) domainEvents.get(0)).getStudentId());
    }

    @Test
    void can_register_student_when_given_invalid_student_params() {
        Result<Void, Message> result = useCase.execute(params("Invalid__StudentParams.json"));
        assertTrue(result.isFailure());
        assertEquals(0, domainEvents.size());
    }


    private JsonStudentParams params(String file) {
        return Utils.read(file, JsonStudentParams.class);
    }

}