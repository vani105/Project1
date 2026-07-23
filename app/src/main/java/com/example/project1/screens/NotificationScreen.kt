package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(onBack: () -> Unit) {
    val notifications = listOf(
        NotificationData("Market Alert", "Nifty 50 reached a new all-time high. Check your Alpha.", Icons.Default.TrendingUp, BrandGreen, "2h ago"),
        NotificationData("Inflation Update", "RBI announced CPI inflation at 5.1% for June 2024.", Icons.Default.Public, BrandSecondary, "5h ago"),
        NotificationData("Security Notice", "Your account was successfully re-authenticated for password change.", Icons.Default.Shield, BrandPrimary, "1d ago"),
        NotificationData("Portfolio Digest", "Your weekly performance report is now available.", Icons.Default.PieChart, BrandPrimary, "2d ago"),
        NotificationData("New Feature", "XIRR Analyzer now supports dynamic cash flow additions.", Icons.Default.AutoGraph, BrandGreen, "3d ago")
    )

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Notifications", fontWeight = FontWeight.ExtraBold, color = BrandDark) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("STAY UPDATED", style = MaterialTheme.typography.labelSmall.copy(color = BrandPrimary, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp))
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(notifications) { notification ->
                NotificationItem(notification)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun NotificationItem(data: NotificationData) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor.copy(alpha = 0.5f))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).background(data.color.copy(alpha = 0.1f), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Icon(data.icon, null, tint = data.color, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(data.title, fontWeight = FontWeight.ExtraBold, color = BrandDark, fontSize = 14.sp)
                    Text(data.time, style = MaterialTheme.typography.labelSmall.copy(color = TextGray))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(data.message, style = MaterialTheme.typography.bodySmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
            }
        }
    }
}

data class NotificationData(
    val title: String,
    val message: String,
    val icon: ImageVector,
    val color: Color,
    val time: String
)
