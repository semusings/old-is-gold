package bhuwanupadhyay.lms.lambda;

import bhuwanupadhyay.core.ddd.DomainEvent;
import bhuwanupadhyay.core.ddd.Publisher;
import bhuwanupadhyay.core.ddd.PublisherAdapter;
import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import bhuwanupadhyay.lms.domain.membership.Member;
import bhuwanupadhyay.lms.domain.membership.MemberId;
import bhuwanupadhyay.lms.domain.membership.MemberRepository;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RegisterMemberLambda
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        MemberRegisterRequest params = MemberRegisterRequest.of(requestEvent.getBody());
        Result<Member, List<Message>> result = Member.create(params)
                .onSuccess(member -> repository().save(member))
                .onSuccess(member -> publisher().publish(member));
        return responseEvent;
    }

    private Publisher publisher() {
        return new PublisherAdapter() {
            @Override
            protected void publish(DomainEvent domainEvent) {

            }
        };
    }

    private MemberRepository repository() {
        return new MemberRepository() {

            @Override
            public void save(Member member) {

            }

            @Override
            public Member getByMemberId(MemberId memberId) {
                return null;
            }

        };
    }

}
