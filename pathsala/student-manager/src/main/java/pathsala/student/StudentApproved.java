package pathsala.student;

import bhuwanupadhyay.core.ddd.DomainEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentApproved extends DomainEvent {
    @Getter
    private final String studentId;

}

