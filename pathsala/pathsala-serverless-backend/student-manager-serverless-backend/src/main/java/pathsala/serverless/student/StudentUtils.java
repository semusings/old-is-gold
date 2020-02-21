package pathsala.serverless.student;

import bhuwanupadhyay.core.ddd.AggregateRoot;
import bhuwanupadhyay.core.ddd.Publisher;

class StudentUtils {

    static Publisher publisher() {
        return new Publisher() {

            @Override
            public <T> void publish(AggregateRoot<T> aggregateRoot) {

            }
        };
    }

    static DynamoStudentRepository repository() {
        return new DynamoStudentRepository();
    }

}
