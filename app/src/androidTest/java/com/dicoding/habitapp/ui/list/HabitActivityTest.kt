package com.dicoding.habitapp.ui.list

import android.app.Activity
import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.dicoding.habitapp.R
import com.dicoding.habitapp.ui.add.AddHabitActivity
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

//TODO 16 : Write UI test to validate when user tap Add Habit (+), the AddHabitActivity displayed
@RunWith(AndroidJUnit4::class)
class HabitActivityTest {

    @Test
    fun showAddHabit() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val habitListActivity = launchActivity<HabitListActivity>()
        Espresso.onView(ViewMatchers.withId(R.id.rv_habit))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.fab))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click())
        val addHabit = getActivityInstance<AddHabitActivity>()
        Assert.assertTrue(addHabit?.javaClass == AddHabitActivity::class.java)
        Espresso.onView(ViewMatchers.withId(R.id.add_ed_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.add_ed_minutes_focus))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.sp_priority_level))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.add_tv_start_time))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        habitListActivity.finish()
        addHabit?.finish()
    }

    private inline fun <reified T : Activity> launchActivity(): T {
        val intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            T::class.java
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return InstrumentationRegistry.getInstrumentation().startActivitySync(intent) as T
    }

    private inline fun <reified T : Activity> getActivityInstance(): T? {
        var activity: Activity? = null
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            activity = ActivityLifecycleMonitorRegistry.getInstance()
                .getActivitiesInStage(Stage.RESUMED).find { it is T }
        }
        return activity as? T
    }
}