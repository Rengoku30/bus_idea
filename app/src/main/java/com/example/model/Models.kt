package com.example.model

data class BusTrip(
    val id: String,
    val busNumber: String,
    val operator: String = "TNT Express",
    val originCode: String,
    val originCity: String,
    val originStation: String,
    val destinationCode: String,
    val destinationCity: String,
    val destinationStation: String,
    val departureTime: String,
    val arrivalTime: String,
    val duration: String,
    val date: String,
    val price: Double,
    val type: String, // "EXPRESS", "DIRECT", "STANDARD"
    val amenities: List<String> = listOf("WiFi", "Power Outlets", "Recliner", "AC", "Restroom"),
    val availableSeats: Int = 18,
    val rating: Double = 4.8
)

data class Seat(
    val id: String,
    val row: Int,
    val column: String, // A, B, C, D
    val isWindow: Boolean,
    val isAisle: Boolean,
    val isAvailable: Boolean = true,
    val isSelected: Boolean = false,
    val priceMultiplier: Double = 1.0
)

data class Booking(
    val id: String,
    val trip: BusTrip,
    val seatNumber: String,
    val passengerName: String,
    val passengerEmail: String,
    val passengerPhone: String,
    val bookingDate: String,
    val status: String = "CONFIRMED", // "CONFIRMED", "COMPLETED", "CANCELLED"
    val totalAmount: Double,
    val paymentMethod: String = "Visa •••• 4242",
    val qrCodeData: String = "TNTBUS-TICKET-${id}"
)

data class UserProfile(
    val name: String = "Aarav Sharma",
    val email: String = "aarav.sharma@tntbus.in",
    val phone: String = "+91 98765 43210",
    val memberTier: String = "Gold Member",
    val joinedYear: String = "2022",
    val avatarDrawableRes: Int? = null,
    val isLoggedIn: Boolean = true
)

data class SearchQuery(
    val origin: String = "Mumbai (BOM)",
    val originCode: String = "BOM",
    val destination: String = "Pune (PNQ)",
    val destinationCode: String = "PNQ",
    val date: String = "28/10/2026",
    val passengers: Int = 1
)

data class TravelAlert(
    val id: String,
    val title: String,
    val description: String,
    val route: String,
    val category: String, // "HIGHWAY & TRAFFIC", "OFFERS & FESTIVE", "SERVICE UPDATE", "WEATHER"
    val timestamp: String,
    val isHighPriority: Boolean = false,
    val isRead: Boolean = false
)
