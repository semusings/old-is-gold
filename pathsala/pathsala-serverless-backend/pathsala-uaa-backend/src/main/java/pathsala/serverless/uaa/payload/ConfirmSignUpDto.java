package pathsala.serverless.uaa.payload;

import lombok.Data;

@Data
public class ConfirmSignUpDto {

    private String username;
    private String confirmationCode;

}
