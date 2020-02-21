package pathsala.backend.student;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import pathsala.backend.ApiConstants;
import pathsala.backend.web.RestWebTest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static pathsala.backend.utils.TestUtils.read;

class StudentManagerEndpointsTest extends RestWebTest {

    @Test
    void getStudentsLinks() {
        String firstId = registerStudent().getStudentId();

        ValidatableResponse response = endpoint().get().then().statusCode(SC_OK);

        String selfUri = ApiConstants.STUDENT + "/" + firstId;
        response.body("_embedded.students[0]._links.actions[0].href", is(url(selfUri + "/approve")));
    }


    @Test
    void registerStudentLinks() {
        StudentResource resource = student(newId());

        ValidatableResponse response = endpoint().body(resource)
                .post().then().statusCode(SC_OK);

        String selfUri = ApiConstants.STUDENT + "/" + resource.getStudentId();
        response.body("_links.actions[0].href", is(url(selfUri + "/approve")));
        response.body("_links.actions[1].href", is(url(selfUri + "/reject")));
    }


    @Test
    void approveStudentLinks() {
        StudentResource resource = registerStudent();

        ValidatableResponse response = endpoint().body(resource)
                .post(resource.getStudentId() + "/approve").then().statusCode(SC_OK);

        String selfUri = ApiConstants.STUDENT + "/" + resource.getStudentId();
        response.body("_links.self", is(url(selfUri)));
    }

    private StudentResource registerStudent() {
        StudentResource resource = student(newId());
        endpoint().body(resource).post().then().statusCode(SC_OK);
        return resource;
    }

    private StudentResource student(String studentId) {
        StudentResource resource = read("student.json", StudentResource.class);
        resource.setStudentId(studentId);
        return resource;
    }

    private RequestSpecification endpoint() {
        return http().basePath(ApiConstants.STUDENT);
    }
}