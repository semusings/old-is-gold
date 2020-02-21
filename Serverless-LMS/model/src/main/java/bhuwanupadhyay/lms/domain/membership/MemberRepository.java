package bhuwanupadhyay.lms.domain.membership;

public interface MemberRepository {

    void save(Member member);

    Member getByMemberId(MemberId memberId);

}
