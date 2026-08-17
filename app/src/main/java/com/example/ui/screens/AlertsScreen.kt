package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Traffic
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TravelAlert
import com.example.navigation.Screen
import com.example.ui.components.BottomTab
import com.example.ui.components.TNTBottomBar
import com.example.ui.components.TNTNavHeader
import com.example.ui.theme.TNTDarkBackground
import com.example.ui.theme.TNTDarkCard
import com.example.ui.theme.TNTDarkCardBorder
import com.example.ui.theme.TNTError
import com.example.ui.theme.TNTSuccess
import com.example.ui.theme.TNTTextMuted
import com.example.ui.theme.TNTTextPrimary
import com.example.ui.theme.TNTTextSecondary
import com.example.ui.theme.TNTYellow
import com.example.ui.theme.TNTYellowBright
import com.example.viewmodel.TNTBusViewModel

@Composable
fun AlertsScreen(
    viewModel: TNTBusViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToBookings: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val alerts by viewModel.alerts.collectAsState()
    var selectedCategory by remember { mutableStateOf("ALL") }

    val categories = listOf(
        "ALL" to "All",
        "HIGHWAY & TRAFFIC" to "Highway & Traffic",
        "OFFERS & FESTIVE" to "Offers & Festive",
        "SERVICE UPDATE" to "Service Updates",
        "WEATHER" to "Weather"
    )

    val filteredAlerts = remember(alerts, selectedCategory) {
        if (selectedCategory == "ALL") {
            alerts
        } else {
            alerts.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        }
    }

    val unreadCount = alerts.count { !it.isRead }

    Scaffold(
        containerColor = TNTDarkBackground,
        topBar = {
            TNTNavHeader(
                onBack = onNavigateBack,
                title = "Live Alerts",
                showLogoTitle = false,
                backTestTag = "alerts_back_btn"
            )
        },
        bottomBar = {
            TNTBottomBar(
                currentRoute = Screen.Alerts.route,
                onNavigate = { route ->
                    when (route) {
                        Screen.Home.route -> onNavigateToHome()
                        Screen.MyBookings.route -> onNavigateToBookings()
                        Screen.Profile.route -> onNavigateToProfile()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header Row with Title & Quick Actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Transit Advisories",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = TNTTextPrimary,
                            letterSpacing = (-0.5).sp
                        )
                        if (unreadCount > 0) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(TNTYellowBright)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "$unreadCount NEW",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                    Text(
                        text = "Real-time updates across Indian highway corridors",
                        fontSize = 13.sp,
                        color = TNTTextSecondary
                    )
                }

                if (unreadCount > 0) {
                    Text(
                        text = "Mark all read",
                        fontSize = 12.sp,
                        color = TNTYellow,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { viewModel.markAllAlertsAsRead() }
                            .padding(4.dp)
                            .testTag("mark_all_read_btn")
                    )
                }
            }

            // Category Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { (catKey, label) ->
                    val isSelected = selectedCategory == catKey
                    val count = if (catKey == "ALL") alerts.size else alerts.count { it.category.equals(catKey, ignoreCase = true) }
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = catKey },
                        label = {
                            Text(
                                text = "$label ($count)",
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = TNTDarkCard,
                            labelColor = TNTTextSecondary,
                            selectedContainerColor = TNTYellow,
                            selectedLabelColor = Color.Black
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = TNTDarkCardBorder,
                            selectedBorderColor = TNTYellow
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.testTag("alert_category_chip_${catKey.lowercase().replace(" ", "_")}")
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            if (filteredAlerts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = "No alerts",
                            tint = TNTTextMuted,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No alerts in this category",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TNTTextPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "All TNT bus services across India are operating smoothly on schedule.",
                            fontSize = 13.sp,
                            color = TNTTextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(filteredAlerts, key = { it.id }) { alert ->
                        AlertCard(
                            alert = alert,
                            onMarkRead = { viewModel.markAlertAsRead(alert.id) },
                            onDismiss = { viewModel.dismissAlert(alert.id) }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertCard(
    alert: TravelAlert,
    onMarkRead: () -> Unit,
    onDismiss: () -> Unit
) {
    val categoryIcon: ImageVector = when (alert.category.uppercase()) {
        "HIGHWAY & TRAFFIC" -> Icons.Default.Traffic
        "OFFERS & FESTIVE" -> Icons.Default.LocalOffer
        "WEATHER" -> Icons.Default.WbSunny
        else -> Icons.Default.DirectionsBus
    }

    val categoryColor: Color = when (alert.category.uppercase()) {
        "HIGHWAY & TRAFFIC" -> if (alert.isHighPriority) TNTError else TNTYellow
        "OFFERS & FESTIVE" -> TNTSuccess
        "WEATHER" -> Color(0xFF64B5F6)
        else -> TNTYellow
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!alert.isRead) TNTDarkCard else TNTDarkCard.copy(alpha = 0.6f)
        ),
        border = BorderStroke(
            width = if (!alert.isRead) 1.5.dp else 1.dp,
            color = if (!alert.isRead) (if (alert.isHighPriority) TNTError.copy(alpha = 0.8f) else TNTYellow.copy(alpha = 0.5f)) else TNTDarkCardBorder
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMarkRead() }
            .testTag("alert_item_card_${alert.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Category Badge + Timestamp + Dismiss
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(categoryColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = categoryIcon,
                            contentDescription = alert.category,
                            tint = categoryColor,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = alert.category,
                        color = categoryColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )

                    if (alert.isHighPriority) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(TNTError)
                                .padding(horizontal = 5.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "HIGH PRIORITY",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Time",
                        tint = TNTTextMuted,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = alert.timestamp,
                        color = TNTTextMuted,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(24.dp)
                            .testTag("dismiss_alert_${alert.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Dismiss Alert",
                            tint = TNTTextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Title
            Text(
                text = alert.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (!alert.isRead) TNTTextPrimary else TNTTextSecondary,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Description
            Text(
                text = alert.description,
                fontSize = 13.sp,
                color = TNTTextSecondary,
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Affected Route Tag
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsBus,
                    contentDescription = "Route",
                    tint = TNTYellow,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = alert.route,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TNTYellow
                )
            }
        }
    }
}
