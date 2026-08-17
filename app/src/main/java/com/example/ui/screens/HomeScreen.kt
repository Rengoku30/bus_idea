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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.ui.components.TNTBrandHeader
import com.example.ui.theme.TNTDarkBackground
import com.example.ui.theme.TNTDarkCard
import com.example.ui.theme.TNTDarkCardBorder
import com.example.ui.theme.TNTDarkInput
import com.example.ui.theme.TNTDarkInputBorder
import com.example.ui.theme.TNTTextMuted
import com.example.ui.theme.TNTTextPrimary
import com.example.ui.theme.TNTTextSecondary
import com.example.ui.theme.TNTYellow
import com.example.ui.theme.TNTYellowBright
import com.example.viewmodel.TNTBusViewModel

@Composable
fun HomeScreen(
    viewModel: TNTBusViewModel,
    onNavigateToSearchResults: () -> Unit,
    onNavigateToRouteDetails: () -> Unit,
    onNavigateToMyBookings: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAlerts: () -> Unit = {}
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val recentSearches by viewModel.recentSearches.collectAsState()

    var originInput by remember(searchQuery) { mutableStateOf(searchQuery.origin) }
    var destInput by remember(searchQuery) { mutableStateOf(searchQuery.destination) }
    var dateInput by remember(searchQuery) { mutableStateOf(searchQuery.date) }
    var passengerCount by remember(searchQuery) { mutableStateOf(searchQuery.passengers) }
    var showPassengerMenu by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = TNTDarkBackground,
        bottomBar = {
            TNTBottomBar(
                currentRoute = Screen.Home.route,
                onNavigate = { route ->
                    when (route) {
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
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                TNTBrandHeader(
                    onProfileClick = onNavigateToProfile
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Where are you\ngoing?",
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Black,
                        color = TNTTextPrimary,
                        lineHeight = 44.sp,
                        letterSpacing = (-1).sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Book your next journey instantly across India. High-speed, reliable intercity bus transit.",
                        fontSize = 15.sp,
                        color = TNTTextSecondary,
                        lineHeight = 22.sp
                    )
                }
            }

            // Search Form Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                    border = BorderStroke(1.dp, TNTDarkCardBorder),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 18.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        // Origin Field
                        Text(
                            text = "ORIGIN",
                            color = TNTYellow,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.8.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = originInput,
                            onValueChange = { originInput = it },
                            placeholder = { Text("e.g. Mumbai, Bengaluru, Delhi", color = TNTTextMuted) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.MyLocation,
                                    contentDescription = "Origin",
                                    tint = TNTTextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = TNTDarkInput,
                                unfocusedContainerColor = TNTDarkInput,
                                focusedBorderColor = TNTYellow,
                                unfocusedBorderColor = TNTDarkInputBorder,
                                focusedTextColor = TNTTextPrimary,
                                unfocusedTextColor = TNTTextPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("origin_input")
                        )

                        // Swap Button
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            IconButton(
                                onClick = {
                                    val temp = originInput
                                    originInput = destInput
                                    destInput = temp
                                    viewModel.swapOriginDestination()
                                },
                                modifier = Modifier
                                    .size(34.dp)
                                    .background(TNTDarkInput, CircleShape)
                                    .testTag("swap_locations_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SwapVert,
                                    contentDescription = "Swap Locations",
                                    tint = TNTYellow,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        // Destination Field
                        Text(
                            text = "DESTINATION",
                            color = TNTYellow,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.8.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = destInput,
                            onValueChange = { destInput = it },
                            placeholder = { Text("e.g. Pune, Chennai, Jaipur", color = TNTTextMuted) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Destination",
                                    tint = TNTTextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = TNTDarkInput,
                                unfocusedContainerColor = TNTDarkInput,
                                focusedBorderColor = TNTYellow,
                                unfocusedBorderColor = TNTDarkInputBorder,
                                focusedTextColor = TNTTextPrimary,
                                unfocusedTextColor = TNTTextPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("destination_input")
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Date Field
                        Text(
                            text = "DATE OF TRAVEL",
                            color = TNTYellow,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.8.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = dateInput,
                            onValueChange = { dateInput = it },
                            placeholder = { Text("DD/MM/YYYY", color = TNTTextMuted) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = "Calendar",
                                    tint = TNTTextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = TNTDarkInput,
                                unfocusedContainerColor = TNTDarkInput,
                                focusedBorderColor = TNTYellow,
                                unfocusedBorderColor = TNTDarkInputBorder,
                                focusedTextColor = TNTTextPrimary,
                                unfocusedTextColor = TNTTextPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("date_input")
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Passengers Selector
                        Text(
                            text = "PASSENGERS",
                            color = TNTYellow,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.8.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(TNTDarkInput, RoundedCornerShape(12.dp))
                                    .clickable { showPassengerMenu = true }
                                    .padding(horizontal = 14.dp, vertical = 14.dp)
                                    .testTag("passenger_selector")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Group,
                                    contentDescription = "Passengers",
                                    tint = TNTTextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = if (passengerCount == 1) "1 Passenger" else "$passengerCount Passengers",
                                    color = TNTTextPrimary,
                                    fontSize = 15.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "▼",
                                    color = TNTTextSecondary,
                                    fontSize = 11.sp
                                )
                            }

                            DropdownMenu(
                                expanded = showPassengerMenu,
                                onDismissRequest = { showPassengerMenu = false },
                                modifier = Modifier.background(TNTDarkCard)
                            ) {
                                (1..5).forEach { count ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = if (count == 1) "1 Passenger" else "$count Passengers",
                                                color = if (count == passengerCount) TNTYellow else TNTTextPrimary
                                            )
                                        },
                                        onClick = {
                                            passengerCount = count
                                            showPassengerMenu = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Search Buses Action Button
                        Button(
                            onClick = {
                                val origCode = when {
                                    originInput.contains("Mumbai", true) || originInput.contains("BOM", true) -> "BOM"
                                    originInput.contains("Bengaluru", true) || originInput.contains("Bangalore", true) || originInput.contains("BLR", true) -> "BLR"
                                    originInput.contains("Delhi", true) || originInput.contains("DEL", true) -> "DEL"
                                    originInput.contains("Hyderabad", true) || originInput.contains("HYD", true) -> "HYD"
                                    originInput.contains("Chennai", true) || originInput.contains("MAA", true) -> "MAA"
                                    originInput.contains("Pune", true) || originInput.contains("PNQ", true) -> "PNQ"
                                    else -> "BOM"
                                }
                                val dstCode = when {
                                    destInput.contains("Pune", true) || destInput.contains("PNQ", true) -> "PNQ"
                                    destInput.contains("Chennai", true) || destInput.contains("MAA", true) -> "MAA"
                                    destInput.contains("Jaipur", true) || destInput.contains("JAI", true) -> "JAI"
                                    destInput.contains("Bengaluru", true) || destInput.contains("Bangalore", true) || destInput.contains("BLR", true) -> "BLR"
                                    destInput.contains("Goa", true) || destInput.contains("GOI", true) -> "GOI"
                                    else -> "PNQ"
                                }
                                viewModel.updateSearch(
                                    origin = originInput,
                                    originCode = origCode,
                                    dest = destInput,
                                    destCode = dstCode,
                                    date = dateInput,
                                    passengers = passengerCount
                                )
                                onNavigateToSearchResults()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TNTYellowBright,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .testTag("search_buses_btn")
                        ) {
                            Text(
                                text = "Search Buses",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Arrow Forward",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Recent Searches Section
            if (recentSearches.isNotEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Recent Searches",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TNTTextPrimary
                            )
                            TextButton(
                                onClick = { viewModel.clearRecentSearches() },
                                modifier = Modifier.testTag("clear_recent_searches_btn")
                            ) {
                                Text(
                                    text = "Clear",
                                    color = TNTYellow,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            recentSearches.forEach { (orig, dest, time) ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .background(TNTDarkCard, RoundedCornerShape(24.dp))
                                        .clickable {
                                            originInput = if (orig == "BOM") "Mumbai (BOM)" else orig
                                            destInput = if (dest == "PNQ") "Pune (PNQ)" else dest
                                            viewModel.updateSearch(
                                                origin = originInput,
                                                originCode = orig,
                                                dest = destInput,
                                                destCode = dest,
                                                date = "28/10/2026",
                                                passengers = 1
                                            )
                                            onNavigateToSearchResults()
                                        }
                                        .padding(horizontal = 16.dp, vertical = 10.dp)
                                        .testTag("recent_search_${orig}_${dest}")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.History,
                                        contentDescription = "History",
                                        tint = TNTTextSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "$orig → $dest",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = TNTTextPrimary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = time,
                                        fontSize = 12.sp,
                                        color = TNTTextMuted
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Popular Indian Routes Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Popular Indian Routes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TNTTextPrimary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Route Card 1 - Mumbai to Pune
                    PopularRouteCard(
                        type = "EXPRESSWAY SHUTTLE",
                        destination = "Pune",
                        origin = "Mumbai (BOM)",
                        price = "₹450",
                        onClick = {
                            viewModel.updateSearch("Mumbai (BOM)", "BOM", "Pune (PNQ)", "PNQ", "28/10/2026", 1)
                            onNavigateToSearchResults()
                        },
                        testTag = "popular_route_pune"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Route Card 2 - Bengaluru to Chennai
                    PopularRouteCard(
                        type = "VOLVO AC MULTI-AXLE",
                        destination = "Chennai",
                        origin = "Bengaluru (BLR)",
                        price = "₹650",
                        onClick = {
                            viewModel.updateSearch("Bengaluru (BLR)", "BLR", "Chennai (MAA)", "MAA", "28/10/2026", 1)
                            onNavigateToSearchResults()
                        },
                        testTag = "popular_route_chennai"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Route Card 3 - Delhi to Jaipur
                    PopularRouteCard(
                        type = "ROYAL CRUISE EXPRESS",
                        destination = "Jaipur",
                        origin = "Delhi (DEL)",
                        price = "₹550",
                        onClick = {
                            viewModel.updateSearch("Delhi (DEL)", "DEL", "Jaipur (JAI)", "JAI", "28/10/2026", 1)
                            onNavigateToSearchResults()
                        },
                        testTag = "popular_route_jaipur"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Route Card 4 - Bengaluru to Hyderabad
                    PopularRouteCard(
                        type = "LUXURY SLEEPER",
                        destination = "Hyderabad",
                        origin = "Bengaluru (BLR)",
                        price = "₹850",
                        onClick = {
                            viewModel.updateSearch("Bengaluru (BLR)", "BLR", "Hyderabad (HYD)", "HYD", "28/10/2026", 1)
                            onNavigateToSearchResults()
                        },
                        testTag = "popular_route_hyderabad"
                    )
                }
            }
        }
    }
}

@Composable
private fun PopularRouteCard(
    type: String,
    destination: String,
    origin: String,
    price: String,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
        border = BorderStroke(1.dp, TNTDarkCardBorder),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = type,
                    color = TNTYellow,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Go",
                    tint = TNTTextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = destination,
                color = TNTTextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(TNTDarkCardBorder)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "From",
                        fontSize = 11.sp,
                        color = TNTTextMuted
                    )
                    Text(
                        text = origin,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TNTTextPrimary
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Starting at",
                        fontSize = 11.sp,
                        color = TNTTextMuted
                    )
                    Text(
                        text = price,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = TNTYellow
                    )
                }
            }
        }
    }
}
