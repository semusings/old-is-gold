package pathsala.student;

import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import org.junit.jupiter.api.Test;
import pathsala.utils.Utils;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void can_create_student_when_given_valid_student_params() {
        Result<Student, Message> result = Student.create(params("Valid__StudentParams.json"));
        assertTrue(result.isSuccess());
    }


    @Test
    void can_not_create_student_when_given_in_valid_student_params() {
        Result<Student, Message> result = Student.create(params("Invalid__StudentParams.json"));
        assertTrue(result.isFailure());
    }

    private JsonStudentParams params(String file) {
        return Utils.read(file, JsonStudentParams.class);
    }

}