package com.example

import com.example.model.BusTrip
import com.example.model.UserProfile
import com.example.viewmodel.TNTBusViewModel
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun userRole_adminAccessControl_worksAsExpected() {
    val regularUser = UserProfile(isLoggedIn = true, role = "user")
    assertFalse(regularUser.isAdmin)

    val adminUser = UserProfile(isLoggedIn = true, role = "admin")
    assertTrue(adminUser.isAdmin)

    val loggedOutAdmin = UserProfile(isLoggedIn = false, role = "admin")
    assertFalse(loggedOutAdmin.isAdmin)
  }

  @Test
  fun viewModel_addBusRoute_provisionsNewTrip() {
    val viewModel = TNTBusViewModel()
    val initialCount = viewModel.trips.value.size

    val newTrip = BusTrip(
      id = "TB-TEST-99",
      busNumber = "#MH04TB9999",
      operator = "TNT Royal Test",
      originCode = "BOM",
      originCity = "Mumbai",
      originStation = "Borivali",
      destinationCode = "GOI",
      destinationCity = "Goa",
      destinationStation = "Panaji",
      departureTime = "09:00 PM",
      arrivalTime = "07:00 AM",
      duration = "10h 00m",
      date = "29 Oct 2026",
      price = 1299.0,
      type = "AC SLEEPER",
      amenities = listOf("WiFi", "Blanket"),
      availableSeats = 20,
      rating = 5.0
    )

    viewModel.addBusRoute(newTrip)

    val updatedTrips = viewModel.trips.value
    assertEquals(initialCount + 1, updatedTrips.size)
    assertEquals("TB-TEST-99", updatedTrips.first().id)
  }

  @Test
  fun viewModel_updateBookingStatus_updatesBooking() {
    val viewModel = TNTBusViewModel()
    val firstBooking = viewModel.bookings.value.first()

    viewModel.updateBookingStatus(firstBooking.id, "COMPLETED")

    val updatedBooking = viewModel.bookings.value.first { it.id == firstBooking.id }
    assertEquals("COMPLETED", updatedBooking.status)
  }

  @Test
  fun viewModel_authenticateAdminPin_grantsAdminRole() {
    val viewModel = TNTBusViewModel()
    viewModel.setUserRole("user")
    assertFalse(viewModel.userProfile.value.isAdmin)

    val authSuccess = viewModel.authenticateAdminPin("9988")
    assertTrue(authSuccess)
    assertTrue(viewModel.userProfile.value.isAdmin)
    assertEquals("admin", viewModel.userProfile.value.role)
  }
}

