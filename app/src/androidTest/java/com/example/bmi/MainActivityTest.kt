package com.example.bmi


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest2() {
        

        val massEditText = onView(withId(R.id.mass_edit))
        massEditText.perform(replaceText("65"), closeSoftKeyboard())

        
        val heightEditText = onView(withId(R.id.height_edit))
        heightEditText.perform(replaceText("170"), closeSoftKeyboard())


        val appCompatButton = onView(withId(R.id.button))
        appCompatButton.perform(click())

        val textView = onView(withId(R.id.result_text))
        textView.check(matches(withText("22.49")))
    }


}
