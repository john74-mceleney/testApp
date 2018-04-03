package com.mytaxi.android_demo;

import android.content.Intent;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.activities.AuthenticationActivity;
import com.mytaxi.android_demo.activities.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private final String USERNAME = "whiteelephant261";
    private final String PASSWORD = "video";
    private final String DRIVER_SEARCH_STRING = "sa";
    private final String DRIVER_NAME = "Sarah Friedrich";
    private final String DRIVER_ADDRESS = "2297 bahnhofstraße";
    private final String DRIVER_DATE= "2013-03-05";

    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityTestRule<AuthenticationActivity> authActivityRule = new ActivityTestRule<>
            (AuthenticationActivity.class,
            false,    // initialTouchMode
            false);  // launchActivity. False to set intent per test);

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity
            .class,
            false,    // initialTouchMode
            false);  // launchActivity. False to set intent per test);


    private void login() {
        onView(withId(R.id.edt_username)).perform(clearText(), typeText(USERNAME));
        onView(withId(R.id.edt_password)).perform(clearText(), typeText(PASSWORD));
        onView(withId(R.id.btn_login)).perform(click());
    }

    private void logout() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_logout));
    }

    @Test
    public void loginTest() {
        authActivityRule.launchActivity(new Intent());
        onView(withId(R.id.edt_username)).check(matches(withText("")));
        onView(withId(R.id.edt_password)).check(matches(withText("")));
        login();
        mainActivityRule.launchActivity(new Intent());
        onView(withId(R.id.textSearch)).check(matches(withText("")));
        logout();
    }

    @Test
    public void searchDriverTest() {
        authActivityRule.launchActivity(new Intent());
        login();
        mainActivityRule.launchActivity(new Intent());
        onView(withId(R.id.textSearch)).perform(clearText(), typeText(DRIVER_SEARCH_STRING));
        onData(CustomMatchers.withDriverName(Matchers.containsString(DRIVER_NAME))).inRoot
                (RootMatchers.withDecorView(not(is(mainActivityRule.getActivity().getWindow()
                        .getDecorView())))).perform(click());
        onView(withId(R.id.textViewDriverName)).check(matches(withText(DRIVER_NAME)));
        onView(withId(R.id.textViewDriverLocation)).check(matches(withText(DRIVER_ADDRESS)));
        onView(withId(R.id.textViewDriverDate)).check(matches(withText(DRIVER_DATE)));
        onView(withId(R.id.fab)).perform(click());
    }

}
