package androidsamples.java.journalapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> MainActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public FragmentTestRule<?,EntryListFragment> fragmentTestRule =
            FragmentTestRule.create(EntryListFragment.class);

    @Test
    public void addButtonAcc(){
        onView(withId(R.id.btn_add_entry)).perform(click());
    }

    @Test
    public void infoButtonAcc(){
        onView(withId(R.id.info_menu)).perform(click());
    }

}