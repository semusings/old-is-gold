package bhuwanupadhyay.lms.domain.membership;

import bhuwanupadhyay.core.ddd.RefNo;

public class MemberId extends RefNo {

    private MemberId(String refNo) {
        super(refNo);
    }

    public static MemberId create(String memberId) {
        return new MemberId(memberId);
    }
}
