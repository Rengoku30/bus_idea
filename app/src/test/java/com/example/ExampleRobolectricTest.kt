package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.viewmodel.TNTBusViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("TNTBus", appName)
  }

  @Test
  fun `test login and profile state`() {
    val viewModel = TNTBusViewModel()
    viewModel.login(
      email = "aarav.sharma@tntbus.in",
      name = "Aarav Sharma",
      phone = "+91 98765 43210"
    )
    val profile = viewModel.userProfile.value
    assertTrue(profile.isLoggedIn)
    assertEquals("Aarav Sharma", profile.name)
    assertEquals("aarav.sharma@tntbus.in", profile.email)
  }

  @Test
  fun `test registration and reset password`() {
    val viewModel = TNTBusViewModel()
    viewModel.register(
      name = "Priya Patel",
      email = "priya.patel@tntbus.in",
      phone = "+91 91234 56789"
    )
    val profile = viewModel.userProfile.value
    assertTrue(profile.isLoggedIn)
    assertEquals("Priya Patel", profile.name)

    val resetResult = viewModel.resetPassword("priya.patel@tntbus.in", "NewPass@2026")
    assertTrue(resetResult)
  }
}
