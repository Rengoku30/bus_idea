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
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun ForgotPasswordScreen(
    onNavigateBackToLogin: () -> Unit
) {
    // Steps: 1 = Request, 2 = Verify OTP, 3 = New Password, 4 = Success
    var currentStep by remember { mutableIntStateOf(1) }
    var recoveryMethod by remember { mutableIntStateOf(0) } // 0: Email, 1: Phone

    var email by remember { mutableStateOf("aarav.sharma@tntbus.in") }
    var phone by remember { mutableStateOf("9876543210") }
    var otpCode by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmNewPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successToast by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = TNTDarkBackground,
        topBar = {
            TNTNavHeader(
                onBack = {
                    if (currentStep > 1 && currentStep < 4) {
                        currentStep -= 1
                    } else {
                        onNavigateBackToLogin()
                    }
                },
                title = "Password Recovery",
                backTestTag = "forgot_password_back_btn"
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Step Indicator Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("1. Request", "2. Verify OTP", "3. Reset").forEachIndexed { index, stepName ->
                    val stepNum = index + 1
                    val isActive = currentStep == stepNum
                    val isDone = currentStep > stepNum

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    when {
                                        isDone -> TNTSuccess
                                        isActive -> TNTYellow
                                        else -> TNTDarkCard
                                    },
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isDone) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Done",
                                    tint = Color.Black,
                                    modifier = Modifier.size(16.dp)
                                )
                            } else {
                                Text(
                                    text = "$stepNum",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isActive) Color.Black else TNTTextMuted
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = stepName,
                            fontSize = 11.sp,
                            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                            color = if (isActive) TNTYellow else if (isDone) TNTSuccess else TNTTextMuted
                        )
                        if (index < 2) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("—", color = TNTDarkCardBorder, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Error banner
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

            // Success toast
            AnimatedVisibility(visible = successToast != null) {
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
                        text = successToast ?: "",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }

            when (currentStep) {
                1 -> {
                    // STEP 1: Request Reset
                    Text(
                        text = "Reset Your Password",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = TNTTextPrimary,
                        letterSpacing = (-0.8).sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Choose how you'd like to receive your secure verification code.",
                        fontSize = 14.sp,
                        color = TNTTextSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Tab for Email / Phone
                    TabRow(
                        selectedTabIndex = recoveryMethod,
                        containerColor = TNTDarkSurface,
                        contentColor = TNTYellow,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[recoveryMethod]),
                                color = TNTYellow
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Tab(
                            selected = recoveryMethod == 0,
                            onClick = {
                                recoveryMethod = 0
                                errorMessage = null
                            },
                            text = {
                                Text(
                                    text = "Email Recovery",
                                    fontWeight = if (recoveryMethod == 0) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp,
                                    color = if (recoveryMethod == 0) TNTYellow else TNTTextSecondary
                                )
                            }
                        )
                        Tab(
                            selected = recoveryMethod == 1,
                            onClick = {
                                recoveryMethod = 1
                                errorMessage = null
                            },
                            text = {
                                Text(
                                    text = "SMS / WhatsApp OTP",
                                    fontWeight = if (recoveryMethod == 1) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp,
                                    color = if (recoveryMethod == 1) TNTYellow else TNTTextSecondary
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    if (recoveryMethod == 0) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "REGISTERED EMAIL",
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
                                    .testTag("forgot_password_email_input")
                            )
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "REGISTERED MOBILE NUMBER",
                                color = TNTYellow,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.8.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = phone,
                                onValueChange = {
                                    if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                                        phone = it
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
                                        Text("+91", color = TNTTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
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
                                    .testTag("forgot_password_phone_input")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Button(
                        onClick = {
                            if (recoveryMethod == 0 && (email.isBlank() || !email.contains("@"))) {
                                errorMessage = "Please enter a valid registered email address."
                                return@Button
                            }
                            if (recoveryMethod == 1 && phone.length < 10) {
                                errorMessage = "Please enter a valid 10-digit mobile number."
                                return@Button
                            }
                            otpCode = "4920"
                            successToast = if (recoveryMethod == 0) "Verification code sent to $email" else "OTP sent to +91 $phone"
                            currentStep = 2
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TNTYellowBright,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("send_reset_code_btn")
                    ) {
                        Text(
                            text = "SEND VERIFICATION CODE",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                2 -> {
                    // STEP 2: Verify OTP
                    Text(
                        text = "Enter Verification Code",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = TNTTextPrimary,
                        letterSpacing = (-0.8).sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (recoveryMethod == 0)
                            "We sent a 4-digit security code to $email"
                        else
                            "We sent a 4-digit SMS OTP to +91 $phone",
                        fontSize = 14.sp,
                        color = TNTTextSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "4-DIGIT VERIFICATION CODE",
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
                            placeholder = { Text("4920", color = TNTTextMuted) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Security,
                                    contentDescription = "OTP",
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("verify_otp_input")
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(TNTDarkCard)
                                    .clickable { otpCode = "4920" }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("⚡ Quick Fill: 4920", color = TNTYellow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            Text(
                                text = "Resend Code",
                                color = TNTTextSecondary,
                                fontSize = 12.sp,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier
                                    .clickable {
                                        successToast = "New code resent successfully!"
                                    }
                                    .padding(4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Button(
                        onClick = {
                            if (otpCode.length < 4) {
                                errorMessage = "Please enter the 4-digit verification code."
                                return@Button
                            }
                            successToast = "Code verified successfully!"
                            currentStep = 3
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TNTYellowBright,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("verify_otp_btn")
                    ) {
                        Text(
                            text = "VERIFY & CONTINUE",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Verify",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                3 -> {
                    // STEP 3: Set New Password
                    Text(
                        text = "Create New Password",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = TNTTextPrimary,
                        letterSpacing = (-0.8).sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Your new password must be at least 6 characters long and different from previous passwords.",
                        fontSize = 14.sp,
                        color = TNTTextSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "NEW PASSWORD",
                            color = TNTYellow,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.8.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = {
                                newPassword = it
                                errorMessage = null
                            },
                            placeholder = { Text("Minimum 6 characters", color = TNTTextMuted) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "New Password",
                                    tint = TNTTextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                                    Icon(
                                        imageVector = if (newPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = "Toggle Password",
                                        tint = TNTTextSecondary
                                    )
                                }
                            },
                            visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                                .testTag("new_password_input")
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "CONFIRM NEW PASSWORD",
                            color = TNTYellow,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.8.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = confirmNewPassword,
                            onValueChange = {
                                confirmNewPassword = it
                                errorMessage = null
                            },
                            placeholder = { Text("Re-enter new password", color = TNTTextMuted) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Confirm Password",
                                    tint = TNTTextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = { confirmNewPasswordVisible = !confirmNewPasswordVisible }) {
                                    Icon(
                                        imageVector = if (confirmNewPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = "Toggle Password",
                                        tint = TNTTextSecondary
                                    )
                                }
                            },
                            visualTransformation = if (confirmNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                                .testTag("confirm_new_password_input")
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Button(
                        onClick = {
                            if (newPassword.length < 6) {
                                errorMessage = "Password must be at least 6 characters long."
                                return@Button
                            }
                            if (newPassword != confirmNewPassword) {
                                errorMessage = "Passwords do not match."
                                return@Button
                            }
                            currentStep = 4
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TNTYellowBright,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("update_password_btn")
                    ) {
                        Text(
                            text = "UPDATE PASSWORD",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Confirm",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                4 -> {
                    // STEP 4: Success State
                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(Color(0xFF1B3D23), CircleShape)
                            .border(BorderStroke(2.dp, TNTSuccess), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = TNTSuccess,
                            modifier = Modifier.size(54.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Password Reset Complete!",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        color = TNTTextPrimary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Your password has been updated securely. You can now log into TNTBus using your new credentials.",
                        fontSize = 14.sp,
                        color = TNTTextSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    Button(
                        onClick = onNavigateBackToLogin,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TNTYellowBright,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("back_to_login_after_reset_btn")
                    ) {
                        Text(
                            text = "PROCEED TO SIGN IN",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Proceed",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (currentStep < 4) {
                // Back to login text button
                Text(
                    text = "Back to Login",
                    color = TNTYellow,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { onNavigateBackToLogin() }
                        .padding(8.dp)
                        .testTag("back_to_login_link")
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
