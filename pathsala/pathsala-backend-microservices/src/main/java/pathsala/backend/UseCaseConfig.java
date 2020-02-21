package pathsala.backend;

import bhuwanupadhyay.core.ddd.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pathsala.student.StudentRepository;
import pathsala.usecase.ApproveStudentUseCase;
import pathsala.usecase.RegisterStudentUseCase;

@Configuration
@RequiredArgsConstructor
public class UseCaseConfig {
    private final StudentRepository repository;
    private final Publisher publisher;

    @Bean
    public ApproveStudentUseCase approveStudentUseCase() {
        return new ApproveStudentUseCase(repository, publisher);
    }

    @Bean
    public RegisterStudentUseCase registerStudentUseCase() {
        return new RegisterStudentUseCase(repository, publisher);
    }
}
