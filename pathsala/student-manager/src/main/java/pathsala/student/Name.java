package pathsala.student;

import bhuwanupadhyay.core.railway.Result;
import bhuwanupadhyay.core.railway.message.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static bhuwanupadhyay.core.railway.message.Message.withError;

@Getter
@RequiredArgsConstructor
public class Name {

    private final String firstName;
    private final String middleName;
    private final String lastName;

    public static Result<Name, Message> create(String firstName, String middleName, String lastName) {
        return Result.combine(
                Result.with(firstName, withError("first.name.is.mandatory"))
                        .ensure(StringUtils::isNotBlank, withError("first.name.must.be.not.empty")),

                Result.with(lastName, withError("last.name.is.mandatory"))
                        .ensure(StringUtils::isNotBlank, withError("last.name.must.be.not.empty"))
        ).map(o -> new Name(firstName, middleName, lastName));
    }

}
