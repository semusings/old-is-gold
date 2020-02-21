package pathsala.backend.web;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pathsala.backend.ApiConstants;
import pathsala.backend.student.data.StudentEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import java.util.Map;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class RestWebTest {

    @LocalServerPort
    protected int port;

    @PersistenceContext
    private EntityManager em;

    protected String url(String part) {
        return "http://localhost:" + port + part;
    }

    protected RequestSpecification http() {
        return RestAssured.
                given()
                .log()
                .all()
                .port(port)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .basePath(ApiConstants.BASE);
    }


    protected Map<Object, Map<String, Object>> hateoasLinks(ValidatableResponse response) {
        return response.statusCode(HttpStatus.SC_OK).extract().path("_links");
    }


    protected String newId() {
        return UUID.randomUUID().toString();
    }

    @AfterEach
    void tearDown() {
        em.createNativeQuery("DELETE FROM " + StudentEntity.class.getAnnotation(Table.class).name());
    }
}
