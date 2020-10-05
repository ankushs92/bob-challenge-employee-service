package com.takeaway.challenge.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class JsonTest {


    @Test
    public void testJsonSerialization() throws Exception {
        var json = "{\n" +
                "\t\"name\" : \"Ankush\",\n" +
                "\t\"age\" : 28\n" +
                "}";

        var person = Json.toObject(json, Person.class);

        assertThat("Ankush", equalTo(person.getName()));
        assertThat(28, equalTo(person.getAge()));
    }

    private static class Person {

        private final String name;
        private final int age;

        Person(
                @JsonProperty("name") String name,
                @JsonProperty("age") int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person person = (Person) o;
            return getAge() == person.getAge() &&
                    getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getName(), getAge());
        }
    }
}
