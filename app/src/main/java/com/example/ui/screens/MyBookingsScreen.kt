package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Booking
import com.example.model.BusTrip
import com.example.navigation.Screen
import com.example.ui.components.TNTBookingsHeader
import com.example.ui.components.TNTBottomBar
import com.example.ui.theme.TNTDarkBackground
import com.example.ui.theme.TNTDarkCard
import com.example.ui.theme.TNTDarkCardBorder
import com.example.ui.theme.TNTDarkInput
import com.example.ui.theme.TNTSuccess
import com.example.ui.theme.TNTTextMuted
import com.example.ui.theme.TNTTextPrimary
import com.example.ui.theme.TNTTextSecondary
import com.example.ui.theme.TNTYellow
import com.example.ui.theme.TNTYellowBright
import com.example.viewmodel.TNTBusViewModel

@Composable
fun MyBookingsScreen(
    viewModel: TNTBusViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToBookingConfirmed: () -> Unit
) {
    val bookings by viewModel.bookings.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Upcoming", "Past")

    val samplePastTrip = BusTrip(
        id = "TB-PAST-1",
        busNumber = "#TB-MH-2024",
        operator = "TNT Shivneri Superfast",
        originCode = "PNQ",
        originCity = "Pune",
        originStation = "Swargate Bus Stand",
        destinationCode = "BOM",
        destinationCity = "Mumbai",
        destinationStation = "Dadar TT Circle Terminal",
        departureTime = "07:00 AM",
        arrivalTime = "10:30 AM",
        duration = "3h 30m",
        date = "15 Sep 2026",
        price = 450.0,
        type = "EXPRESS"
    )

    val pastBookings = listOf(
        Booking(
            id = "TB98421",
            trip = samplePastTrip,
            seatNumber = "2B",
            passengerName = "Aarav Sharma",
            passengerEmail = "aarav.sharma@example.com",
            passengerPhone = "+91 98765 43210",
            bookingDate = "15 Sep • 07:00 AM",
            status = "COMPLETED",
            totalAmount = 450.0
        )
    )

    Scaffold(
        containerColor = TNTDarkBackground,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TNTDarkBackground)
            ) {
                TNTBookingsHeader(
                    onMenuClick = { },
                    onProfileClick = onNavigateToProfile
                )

                HorizontalDivider(color = TNTYellow, thickness = 2.dp)

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = TNTDarkBackground,
                    contentColor = TNTYellow,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            height = 3.dp,
                            color = TNTYellow
                        )
                    },
                    divider = {
                        HorizontalDivider(color = TNTDarkCardBorder, thickness = 1.dp)
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTabIndex == index
                        Tab(
                            selected = isSelected,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 15.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) TNTYellow else TNTTextSecondary
                                )
                            },
                            modifier = Modifier.testTag("bookings_tab_$title")
                        )
                    }
                }
            }
        },
        bottomBar = {
            TNTBottomBar(
                currentRoute = Screen.MyBookings.route,
                onNavigate = { route ->
                    when (route) {
                        Screen.Home.route -> onNavigateToHome()
                        Screen.Profile.route -> onNavigateToProfile()
                        Screen.Alerts.route -> onNavigateToAlerts()
                    }
                }
            )
        }
    ) { paddingValues ->
        val currentList = if (selectedTabIndex == 0) bookings else pastBookings

        if (currentList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = "No Bookings",
                        tint = TNTTextMuted,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No bookings found",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Your bus travel tickets will appear here.",
                        fontSize = 14.sp,
                        color = TNTTextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(currentList, key = { it.id }) { booking ->
                    BookingCardItem(
                        booking = booking,
                        onViewTicket = {
                            viewModel.setActiveBooking(booking)
                            onNavigateToBookingConfirmed()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BookingCardItem(
    booking: Booking,
    onViewTicket: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
        border = BorderStroke(1.dp, TNTDarkCardBorder),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Top Tag Row: ID & Status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .background(TNTDarkInput, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "ID: #${booking.id}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TNTTextSecondary
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Confirmed",
                        tint = if (booking.status == "CONFIRMED") TNTYellow else TNTSuccess,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = booking.status,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = if (booking.status == "CONFIRMED") TNTYellow else TNTSuccess
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Route Graphic Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = booking.trip.originCode,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Text(
                        text = booking.trip.originCity,
                        fontSize = 13.sp,
                        color = TNTTextSecondary
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.width(90.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(2.dp)
                                .background(TNTDarkCardBorder)
                        )
                        Icon(
                            imageVector = Icons.Default.DirectionsBus,
                            contentDescription = "Bus",
                            tint = TNTYellow,
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(18.dp)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(2.dp)
                                .background(TNTDarkCardBorder)
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = booking.trip.destinationCode,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Text(
                        text = booking.trip.destinationCity,
                        fontSize = 13.sp,
                        color = TNTTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Departure info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TNTDarkInput, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "DEPARTURE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = TNTTextMuted,
                    letterSpacing = 0.8.sp
                )
                Text(
                    text = booking.bookingDate,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TNTYellow
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // VIEW TICKET Action Button
            Button(
                onClick = onViewTicket,
                colors = ButtonDefaults.buttonColors(
                    containerColor = TNTYellowBright,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("view_ticket_btn_${booking.id}")
            ) {
                Text(
                    text = "VIEW TICKET",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
