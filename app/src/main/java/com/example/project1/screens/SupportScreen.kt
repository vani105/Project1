package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(onBack: () -> Unit) {
    var inquiryCategory by remember { mutableStateOf("Portfolio Management") }
    var message by remember { mutableStateOf("") }

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Concierge Support", fontWeight = FontWeight.ExtraBold, color = BrandDark) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                actions = { 
                    IconButton(onClick = {}) { Icon(Icons.Default.Notifications, null, tint = BrandDark) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("How can we help?", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Text("Our team typically responds within 2 minutes.", color = TextGray, fontWeight = FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                VibrantSupportCard(
                    title = "Live Chat",
                    subtitle = "Instant help from experts",
                    icon = Icons.Default.Chat,
                    buttonText = "START CHAT",
                    accentColor = BrandGreen
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                VibrantSupportCard(
                    title = "Email Priority",
                    subtitle = "4-6 hours average response",
                    icon = Icons.Default.Email,
                    buttonText = "SEND EMAIL",
                    accentColor = BrandPrimary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                VibrantSupportCard(
                    title = "Priority Callback",
                    subtitle = "Dedicated institutional line",
                    icon = Icons.Default.Phone,
                    buttonText = "+91 1800 200 4500",
                    accentColor = BrandSecondary
                )
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Text("Direct Message", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.height(20.dp))
                
                VibrantTextField(value = inquiryCategory, onValueChange = { inquiryCategory = it }, label = "INQUIRY CATEGORY", placeholder = "Portfolio Management")
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("DESCRIBE YOUR ISSUE", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    placeholder = { Text("How can we assist you today?", color = TextGray.copy(alpha = 0.5f)) },
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedBorderColor = DividerColor,
                        focusedBorderColor = BrandPrimary
                    )
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandDark),
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Text("SUBMIT MESSAGE", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                VibrantSupportHoursCard()
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun VibrantSupportCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, buttonText: String, accentColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(56.dp).background(accentColor.copy(alpha = 0.1f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = accentColor, modifier = Modifier.size(28.dp))
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark, fontSize = 18.sp)
                    Text(subtitle, style = MaterialTheme.typography.bodySmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (buttonText.startsWith("+")) BrandBackground else BrandDark
                ),
                elevation = null
            ) {
                Text(
                    buttonText, 
                    color = if (buttonText.startsWith("+")) BrandPrimary else Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun VibrantSupportHoursCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = BrandPrimary.copy(alpha = 0.05f)),
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BrandPrimary.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("OPERATING HOURS", style = MaterialTheme.typography.labelSmall.copy(color = BrandPrimary, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp))
            Spacer(modifier = Modifier.height(16.dp))
            VibrantHourRow("Monday - Friday", "09:00 - 20:00")
            VibrantHourRow("Saturday", "10:00 - 16:00")
            VibrantHourRow("Sunday", "Closed")
            
            Spacer(modifier = Modifier.height(20.dp))
            Surface(color = BrandGreen.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp)) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, null, tint = BrandGreen, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Standard response times apply outside these hours.", fontSize = 12.sp, color = BrandGreen, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun VibrantHourRow(day: String, hours: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(day, style = MaterialTheme.typography.bodyMedium.copy(color = BrandDark, fontWeight = FontWeight.Bold))
        Text(hours, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandPrimary))
    }
}
