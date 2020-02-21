package io.github.bhuwanupadhyay.railway;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor
@Getter
public class Ensure<TSuccess, TFailure> {
    private Predicate<TSuccess> predicate;
    private TFailure failure;

    public static class Builder<TSuccess, TFailure> {
        private List<Ensure<TSuccess, TFailure>> ensures = new ArrayList<>();

        public Builder<TSuccess, TFailure> put(Ensure<TSuccess, TFailure> ensure) {
            this.ensures.add(ensure);
            return this;
        }

        public List<Ensure<TSuccess, TFailure>> build() {
            return ensures;
        }
    }
}