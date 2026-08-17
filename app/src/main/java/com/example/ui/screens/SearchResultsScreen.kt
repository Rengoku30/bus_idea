package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BusTrip
import com.example.navigation.Screen
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
fun SearchResultsScreen(
    viewModel: TNTBusViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToRouteDetails: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToMyBookings: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAlerts: () -> Unit = {}
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val trips by viewModel.trips.collectAsState()

    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Express", "Cheapest", "Fastest", "Morning")

    val filteredTrips = remember(trips, selectedFilter) {
        when (selectedFilter) {
            "Express" -> trips.filter { it.type == "EXPRESS" }
            "Cheapest" -> trips.sortedBy { it.price }
            "Fastest" -> trips.sortedBy { it.duration }
            "Morning" -> trips.filter { it.departureTime.contains("AM") }
            else -> trips
        }
    }

    Scaffold(
        containerColor = TNTDarkBackground,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TNTDarkBackground)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("search_results_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = searchQuery.originCode.ifEmpty { "NYC" },
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "To",
                                tint = TNTYellow,
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .size(16.dp)
                            )
                            Text(
                                text = searchQuery.destinationCode.ifEmpty { "BOS" },
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "${searchQuery.date} • ${if (searchQuery.passengers == 1) "1 Passenger" else "${searchQuery.passengers} Passengers"}",
                            color = TNTTextSecondary,
                            fontSize = 12.sp
                        )
                    }

                    IconButton(
                        onClick = {},
                        modifier = Modifier.testTag("filter_icon_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Filter",
                            tint = TNTYellow
                        )
                    }
                }

                // Filter Chips Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filters.forEach { filter ->
                        val isSelected = selectedFilter == filter
                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (isSelected) TNTYellow else TNTDarkCard,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable { selectedFilter = filter }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                                .testTag("filter_chip_$filter")
                        ) {
                            Text(
                                text = filter,
                                color = if (isSelected) Color.Black else TNTTextSecondary,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }

                HorizontalDivider(color = TNTDarkCardBorder, thickness = 1.dp)
            }
        },
        bottomBar = {
            TNTBottomBar(
                currentRoute = Screen.SearchResults.route,
                onNavigate = { route ->
                    when (route) {
                        Screen.Home.route -> onNavigateToHome()
                        Screen.MyBookings.route -> onNavigateToMyBookings()
                        Screen.Profile.route -> onNavigateToProfile()
                        Screen.Alerts.route -> onNavigateToAlerts()
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${filteredTrips.size} Available Buses",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TNTTextSecondary
                    )
                    Text(
                        text = "Prices include all taxes",
                        fontSize = 12.sp,
                        color = TNTTextMuted
                    )
                }
            }

            items(filteredTrips, key = { it.id }) { trip ->
                TripResultCard(
                    trip = trip,
                    onSelectSeat = {
                        viewModel.selectTrip(trip)
                        onNavigateToRouteDetails()
                    }
                )
            }
        }
    }
}

@Composable
private fun TripResultCard(
    trip: BusTrip,
    onSelectSeat: () -> Unit
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
                .padding(16.dp)
        ) {
            // Header Row: Operator, Type Badge & Rating
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .background(TNTYellow, RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = trip.type,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = trip.operator,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TNTTextPrimary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = TNTYellow,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${trip.rating}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TNTTextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Time & Route Line
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Departure
                Column {
                    Text(
                        text = trip.departureTime,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = TNTTextPrimary
                    )
                    Text(
                        text = trip.originCode,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TNTYellow
                    )
                    Text(
                        text = trip.originCity,
                        fontSize = 11.sp,
                        color = TNTTextMuted
                    )
                }

                // Middle duration bar
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = trip.duration,
                        fontSize = 11.sp,
                        color = TNTTextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.width(80.dp)
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
                                .size(14.dp)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(2.dp)
                                .background(TNTDarkCardBorder)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Direct",
                        fontSize = 10.sp,
                        color = TNTSuccess,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Arrival
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = trip.arrivalTime,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = TNTTextPrimary
                    )
                    Text(
                        text = trip.destinationCode,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TNTYellow
                    )
                    Text(
                        text = trip.destinationCity,
                        fontSize = 11.sp,
                        color = TNTTextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Amenities Icons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Wifi,
                    contentDescription = "WiFi",
                    tint = TNTTextSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Icon(
                    imageVector = Icons.Default.Power,
                    contentDescription = "Power Outlets",
                    tint = TNTTextSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Icon(
                    imageVector = Icons.Default.AcUnit,
                    contentDescription = "AC",
                    tint = TNTTextSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "• ${trip.availableSeats} seats left",
                    fontSize = 11.sp,
                    color = TNTTextMuted
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = TNTDarkCardBorder, thickness = 1.dp)
            Spacer(modifier = Modifier.height(14.dp))

            // Price and Select Seat Action Button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Total Price",
                        fontSize = 11.sp,
                        color = TNTTextMuted
                    )
                    Text(
                        text = "₹${trip.price.toInt()}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = TNTYellow
                    )
                }

                Button(
                    onClick = onSelectSeat,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TNTYellowBright,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(44.dp)
                        .testTag("select_seat_btn_${trip.id}")
                ) {
                    Text(
                        text = "Select Seat",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Select Seat",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
