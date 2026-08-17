package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigation.Screen
import com.example.ui.theme.TNTDarkBackground
import com.example.ui.theme.TNTDarkCardBorder
import com.example.ui.theme.TNTTextMuted
import com.example.ui.theme.TNTYellow

enum class BottomTab(
    val title: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val route: String,
    val testTag: String
) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home, Screen.Home.route, "nav_home"),
    SEARCH("Home", Icons.Filled.Home, Icons.Outlined.Home, Screen.Home.route, "nav_home"),
    BOOKINGS("Bookings", Icons.Filled.ConfirmationNumber, Icons.Outlined.ConfirmationNumber, Screen.MyBookings.route, "nav_bookings"),
    TICKETS("Bookings", Icons.Filled.ConfirmationNumber, Icons.Outlined.ConfirmationNumber, Screen.MyBookings.route, "nav_bookings"),
    ALERTS("Alerts", Icons.Filled.Notifications, Icons.Outlined.Notifications, Screen.Alerts.route, "nav_alerts"),
    PROFILE("Profile", Icons.Filled.Person, Icons.Outlined.Person, Screen.Profile.route, "nav_profile")
}

@Composable
fun TNTBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val visibleTabs = listOf(BottomTab.HOME, BottomTab.BOOKINGS, BottomTab.ALERTS, BottomTab.PROFILE)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TNTDarkBackground)
            .navigationBarsPadding()
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = TNTDarkCardBorder
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            visibleTabs.forEach { tab ->
                val isSelected = when (tab) {
                    BottomTab.HOME, BottomTab.SEARCH -> currentRoute == Screen.Home.route || currentRoute == Screen.SearchResults.route
                    BottomTab.BOOKINGS, BottomTab.TICKETS -> currentRoute == Screen.MyBookings.route
                    BottomTab.ALERTS -> currentRoute == Screen.Alerts.route
                    BottomTab.PROFILE -> currentRoute == Screen.Profile.route
                }

                val interactionSource = remember { MutableInteractionSource() }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .testTag(tab.testTag)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onNavigate(tab.route)
                        }
                ) {
                    Icon(
                        imageVector = if (isSelected) tab.filledIcon else tab.outlinedIcon,
                        contentDescription = tab.title,
                        tint = if (isSelected) TNTYellow else TNTTextMuted,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = tab.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) TNTYellow else TNTTextMuted,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TNTBottomBar(
    currentTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val visibleTabs = listOf(BottomTab.HOME, BottomTab.BOOKINGS, BottomTab.ALERTS, BottomTab.PROFILE)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(TNTDarkBackground)
            .navigationBarsPadding()
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = TNTDarkCardBorder
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            visibleTabs.forEach { tab ->
                val isSelected = when (currentTab) {
                    BottomTab.HOME, BottomTab.SEARCH -> tab == BottomTab.HOME || tab == BottomTab.SEARCH
                    BottomTab.BOOKINGS, BottomTab.TICKETS -> tab == BottomTab.BOOKINGS || tab == BottomTab.TICKETS
                    BottomTab.ALERTS -> tab == BottomTab.ALERTS
                    BottomTab.PROFILE -> tab == BottomTab.PROFILE
                }

                val interactionSource = remember { MutableInteractionSource() }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .testTag(tab.testTag)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onTabSelected(tab)
                        }
                ) {
                    Icon(
                        imageVector = if (isSelected) tab.filledIcon else tab.outlinedIcon,
                        contentDescription = tab.title,
                        tint = if (isSelected) TNTYellow else TNTTextMuted,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = tab.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) TNTYellow else TNTTextMuted,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}
