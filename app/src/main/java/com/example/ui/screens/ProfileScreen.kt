package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.navigation.Screen
import com.example.ui.components.TNTBottomBar
import com.example.ui.theme.TNTDarkBackground
import com.example.ui.theme.TNTDarkCard
import com.example.ui.theme.TNTDarkCardBorder
import com.example.ui.theme.TNTDarkInput
import com.example.ui.theme.TNTDarkSurface
import com.example.ui.theme.TNTTextMuted
import com.example.ui.theme.TNTTextPrimary
import com.example.ui.theme.TNTTextSecondary
import com.example.ui.theme.TNTYellow
import com.example.ui.theme.TNTYellowBright
import com.example.viewmodel.TNTBusViewModel

@Composable
fun ProfileScreen(
    viewModel: TNTBusViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToMyBookings: () -> Unit,
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToForgotPassword: () -> Unit = {},
    onNavigateToAdminDashboard: () -> Unit = {}
) {
    val userProfile by viewModel.userProfile.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            containerColor = TNTDarkCard,
            title = {
                Text(
                    text = "Sign Out of TNTBus?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = "You will need to sign back in to view your tickets and express loyalty perks.",
                    color = TNTTextSecondary,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        viewModel.logout()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TNTYellow,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Sign Out", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = TNTTextSecondary)
                }
            }
        )
    }

    Scaffold(
        containerColor = TNTDarkBackground,
        bottomBar = {
            TNTBottomBar(
                currentRoute = Screen.Profile.route,
                onNavigate = { route ->
                    when (route) {
                        Screen.Home.route -> onNavigateToHome()
                        Screen.MyBookings.route -> onNavigateToMyBookings()
                        Screen.Alerts.route -> onNavigateToAlerts()
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .statusBarsPadding(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (userProfile.isLoggedIn) {
                // LOGGED IN STATE
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier.size(110.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(110.dp)
                                    .border(3.dp, TNTYellow, CircleShape)
                                    .padding(4.dp)
                                    .clip(CircleShape)
                                    .background(TNTDarkCard)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.avatar_user),
                                    contentDescription = "Profile Avatar",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            // Edit badge
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color(0xFF1E1E22), CircleShape)
                                    .border(1.5.dp, Color(0xFF383842), CircleShape)
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Avatar",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = userProfile.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(TNTYellow.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                                    .border(BorderStroke(1.dp, TNTYellow), RoundedCornerShape(20.dp))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Tier",
                                        tint = TNTYellow,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = userProfile.memberTier.uppercase(),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black,
                                        color = TNTYellow
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "•  ${userProfile.phone}",
                                fontSize = 13.sp,
                                color = TNTTextSecondary
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = userProfile.email,
                            fontSize = 13.sp,
                            color = TNTTextMuted
                        )
                    }
                }

                // Stats Dashboard Cards
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, TNTDarkCardBorder),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "14",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TNTYellow
                                )
                                Text(
                                    text = "TRIPS TAKEN",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TNTTextSecondary
                                )
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, TNTDarkCardBorder),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "₹1,250",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                                Text(
                                    text = "SMART COINS",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TNTTextSecondary
                                )
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, TNTDarkCardBorder),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "1 Active",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TNTYellowBright
                                )
                                Text(
                                    text = "BOOKINGS",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TNTTextSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Featured Admin Banner if user has admin role
                if (userProfile.isAdmin) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = TNTDarkInput),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.5.dp, TNTYellow),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 18.dp)
                                .testTag("profile_admin_banner_card")
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
                                                .size(36.dp)
                                                .background(TNTYellow.copy(alpha = 0.2f), CircleShape)
                                                .border(BorderStroke(1.dp, TNTYellow), CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.AdminPanelSettings,
                                                contentDescription = "Admin Shield",
                                                tint = TNTYellow,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = "FLEET OPS CONSOLE",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Black,
                                                color = TNTYellow,
                                                letterSpacing = 1.sp
                                            )
                                            Text(
                                                text = "Authorized Operations Manager",
                                                fontSize = 11.sp,
                                                color = TNTTextSecondary
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .background(TNTYellow, RoundedCornerShape(4.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "ADMIN",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color.Black
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Access all passenger bookings, update trip manifests, and dispatch new bus routes to national fleet schedules.",
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    lineHeight = 16.sp
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = onNavigateToAdminDashboard,
                                    colors = ButtonDefaults.buttonColors(containerColor = TNTYellowBright, contentColor = Color.Black),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(42.dp)
                                        .testTag("profile_open_admin_btn")
                                ) {
                                    Icon(Icons.Default.DirectionsBus, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("OPEN FLEET ADMIN DASHBOARD", fontWeight = FontWeight.Black, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }

                // Account & Security Group
                item {
                    Text(
                        text = "ACCOUNT & SECURITY",
                        color = TNTYellow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, start = 4.dp)
                    )

                    Card(
                        colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, TNTDarkCardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            ProfileMenuItem(
                                icon = Icons.Default.AdminPanelSettings,
                                title = "Fleet Operations Portal",
                                subtitle = if (userProfile.isAdmin) "Manage bookings and bus routes (Authorized)" else "Admin & dispatcher login required",
                                onClick = onNavigateToAdminDashboard,
                                testTag = "menu_admin_portal"
                            )
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(TNTDarkCardBorder))
                            }
                            ProfileMenuItem(
                                icon = Icons.Default.LockReset,
                                title = "Reset / Change Password",
                                subtitle = "Update your credentials securely",
                                onClick = onNavigateToForgotPassword,
                                testTag = "menu_reset_password"
                            )
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(TNTDarkCardBorder))
                            }
                            ProfileMenuItem(
                                icon = Icons.Default.Shield,
                                title = "Two-Factor Authentication",
                                subtitle = "SMS OTP Verification active (+91)",
                                onClick = { },
                                testTag = "menu_2fa"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                }

                // Services & Features Group
                item {
                    Text(
                        text = "TRAVEL SERVICES",
                        color = TNTYellow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, start = 4.dp)
                    )

                    Card(
                        colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, TNTDarkCardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            ProfileMenuItem(
                                icon = Icons.Default.NotificationsActive,
                                title = "Live Highway & Route Advisories",
                                subtitle = "View real-time alerts and notices",
                                onClick = onNavigateToAlerts,
                                testTag = "menu_live_alerts"
                            )
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(TNTDarkCardBorder))
                            }
                            ProfileMenuItem(
                                icon = Icons.Default.ReceiptLong,
                                title = "Booking History & Tax Invoices",
                                subtitle = "GST ready receipts and past trips",
                                onClick = onNavigateToMyBookings,
                                testTag = "menu_my_bookings"
                            )
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(TNTDarkCardBorder))
                            }
                            ProfileMenuItem(
                                icon = Icons.Default.CreditCard,
                                title = "Saved UPI & Payment Cards",
                                subtitle = "Google Pay, PhonePe, Cards",
                                onClick = { },
                                testTag = "menu_payments"
                            )
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(TNTDarkCardBorder))
                            }
                            ProfileMenuItem(
                                icon = Icons.Default.HelpOutline,
                                title = "24x7 Passenger Helpline",
                                subtitle = "Contact support & SOS emergency assistance",
                                onClick = { },
                                testTag = "menu_help"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Logout Button
                item {
                    OutlinedButton(
                        onClick = { showLogoutDialog = true },
                        border = BorderStroke(1.5.dp, TNTYellow),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = TNTYellow
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("logout_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = TNTYellow,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sign Out",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TNTYellow
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            } else {
                // LOGGED OUT / GUEST STATE
                item {
                    Spacer(modifier = Modifier.height(10.dp))

                    Card(
                        colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, TNTDarkCardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .background(TNTDarkInput, CircleShape)
                                    .border(BorderStroke(2.dp, TNTYellow), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Guest",
                                    tint = TNTYellow,
                                    modifier = Modifier.size(36.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Guest Traveler",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Sign in or create an account to view your confirmed tickets, earn loyalty SmartCoins, and save passenger profiles.",
                                fontSize = 13.sp,
                                color = TNTTextSecondary,
                                textAlign = TextAlign.Center,
                                lineHeight = 19.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Sign In button
                            Button(
                                onClick = onNavigateToLogin,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = TNTYellowBright,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .testTag("profile_login_btn")
                            ) {
                                Text("LOG IN TO TNTBUS", fontWeight = FontWeight.Black, fontSize = 14.sp)
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Register button
                            OutlinedButton(
                                onClick = onNavigateToRegister,
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, TNTYellow),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = TNTYellow
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("profile_register_btn")
                            ) {
                                Text("CREATE NEW ACCOUNT", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TNTYellow)
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Quick Demo Sign In Options
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(TNTDarkInput)
                                        .clickable {
                                            viewModel.login("aarav.sharma@tntbus.in", "Aarav Sharma", "+91 98765 43210")
                                        }
                                        .padding(horizontal = 8.dp, vertical = 8.dp)
                                        .testTag("quick_demo_sign_in"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("⚡ Traveler Aarav", color = TNTYellow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(TNTDarkInput)
                                        .border(BorderStroke(1.dp, TNTYellow.copy(alpha = 0.5f)), RoundedCornerShape(8.dp))
                                        .clickable {
                                            viewModel.loginAsAdmin()
                                        }
                                        .padding(horizontal = 8.dp, vertical = 8.dp)
                                        .testTag("quick_admin_sign_in"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("🛡️ Fleet Admin", color = TNTYellow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Public Services Group
                    Card(
                        colors = CardDefaults.cardColors(containerColor = TNTDarkCard),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, TNTDarkCardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            ProfileMenuItem(
                                icon = Icons.Default.AdminPanelSettings,
                                title = "Fleet Operations Portal (Admin)",
                                subtitle = "Authorized dispatch & booking console",
                                onClick = onNavigateToAdminDashboard,
                                testTag = "guest_menu_admin_portal"
                            )
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(TNTDarkCardBorder))
                            }
                            ProfileMenuItem(
                                icon = Icons.Default.NotificationsActive,
                                title = "Live Highway & Route Advisories",
                                subtitle = "View real-time alerts and notices",
                                onClick = onNavigateToAlerts,
                                testTag = "guest_menu_alerts"
                            )
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(TNTDarkCardBorder))
                            }
                            ProfileMenuItem(
                                icon = Icons.Default.LockReset,
                                title = "Forgot / Reset Password",
                                subtitle = "Recover account access via email/SMS",
                                onClick = onNavigateToForgotPassword,
                                testTag = "guest_menu_forgot_password"
                            )
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(TNTDarkCardBorder))
                            }
                            ProfileMenuItem(
                                icon = Icons.Default.HelpOutline,
                                title = "Passenger Helpline & FAQ",
                                subtitle = "General guidelines & travel safety",
                                onClick = { },
                                testTag = "guest_menu_help"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .testTag(testTag)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(TNTDarkInput, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = TNTTextPrimary,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TNTTextPrimary
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = TNTTextSecondary
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = "Navigate",
            tint = TNTTextMuted,
            modifier = Modifier.size(13.dp)
        )
    }
}
