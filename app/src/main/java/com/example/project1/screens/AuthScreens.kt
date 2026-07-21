package com.example.project1.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1.ui.theme.*

@Composable
fun SignUpScreen(onSignUp: () -> Unit, onLogin: () -> Unit, onGoogleLogin: () -> Unit) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var agreed by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(BrandBackground)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Inflatio Smart",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandPrimary)
            )
            Text(
                "Create your professional investment account",
                style = MaterialTheme.typography.bodyMedium.copy(color = TextGray),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedButton(
                onClick = onGoogleLogin,
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, BrandPrimary.copy(alpha = 0.2f)),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    GoogleIcon()
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Sign Up with Google", color = BrandDark, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = DividerColor)
                Text(" OR ", modifier = Modifier.padding(horizontal = 16.dp), color = TextGray, style = MaterialTheme.typography.labelMedium)
                HorizontalDivider(modifier = Modifier.weight(1f), color = DividerColor)
            }

            Spacer(modifier = Modifier.height(32.dp))

            VibrantTextField(value = fullName, onValueChange = { fullName = it }, label = "Full Name", placeholder = "Alexander Sterling")
            VibrantTextField(value = email, onValueChange = { email = it }, label = "Email Address", placeholder = "alex@invest.com", leadingIcon = Icons.Default.Email)
            VibrantTextField(value = password, onValueChange = { password = it }, label = "Password", placeholder = "Min. 8 characters", isPassword = true, leadingIcon = Icons.Default.Lock)
            VibrantTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = "Confirm Password", placeholder = "Re-enter password", isPassword = true, leadingIcon = Icons.Default.Lock)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically, 
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = agreed, 
                    onCheckedChange = { agreed = it },
                    colors = CheckboxDefaults.colors(checkedColor = BrandPrimary)
                )
                Text(
                    "I agree to the Terms of Service and Privacy Policy.", 
                    fontSize = 12.sp,
                    color = TextGray
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onSignUp,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text("Create Account", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = onLogin) {
                Text(
                    "Already have an account? Log In", 
                    color = BrandPrimary, 
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun LoginScreen(onLogin: () -> Unit, onSignUp: () -> Unit, onGoogleLogin: () -> Unit, onForgotPassword: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(BrandBackground)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                "Welcome Back",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark)
            )
            Text(
                "Sign in to your high-performance portfolio.",
                style = MaterialTheme.typography.bodyLarge.copy(color = TextGray)
            )

            Spacer(modifier = Modifier.height(48.dp))

            VibrantTextField(value = email, onValueChange = { email = it }, label = "Email Address", placeholder = "name@company.com", leadingIcon = Icons.Default.Email)
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Password", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                    TextButton(onClick = { onForgotPassword() }) {
                        Text("Forgot Password?", fontSize = 12.sp, color = BrandPrimary, fontWeight = FontWeight.Bold)
                    }
                }
                VibrantTextField(
                    value = password, 
                    onValueChange = { password = it }, 
                    label = "", 
                    placeholder = "........", 
                    isPassword = true,
                    showLabel = false,
                    leadingIcon = Icons.Default.Lock
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onLogin,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Log In", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = DividerColor)
                Text(" OR ", modifier = Modifier.padding(horizontal = 16.dp), color = TextGray)
                HorizontalDivider(modifier = Modifier.weight(1f), color = DividerColor)
            }

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedButton(
                onClick = onGoogleLogin,
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, BrandPrimary.copy(alpha = 0.2f)),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    GoogleIcon()
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Continue with Google", color = BrandDark, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = onSignUp) {
                Text("Don't have an account? Sign Up", color = BrandPrimary, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

@Composable
fun ForgotPasswordScreen(onSendLink: () -> Unit, onBackToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(BrandBackground)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                "Reset Password",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark)
            )
            Text(
                "Enter your email address and we'll send you a link to reset your password.",
                style = MaterialTheme.typography.bodyLarge.copy(color = TextGray),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            VibrantTextField(
                value = email, 
                onValueChange = { email = it }, 
                label = "Email Address", 
                placeholder = "name@company.com", 
                leadingIcon = Icons.Default.Email
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onSendLink,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text("Send Reset Link", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = onBackToLogin) {
                Text("Back to Login", color = BrandPrimary, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}
