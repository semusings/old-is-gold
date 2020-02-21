package pathsala.serverless.uaa.payload;

import lombok.Data;

@Data
public class SignUpDto {

    private String username;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
}
