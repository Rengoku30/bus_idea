package com.example.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SearchResults : Screen("search_results")
    object RouteDetails : Screen("route_details")
    object BookingConfirmed : Screen("booking_confirmed")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object Profile : Screen("profile")
    object MyBookings : Screen("my_bookings")
    object Alerts : Screen("alerts")
    object AdminDashboard : Screen("admin_dashboard")
}
