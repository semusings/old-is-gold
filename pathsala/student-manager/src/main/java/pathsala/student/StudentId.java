package pathsala.student;

import bhuwanupadhyay.core.ddd.RefNo;
import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class StudentId extends RefNo {

    public StudentId(String refNo) {
        super(refNo);
    }

    public static Result<StudentId, Message> create(String studentId) {
        return Result.with(studentId, Message.withError("student.id.is.mandatory"))
                .ensure(StringUtils::isNotBlank, Message.withError("student.id.must.be.not.empty"))
                .map(s -> new StudentId(studentId));
    }
}
