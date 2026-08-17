package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.TNTNavHeader
import com.example.ui.theme.TNTDarkBackground
import com.example.ui.theme.TNTDarkCard
import com.example.ui.theme.TNTDarkCardBorder
import com.example.ui.theme.TNTDarkInput
import com.example.ui.theme.TNTDarkInputBorder
import com.example.ui.theme.TNTDarkSurface
import com.example.ui.theme.TNTError
import com.example.ui.theme.TNTSuccess
import com.example.ui.theme.TNTTextMuted
import com.example.ui.theme.TNTTextPrimary
import com.example.ui.theme.TNTTextSecondary
import com.example.ui.theme.TNTYellow
import com.example.ui.theme.TNTYellowBright
import com.example.viewmodel.TNTBusViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    viewModel: TNTBusViewModel,
    onNavigateBackToRouteDetails: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Email, 1: Mobile OTP

    // Email tab states
    var email by remember { mutableStateOf("aarav.sharma@tntbus.in") }
    var password by remember { mutableStateOf("Travel@2026") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Phone tab states
    var phoneNumber by remember { mutableStateOf("9876543210") }
    var otpCode by remember { mutableStateOf("") }
    var otpSent by remember { mutableStateOf(false) }

    var rememberMe by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(successMessage) {
        if (successMessage != null) {
            delay(1000L)
            onNavigateBackToRouteDetails()
        }
    }

    Scaffold(
        containerColor = TNTDarkBackground,
        topBar = {
            TNTNavHeader(
                onBack = onNavigateBackToRouteDetails,
                title = "TNTBus Account",
                backTestTag = "login_back_btn"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Badge / Subtitle
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(TNTDarkCard, RoundedCornerShape(20.dp))
                    .border(BorderStroke(1.dp, TNTDarkCardBorder), RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(TNTYellow, CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "TNTBUS MEMBER PORTAL",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = TNTYellow,
                    letterSpacing = 0.8.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Welcome Back",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = TNTTextPrimary,
                letterSpacing = (-0.8).sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Log in to manage bookings, track buses, and redeem smart coins.",
                fontSize = 14.sp,
                color = TNTTextSecondary,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Demo Quick Fill Chips
            Text(
                text = "QUICK DEMO ACCOUNTS",
                color = TNTYellow,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(TNTDarkCard)
                        .border(BorderStroke(1.dp, if (email.contains("aarav")) TNTYellow else TNTDarkCardBorder), RoundedCornerShape(10.dp))
                        .clickable {
                            email = "aarav.sharma@tntbus.in"
                            phoneNumber = "9876543210"
                            password = "Password@123"
                            selectedTab = 0
                            errorMessage = null
                        }
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .testTag("demo_user_aarav")
                ) {
                    Column {
                        Text("Aarav Sharma", color = TNTTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("Gold • Mumbai", color = TNTYellow, fontSize = 10.sp)
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(TNTDarkCard)
                        .border(BorderStroke(1.dp, if (email.contains("priya")) TNTYellow else TNTDarkCardBorder), RoundedCornerShape(10.dp))
                        .clickable {
                            email = "priya.patel@tntbus.in"
                            phoneNumber = "9123456789"
                            password = "Password@123"
                            selectedTab = 0
                            errorMessage = null
                        }
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                        .testTag("demo_user_priya")
                ) {
                    Column {
                        Text("Priya Patel", color = TNTTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("Silver • Pune", color = TNTTextSecondary, fontSize = 10.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Tab Selector
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = TNTDarkSurface,
                contentColor = TNTYellow,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = TNTYellow
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        errorMessage = null
                    },
                    text = {
                        Text(
                            text = "Email & Password",
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp,
                            color = if (selectedTab == 0) TNTYellow else TNTTextSecondary
                        )
                    },
                    modifier = Modifier.testTag("login_tab_email")
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        errorMessage = null
                    },
                    text = {
                        Text(
                            text = "Mobile OTP",
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp,
                            color = if (selectedTab == 1) TNTYellow else TNTTextSecondary
                        )
                    },
                    modifier = Modifier.testTag("login_tab_phone")
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Error banner if any
            AnimatedVisibility(visible = errorMessage != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp)
                        .background(Color(0xFF331414), RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, TNTError), RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Error",
                        tint = TNTError,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }

            // Success banner if any
            AnimatedVisibility(visible = successMessage != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp)
                        .background(Color(0xFF14331E), RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, TNTSuccess), RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = TNTSuccess,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = successMessage ?: "",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }

            if (selectedTab == 0) {
                // TAB 0: Email & Password
                Text(
                    text = "EMAIL ADDRESS",
                    color = TNTYellow,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = null
                    },
                    placeholder = { Text("e.g. aarav.sharma@tntbus.in", color = TNTTextMuted) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("login_email_input")
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                Text(
                    text = "PASSWORD",
                    color = TNTYellow,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = null
                    },
                    placeholder = { Text("••••••••", color = TNTTextMuted) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password",
                            tint = TNTTextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle Password",
                                tint = TNTTextSecondary
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("login_password_input")
                )
            } else {
                // TAB 1: Mobile + OTP
                Text(
                    text = "INDIAN MOBILE NUMBER",
                    color = TNTYellow,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                            phoneNumber = it
                            errorMessage = null
                        }
                    },
                    placeholder = { Text("10-digit mobile number", color = TNTTextMuted) },
                    leadingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 12.dp, end = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Phone",
                                tint = TNTTextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "+91",
                                color = TNTTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("login_phone_input")
                )

                Spacer(modifier = Modifier.height(14.dp))

                if (!otpSent) {
                    OutlinedButton(
                        onClick = {
                            if (phoneNumber.length < 10) {
                                errorMessage = "Please enter a valid 10-digit Indian mobile number."
                            } else {
                                otpSent = true
                                otpCode = "4920"
                                successMessage = "OTP sent to +91 $phoneNumber! (Demo code: 4920)"
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, TNTYellow),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = TNTDarkCard,
                            contentColor = TNTYellow
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("send_otp_btn")
                    ) {
                        Text("Send OTP", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                } else {
                    Text(
                        text = "ENTER 4-DIGIT OTP",
                        color = TNTYellow,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.8.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = otpCode,
                        onValueChange = {
                            if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                                otpCode = it
                                errorMessage = null
                            }
                        },
                        placeholder = { Text("e.g. 4920", color = TNTTextMuted) },
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("login_otp_input")
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "OTP: 4920",
                            color = TNTYellow,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable { otpCode = "4920" }
                                .padding(4.dp)
                        )

                        Text(
                            text = "Resend Code",
                            color = TNTTextSecondary,
                            fontSize = 12.sp,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .clickable {
                                    successMessage = "New OTP resent to +91 $phoneNumber!"
                                }
                                .padding(4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Remember Me & Forgot Password Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { rememberMe = !rememberMe }
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = TNTYellow,
                            checkmarkColor = Color.Black,
                            uncheckedColor = TNTDarkInputBorder
                        ),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Remember me",
                        color = TNTTextSecondary,
                        fontSize = 13.sp
                    )
                }

                Text(
                    text = "Forgot Password?",
                    color = TNTYellow,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { onNavigateToForgotPassword() }
                        .padding(vertical = 6.dp)
                        .testTag("forgot_password_link")
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary Sign In Button
            Button(
                onClick = {
                    if (selectedTab == 0) {
                        if (email.isBlank() || !email.contains("@")) {
                            errorMessage = "Please enter a valid email address."
                            return@Button
                        }
                        if (password.length < 4) {
                            errorMessage = "Please enter your password."
                            return@Button
                        }
                        val name = if (email.contains("priya", ignoreCase = true)) "Priya Patel" else "Aarav Sharma"
                        viewModel.login(email = email, name = name)
                        errorMessage = null
                        successMessage = "Signed in successfully! Welcome back, $name."
                    } else {
                        if (phoneNumber.length < 10) {
                            errorMessage = "Please enter a valid 10-digit phone number."
                            return@Button
                        }
                        if (!otpSent || otpCode.length < 4) {
                            errorMessage = "Please enter the 4-digit OTP sent to your phone."
                            return@Button
                        }
                        val name = if (phoneNumber.endsWith("89")) "Priya Patel" else "Aarav Sharma"
                        viewModel.loginWithPhone(phone = "+91 $phoneNumber", name = name)
                        errorMessage = null
                        successMessage = "Signed in successfully! Welcome back, $name."
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = TNTYellowBright,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("sign_in_btn")
            ) {
                Text(
                    text = "SIGN IN TO ACCOUNT",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Sign In",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Divider
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = TNTDarkCardBorder)
                Text(
                    text = "OR SIGN IN WITH",
                    color = TNTTextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = TNTDarkCardBorder)
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Social Logins
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        viewModel.login("aarav.sharma@gmail.com", "Aarav Sharma", "+91 98765 43210")
                        errorMessage = null
                        successMessage = "Signed in with Google! Welcome back, Aarav Sharma."
                    },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, TNTDarkCardBorder),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = TNTDarkCard,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("continue_google_btn")
                ) {
                    Text(
                        text = "G",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = TNTYellow
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Google",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                OutlinedButton(
                    onClick = {
                        viewModel.login("aarav.sharma@tntbus.in", "Aarav Sharma", "+91 98765 43210")
                        errorMessage = null
                        successMessage = "Signed in with Truecaller! Welcome back, Aarav Sharma."
                    },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, TNTDarkCardBorder),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = TNTDarkCard,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("continue_truecaller_btn")
                ) {
                    Text(
                        text = "⚡",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Truecaller",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Footer Register Prompt
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = TNTTextSecondary,
                    fontSize = 14.sp
                )
                Text(
                    text = "Sign Up / Register",
                    color = TNTYellow,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable { onNavigateToRegister() }
                        .padding(4.dp)
                        .testTag("sign_up_link")
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
