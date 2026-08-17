package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Seat
import com.example.ui.components.SeatSelectionGrid
import com.example.ui.theme.TNTDarkBackground
import com.example.ui.theme.TNTDarkCard
import com.example.ui.theme.TNTDarkCardBorder
import com.example.ui.theme.TNTDarkInput
import com.example.ui.theme.TNTDarkInputBorder
import com.example.ui.theme.TNTDarkSurface
import com.example.ui.theme.TNTSuccess
import com.example.ui.theme.TNTTextMuted
import com.example.ui.theme.TNTTextPrimary
import com.example.ui.theme.TNTTextSecondary
import com.example.ui.theme.TNTYellow
import com.example.ui.theme.TNTYellowBright
import com.example.viewmodel.TNTBusViewModel

@Composable
fun RouteDetailsScreen(
    viewModel: TNTBusViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToBookingConfirmed: () -> Unit
) {
    val trip by viewModel.selectedTrip.collectAsState()
    val selectedSeat by viewModel.selectedSeat.collectAsState()
    val seatsList by viewModel.seatsList.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()

    Scaffold(
        containerColor = TNTDarkBackground,
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TNTDarkBackground)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.testTag("route_details_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Select Seat & Review",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TNTDarkSurface)
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "Selected Seat: $selectedSeat",
                            color = TNTYellow,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Total ₹${trip.price.toInt()}",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Button(
                        onClick = {
                            if (userProfile.isLoggedIn) {
                                viewModel.confirmBooking()
                                onNavigateToBookingConfirmed()
                            } else {
                                onNavigateToLogin()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TNTYellowBright,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .testTag("book-btn")
                    ) {
                        Text(
                            text = if (userProfile.isLoggedIn) "Book Now" else "Login to Book",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Proceed",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Trip Overview Card
            item {
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = trip.operator,
                                color = TNTYellow,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = trip.busNumber,
                                color = TNTTextMuted,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Origin Station
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.PinDrop,
                                contentDescription = "Origin",
                                tint = TNTYellow,
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "${trip.departureTime} • ${trip.originCity} (${trip.originCode})",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = trip.originStation,
                                    color = TNTTextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Destination Station
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.PinDrop,
                                contentDescription = "Destination",
                                tint = TNTYellow,
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "${trip.arrivalTime} • ${trip.destinationCity} (${trip.destinationCode})",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = trip.destinationStation,
                                    color = TNTTextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            // Interactive Bus Seat Selection Grid Component
            item {
                SeatSelectionGrid(
                    seats = seatsList,
                    selectedSeatId = selectedSeat,
                    onSeatSelected = { seat ->
                        viewModel.selectSeat(seat.id)
                    }
                )
            }

            // Fare Breakdown
            item {
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
                        Text(
                            text = "PRICE SUMMARY",
                            color = TNTYellow,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("1x Base Adult Fare", color = TNTTextSecondary, fontSize = 14.sp)
                            Text("₹${trip.price.toInt()}", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Seat Selection ($selectedSeat)", color = TNTTextSecondary, fontSize = 14.sp)
                            Text("FREE", color = TNTSuccess, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("GST & Service Tax", color = TNTTextSecondary, fontSize = 14.sp)
                            Text("₹0.00", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = TNTDarkCardBorder, thickness = 1.dp)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Total Amount", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text("₹${trip.price.toInt()}", color = TNTYellow, fontSize = 22.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }
    }
}

