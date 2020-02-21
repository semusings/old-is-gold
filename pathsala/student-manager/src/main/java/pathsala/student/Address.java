package pathsala.student;

import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import static bhuwanupadhyay.core.railway.message.Message.withError;

@Getter
public class Address {
    private final String addressLine;
    private final String state;
    private final String zipCode;
    private String city;

    private Address(@NonNull String addressLine, @NonNull String state, @NonNull String zipCode, String city) {
        this.addressLine = addressLine;
        this.state = state;
        this.zipCode = zipCode;
        this.city = city;
    }

    public static Result<Address, Message> create(String addressLine, String city, String state, String zipCode) {
        return Result.combine(
                Result.with(addressLine, withError("address.line.is.mandatory"))
                        .ensure(StringUtils::isNotBlank, withError("address.line.must.be.not.empty")),
                Result.with(state, withError("state.is.mandatory"))
                        .ensure(StringUtils::isNotBlank, withError("state.must.be.not.empty")),
                Result.with(zipCode, withError("zip.code.is.mandatory"))
                        .ensure(StringUtils::isNotBlank, withError("zip.code.must.be.not.empty"))
        ).map(s -> new Address(addressLine, state, zipCode, city));
    }

}
