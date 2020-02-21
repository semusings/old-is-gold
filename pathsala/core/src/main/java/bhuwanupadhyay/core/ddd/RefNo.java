package bhuwanupadhyay.core.ddd;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RefNo {

    private final String refNo;

    public String getRefNo() {
        return refNo;
    }
}
