package com.takeaway.challenge.util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StringsTest {

    @Test
    public void testHasText_NullArg_shouldReturnFalse() {
        assertThat(Strings.hasText(null), equalTo(false));
    }

    @Test
    public void testHasText_WhitespaceArg_shouldReturnFalse() {
        assertThat(Strings.hasText("   "), equalTo(false));
    }

    @Test
    public void testHasText_RandomCharArg_shouldReturnTrue() {
        assertThat(Strings.hasText("something "), equalTo(true));
    }



}
