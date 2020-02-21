package pathsala.serverless.student;

import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import lombok.extern.log4j.Log4j;
import pathsala.serverless.ApiGatewayResponse;
import pathsala.serverless.HttpEventHandler;
import pathsala.usecase.ApproveStudentUseCase;

import static pathsala.serverless.student.StudentUtils.publisher;
import static pathsala.serverless.student.StudentUtils.repository;

@Log4j
public class ApproveStudentHandler extends HttpEventHandler<StudentData> {

    @Override
    protected ApiGatewayResponse handle(StudentData request, LambdaLogger log) {
        ApproveStudentUseCase useCase = new ApproveStudentUseCase(repository(), publisher());
        Result<Void, Message> result = useCase.execute(request.getStudentId()).
                onFailure(message -> log.log(message.getText()));
        return ApiGatewayResponse.make(result, request);
    }

}
