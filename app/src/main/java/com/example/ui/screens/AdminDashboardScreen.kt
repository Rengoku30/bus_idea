package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Booking
import com.example.model.BusTrip
import com.example.ui.theme.TNTDarkBackground
import com.example.ui.theme.TNTDarkCard
import com.example.ui.theme.TNTDarkCardBorder
import com.example.ui.theme.TNTDarkInput
import com.example.ui.theme.TNTDarkSurface
import com.example.ui.theme.TNTSuccess
import com.example.ui.theme.TNTTextMuted
import com.example.ui.theme.TNTTextPrimary
import com.example.ui.theme.TNTTextSecondary
import com.example.ui.theme.TNTYellow
import com.example.ui.theme.TNTYellowBright
import com.example.viewmodel.TNTBusViewModel

@Composable
fun AdminDashboardScreen(
    viewModel: TNTBusViewModel,
    onNavigateBack: () -> Unit
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val bookings by viewModel.bookings.collectAsState()
    val trips by viewModel.trips.collectAsState()

    if (!userProfile.isAdmin) {
        // Access Denied / Admin Authentication Gate
        AdminAccessGateScreen(
            viewModel = viewModel,
            onNavigateBack = onNavigateBack
        )
    } else {
        // Authenticated Admin Dashboard
        AdminDashboardContent(
            viewModel = viewModel,
            bookings = bookings,
            trips = trips,
            onNavigateBack = onNavigateBack
        )
    }
}

@Composable
private fun AdminAccessGateScreen(
    viewModel: TNTBusViewModel,
    onNavigateBack: () -> Unit
) {
    var adminPin by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = TNTDarkBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.testTag("admin_gate_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "TNTBus Security Gateway",
                    color = TNTTextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(TNTDarkCard, CircleShape)
                    .border(BorderStroke(2.dp, TNTYellow), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = "Admin Security Shield",
                    tint = TNTYellow,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "ADMIN CONSOLE",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Restricted to authorized TNTBus operations managers and route dispatchers.",
                fontSize = 14.sp,
                color = TNTTextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, TNTDarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "ENTER 4-DIGIT ADMIN PIN",
                        color = TNTYellow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = adminPin,
                        onValueChange = {
                            if (it.length <= 4) {
                                adminPin = it
                                pinError = null
                            }
                        },
                        placeholder = { Text("••••", color = TNTTextMuted, fontSize = 18.sp) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = TNTDarkInput,
                            unfocusedContainerColor = TNTDarkInput,
                            focusedBorderColor = TNTYellow,
                            unfocusedBorderColor = TNTDarkCardBorder,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("admin_pin_input"),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = TNTYellow, modifier = Modifier.size(18.dp))
                        }
                    )

                    if (pinError != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = pinError ?: "",
                            color = Color(0xFFFF5252),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (viewModel.authenticateAdminPin(adminPin)) {
                                pinError = null
                            } else {
                                pinError = "Invalid Admin PIN. (Hint: Use 9988)"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TNTYellowBright,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("admin_verify_pin_btn")
                    ) {
                        Icon(Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("UNLOCK ADMIN CONSOLE", fontWeight = FontWeight.Black, fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 1-Tap Quick Authenticate
            OutlinedButton(
                onClick = {
                    viewModel.loginAsAdmin()
                },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, TNTYellow),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = TNTYellow
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("quick_admin_login_btn")
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = TNTYellow, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Instant Auth: Rajesh Verma (Fleet Mgr)", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = onNavigateBack,
                modifier = Modifier.testTag("admin_gate_cancel_btn")
            ) {
                Text("Return to Passenger Mode", color = TNTTextSecondary, fontSize = 13.sp)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AdminDashboardContent(
    viewModel: TNTBusViewModel,
    bookings: List<Booking>,
    trips: List<BusTrip>,
    onNavigateBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: All Bookings, 1: Add New Route, 2: Active Routes
    var bookingSearchQuery by remember { mutableStateOf("") }
    var selectedStatusFilter by remember { mutableStateOf("ALL") }
    var selectedBookingForQr by remember { mutableStateOf<Booking?>(null) }
    var tripToDelete by remember { mutableStateOf<BusTrip?>(null) }

    // Dialog for viewing ticket QR / metadata
    if (selectedBookingForQr != null) {
        val b = selectedBookingForQr!!
        AlertDialog(
            onDismissRequest = { selectedBookingForQr = null },
            containerColor = TNTDarkCard,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ConfirmationNumber, contentDescription = null, tint = TNTYellow, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ticket Manifest • ${b.id}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            },
            text = {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.QrCode,
                                contentDescription = "QR Code",
                                tint = Color.Black,
                                modifier = Modifier.size(140.dp)
                            )
                            Text(
                                text = b.qrCodeData,
                                color = Color.Black,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text("Passenger: ${b.passengerName}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("Phone: ${b.passengerPhone}", color = TNTTextSecondary, fontSize = 13.sp)
                    Text("Route: ${b.trip.originCity} (${b.trip.originCode}) → ${b.trip.destinationCity} (${b.trip.destinationCode})", color = TNTYellow, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    Text("Bus: ${b.trip.busNumber} • Seat: ${b.seatNumber}", color = TNTTextSecondary, fontSize = 13.sp)
                    Text("Fare Paid: ₹${b.totalAmount} (${b.paymentMethod})", color = Color.White, fontSize = 13.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = { selectedBookingForQr = null },
                    colors = ButtonDefaults.buttonColors(containerColor = TNTYellow, contentColor = Color.Black)
                ) {
                    Text("Close", fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // Delete Route Confirmation Dialog
    if (tripToDelete != null) {
        val t = tripToDelete!!
        AlertDialog(
            onDismissRequest = { tripToDelete = null },
            containerColor = TNTDarkCard,
            title = {
                Text("Decommission Route?", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            },
            text = {
                Text(
                    text = "Are you sure you want to remove route ${t.originCity} → ${t.destinationCity} (${t.busNumber}) from the active fleet schedule?",
                    color = TNTTextSecondary,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteBusRoute(t.id)
                        tripToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5252), contentColor = Color.White)
                ) {
                    Text("Delete Route", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { tripToDelete = null }) {
                    Text("Cancel", color = TNTTextSecondary)
                }
            }
        )
    }

    Scaffold(
        containerColor = TNTDarkBackground,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TNTDarkSurface)
                    .statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onNavigateBack,
                            modifier = Modifier.testTag("admin_top_back_btn")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "TNTBus Ops Admin",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .background(TNTYellow.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                        .border(BorderStroke(1.dp, TNTYellow), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "SUPER ADMIN",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black,
                                        color = TNTYellow
                                    )
                                }
                            }
                            Text(
                                text = "Fleet Operations & Dispatch Console",
                                fontSize = 11.sp,
                                color = TNTTextSecondary
                            )
                        }
                    }

                    // Role Switcher / Exit Admin
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(TNTDarkInput)
                            .border(BorderStroke(1.dp, TNTDarkCardBorder), RoundedCornerShape(8.dp))
                            .clickable {
                                viewModel.setUserRole("user")
                                onNavigateBack()
                            }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .testTag("admin_exit_btn")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Exit Admin",
                                tint = TNTYellow,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Exit Admin",
                                color = TNTYellow,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Summary Metrics Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val totalRevenue = bookings.filter { it.status != "CANCELLED" }.sumOf { it.totalAmount }

                    AdminMetricPill(
                        label = "BOOKINGS",
                        value = "${bookings.size}",
                        icon = Icons.Default.ConfirmationNumber,
                        modifier = Modifier.weight(1f)
                    )
                    AdminMetricPill(
                        label = "REVENUE",
                        value = "₹${totalRevenue.toInt()}",
                        icon = Icons.Default.CurrencyRupee,
                        modifier = Modifier.weight(1.2f)
                    )
                    AdminMetricPill(
                        label = "ROUTES",
                        value = "${trips.size}",
                        icon = Icons.Default.DirectionsBus,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Tab Row
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = TNTDarkSurface,
                    contentColor = TNTYellow,
                    edgePadding = 16.dp,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = TNTYellow,
                            height = 3.dp
                        )
                    },
                    divider = { HorizontalDivider(color = TNTDarkCardBorder) }
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = {
                            Text(
                                text = "ALL BOOKINGS (${bookings.size})",
                                fontSize = 12.sp,
                                fontWeight = if (selectedTab == 0) FontWeight.Black else FontWeight.Bold,
                                color = if (selectedTab == 0) TNTYellow else TNTTextSecondary
                            )
                        },
                        modifier = Modifier.testTag("admin_tab_bookings")
                    )

                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = {
                            Text(
                                text = "+ ADD BUS ROUTE",
                                fontSize = 12.sp,
                                fontWeight = if (selectedTab == 1) FontWeight.Black else FontWeight.Bold,
                                color = if (selectedTab == 1) TNTYellow else TNTTextSecondary
                            )
                        },
                        modifier = Modifier.testTag("admin_tab_add_route")
                    )

                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = {
                            Text(
                                text = "ACTIVE ROUTES (${trips.size})",
                                fontSize = 12.sp,
                                fontWeight = if (selectedTab == 2) FontWeight.Black else FontWeight.Bold,
                                color = if (selectedTab == 2) TNTYellow else TNTTextSecondary
                            )
                        },
                        modifier = Modifier.testTag("admin_tab_active_routes")
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> {
                    AdminBookingsTab(
                        bookings = bookings,
                        searchQuery = bookingSearchQuery,
                        onSearchChange = { bookingSearchQuery = it },
                        statusFilter = selectedStatusFilter,
                        onStatusFilterChange = { selectedStatusFilter = it },
                        onUpdateStatus = { bId, status -> viewModel.updateBookingStatus(bId, status) },
                        onViewQr = { selectedBookingForQr = it }
                    )
                }
                1 -> {
                    AdminAddRouteTab(
                        onAddRoute = { newTrip ->
                            viewModel.addBusRoute(newTrip)
                            selectedTab = 2 // Switch to active routes to view newly added route
                        }
                    )
                }
                2 -> {
                    AdminActiveRoutesTab(
                        trips = trips,
                        onDeleteTrip = { tripToDelete = it },
                        onAddNewClick = { selectedTab = 1 }
                    )
                }
            }
        }
    }
}

@Composable
private fun AdminMetricPill(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, TNTDarkCardBorder),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = TNTYellow, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(text = label, fontSize = 8.sp, fontWeight = FontWeight.Bold, color = TNTTextMuted)
                Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AdminBookingsTab(
    bookings: List<Booking>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    statusFilter: String,
    onStatusFilterChange: (String) -> Unit,
    onUpdateStatus: (String, String) -> Unit,
    onViewQr: (Booking) -> Unit
) {
    val filteredBookings = remember(bookings, searchQuery, statusFilter) {
        bookings.filter { b ->
            val matchesFilter = when (statusFilter) {
                "CONFIRMED" -> b.status.equals("CONFIRMED", ignoreCase = true)
                "COMPLETED" -> b.status.equals("COMPLETED", ignoreCase = true)
                "CANCELLED" -> b.status.equals("CANCELLED", ignoreCase = true)
                else -> true
            }
            val matchesSearch = if (searchQuery.isBlank()) true else {
                b.id.contains(searchQuery, ignoreCase = true) ||
                b.passengerName.contains(searchQuery, ignoreCase = true) ||
                b.passengerPhone.contains(searchQuery, ignoreCase = true) ||
                b.trip.busNumber.contains(searchQuery, ignoreCase = true) ||
                b.trip.originCity.contains(searchQuery, ignoreCase = true) ||
                b.trip.destinationCity.contains(searchQuery, ignoreCase = true)
            }
            matchesFilter && matchesSearch
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                placeholder = { Text("Search by PNR, passenger, or bus...", color = TNTTextMuted, fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = TNTYellow, modifier = Modifier.size(18.dp)) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchChange("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = TNTTextSecondary, modifier = Modifier.size(16.dp))
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = TNTDarkInput,
                    unfocusedContainerColor = TNTDarkInput,
                    focusedBorderColor = TNTYellow,
                    unfocusedBorderColor = TNTDarkCardBorder,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_bookings_search_input"),
                singleLine = true
            )
        }

        item {
            // Status Filter Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("ALL", "CONFIRMED", "COMPLETED", "CANCELLED").forEach { filter ->
                    FilterChip(
                        selected = statusFilter == filter,
                        onClick = { onStatusFilterChange(filter) },
                        label = {
                            Text(
                                text = filter,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (statusFilter == filter) Color.Black else TNTTextSecondary
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = TNTYellow,
                            selectedLabelColor = Color.Black,
                            containerColor = TNTDarkCard
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = statusFilter == filter,
                            borderColor = if (statusFilter == filter) TNTYellow else TNTDarkCardBorder
                        ),
                        modifier = Modifier.testTag("admin_filter_${filter.lowercase()}")
                    )
                }
            }
        }

        if (filteredBookings.isEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, TNTDarkCardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ConfirmationNumber,
                            contentDescription = null,
                            tint = TNTTextMuted,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("No Bookings Found", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Try adjusting your search criteria or filter.", color = TNTTextSecondary, fontSize = 13.sp)
                    }
                }
            }
        } else {
            items(filteredBookings, key = { it.id }) { booking ->
                AdminBookingCard(
                    booking = booking,
                    onUpdateStatus = { status -> onUpdateStatus(booking.id, status) },
                    onViewQr = { onViewQr(booking) }
                )
            }
        }
    }
}

@Composable
private fun AdminBookingCard(
    booking: Booking,
    onUpdateStatus: (String) -> Unit,
    onViewQr: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, TNTDarkCardBorder),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("admin_booking_card_${booking.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: PNR, Date, Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(TNTYellow.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                            .border(BorderStroke(1.dp, TNTYellow), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "#${booking.id}",
                            color = TNTYellow,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = booking.bookingDate,
                        color = TNTTextSecondary,
                        fontSize = 11.sp
                    )
                }

                // Status Badge
                val (statusBg, statusBorder, statusColor) = when (booking.status) {
                    "CONFIRMED" -> Triple(TNTSuccess.copy(alpha = 0.15f), TNTSuccess, TNTSuccess)
                    "COMPLETED" -> Triple(Color(0xFF2196F3).copy(alpha = 0.15f), Color(0xFF2196F3), Color(0xFF2196F3))
                    else -> Triple(Color(0xFFFF5252).copy(alpha = 0.15f), Color(0xFFFF5252), Color(0xFFFF5252))
                }

                Box(
                    modifier = Modifier
                        .background(statusBg, RoundedCornerShape(6.dp))
                        .border(BorderStroke(1.dp, statusBorder), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = booking.status,
                        color = statusColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Route & Bus Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = booking.trip.originCity,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = TNTYellow,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = booking.trip.destinationCity,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "${booking.trip.busNumber} • ${booking.trip.type}",
                        fontSize = 12.sp,
                        color = TNTTextSecondary
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Seat ${booking.seatNumber}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = TNTYellowBright
                    )
                    Text(
                        text = "₹${booking.totalAmount}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = TNTDarkCardBorder)
            Spacer(modifier = Modifier.height(10.dp))

            // Passenger Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(TNTDarkInput, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.People, contentDescription = null, tint = TNTYellow, modifier = Modifier.size(14.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = booking.passengerName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(text = booking.passengerPhone, fontSize = 11.sp, color = TNTTextSecondary)
                    }
                }

                // QR preview button
                OutlinedButton(
                    onClick = onViewQr,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, TNTDarkCardBorder),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Icon(Icons.Default.QrCode, contentDescription = "View QR", tint = TNTYellow, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Manifest QR", fontSize = 11.sp, color = Color.White)
                }
            }

            // Action Buttons Row (For changing status)
            if (booking.status == "CONFIRMED") {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onUpdateStatus("COMPLETED") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A2F), contentColor = TNTSuccess),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Mark Completed", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { onUpdateStatus("CANCELLED") },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFF6B2020)),
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent, contentColor = Color(0xFFFF5252)),
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp)
                    ) {
                        Icon(Icons.Default.Cancel, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Cancel Booking", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AdminAddRouteTab(
    onAddRoute: (BusTrip) -> Unit
) {
    var originCity by remember { mutableStateOf("Mumbai") }
    var originCode by remember { mutableStateOf("BOM") }
    var originStation by remember { mutableStateOf("Borivali Gokul / Dadar TT Circle") }

    var destinationCity by remember { mutableStateOf("Goa (Panaji)") }
    var destinationCode by remember { mutableStateOf("GOI") }
    var destinationStation by remember { mutableStateOf("Panaji KTC Stand / Mapusa Bypass") }

    var operatorName by remember { mutableStateOf("TNT Royal Sleeper India") }
    var busNumber by remember { mutableStateOf("#MH04TB8821") }
    var busType by remember { mutableStateOf("AC SLEEPER") }

    var departureTime by remember { mutableStateOf("08:30 PM") }
    var arrivalTime by remember { mutableStateOf("07:30 AM") }
    var duration by remember { mutableStateOf("11h 00m") }
    var travelDate by remember { mutableStateOf("29 Oct 2026") }
    var priceText by remember { mutableStateOf("1199") }
    var availableSeatsText by remember { mutableStateOf("24") }

    val selectedAmenities = remember {
        mutableStateListOf("High-Speed WiFi", "Charging Sockets", "AC Sleeper Berth", "Clean Restroom", "Water Bottle", "Blanket")
    }

    val allAmenities = listOf(
        "High-Speed WiFi", "Charging Sockets", "AC Sleeper Berth", "Clean Restroom",
        "Water Bottle", "Blanket", "Live GPS", "Recliner Seats", "Snack Box"
    )

    var formError by remember { mutableStateOf<String?>(null) }
    var showSuccessToast by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Add, contentDescription = null, tint = TNTYellow, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "DISPATCH NEW ROUTE",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }
            Text(
                text = "Add a new bus service to the national schedule. It will be immediately bookable by travelers.",
                fontSize = 13.sp,
                color = TNTTextSecondary
            )
        }

        // Quick Preset Chips
        item {
            Text(text = "FAST PRESET TEMPLATES", color = TNTYellow, fontSize = 11.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    PresetChip(
                        title = "Mumbai → Goa",
                        subtitle = "AC Sleeper • ₹1199",
                        onClick = {
                            originCity = "Mumbai"
                            originCode = "BOM"
                            originStation = "Borivali / Dadar TT"
                            destinationCity = "Goa (Panaji)"
                            destinationCode = "GOI"
                            destinationStation = "Panaji KTC / Mapusa"
                            operatorName = "TNT Royal Sleeper India"
                            busNumber = "#MH04TB8821"
                            busType = "AC SLEEPER"
                            departureTime = "08:30 PM"
                            arrivalTime = "07:30 AM"
                            duration = "11h 00m"
                            priceText = "1199"
                            availableSeatsText = "24"
                        }
                    )
                }
                item {
                    PresetChip(
                        title = "Delhi → Manali",
                        subtitle = "Snow Volvo • ₹1499",
                        onClick = {
                            originCity = "Delhi"
                            originCode = "DEL"
                            originStation = "Kashmere Gate ISBT"
                            destinationCity = "Manali"
                            destinationCode = "MNL"
                            destinationStation = "Private Bus Stand Mall Road"
                            operatorName = "TNT Himalayan Cruiser"
                            busNumber = "#DL01TB7744"
                            busType = "VOLVO MULTI-AXLE"
                            departureTime = "06:00 PM"
                            arrivalTime = "08:00 AM"
                            duration = "14h 00m"
                            priceText = "1499"
                            availableSeatsText = "28"
                        }
                    )
                }
                item {
                    PresetChip(
                        title = "BLR → Hyderabad",
                        subtitle = "Night Multi-Axle • ₹999",
                        onClick = {
                            originCity = "Bengaluru"
                            originCode = "BLR"
                            originStation = "Majestic Kempegowda"
                            destinationCity = "Hyderabad"
                            destinationCode = "HYD"
                            destinationStation = "Gachibowli / MGBS"
                            operatorName = "TNT Deccan Sleeper"
                            busNumber = "#KA05TB3322"
                            busType = "VOLVO MULTI-AXLE"
                            departureTime = "10:00 PM"
                            arrivalTime = "06:30 AM"
                            duration = "8h 30m"
                            priceText = "999"
                            availableSeatsText = "18"
                        }
                    )
                }
            }
        }

        // Section 1: Route Journey
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, TNTDarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("1. ROUTE & STATIONS", color = TNTYellow, fontSize = 12.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(modifier = Modifier.weight(2f)) {
                            AdminFieldLabel("ORIGIN CITY")
                            AdminInputField(value = originCity, onValueChange = { originCity = it }, placeholder = "e.g. Mumbai")
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            AdminFieldLabel("CODE")
                            AdminInputField(value = originCode, onValueChange = { originCode = it.uppercase() }, placeholder = "BOM")
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    AdminFieldLabel("ORIGIN BOARDING POINTS")
                    AdminInputField(value = originStation, onValueChange = { originStation = it }, placeholder = "e.g. Borivali / Dadar Circle")

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(modifier = Modifier.weight(2f)) {
                            AdminFieldLabel("DESTINATION CITY")
                            AdminInputField(value = destinationCity, onValueChange = { destinationCity = it }, placeholder = "e.g. Pune / Goa")
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            AdminFieldLabel("CODE")
                            AdminInputField(value = destinationCode, onValueChange = { destinationCode = it.uppercase() }, placeholder = "PNQ")
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    AdminFieldLabel("DESTINATION DROPPING POINTS")
                    AdminInputField(value = destinationStation, onValueChange = { destinationStation = it }, placeholder = "e.g. Swargate / Hinjewadi")
                }
            }
        }

        // Section 2: Bus & Operator Details
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, TNTDarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("2. BUS & FLEET DETAILS", color = TNTYellow, fontSize = 12.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(modifier = Modifier.weight(1.5f)) {
                            AdminFieldLabel("OPERATOR BRAND")
                            AdminInputField(value = operatorName, onValueChange = { operatorName = it }, placeholder = "TNT Express")
                        }
                        Column(modifier = Modifier.weight(1.2f)) {
                            AdminFieldLabel("BUS REGISTRATION")
                            AdminInputField(value = busNumber, onValueChange = { busNumber = it }, placeholder = "#MH12TB9999")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    AdminFieldLabel("BUS CATEGORY / TYPE")
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("AC SLEEPER", "VOLVO MULTI-AXLE", "EXPRESS", "DIRECT", "ULTRA LUXURY").forEach { type ->
                            FilterChip(
                                selected = busType == type,
                                onClick = { busType = type },
                                label = { Text(type, fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = TNTYellow,
                                    selectedLabelColor = Color.Black,
                                    containerColor = TNTDarkInput
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = busType == type,
                                    borderColor = if (busType == type) TNTYellow else TNTDarkCardBorder
                                )
                            )
                        }
                    }
                }
            }
        }

        // Section 3: Schedule, Fare & Capacity
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, TNTDarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("3. SCHEDULE & FARE", color = TNTYellow, fontSize = 12.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            AdminFieldLabel("DEPARTURE TIME")
                            AdminInputField(value = departureTime, onValueChange = { departureTime = it }, placeholder = "06:30 AM")
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            AdminFieldLabel("ARRIVAL TIME")
                            AdminInputField(value = arrivalTime, onValueChange = { arrivalTime = it }, placeholder = "10:00 AM")
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            AdminFieldLabel("DURATION")
                            AdminInputField(value = duration, onValueChange = { duration = it }, placeholder = "3h 30m")
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(modifier = Modifier.weight(1.2f)) {
                            AdminFieldLabel("TICKET FARE (₹)")
                            AdminInputField(value = priceText, onValueChange = { priceText = it }, placeholder = "499", keyboardType = KeyboardType.Number)
                        }
                        Column(modifier = Modifier.weight(1.2f)) {
                            AdminFieldLabel("SEAT CAPACITY")
                            AdminInputField(value = availableSeatsText, onValueChange = { availableSeatsText = it }, placeholder = "18", keyboardType = KeyboardType.Number)
                        }
                        Column(modifier = Modifier.weight(1.2f)) {
                            AdminFieldLabel("SERVICE DATE")
                            AdminInputField(value = travelDate, onValueChange = { travelDate = it }, placeholder = "28 Oct 2026")
                        }
                    }
                }
            }
        }

        // Section 4: Amenities
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, TNTDarkCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("4. ONBOARD AMENITIES", color = TNTYellow, fontSize = 12.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        allAmenities.forEach { amenity ->
                            val isChecked = selectedAmenities.contains(amenity)
                            FilterChip(
                                selected = isChecked,
                                onClick = {
                                    if (isChecked) selectedAmenities.remove(amenity) else selectedAmenities.add(amenity)
                                },
                                label = { Text(amenity, fontSize = 11.sp) },
                                leadingIcon = {
                                    if (isChecked) {
                                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                                    }
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = TNTYellow.copy(alpha = 0.2f),
                                    selectedLabelColor = TNTYellow,
                                    containerColor = TNTDarkInput
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = isChecked,
                                    borderColor = if (isChecked) TNTYellow else TNTDarkCardBorder
                                )
                            )
                        }
                    }
                }
            }
        }

        // Error message if any
        if (formError != null) {
            item {
                Text(
                    text = formError ?: "",
                    color = Color(0xFFFF5252),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Submit Button
        item {
            Button(
                onClick = {
                    if (originCity.isBlank() || destinationCity.isBlank() || busNumber.isBlank()) {
                        formError = "Please fill in Origin City, Destination City, and Bus Number."
                        return@Button
                    }
                    val fare = priceText.toDoubleOrNull() ?: 499.0
                    val seats = availableSeatsText.toIntOrNull() ?: 18
                    val newTripId = "TB-" + (100..999).random()

                    val newTrip = BusTrip(
                        id = newTripId,
                        busNumber = if (busNumber.startsWith("#")) busNumber else "#$busNumber",
                        operator = operatorName.ifBlank { "TNT Express" },
                        originCode = originCode.ifBlank { "ORG" },
                        originCity = originCity,
                        originStation = originStation.ifBlank { "$originCity Central Terminal" },
                        destinationCode = destinationCode.ifBlank { "DST" },
                        destinationCity = destinationCity,
                        destinationStation = destinationStation.ifBlank { "$destinationCity Main Bus Station" },
                        departureTime = departureTime.ifBlank { "08:00 AM" },
                        arrivalTime = arrivalTime.ifBlank { "02:00 PM" },
                        duration = duration.ifBlank { "6h 00m" },
                        date = travelDate.ifBlank { "28 Oct 2026" },
                        price = fare,
                        type = busType,
                        amenities = if (selectedAmenities.isEmpty()) listOf("WiFi", "AC", "Charging Sockets") else selectedAmenities.toList(),
                        availableSeats = seats,
                        rating = 4.9
                    )

                    onAddRoute(newTrip)
                },
                colors = ButtonDefaults.buttonColors(containerColor = TNTYellowBright, contentColor = Color.Black),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("admin_publish_route_btn")
            ) {
                Icon(Icons.Default.DirectionsBus, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("PUBLISH ROUTE TO FLEET", fontWeight = FontWeight.Black, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun AdminActiveRoutesTab(
    trips: List<BusTrip>,
    onDeleteTrip: (BusTrip) -> Unit,
    onAddNewClick: () -> Unit
) {
    var routeSearchQuery by remember { mutableStateOf("") }
    val filteredTrips = remember(trips, routeSearchQuery) {
        if (routeSearchQuery.isBlank()) trips else {
            trips.filter { t ->
                t.originCity.contains(routeSearchQuery, ignoreCase = true) ||
                t.destinationCity.contains(routeSearchQuery, ignoreCase = true) ||
                t.busNumber.contains(routeSearchQuery, ignoreCase = true) ||
                t.operator.contains(routeSearchQuery, ignoreCase = true) ||
                t.type.contains(routeSearchQuery, ignoreCase = true)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = routeSearchQuery,
                    onValueChange = { routeSearchQuery = it },
                    placeholder = { Text("Filter active routes...", color = TNTTextMuted, fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = TNTYellow, modifier = Modifier.size(18.dp)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = TNTDarkInput,
                        unfocusedContainerColor = TNTDarkInput,
                        focusedBorderColor = TNTYellow,
                        unfocusedBorderColor = TNTDarkCardBorder,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("admin_routes_search_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = onAddNewClick,
                    colors = ButtonDefaults.buttonColors(containerColor = TNTYellow, contentColor = Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }

        items(filteredTrips, key = { it.id }) { trip ->
            Card(
                colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, TNTDarkCardBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_route_card_${trip.id}")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .background(TNTDarkInput, RoundedCornerShape(6.dp))
                                    .border(BorderStroke(1.dp, TNTDarkCardBorder), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = trip.busNumber,
                                    color = TNTYellow,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = trip.type, color = TNTTextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        IconButton(
                            onClick = { onDeleteTrip(trip) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Route",
                                tint = Color(0xFFFF5252),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${trip.originCity} (${trip.originCode}) → ${trip.destinationCity} (${trip.destinationCode})",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${trip.departureTime} - ${trip.arrivalTime} • ${trip.duration}",
                                fontSize = 12.sp,
                                color = TNTTextSecondary
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "₹${trip.price.toInt()}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = TNTYellowBright
                            )
                            Text(
                                text = "${trip.availableSeats} Seats",
                                fontSize = 11.sp,
                                color = TNTTextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Operator: ${trip.operator} • Rating: ★ ${trip.rating}",
                        fontSize = 11.sp,
                        color = TNTTextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun PresetChip(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = TNTDarkInput),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, TNTYellow.copy(alpha = 0.5f)),
        modifier = Modifier.clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            Text(text = title, color = TNTYellow, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Text(text = subtitle, color = TNTTextSecondary, fontSize = 10.sp)
        }
    }
}

@Composable
private fun AdminFieldLabel(text: String) {
    Text(
        text = text,
        color = TNTTextSecondary,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp,
        modifier = Modifier.padding(bottom = 4.dp, start = 2.dp)
    )
}

@Composable
private fun AdminInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = TNTTextMuted, fontSize = 13.sp) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = TNTDarkInput,
            unfocusedContainerColor = TNTDarkInput,
            focusedBorderColor = TNTYellow,
            unfocusedBorderColor = TNTDarkCardBorder,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}
