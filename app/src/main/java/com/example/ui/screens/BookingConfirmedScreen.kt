package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Booking
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
fun BookingConfirmedScreen(
    viewModel: TNTBusViewModel,
    onNavigateToHome: () -> Unit
) {
    val activeBooking by viewModel.activeBooking.collectAsState()

    Scaffold(
        containerColor = TNTDarkBackground
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .statusBarsPadding()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Success Header
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(TNTYellow, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = Color.Black,
                            modifier = Modifier.size(44.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Booking Confirmed!",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Your digital boarding pass is ready",
                        fontSize = 14.sp,
                        color = TNTTextSecondary
                    )
                }
            }

            // Ticket Boarding Pass Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                    border = BorderStroke(1.dp, TNTDarkCardBorder),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        // Ticket Top Header
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(TNTDarkInput, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "ID: #${activeBooking.id}",
                                    color = TNTYellow,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(Color(0x2222C55E), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "✓  CONFIRMED",
                                    color = TNTSuccess,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Route Visual
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = activeBooking.trip.originCode,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                                Text(
                                    text = activeBooking.trip.originCity,
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
                                    modifier = Modifier.width(100.dp)
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
                                            .size(20.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(2.dp)
                                            .background(TNTDarkCardBorder)
                                    )
                                }
                                Text(
                                    text = activeBooking.trip.duration,
                                    fontSize = 11.sp,
                                    color = TNTTextMuted,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = activeBooking.trip.destinationCode,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                                Text(
                                    text = activeBooking.trip.destinationCity,
                                    fontSize = 13.sp,
                                    color = TNTTextSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Departure Time Banner
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(TNTDarkInput, RoundedCornerShape(10.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = "DEPARTURE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TNTTextMuted,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = "${activeBooking.trip.date} • ${activeBooking.trip.departureTime}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TNTYellow
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Passenger & Seat Grid
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text("PASSENGER", fontSize = 10.sp, color = TNTTextMuted, fontWeight = FontWeight.Bold)
                                Text(activeBooking.passengerName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                            Column {
                                Text("SEAT", fontSize = 10.sp, color = TNTTextMuted, fontWeight = FontWeight.Bold)
                                Text(activeBooking.seatNumber, fontSize = 15.sp, fontWeight = FontWeight.Black, color = TNTYellow)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("BUS OPERATOR", fontSize = 10.sp, color = TNTTextMuted, fontWeight = FontWeight.Bold)
                                Text(activeBooking.trip.operator, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Perforated Ticket Divider
                        TicketDashedDivider()

                        Spacer(modifier = Modifier.height(18.dp))

                        // QR Code Section
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BoardingQRCode(data = activeBooking.qrCodeData)

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Scan QR at gate for contactless boarding",
                                fontSize = 12.sp,
                                color = TNTTextMuted,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Payment Total
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Paid via ${activeBooking.paymentMethod}",
                                fontSize = 12.sp,
                                color = TNTTextSecondary
                            )
                            Text(
                                text = "₹${activeBooking.totalAmount.toInt()}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = TNTYellow
                            )
                        }
                    }
                }
            }

            // Return Home Action Button
            item {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onNavigateToHome,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TNTYellowBright,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("return_home_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Return Home",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun TicketDashedDivider() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = Color(0xFF383842),
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f),
            strokeWidth = 2f
        )
    }
}

@Composable
private fun BoardingQRCode(data: String) {
    Box(
        modifier = Modifier
            .size(130.dp)
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val step = size.width / 11f
            // QR corner boxes
            drawRect(Color.Black, Offset(0f, 0f), Size(step * 3, step * 3))
            drawRect(Color.White, Offset(step, step), Size(step, step))

            drawRect(Color.Black, Offset(size.width - step * 3, 0f), Size(step * 3, step * 3))
            drawRect(Color.White, Offset(size.width - step * 2, step), Size(step, step))

            drawRect(Color.Black, Offset(0f, size.height - step * 3), Size(step * 3, step * 3))
            drawRect(Color.White, Offset(step, size.height - step * 2), Size(step, step))

            // Center pattern bits
            for (i in 0..10) {
                for (j in 0..10) {
                    if ((i + j) % 3 == 0 || (i * j) % 5 == 0) {
                        if (!(i < 4 && j < 4) && !(i > 6 && j < 4) && !(i < 4 && j > 6)) {
                            drawRect(
                                Color.Black,
                                Offset(i * step + 1f, j * step + 1f),
                                Size(step - 2f, step - 2f)
                            )
                        }
                    }
                }
            }
        }
    }
}
