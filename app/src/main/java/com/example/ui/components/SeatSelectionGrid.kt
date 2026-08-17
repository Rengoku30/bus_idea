package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Seat
import com.example.ui.theme.TNTDarkBackground
import com.example.ui.theme.TNTDarkCard
import com.example.ui.theme.TNTDarkCardBorder
import com.example.ui.theme.TNTDarkInput
import com.example.ui.theme.TNTDarkInputBorder
import com.example.ui.theme.TNTSuccess
import com.example.ui.theme.TNTTextMuted
import com.example.ui.theme.TNTTextPrimary
import com.example.ui.theme.TNTTextSecondary
import com.example.ui.theme.TNTYellow
import com.example.ui.theme.TNTYellowBright

enum class SeatFilter {
    ALL, WINDOW, AISLE
}

/**
 * Interactive Seat Selection UI Component representing a bus cabin with
 * visual states for Available, Reserved (Occupied), and Selected seats.
 */
@Composable
fun SeatSelectionGrid(
    seats: List<Seat>,
    selectedSeatId: String,
    onSeatSelected: (Seat) -> Unit,
    modifier: Modifier = Modifier
) {
    var activeFilter by remember { mutableStateOf(SeatFilter.ALL) }
    val selectedSeatObj = seats.firstOrNull { it.id == selectedSeatId }

    val availableCount = seats.count { it.isAvailable }
    val reservedCount = seats.count { !it.isAvailable }

    Card(
        colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
        border = BorderStroke(1.dp, TNTDarkCardBorder),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("seat_selection_grid")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with Title and counts
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "BUS SEAT LAYOUT",
                        color = TNTYellow,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Standard 2x2 Coach • 32 Seats",
                        color = TNTTextSecondary,
                        fontSize = 12.sp
                    )
                }

                Surface(
                    color = TNTDarkInput,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, TNTDarkInputBorder)
                ) {
                    Text(
                        text = "$availableCount Available",
                        color = TNTSuccess,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Visual State Legend
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TNTDarkBackground, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                SeatLegendItem(
                    state = SeatVisualState.AVAILABLE,
                    label = "Available ($availableCount)"
                )
                SeatLegendItem(
                    state = SeatVisualState.SELECTED,
                    label = "Selected (${if (selectedSeatId.isNotEmpty()) selectedSeatId else "--"})"
                )
                SeatLegendItem(
                    state = SeatVisualState.RESERVED,
                    label = "Reserved ($reservedCount)"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Quick Filter Chips (All, Window, Aisle)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Filter:",
                    color = TNTTextMuted,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                FilterChip(
                    selected = activeFilter == SeatFilter.ALL,
                    onClick = { activeFilter = SeatFilter.ALL },
                    label = { Text("All Seats", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = TNTYellow,
                        selectedLabelColor = Color.Black,
                        containerColor = TNTDarkInput,
                        labelColor = TNTTextSecondary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = activeFilter == SeatFilter.ALL,
                        borderColor = TNTDarkInputBorder,
                        selectedBorderColor = TNTYellow
                    ),
                    modifier = Modifier.height(32.dp)
                )
                FilterChip(
                    selected = activeFilter == SeatFilter.WINDOW,
                    onClick = { activeFilter = SeatFilter.WINDOW },
                    label = { Text("Window", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = TNTYellow,
                        selectedLabelColor = Color.Black,
                        containerColor = TNTDarkInput,
                        labelColor = TNTTextSecondary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = activeFilter == SeatFilter.WINDOW,
                        borderColor = TNTDarkInputBorder,
                        selectedBorderColor = TNTYellow
                    ),
                    modifier = Modifier.height(32.dp)
                )
                FilterChip(
                    selected = activeFilter == SeatFilter.AISLE,
                    onClick = { activeFilter = SeatFilter.AISLE },
                    label = { Text("Aisle", fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = TNTYellow,
                        selectedLabelColor = Color.Black,
                        containerColor = TNTDarkInput,
                        labelColor = TNTTextSecondary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = activeFilter == SeatFilter.AISLE,
                        borderColor = TNTDarkInputBorder,
                        selectedBorderColor = TNTYellow
                    ),
                    modifier = Modifier.height(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bus Chassis Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 16.dp, bottomEnd = 16.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF141416),
                                Color(0xFF111113)
                            )
                        )
                    )
                    .border(
                        BorderStroke(1.5.dp, Color(0xFF2A2A30)),
                        RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .padding(14.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Front Windshield & Driver Cab Indicator
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF1D1D22), RoundedCornerShape(10.dp))
                            .border(BorderStroke(1.dp, Color(0xFF2C2C34)), RoundedCornerShape(10.dp))
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DirectionsBus,
                                contentDescription = "Front of Bus",
                                tint = TNTYellow,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "FRONT / DRIVER CAB",
                                color = TNTTextSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "ENTRANCE",
                                color = TNTYellowBright,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(TNTSuccess, CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Column Header Labels (A, B | Aisle | C, D)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "A (Window)",
                            color = TNTTextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(52.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "B (Aisle)",
                            color = TNTTextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(52.dp)
                        )
                        Box(modifier = Modifier.width(36.dp), contentAlignment = Alignment.Center) {
                            Text(
                                text = "AISLE",
                                color = TNTTextMuted,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                        Text(
                            text = "C (Aisle)",
                            color = TNTTextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(52.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "D (Window)",
                            color = TNTTextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(52.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = Color(0xFF222228), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(10.dp))

                    // Seat Rows 1 to 8
                    val groupedByRow = seats.groupBy { it.row }
                    groupedByRow.forEach { (rowNum, seatsInRow) ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                        ) {
                            // Column A (Window)
                            val seatA = seatsInRow.firstOrNull { it.column == "A" }
                            SeatGridItem(
                                seat = seatA,
                                isSelected = seatA?.id == selectedSeatId,
                                isHighlighted = when (activeFilter) {
                                    SeatFilter.ALL -> true
                                    SeatFilter.WINDOW -> seatA?.isWindow == true
                                    SeatFilter.AISLE -> seatA?.isAisle == true
                                },
                                onSelect = { seatA?.let { onSeatSelected(it) } }
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // Column B (Aisle)
                            val seatB = seatsInRow.firstOrNull { it.column == "B" }
                            SeatGridItem(
                                seat = seatB,
                                isSelected = seatB?.id == selectedSeatId,
                                isHighlighted = when (activeFilter) {
                                    SeatFilter.ALL -> true
                                    SeatFilter.WINDOW -> seatB?.isWindow == true
                                    SeatFilter.AISLE -> seatB?.isAisle == true
                                },
                                onSelect = { seatB?.let { onSeatSelected(it) } }
                            )

                            // Central Aisle with Row Number
                            Box(
                                modifier = Modifier
                                    .width(36.dp)
                                    .height(48.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$rowNum",
                                    color = TNTTextMuted,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }

                            // Column C (Aisle)
                            val seatC = seatsInRow.firstOrNull { it.column == "C" }
                            SeatGridItem(
                                seat = seatC,
                                isSelected = seatC?.id == selectedSeatId,
                                isHighlighted = when (activeFilter) {
                                    SeatFilter.ALL -> true
                                    SeatFilter.WINDOW -> seatC?.isWindow == true
                                    SeatFilter.AISLE -> seatC?.isAisle == true
                                },
                                onSelect = { seatC?.let { onSeatSelected(it) } }
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            // Column D (Window)
                            val seatD = seatsInRow.firstOrNull { it.column == "D" }
                            SeatGridItem(
                                seat = seatD,
                                isSelected = seatD?.id == selectedSeatId,
                                isHighlighted = when (activeFilter) {
                                    SeatFilter.ALL -> true
                                    SeatFilter.WINDOW -> seatD?.isWindow == true
                                    SeatFilter.AISLE -> seatD?.isAisle == true
                                },
                                onSelect = { seatD?.let { onSeatSelected(it) } }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = Color(0xFF222228), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(10.dp))

                    // Rear Coach Area (Restroom & Emergency Exit)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = "🚻 REAR RESTROOM",
                            color = TNTTextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "EMERGENCY EXIT 🚪",
                            color = TNTTextMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Selected Seat Details Summary Banner
            AnimatedVisibility(
                visible = selectedSeatObj != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                selectedSeatObj?.let { seat ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        color = TNTDarkInput,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, TNTYellow.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(TNTYellow, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = seat.id,
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Seat ${seat.id} (${if (seat.isWindow) "Window" else "Aisle"})",
                                        color = TNTTextPrimary,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Row ${seat.row} • ${if (seat.column in listOf("A", "B")) "Left" else "Right"} Side",
                                        color = TNTTextSecondary,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Wifi,
                                    contentDescription = "WiFi",
                                    tint = TNTYellow,
                                    modifier = Modifier.size(16.dp)
                                )
                                Icon(
                                    imageVector = Icons.Default.Power,
                                    contentDescription = "Power Outlet",
                                    tint = TNTYellow,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class SeatVisualState {
    AVAILABLE, SELECTED, RESERVED
}

@Composable
private fun SeatGridItem(
    seat: Seat?,
    isSelected: Boolean,
    isHighlighted: Boolean,
    onSelect: () -> Unit
) {
    if (seat == null) {
        Box(modifier = Modifier.size(width = 52.dp, height = 48.dp))
        return
    }

    val isAvailable = seat.isAvailable
    val state = when {
        isSelected -> SeatVisualState.SELECTED
        !isAvailable -> SeatVisualState.RESERVED
        else -> SeatVisualState.AVAILABLE
    }

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.08f else if (!isHighlighted) 0.85f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
        label = "seatScale"
    )

    val alpha = if (isHighlighted || isSelected) 1f else 0.4f

    val backgroundColor by animateColorAsState(
        targetValue = when (state) {
            SeatVisualState.SELECTED -> TNTYellow
            SeatVisualState.RESERVED -> Color(0xFF222228)
            SeatVisualState.AVAILABLE -> Color(0xFF18181D)
        },
        label = "seatBg"
    )

    val borderColor by animateColorAsState(
        targetValue = when (state) {
            SeatVisualState.SELECTED -> TNTYellowBright
            SeatVisualState.RESERVED -> Color(0xFF2C2C34)
            SeatVisualState.AVAILABLE -> Color(0xFF33333E)
        },
        label = "seatBorder"
    )

    val textColor = when (state) {
        SeatVisualState.SELECTED -> Color.Black
        SeatVisualState.RESERVED -> TNTTextMuted
        SeatVisualState.AVAILABLE -> TNTTextPrimary
    }

    val description = when (state) {
        SeatVisualState.SELECTED -> "Seat ${seat.id}, Selected, ${if (seat.isWindow) "Window" else "Aisle"}"
        SeatVisualState.RESERVED -> "Seat ${seat.id}, Reserved, ${if (seat.isWindow) "Window" else "Aisle"}"
        SeatVisualState.AVAILABLE -> "Seat ${seat.id}, Available, ${if (seat.isWindow) "Window" else "Aisle"}, Double tap to select"
    }

    Box(
        modifier = Modifier
            .size(width = 52.dp, height = 48.dp)
            .scale(scale)
            .semantics {
                this.contentDescription = description
                this.role = Role.Button
            }
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor.copy(alpha = alpha))
            .border(BorderStroke(if (isSelected) 1.5.dp else 1.dp, borderColor.copy(alpha = alpha)), RoundedCornerShape(10.dp))
            .clickable(
                enabled = isAvailable,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true, color = TNTYellow)
            ) { onSelect() }
            .testTag("seat_${seat.id}"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(2.dp)
        ) {
            // Headrest bar simulation
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .height(3.dp)
                    .background(
                        if (isSelected) Color(0x33000000) else Color(0x22FFFFFF),
                        RoundedCornerShape(2.dp)
                    )
            )

            Spacer(modifier = Modifier.height(3.dp))

            if (state == SeatVisualState.RESERVED) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Reserved",
                    tint = TNTTextMuted,
                    modifier = Modifier.size(14.dp)
                )
            } else if (state == SeatVisualState.SELECTED) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = seat.id,
                        color = textColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.Black,
                        modifier = Modifier.size(12.dp)
                    )
                }
            } else {
                Text(
                    text = seat.id,
                    color = textColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Small bottom dot or indicator for window seats
            if (seat.isWindow && state != SeatVisualState.SELECTED) {
                Box(
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .size(3.dp)
                        .background(if (state == SeatVisualState.AVAILABLE) TNTYellow.copy(alpha = 0.6f) else Color.Transparent, CircleShape)
                )
            }
        }
    }
}

@Composable
private fun SeatLegendItem(
    state: SeatVisualState,
    label: String
) {
    val bgColor = when (state) {
        SeatVisualState.AVAILABLE -> Color(0xFF18181D)
        SeatVisualState.SELECTED -> TNTYellow
        SeatVisualState.RESERVED -> Color(0xFF222228)
    }

    val borderColor = when (state) {
        SeatVisualState.AVAILABLE -> Color(0xFF33333E)
        SeatVisualState.SELECTED -> TNTYellowBright
        SeatVisualState.RESERVED -> Color(0xFF2C2C34)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(bgColor, RoundedCornerShape(4.dp))
                .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (state == SeatVisualState.SELECTED) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(10.dp)
                )
            } else if (state == SeatVisualState.RESERVED) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = TNTTextMuted,
                    modifier = Modifier.size(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = if (state == SeatVisualState.SELECTED) TNTYellow else TNTTextSecondary
        )
    }
}
