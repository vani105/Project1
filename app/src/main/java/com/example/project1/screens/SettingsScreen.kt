package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project1.data.FirebaseManager
import com.example.project1.data.UserProfile
import com.example.project1.ui.theme.*
import com.example.project1.data.AppViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController, viewModel: AppViewModel) {
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    AppNavigationWrapper(navController, "settings") { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().background(BrandBackground).padding(padding).padding(horizontal = 16.dp)) {
            item {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Settings", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                    IconButton(onClick = {}, modifier = Modifier.background(Color.White, CircleShape)) {
                        Icon(Icons.Default.Notifications, null, tint = BrandDark)
                    }
                }
                
                VibrantProfileSection(viewModel.userProfile)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                VibrantSettingsGroup("ACCOUNT") {
                    VibrantSettingsItem(Icons.Default.Person, "Personal Information") { navController.navigate("personal_info") }
                    VibrantSettingsItem(Icons.Default.Lock, "Login & Security") { navController.navigate("login_security") }
                }
                
                VibrantSettingsGroup("PREFERENCES") {
                    VibrantSettingsItem(Icons.Default.HelpOutline, "Help Center") { navController.navigate("help_center") }
                    VibrantSettingsItem(Icons.Default.ChatBubbleOutline, "Contact Support") { navController.navigate("support") }
                    VibrantSettingsItem(Icons.Default.Info, "App Version", "v2.4.1")
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = {
                        FirebaseManager.logout()
                        viewModel.userProfile = null // Clear profile state
                        navController.navigate("landing") {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NegativeRed.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, NegativeRed.copy(alpha = 0.2f))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.AutoMirrored.Filled.Logout, null, tint = NegativeRed)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Sign Out", color = NegativeRed, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                    }
                }
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun VibrantProfileSection(profile: UserProfile?) {
    val maskedEmail = remember(profile?.email) {
        val email = profile?.email ?: ""
        if (email.contains("@")) {
            val parts = email.split("@")
            val name = parts[0]
            if (name.length > 2) {
                name.take(2) + "****@" + parts[1]
            } else {
                "****@" + parts[1]
            }
        } else email
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(72.dp).background(BrandPrimary.copy(alpha = 0.1f), RoundedCornerShape(20.dp)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Person, null, modifier = Modifier.size(44.dp), tint = BrandPrimary)
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(profile?.fullName ?: "Invest Manager", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = BrandDark)
                Text(maskedEmail, style = MaterialTheme.typography.bodySmall.copy(color = Color.Black, fontWeight = FontWeight.Bold))
                if (profile?.isPro == true) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(color = BrandPrimary, shape = RoundedCornerShape(10.dp)) {
                        Text("PRO MEMBER", color = Color.White, modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp), fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}

@Composable
fun VibrantSettingsGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(title, style = MaterialTheme.typography.labelSmall.copy(color = BrandPrimary, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp))
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(content = content)
        }
    }
}

@Composable
fun VibrantSettingsItem(icon: ImageVector, title: String, badge: String? = null, onClick: () -> Unit = {}) {
    Surface(onClick = onClick, color = Color.Transparent) {
        Column {
            Row(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).background(BrandBackground, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                    Icon(icon, null, modifier = Modifier.size(20.dp), tint = BrandPrimary)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(title, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = BrandDark, fontSize = 16.sp)
                if (badge != null) {
                    Surface(color = BrandGreen.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                        Text(badge, color = BrandGreen, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = BrandDark)
            }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = DividerColor.copy(alpha = 0.5f))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfoScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    val user = FirebaseManager.getCurrentUser()
    val isVerified = user?.isEmailVerified ?: false

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Personal Details", fontWeight = FontWeight.ExtraBold, color = BrandDark) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                actions = { IconButton(onClick = {}) { Icon(Icons.Default.Notifications, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
           item {
                Spacer(modifier = Modifier.height(16.dp))
                VibrantProfilePictureEdit(viewModel.userProfile)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                VibrantInfoField("Full Name", viewModel.userProfile?.fullName ?: "N/A")
                
                Column {
                    VibrantInfoField("Email Address", viewModel.userProfile?.email ?: "N/A", isVerified = isVerified)
                    if (!isVerified) {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    val result = FirebaseManager.sendEmailVerification()
                                    if (result.isSuccess) {
                                        android.widget.Toast.makeText(context, "Verification email sent!", android.widget.Toast.LENGTH_SHORT).show()
                                    } else {
                                        android.widget.Toast.makeText(context, "Error: ${result.exceptionOrNull()?.message}", android.widget.Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        ) {
                            Text("Resend Verification Email", color = BrandPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                VibrantInfoField("Phone Number", viewModel.userProfile?.phoneNumber ?: "N/A")
                VibrantInfoField("Date of Birth", "05/12/1985")
                
                Spacer(modifier = Modifier.height(32.dp))
                
                VibrantDataPrivacyNote()
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Button(
                    onClick = onBack, 
                    modifier = Modifier.fillMaxWidth().height(64.dp), 
                    colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary), 
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text("Save Changes", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.height(48.dp))
           }
        }
    }
}

@Composable
fun VibrantProfilePictureEdit(profile: UserProfile?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(120.dp)) {
                Box(modifier = Modifier.fillMaxSize().background(BrandPrimary.copy(alpha = 0.1f), RoundedCornerShape(36.dp)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Person, null, modifier = Modifier.size(64.dp), tint = BrandPrimary)
                }
                IconButton(onClick = {}, modifier = Modifier.align(Alignment.BottomEnd).background(BrandDark, CircleShape).size(36.dp).padding(8.dp)) {
                    Icon(Icons.Default.Edit, null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(profile?.fullName ?: "Invest Manager", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = BrandDark)
            Text(profile?.email ?: "user@example.com", style = MaterialTheme.typography.bodySmall.copy(color = Color.Black, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = {}, shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = BrandBackground), elevation = null) { 
                    Text("Upload New", color = BrandPrimary, fontWeight = FontWeight.Bold) 
                }
                TextButton(onClick = {}) { Text("Remove", color = NegativeRed, fontWeight = FontWeight.Bold) }
            }
        }
    }
}

@Composable
fun VibrantInfoField(label: String, value: String, isVerified: Boolean = false) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(label, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedContainerColor = Color.White, 
                focusedContainerColor = Color.White, 
                unfocusedBorderColor = DividerColor
            ),
            trailingIcon = if (isVerified) { {
                Surface(color = BrandGreen.copy(alpha = 0.1f), shape = RoundedCornerShape(10.dp), modifier = Modifier.padding(end = 8.dp)) {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, null, tint = BrandGreen, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("VERIFIED", color = BrandGreen, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
            } } else null
        )
    }
}

@Composable
fun VibrantDataPrivacyNote() {
    Card(colors = CardDefaults.cardColors(containerColor = InfoBlueBg), shape = RoundedCornerShape(20.dp)) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.Top) {
            Icon(Icons.Default.Shield, null, tint = BrandPrimary)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Institutional-Grade Security", fontWeight = FontWeight.ExtraBold, color = BrandDark)
                Text("Your data is encrypted using AES-256 and stored in compliant data centers.", style = MaterialTheme.typography.bodySmall.copy(color = Color.Black, fontWeight = FontWeight.Bold))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginSecurityScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    var newPassword by remember { mutableStateOf("") }
    var currentPassword by remember { mutableStateOf("") }
    var showReauthDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    
    val scope = rememberCoroutineScope()
    val context = androidx.compose.ui.platform.LocalContext.current

    if (showReauthDialog) {
        AlertDialog(
            onDismissRequest = { showReauthDialog = false },
            title = { Text("Verify Identity", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("This is a sensitive operation. Please enter your current password to continue.")
                    Spacer(modifier = Modifier.height(16.dp))
                    VibrantTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        label = "CURRENT PASSWORD",
                        placeholder = "********",
                        isPassword = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (currentPassword.isEmpty()) return@Button
                        showReauthDialog = false
                        isLoading = true
                        scope.launch {
                            val result = FirebaseManager.updatePassword(newPassword, currentPassword)
                            isLoading = false
                            if (result.isSuccess) {
                                android.widget.Toast.makeText(context, "Password updated successfully!", android.widget.Toast.LENGTH_SHORT).show()
                                newPassword = ""
                                currentPassword = ""
                            } else {
                                val error = result.exceptionOrNull()?.message ?: "Unknown error"
                                android.widget.Toast.makeText(context, "Update failed: $error", android.widget.Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrandDark)
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showReauthDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Login & Security", fontWeight = FontWeight.ExtraBold, color = BrandDark) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                actions = { IconButton(onClick = {}) { Icon(Icons.Default.Notifications, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("Manage credentials and security protocols.", color = Color.Black, fontWeight = FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Change Password Section
                VibrantSecurityCard(title = "Change Password", icon = Icons.Default.SyncLock) {
                    VibrantTextField(value = newPassword, onValueChange = { newPassword = it }, label = "NEW PASSWORD", placeholder = "********", isPassword = true)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (newPassword.length < 6) {
                                android.widget.Toast.makeText(context, "Min 6 characters required", android.widget.Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            showReauthDialog = true
                        },
                        enabled = !isLoading,
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandDark),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Update Password", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Biometric Auth
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                    Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(48.dp).background(BrandGreen.copy(alpha = 0.1f), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Fingerprint, null, tint = BrandGreen)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Biometric Authentication", fontWeight = FontWeight.ExtraBold, color = BrandDark)
                            Text("Use Face ID or Touch ID for faster access.", style = MaterialTheme.typography.bodySmall.copy(color = Color.Black, fontWeight = FontWeight.Bold))
                        }
                        Switch(checked = viewModel.volAlertsEnabled, onCheckedChange = { 
                            viewModel.volAlertsEnabled = it 
                            viewModel.syncPreferences()
                        }, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = BrandPrimary))
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // 2FA Section
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                    Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(48.dp).background(BrandPrimary.copy(alpha = 0.1f), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Security, null, tint = BrandPrimary)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Two-Factor Auth", fontWeight = FontWeight.ExtraBold, color = BrandDark)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(6.dp).background(BrandGreen, CircleShape))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Active", color = BrandGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        OutlinedButton(onClick = {}, shape = RoundedCornerShape(12.dp), border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)) {
                            Text("Manage", color = BrandDark, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("ACTIVE SESSIONS", style = MaterialTheme.typography.labelSmall.copy(color = Color.Black, fontWeight = FontWeight.ExtraBold))
                    TextButton(onClick = {}) { Text("Revoke All", color = NegativeRed, fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                SessionItem("MacBook Pro 16\"", "Chrome • Mumbai, India", "IP: 192.168.1.1", "Current", Icons.Default.Laptop)
                Spacer(modifier = Modifier.height(12.dp))
                SessionItem("iPhone 15 Pro", "Inflatio Smart App • Bangalore, India", "Active 2h ago", null, Icons.Default.Smartphone)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = BrandDark), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lightbulb, null, tint = AccentYellow)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Security Tip", color = Color.White, fontWeight = FontWeight.ExtraBold)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Institutional accounts are recommended to rotate passwords every 90 days and use hardware-based 2FA tokens for maximum protection.", color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Read Security Protocol ↗", color = BrandGreen, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun VibrantSecurityCard(title: String, icon: ImageVector, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).background(BrandPrimary.copy(alpha = 0.1f), RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = BrandPrimary, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
            content()
        }
    }
}

@Composable
fun SessionItem(device: String, location: String, status: String, badge: String?, icon: ImageVector) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(44.dp).background(BrandBackground, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = BrandDark, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(device, fontWeight = FontWeight.ExtraBold, color = BrandDark)
                    if (badge != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(color = BrandGreen.copy(alpha = 0.1f), shape = RoundedCornerShape(6.dp)) {
                            Text(badge, color = BrandGreen, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)
                        }
                    }
                }
                Text(location, style = MaterialTheme.typography.bodySmall.copy(color = Color.Black, fontWeight = FontWeight.Bold))
                Text(status, style = MaterialTheme.typography.labelSmall.copy(color = Color.Black))
            }
        }
    }
}
