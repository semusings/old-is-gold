package bhuwanupadhyay.lms.lambda;

import bhuwanupadhyay.lms.domain.membership.MemberParams;
import bhuwanupadhyay.lms.lambda.api.APIGatewayUtils;
import lombok.Data;

@Data
class MemberRegisterRequest implements MemberParams {

    private String email;
    private String name;
    private String contact;
    private String address;
    private String memberId;

    static MemberRegisterRequest of(String json) {
        return APIGatewayUtils.toBody(json, MemberRegisterRequest.class);
    }
}
