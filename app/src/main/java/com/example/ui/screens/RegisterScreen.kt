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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.TNTNavHeader
import com.example.ui.theme.TNTDarkBackground
import com.example.ui.theme.TNTDarkCard
import com.example.ui.theme.TNTDarkCardBorder
import com.example.ui.theme.TNTDarkInput
import com.example.ui.theme.TNTDarkInputBorder
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
fun RegisterScreen(
    viewModel: TNTBusViewModel,
    onNavigateBackToLogin: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var fullName by remember { mutableStateOf("Aarav Sharma") }
    var email by remember { mutableStateOf("aarav.sharma@tntbus.in") }
    var phone by remember { mutableStateOf("9876543210") }
    var password by remember { mutableStateOf("Pass@1234") }
    var confirmPassword by remember { mutableStateOf("Pass@1234") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var termsAgreed by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(successMessage) {
        if (successMessage != null) {
            delay(1000L)
            onNavigateBackToLogin()
        }
    }

    // Calculate password strength
    val passwordStrength = remember(password) {
        when {
            password.length < 6 -> "Too Short" to TNTError
            password.length < 8 || !password.any { it.isDigit() } -> "Medium" to TNTYellow
            else -> "Strong" to TNTSuccess
        }
    }

    Scaffold(
        containerColor = TNTDarkBackground,
        topBar = {
            TNTNavHeader(
                onBack = onNavigateBackToLogin,
                title = "Create Account",
                backTestTag = "register_back_btn"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Badge
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
                    text = "NEW MEMBER REGISTRATION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = TNTYellow,
                    letterSpacing = 0.8.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Join TNTBus India",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = TNTTextPrimary,
                letterSpacing = (-0.8).sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Unlock smart seat selection, instant UPI refunds, and 500 bonus loyalty coins.",
                fontSize = 14.sp,
                color = TNTTextSecondary,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Demo prefill chip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(TNTDarkCard)
                        .border(BorderStroke(1.dp, TNTDarkCardBorder), RoundedCornerShape(8.dp))
                        .clickable {
                            fullName = "Aarav Sharma"
                            email = "aarav.sharma@tntbus.in"
                            phone = "9876543210"
                            password = "Travel@Pass2026"
                            confirmPassword = "Travel@Pass2026"
                            termsAgreed = true
                            errorMessage = null
                        }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .testTag("fill_sample_register_btn")
                ) {
                    Text("⚡ Fill Sample Details", color = TNTYellow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Error Message
            AnimatedVisibility(visible = errorMessage != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .background(Color(0xFF331414), RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, TNTError), RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Error",
                        tint = TNTError,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }

            // Success Message
            AnimatedVisibility(visible = successMessage != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .background(Color(0xFF14331E), RoundedCornerShape(8.dp))
                        .border(BorderStroke(1.dp, TNTSuccess), RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = TNTSuccess,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = successMessage ?: "",
                        color = Color.White,
                        fontSize = 13.sp
                    )
                }
            }

            // Full Name
            Text(
                text = "FULL NAME",
                color = TNTYellow,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    errorMessage = null
                },
                placeholder = { Text("e.g. Aarav Sharma", color = TNTTextMuted) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Name",
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
                    .testTag("register_name_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Email Address
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
                    .testTag("register_email_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Mobile Number
            Text(
                text = "INDIAN MOBILE NUMBER",
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
                    .testTag("register_phone_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Password
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PASSWORD",
                    color = TNTYellow,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.8.sp
                )
                if (password.isNotEmpty()) {
                    Text(
                        text = "Strength: ${passwordStrength.first}",
                        color = passwordStrength.second,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = null
                },
                placeholder = { Text("Minimum 6 characters", color = TNTTextMuted) },
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
                    .testTag("register_password_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Confirm Password
            Text(
                text = "CONFIRM PASSWORD",
                color = TNTYellow,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    errorMessage = null
                },
                placeholder = { Text("Re-enter password", color = TNTTextMuted) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Confirm Password",
                        tint = TNTTextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle Password",
                            tint = TNTTextSecondary
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                    .testTag("register_confirm_password_input")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Terms Checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { termsAgreed = !termsAgreed }
                    .padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = termsAgreed,
                    onCheckedChange = { termsAgreed = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = TNTYellow,
                        checkmarkColor = Color.Black,
                        uncheckedColor = TNTDarkInputBorder
                    ),
                    modifier = Modifier.testTag("terms_checkbox")
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "I agree to the TNTBus Terms of Service and Privacy Policy.",
                    color = TNTTextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Create Account Button
            Button(
                onClick = {
                    if (fullName.isBlank()) {
                        errorMessage = "Please enter your full name."
                        return@Button
                    }
                    if (email.isBlank() || !email.contains("@")) {
                        errorMessage = "Please enter a valid email address."
                        return@Button
                    }
                    if (phone.length < 10) {
                        errorMessage = "Please enter a valid 10-digit mobile number."
                        return@Button
                    }
                    if (password.length < 6) {
                        errorMessage = "Password must be at least 6 characters long."
                        return@Button
                    }
                    if (password != confirmPassword) {
                        errorMessage = "Passwords do not match."
                        return@Button
                    }
                    if (!termsAgreed) {
                        errorMessage = "Please agree to the Terms of Service to continue."
                        return@Button
                    }

                    viewModel.register(name = fullName, email = email, phone = "+91 $phone")
                    errorMessage = null
                    successMessage = "Account created successfully! Welcome to TNTBus, $fullName."
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = TNTYellowBright,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("create_account_btn")
            ) {
                Text(
                    text = "CREATE ACCOUNT",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black,
                    letterSpacing = 0.8.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Submit",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            // Footer
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Already have an account? ",
                    color = TNTTextSecondary,
                    fontSize = 14.sp
                )
                Text(
                    text = "Log In",
                    color = TNTYellow,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable { onNavigateToLogin() }
                        .padding(4.dp)
                        .testTag("already_have_account_login_link")
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
