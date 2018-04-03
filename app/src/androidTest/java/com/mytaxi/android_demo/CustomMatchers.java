package com.mytaxi.android_demo;

import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;

import com.mytaxi.android_demo.models.Driver;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public final class CustomMatchers {

    /**
     * Returns a matcher that matches {@link Driver}s based on name property value.
     *
     * @param stringMatcher {@link Matcher} of {@link String} with text to match
     */
    @NonNull
    public static Matcher<Object> withDriverName(final Matcher<String> stringMatcher) {

        return new BoundedMatcher<Object, Driver>(Driver.class) {

            @Override
            public void describeTo(final Description description) {
                description.appendText("with driver name: ");
                stringMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(final Driver driver) {
                return stringMatcher.matches(driver.getName());
            }
        };
    }
}

