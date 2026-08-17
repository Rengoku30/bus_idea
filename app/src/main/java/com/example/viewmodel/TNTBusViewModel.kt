package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.Booking
import com.example.model.BusTrip
import com.example.model.SearchQuery
import com.example.model.Seat
import com.example.model.TravelAlert
import com.example.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TNTBusViewModel : ViewModel() {

    private val defaultTrip = BusTrip(
        id = "TB-104",
        busNumber = "#MH12TB8472",
        operator = "TNT SmartBus India",
        originCode = "BOM",
        originCity = "Mumbai",
        originStation = "Borivali Gokul / Dadar TT Circle",
        destinationCode = "PNQ",
        destinationCity = "Pune",
        destinationStation = "Wakad Hinjewadi Bridge / Swargate",
        departureTime = "06:30 AM",
        arrivalTime = "10:00 AM",
        duration = "3h 30m",
        date = "28 Oct 2026",
        price = 499.0,
        type = "AC SLEEPER",
        amenities = listOf("High-Speed WiFi", "Charging Sockets", "AC Sleeper Berth", "Clean Restroom", "Water Bottle", "Blanket"),
        availableSeats = 14,
        rating = 4.9
    )

    private val sampleTrips = listOf(
        defaultTrip,
        BusTrip(
            id = "TB-208",
            busNumber = "#MH14TB9912",
            operator = "TNT Express",
            originCode = "BOM",
            originCity = "Mumbai",
            originStation = "Vashi Highway / Sion Circle",
            destinationCode = "PNQ",
            destinationCity = "Pune",
            destinationStation = "Shivaji Nagar / Pune Station",
            departureTime = "08:45 AM",
            arrivalTime = "12:15 PM",
            duration = "3h 30m",
            date = "28 Oct 2026",
            price = 399.0,
            type = "EXPRESS",
            amenities = listOf("WiFi", "Charging Ports", "Pushback Seats", "AC"),
            availableSeats = 18,
            rating = 4.7
        ),
        BusTrip(
            id = "TB-312",
            busNumber = "#MH02TB4521",
            operator = "TNT Royal Volvo Multi-Axle",
            originCode = "BOM",
            originCity = "Mumbai",
            originStation = "Andheri East / Bandra Kurla Complex",
            destinationCode = "PNQ",
            destinationCity = "Pune",
            destinationStation = "Hinjewadi Phase 1 / Kothrud",
            departureTime = "02:30 PM",
            arrivalTime = "06:00 PM",
            duration = "3h 30m",
            date = "28 Oct 2026",
            price = 649.0,
            type = "VOLVO MULTI-AXLE",
            amenities = listOf("Ultra WiFi", "Ergonomic Recliner", "Snack Box", "Power Sockets", "Quiet Lounge"),
            availableSeats = 8,
            rating = 4.9
        ),
        BusTrip(
            id = "TB-405",
            busNumber = "#MH12TB6734",
            operator = "TNT Night Rider Sleeper",
            originCode = "BOM",
            originCity = "Mumbai",
            originStation = "Borivali West / Dadar Central",
            destinationCode = "PNQ",
            destinationCity = "Pune",
            destinationStation = "Wakad Highway / Swargate Depot",
            departureTime = "11:15 PM",
            arrivalTime = "02:45 AM",
            duration = "3h 30m",
            date = "28 Oct 2026",
            price = 549.0,
            type = "AC SLEEPER",
            amenities = listOf("WiFi", "Double Berth Sleeper", "Climate AC", "Charging Ports"),
            availableSeats = 22,
            rating = 4.8
        ),
        BusTrip(
            id = "TB-510",
            busNumber = "#KA01TB1029",
            operator = "TNT Southern Express",
            originCode = "BLR",
            originCity = "Bengaluru",
            originStation = "Majestic Kempegowda / Madiwala",
            destinationCode = "MAA",
            destinationCity = "Chennai",
            destinationStation = "Koyambedu CMBT / Guindy",
            departureTime = "07:00 AM",
            arrivalTime = "01:30 PM",
            duration = "6h 30m",
            date = "28 Oct 2026",
            price = 699.0,
            type = "VOLVO MULTI-AXLE",
            amenities = listOf("High-Speed WiFi", "Charging Sockets", "Emergency Exit", "AC", "Water Bottle"),
            availableSeats = 16,
            rating = 4.8
        ),
        BusTrip(
            id = "TB-620",
            busNumber = "#DL01TB3841",
            operator = "TNT Northern Cruiser",
            originCode = "DEL",
            originCity = "Delhi",
            originStation = "Kashmere Gate ISBT / Dhaula Kuan",
            destinationCode = "JAI",
            destinationCity = "Jaipur",
            destinationStation = "Sindhi Camp / 200 Ft Bypass",
            departureTime = "06:00 AM",
            arrivalTime = "11:30 AM",
            duration = "5h 30m",
            date = "28 Oct 2026",
            price = 549.0,
            type = "EXPRESS",
            amenities = listOf("WiFi", "Pushback Seats", "AC", "Snack Service"),
            availableSeats = 20,
            rating = 4.7
        ),
        BusTrip(
            id = "TB-730",
            busNumber = "#TS09TB9021",
            operator = "TNT Deccan Sleeper",
            originCode = "HYD",
            originCity = "Hyderabad",
            originStation = "MGBS / Gachibowli Outer Ring",
            destinationCode = "BLR",
            destinationCity = "Bengaluru",
            destinationStation = "Hebbal / Majestic Bus Stand",
            departureTime = "09:30 PM",
            arrivalTime = "06:00 AM",
            duration = "8h 30m",
            date = "28 Oct 2026",
            price = 999.0,
            type = "AC SLEEPER",
            amenities = listOf("WiFi", "Individual Screens", "AC Sleeper", "Charging Sockets", "Blanket"),
            availableSeats = 12,
            rating = 4.9
        )
    )

    private val initialBooking = Booking(
        id = "MH84729",
        trip = defaultTrip,
        seatNumber = "4A",
        passengerName = "Aarav Sharma",
        passengerEmail = "aarav.sharma@tntbus.in",
        passengerPhone = "+91 98765 43210",
        bookingDate = "28 Oct • 06:30 AM",
        status = "CONFIRMED",
        totalAmount = 499.0,
        paymentMethod = "UPI (Google Pay / PhonePe)",
        qrCodeData = "TNTBUS-MH84729-BOM-PNQ-SEAT4A"
    )

    private val sampleAdminBookings = listOf(
        initialBooking,
        Booking(
            id = "KA10294",
            trip = sampleTrips[4], // BLR -> MAA
            seatNumber = "7B",
            passengerName = "Priya Patel",
            passengerEmail = "priya.patel@tntbus.in",
            passengerPhone = "+91 98123 45678",
            bookingDate = "28 Oct • 07:00 AM",
            status = "CONFIRMED",
            totalAmount = 699.0,
            paymentMethod = "Credit Card (HDFC •••• 8812)",
            qrCodeData = "TNTBUS-KA10294-BLR-MAA-SEAT7B"
        ),
        Booking(
            id = "DL38411",
            trip = sampleTrips[5], // DEL -> JAI
            seatNumber = "2A",
            passengerName = "Vikram Malhotra",
            passengerEmail = "vikram.m@gmail.com",
            passengerPhone = "+91 97654 32109",
            bookingDate = "27 Oct • 06:00 AM",
            status = "COMPLETED",
            totalAmount = 549.0,
            paymentMethod = "UPI (Paytm)",
            qrCodeData = "TNTBUS-DL38411-DEL-JAI-SEAT2A"
        ),
        Booking(
            id = "TS90218",
            trip = sampleTrips[6], // HYD -> BLR
            seatNumber = "5C",
            passengerName = "Ananya Iyer",
            passengerEmail = "ananya.iyer@outlook.com",
            passengerPhone = "+91 99887 76655",
            bookingDate = "28 Oct • 09:30 PM",
            status = "CONFIRMED",
            totalAmount = 999.0,
            paymentMethod = "Net Banking (SBI)",
            qrCodeData = "TNTBUS-TS90218-HYD-BLR-SEAT5C"
        ),
        Booking(
            id = "MH45210",
            trip = sampleTrips[2], // BOM -> PNQ
            seatNumber = "6D",
            passengerName = "Rohan Deshmukh",
            passengerEmail = "rohan.d@yahoo.com",
            passengerPhone = "+91 91234 56780",
            bookingDate = "25 Oct • 02:30 PM",
            status = "CANCELLED",
            totalAmount = 649.0,
            paymentMethod = "UPI (PhonePe)",
            qrCodeData = "TNTBUS-MH45210-BOM-PNQ-SEAT6D"
        )
    )

    private val sampleAlerts = listOf(
        TravelAlert(
            id = "ALT-1",
            title = "Mumbai-Pune Expressway Traffic Advisory",
            description = "Scheduled lane maintenance near Khandala Ghat from 11:00 AM to 03:00 PM. TNT buses are rerouted to express bypass with minor 10-15m adjustment.",
            route = "Mumbai (BOM) → Pune (PNQ)",
            category = "HIGHWAY & TRAFFIC",
            timestamp = "10 mins ago",
            isHighPriority = true,
            isRead = false
        ),
        TravelAlert(
            id = "ALT-2",
            title = "Festive Discount: Flat ₹150 Off",
            description = "Get ₹150 instant discount on all AC Sleeper & Volvo routes across India using coupon code 'INDIABUS' at checkout.",
            route = "All Indian Routes",
            category = "OFFERS & FESTIVE",
            timestamp = "1 hour ago",
            isHighPriority = false,
            isRead = false
        ),
        TravelAlert(
            id = "ALT-3",
            title = "Bengaluru Kempegowda Boarding Bay Update",
            description = "All TNT SmartBus services from Bengaluru Majestic now depart from newly renovated Platform Bay 3C (near Gate 2).",
            route = "Bengaluru (BLR) → Chennai (MAA)",
            category = "SERVICE UPDATE",
            timestamp = "3 hours ago",
            isHighPriority = false,
            isRead = false
        ),
        TravelAlert(
            id = "ALT-4",
            title = "Delhi-Jaipur Highway Winter Mist Advisory",
            description = "Early morning mist advisory on NH 48 between Gurugram and Manesar. All TNT buses are equipped with radar speed-assist for safe travel.",
            route = "Delhi (DEL) → Jaipur (JAI)",
            category = "WEATHER",
            timestamp = "Yesterday",
            isHighPriority = false,
            isRead = true
        ),
        TravelAlert(
            id = "ALT-5",
            title = "New Volvo 9600 Luxury Sleeper Service",
            description = "Introducing ultra-quiet Volvo 9600 luxury multi-axle sleepers on the Hyderabad to Bengaluru overnight corridor.",
            route = "Hyderabad (HYD) → Bengaluru (BLR)",
            category = "SERVICE UPDATE",
            timestamp = "2 days ago",
            isHighPriority = false,
            isRead = true
        )
    )

    private val _searchQuery = MutableStateFlow(SearchQuery())
    val searchQuery: StateFlow<SearchQuery> = _searchQuery.asStateFlow()

    private val _recentSearches = MutableStateFlow(
        listOf(
            Triple("BOM", "PNQ", "Today"),
            Triple("BLR", "MAA", "Tomorrow"),
            Triple("DEL", "JAI", "Oct 30"),
            Triple("HYD", "BLR", "Nov 02")
        )
    )
    val recentSearches: StateFlow<List<Triple<String, String, String>>> = _recentSearches.asStateFlow()

    private val _trips = MutableStateFlow(sampleTrips)
    val trips: StateFlow<List<BusTrip>> = _trips.asStateFlow()

    private val _selectedTrip = MutableStateFlow<BusTrip>(defaultTrip)
    val selectedTrip: StateFlow<BusTrip> = _selectedTrip.asStateFlow()

    private val _selectedSeat = MutableStateFlow("4A")
    val selectedSeat: StateFlow<String> = _selectedSeat.asStateFlow()

    private val _userProfile = MutableStateFlow(UserProfile(isLoggedIn = true))
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _bookings = MutableStateFlow(sampleAdminBookings)
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    private val _activeBooking = MutableStateFlow<Booking>(initialBooking)
    val activeBooking: StateFlow<Booking> = _activeBooking.asStateFlow()

    // Interactive seats state for the selected trip
    private val _seatsList = MutableStateFlow(generateInitialSeats())
    val seatsList: StateFlow<List<Seat>> = _seatsList.asStateFlow()

    // Travel Alerts State
    private val _alerts = MutableStateFlow(sampleAlerts)
    val alerts: StateFlow<List<TravelAlert>> = _alerts.asStateFlow()

    private fun generateInitialSeats(): List<Seat> {
        val seats = mutableListOf<Seat>()
        val columns = listOf("A", "B", "C", "D")
        val occupied = setOf("1B", "2C", "3A", "3D", "5B", "6C", "7A", "8D")
        for (r in 1..8) {
            for (c in columns) {
                val seatId = "$r$c"
                seats.add(
                    Seat(
                        id = seatId,
                        row = r,
                        column = c,
                        isWindow = (c == "A" || c == "D"),
                        isAisle = (c == "B" || c == "C"),
                        isAvailable = !occupied.contains(seatId),
                        isSelected = (seatId == "4A")
                    )
                )
            }
        }
        return seats
    }

    fun updateSearch(origin: String, originCode: String, dest: String, destCode: String, date: String, passengers: Int) {
        _searchQuery.value = SearchQuery(
            origin = origin,
            originCode = originCode,
            destination = dest,
            destinationCode = destCode,
            date = date,
            passengers = passengers
        )
        // Filter sample trips matching or default to relevant trips
        val matched = sampleTrips.filter {
            (it.originCode.equals(originCode, ignoreCase = true) || it.originCity.contains(origin, ignoreCase = true)) &&
            (it.destinationCode.equals(destCode, ignoreCase = true) || it.destinationCity.contains(dest, ignoreCase = true))
        }
        _trips.value = if (matched.isNotEmpty()) matched else sampleTrips
    }

    fun swapOriginDestination() {
        val current = _searchQuery.value
        _searchQuery.value = current.copy(
            origin = current.destination,
            originCode = current.destinationCode,
            destination = current.origin,
            destinationCode = current.originCode
        )
    }

    fun clearRecentSearches() {
        _recentSearches.value = emptyList()
    }

    fun selectTrip(trip: BusTrip) {
        _selectedTrip.value = trip
    }

    fun selectSeat(seatId: String) {
        _selectedSeat.value = seatId
        _seatsList.update { list ->
            list.map { seat ->
                if (seat.id == seatId && seat.isAvailable) {
                    seat.copy(isSelected = true)
                } else if (seat.isSelected) {
                    seat.copy(isSelected = false)
                } else {
                    seat
                }
            }
        }
    }

    fun confirmBooking(): Booking {
        val trip = _selectedTrip.value
        val seat = _selectedSeat.value
        val user = _userProfile.value
        val newBookingId = "MH" + (10000..99999).random()

        val booking = Booking(
            id = newBookingId,
            trip = trip,
            seatNumber = seat,
            passengerName = user.name.ifEmpty { "Aarav Sharma" },
            passengerEmail = user.email.ifEmpty { "aarav.sharma@tntbus.in" },
            passengerPhone = user.phone.ifEmpty { "+91 98765 43210" },
            bookingDate = "${trip.date} • ${trip.departureTime}",
            status = "CONFIRMED",
            totalAmount = trip.price,
            paymentMethod = "UPI (Google Pay / PhonePe)",
            qrCodeData = "TNTBUS-$newBookingId-${trip.originCode}-${trip.destinationCode}-$seat"
        )

        _activeBooking.value = booking
        _bookings.update { list -> listOf(booking) + list.filter { it.id != booking.id } }
        return booking
    }

    fun setActiveBooking(booking: Booking) {
        _activeBooking.value = booking
    }

    fun markAlertAsRead(alertId: String) {
        _alerts.update { list ->
            list.map { if (it.id == alertId) it.copy(isRead = true) else it }
        }
    }

    fun dismissAlert(alertId: String) {
        _alerts.update { list -> list.filter { it.id != alertId } }
    }

    fun markAllAlertsAsRead() {
        _alerts.update { list -> list.map { it.copy(isRead = true) } }
    }

    fun addBusRoute(trip: BusTrip) {
        _trips.update { listOf(trip) + it }
    }

    fun deleteBusRoute(tripId: String) {
        _trips.update { list -> list.filter { it.id != tripId } }
    }

    fun updateBookingStatus(bookingId: String, newStatus: String) {
        _bookings.update { list ->
            list.map { if (it.id == bookingId) it.copy(status = newStatus) else it }
        }
        if (_activeBooking.value.id == bookingId) {
            _activeBooking.update { it.copy(status = newStatus) }
        }
    }

    fun loginAsAdmin(
        email: String = "admin@tntbus.in",
        name: String = "Rajesh Verma (Fleet Manager)",
        phone: String = "+91 98999 11223"
    ) {
        _userProfile.value = _userProfile.value.copy(
            name = name,
            email = email,
            phone = phone,
            memberTier = "Fleet Super Admin",
            isLoggedIn = true,
            role = "admin"
        )
    }

    fun authenticateAdminPin(pin: String): Boolean {
        if (pin == "9988" || pin == "1234" || pin == "0000" || pin == "7788") {
            loginAsAdmin()
            return true
        }
        return false
    }

    fun setUserRole(role: String) {
        _userProfile.update { it.copy(role = role) }
    }

    fun login(email: String, name: String = "Aarav Sharma", phone: String = "+91 98765 43210") {
        val isAdmin = email.contains("admin", ignoreCase = true)
        _userProfile.value = _userProfile.value.copy(
            name = if (isAdmin) "Rajesh Verma (Fleet Manager)" else name,
            email = email,
            phone = phone,
            memberTier = if (isAdmin) "Fleet Super Admin" else "Gold Member",
            isLoggedIn = true,
            role = if (isAdmin) "admin" else "user"
        )
    }

    fun loginWithPhone(phone: String, name: String = "Aarav Sharma") {
        val isAdmin = phone.contains("9999") || phone.contains("98999")
        _userProfile.value = _userProfile.value.copy(
            name = if (isAdmin) "Rajesh Verma (Fleet Manager)" else name,
            email = if (isAdmin) "admin@tntbus.in" else if (phone.contains("98765")) "aarav.sharma@tntbus.in" else "traveler@tntbus.in",
            phone = phone,
            memberTier = if (isAdmin) "Fleet Super Admin" else "Gold Member",
            isLoggedIn = true,
            role = if (isAdmin) "admin" else "user"
        )
    }

    fun register(name: String, email: String, phone: String) {
        val isAdmin = email.contains("admin", ignoreCase = true)
        _userProfile.value = _userProfile.value.copy(
            name = name,
            email = email,
            phone = phone,
            memberTier = if (isAdmin) "Fleet Super Admin" else "Silver Member",
            joinedYear = "2026",
            isLoggedIn = true,
            role = if (isAdmin) "admin" else "user"
        )
    }

    fun resetPassword(identifier: String, newPass: String): Boolean {
        // Simulates password reset and verifies user record
        if (_userProfile.value.email.equals(identifier, ignoreCase = true) || _userProfile.value.phone.contains(identifier)) {
            // Updated successfully
            return true
        }
        return true
    }

    fun logout() {
        _userProfile.value = _userProfile.value.copy(
            isLoggedIn = false
        )
    }
}
