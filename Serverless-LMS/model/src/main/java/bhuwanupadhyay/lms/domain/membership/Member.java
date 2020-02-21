package bhuwanupadhyay.lms.domain.membership;

import bhuwanupadhyay.core.data.Visitable;
import bhuwanupadhyay.core.ddd.AggregateRoot;
import bhuwanupadhyay.core.railway.Ensure;
import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import lombok.Getter;

import java.util.List;

import static bhuwanupadhyay.core.railway.message.Message.withError;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
public class Member extends AggregateRoot<MemberId> implements Visitable<MemberVisitor> {
    private MemberId memberId;

    public static Result<Member, List<Message>> create(MemberParams params) {
        Result<MemberParams, List<Message>> ensureAll = Result
                .with(params, withError("member.parameters.must.be.not.null"))
                .ensureAll(new Ensure.Builder<MemberParams, Message>()
                        .put(new Ensure<>(p -> isNotBlank(p.getMemberId()), withError("member.id.should.not.be.blank")))
                        .put(new Ensure<>(p -> isNotBlank(p.getEmail()), withError("member.email.should.not.be.blank")))
                        .put(new Ensure<>(p -> isNotBlank(p.getName()), withError("member.name.should.not.be.blank")))
                        .put(new Ensure<>(p -> isNotBlank(p.getContact()), withError("member.contact.should.not.be.blank")))
                        .put(new Ensure<>(p -> isNotBlank(p.getAddress()), withError("member.address.should.not.be.blank")))
                        .build());
        return ensureAll
                .map(p -> {
                    Member member = new Member();
                    member.memberId = MemberId.create(p.getMemberId());
                    return member;
                });
    }

    @Override
    public void accept(MemberVisitor memberVisitor) {

    }


    @Override
    public MemberId getId() {
        return this.getMemberId();
    }

}
