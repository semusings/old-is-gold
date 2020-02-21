package io.github.bhuwanupadhyay.ddd;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class RefNo {

    private final String refNo;

    public String getRefNo() {
        return refNo;
    }
}
