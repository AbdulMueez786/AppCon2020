package com.example.appcon;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest1 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest1() {
        ViewInteraction textInputEditText = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.login_email),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("test1@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.login_password),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction checkableImageButton = onView(
                allOf(withId(R.id.text_input_end_icon), withContentDescription("Show password"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        2),
                                0),
                        isDisplayed()));
        checkableImageButton.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withText("12345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(click());

        ViewInteraction textInputEditText4 = onView(
                allOf(withText("12345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withText("12345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(click());

        ViewInteraction textInputEditText6 = onView(
                allOf(withText("12345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(click());

        ViewInteraction textInputEditText7 = onView(
                allOf(withText("12345"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("123456"));

        ViewInteraction textInputEditText8 = onView(
                allOf(withText("123456"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText9 = onView(
                allOf(withText("123456"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText9.perform(click());

        ViewInteraction textInputEditText10 = onView(
                allOf(withText("123456"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.login_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText10.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.login_button), withText("LOG IN"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction cellImageView = onView(
                allOf(withId(R.id.iv),
                        childAtPosition(
                                allOf(withId(R.id.fl),
                                        childAtPosition(
                                                withClassName(is("com.etebarian.meowbottomnavigation.MeowBottomNavigationCell")),
                                                1)),
                                0),
                        isDisplayed()));
        cellImageView.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.text_send),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("nice work"), closeSoftKeyboard());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.btn_send),
                        childAtPosition(
                                allOf(withId(R.id.ln_action),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.attach_pic),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.btn_send),
                        childAtPosition(
                                allOf(withId(R.id.ln_action),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction viewInteraction = onView(
                allOf(withId(R.id.fab_expand_menu_button),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        2),
                                2),
                        isDisplayed()));
        viewInteraction.perform(click());

        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.action2),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        2),
                                1),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.text_send),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("hello what's 's up"), closeSoftKeyboard());

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.btn_send),
                        childAtPosition(
                                allOf(withId(R.id.ln_action),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton4.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appbar),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton2.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
