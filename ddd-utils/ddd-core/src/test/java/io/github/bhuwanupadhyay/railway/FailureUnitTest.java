package io.github.bhuwanupadhyay.railway;

import io.github.bhuwanupadhyay.railway.message.Message;
import io.github.bhuwanupadhyay.railway.message.MessageRuntimeException;
import org.junit.Test;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.Assert.*;

public class FailureUnitTest {

    @Test(expected = MessageRuntimeException.class)
    public void onFailureThrow() {
        Result.with(null, Message.withError("NotNull"))
                .onFailureThrow(MessageRuntimeException::new);
    }

    @Test
    public void onEnsureAll_Succeed() {
        Person person = new Person();
        person.setName("Bhuwan Upadhyay");
        person.setEmail("email@email.com");
        Result<Person, List<Message>> result =
                Result.with(person, Message.withError("person.should.not.null"))
                        .ensureAll(personEnsure());
        assertTrue(result.isSuccess());
        assertFalse(result.isFailure());
    }

    @Test
    public void onEnsureAll_Failed() {
        Person person = new Person();
        person.setName("");
        person.setEmail("email");
        Result<Person, List<Message>> result =
                Result.with(person, Message.withError("person.should.not.null"))
                        .ensureAll(personEnsure());
        assertFalse(result.isSuccess());
        assertTrue(result.isFailure());
        List<Message> messages = result.getError();
        assertEquals("person.name.should.not.be.blank", messages.get(0).getText());
        assertEquals("person.email.should.have_@", messages.get(1).getText());
    }


    @Test
    public void onEnsureAll_FailedWithNull() {
        Person person = null;
        Result<Person, List<Message>> result =
                Result.with(person, Message.withError("person.should.not.null"))
                        .ensureAll(personEnsure());
        assertFalse(result.isSuccess());
        assertTrue(result.isFailure());
        List<Message> messages = result.getError();
        assertEquals("person.should.not.null", messages.get(0).getText());
    }

    private List<Ensure<Person, Message>> personEnsure() {
        return new Ensure.Builder<Person, Message>()
                .put(new Ensure<>(p -> isNotBlank(p.getName()), Message.withError("person.name.should.not.be.blank")))
                .put(new Ensure<>(p -> isNotBlank(p.getEmail()), Message.withError("person.email.should.not.be.blank")))
                .put(new Ensure<>(p -> p.getEmail().contains("@"), Message.withError("person.email.should.have_@")))
                .build();
    }

}




