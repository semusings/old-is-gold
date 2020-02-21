package pathsala.serverless.student;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import pathsala.serverless.ApiGatewayResponse;
import pathsala.serverless.HttpEventHandler;

import static pathsala.serverless.student.StudentUtils.repository;

public class FetchStudentHandler extends HttpEventHandler<Void> {

    @Override
    protected ApiGatewayResponse handle(Void request, LambdaLogger log) {
        return ApiGatewayResponse.ok(repository().getStudents());
    }

}
