package androidsamples.java.dicegames;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {
//  @Rule
//  public ActivityScenarioRule<WalletActivity> WalletActivityTestRule = new ActivityScenarioRule<>(WalletActivity.class);

  @Rule
  public ActivityScenarioRule<TwoOrMoreActivity> TwoOrMoreActivityTestRule = new ActivityScenarioRule<>(TwoOrMoreActivity.class);


  @Test
  public void wagerCheck(){
    onView(withId(R.id.wager)).check(matches(withHint("Wager")));
  }

  @Test
  public void infoButtonCheck(){
    onView(withId(R.id.btn_info)).perform(click());
  }

  @Test
  public void backButtonCheck(){
    onView(withId(R.id.btn_back)).perform(click());
  }

  @Test
  public void goButtonCheck(){
    onView(withId(R.id.btn_go)).perform(click());
  }



  @Test
  public void useAppContext() {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    assertEquals("androidsamples.java.dicegames", appContext.getPackageName());
  }
}