package pathsala.serverless.student;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pathsala.student.StudentParams;
import pathsala.student.StudentVisitor;

@Getter
@Setter
@DynamoDBTable(tableName = "PSStudent")
@ToString
public class StudentData implements StudentParams, StudentVisitor {
    @DynamoDBHashKey
    private String studentId;
    @DynamoDBAttribute
    private String firstName;
    @DynamoDBAttribute
    private String middleName;
    @DynamoDBAttribute
    private String lastName;
    @DynamoDBAttribute
    private String addressLine;
    @DynamoDBAttribute
    private String city;
    @DynamoDBAttribute
    private String state;
    @DynamoDBAttribute
    private String zipCode;
}
