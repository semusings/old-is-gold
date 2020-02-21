package pathsala.backend.student;

import bhuwanupadhyay.core.ddd.Publisher;
import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import pathsala.backend.ApiConstants;
import pathsala.backend.student.data.PostgreSQLStudentRepository;
import pathsala.student.Student;
import pathsala.student.StudentId;

import java.util.Optional;

import static bhuwanupadhyay.core.railway.message.Message.withError;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static pathsala.backend.ApiConstants.ACTIONS_REL;
import static pathsala.backend.ApiConstants.POST_TYPE;

@RestController
@RequestMapping(ApiConstants.STUDENT)
@RequiredArgsConstructor
@Slf4j
public class StudentManagerEndpoints {

    private final StudentResourceAssembler assembler;
    private final PostgreSQLStudentRepository repository;
    private final Publisher publisher;

    @PostMapping
    public ResponseEntity<StudentResource> register(@RequestBody StudentResource request) {
        Result<StudentResource, Message> result = Student.create(request)
                .onSuccess(Student::register)
                .onSuccess(repository::save)
                .onSuccess(publisher::publish)
                .map(assembler::toResource)
                .onFailure(message -> LOG.error(message.getText()));
        if (result.isFailure())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.OK).body(result.getValue());
    }

    @PostMapping("/{studentId}/approve")
    public ResponseEntity<StudentResource> approve(@PathVariable("studentId") String studentId) {
        Result<StudentResource, Message> result = findById(studentId)
                .onSuccess(Student::approve)
                .onSuccess(repository::save)
                .onSuccess(publisher::publish)
                .map(assembler::toResource)
                .onFailure(message -> LOG.error(message.getText()));
        if (result.isFailure())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok(result.getValue());
    }


    @PostMapping("/{studentId}/reject")
    public ResponseEntity<StudentResource> reject(@PathVariable("studentId") String studentId) {
        Result<StudentResource, Message> result = findById(studentId)
                .onSuccess(Student::reject)
                .onSuccess(repository::save)
                .onSuccess(publisher::publish)
                .map(assembler::toResource)
                .onFailure(message -> LOG.error(message.getText()));
        if (result.isFailure())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok(result.getValue());
    }

    @GetMapping
    public ResponseEntity<Resources<StudentResource>> getStudents() {
        return ResponseEntity.ok(new Resources<>(assembler.toResources(repository.getStudents())));
    }

    private Result<Student, Message> findById(String studentId) {
        return StudentId.create(studentId)
                .map(repository::getById)
                .ensure(Optional::isPresent, withError("Student not registered with id " + studentId))
                .map(Optional::get);
    }

    @Service
    static class StudentResourceAssembler extends ResourceAssemblerSupport<Student, StudentResource> {

        @Autowired
        public StudentResourceAssembler() {
            super(StudentManagerEndpoints.class, StudentResource.class);
        }

        @Override
        public StudentResource toResource(Student student) {
            String refNo = student.getId().getRefNo();
            StudentResource resource = createResourceWithId(refNo, student);
            student.accept(resource);
            if (student.isNotApproved()) {
                StudentManagerEndpoints endpoints = methodOn(StudentManagerEndpoints.class);
                resource.add(actionPost(linkTo(endpoints.approve(refNo)), "Approve"));
                resource.add(actionPost(linkTo(endpoints.reject(refNo)), "Reject"));
            }
            return resource;
        }

        private Link actionPost(ControllerLinkBuilder linkTo, String title) {
            return linkTo.withRel(ACTIONS_REL).withTitle(title).withType(POST_TYPE);
        }

    }

}
