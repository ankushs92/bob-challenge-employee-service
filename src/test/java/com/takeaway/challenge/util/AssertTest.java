package com.takeaway.challenge.util;

import com.takeaway.challenge.ChallengeApplicationTests;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertTest  {

    @Test
    public void testNotNull_NullArg_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> Assert.notNull(null, "Some message"));
    }

}
