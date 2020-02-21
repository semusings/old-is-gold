package pathsala.backend.utils;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import pathsala.backend.ApiConstants;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("UnusedReturnValue")
public class LinkAssert extends AbstractAssert<LinkAssert, Map<Object, Map<String, Object>>> {

    private static final String HREF = "href";
    private static final String TITLE = "title";

    private LinkAssert(Map<Object, Map<String, Object>> actual) {
        super(actual, LinkAssert.class);
    }

    public static LinkAssert assertThat(Map<Object, Map<String, Object>> actual) {
        return new LinkAssert(actual);
    }

    @SuppressWarnings("unchecked")
    public LinkAssert hasRelWithHref(String rel, String href) {
        Assertions.assertThat(actual)
                .containsKey(rel)
                .extracting(rel)
                .first()
                .matches(o -> {
                    Map<String, Object> value = (Map<String, Object>) o;
                    return Objects.equals(value.get(HREF), href);
                });
        return this;
    }

    public LinkAssert hasPostAction(String url, String actionTitle) {
        Assertions.assertThat(actions())
                .isNotEmpty()
                .anyMatch(value -> Objects.equals(value.get(HREF), url) && Objects.equals(value.get(TITLE), actionTitle));
        return this;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, String>> actions() {
        return (List<Map<String, String>>) actual.get(ApiConstants.ACTIONS_REL);
    }
}
