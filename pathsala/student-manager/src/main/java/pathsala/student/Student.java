package pathsala.student;

import bhuwanupadhyay.core.data.Visitable;
import bhuwanupadhyay.core.ddd.AggregateRoot;
import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import lombok.NonNull;


public class Student extends AggregateRoot<StudentId> implements Visitable<StudentVisitor> {

    private Name name;
    private Address address;
    private StudentStatus status;

    private Student(StudentId studentId, Name name, Address address) {
        super(studentId);
        this.name = name;
        this.address = address;
        this.status = StudentStatus.INITIALIZED;
    }

    public static Result<Student, Message> create(StudentParams params) {
        Result<StudentId, Message> studentId = StudentId.
                create(params.getStudentId());

        Result<Name, Message> name = Name.
                create(params.getFirstName(), params.getMiddleName(), params.getLastName());

        Result<Address, Message> address = Address.
                create(params.getAddressLine(), params.getCity(), params.getState(), params.getZipCode());

        return Result.sum(studentId, name, address).
                map(ignored -> new Student(studentId.getValue(), name.getValue(), address.getValue()));
    }

    public void register() {
        status = StudentStatus.SCREENING;
        registerEvent(new StudentRegistered(getId().getRefNo()));
    }

    @Override
    @NonNull
    public void accept(StudentVisitor visitor) {
        visitor.setStudentId(getId().getRefNo());
        visitor.setFirstName(name.getFirstName());
        visitor.setMiddleName(name.getFirstName());
        visitor.setLastName(name.getFirstName());
        visitor.setAddressLine(address.getAddressLine());
        visitor.setCity(address.getCity());
        visitor.setState(address.getState());
        visitor.setZipCode(address.getZipCode());
    }

    public void approve() {
        this.status = StudentStatus.APPROVED;
        registerEvent(new StudentApproved(getId().getRefNo()));
    }

    public void reject() {
        this.status = StudentStatus.SCREENING;
    }

    private boolean isApproved() {
        return StudentStatus.APPROVED.equals(this.status);
    }

    public boolean isNotApproved() {
        return !isApproved();
    }

    public enum StudentStatus {
        INITIALIZED,
        SCREENING,
        APPROVED
    }
}
