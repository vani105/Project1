package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project1.ui.theme.*

@Composable
fun HistoryScreen(navController: NavController) {
    AppNavigationWrapper(navController, "history") { padding ->
        Column(modifier = Modifier.fillMaxSize().background(BrandBackground).padding(padding).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(44.dp).background(BrandPrimary.copy(alpha = 0.1f), CircleShape), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.History, null, tint = BrandPrimary, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Inflatio Smart", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {}) { Icon(Icons.Default.Notifications, null, tint = BrandDark) }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search calculations...", color = TextGray.copy(alpha = 0.5f)) },
                leadingIcon = { Icon(Icons.Default.Search, null, tint = BrandPrimary) },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = DividerColor,
                    focusedBorderColor = BrandPrimary
                )
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                VibrantFilterChip(selected = true, label = "All")
                VibrantFilterChip(selected = false, label = "SIP")
                VibrantFilterChip(selected = false, label = "CAGR")
                VibrantFilterChip(selected = false, label = "Lumpsum")
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("RECENT CALCULATIONS", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.ExtraBold))
            Spacer(modifier = Modifier.height(12.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(historyItems) { item ->
                    VibrantHistoryItemCard(item)
                }
                item { Spacer(modifier = Modifier.height(40.dp)) }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                Icon(Icons.Default.Refresh, null, tint = BrandPrimary.copy(alpha = 0.5f))
                Text("Swipe left on any item to delete it from history.", color = TextGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun VibrantFilterChip(selected: Boolean, label: String) {
    Surface(
        onClick = {},
        color = if (selected) BrandPrimary else Color.White,
        shape = RoundedCornerShape(12.dp),
        border = if (!selected) androidx.compose.foundation.BorderStroke(1.dp, DividerColor) else null,
        shadowElevation = if (selected) 4.dp else 0.dp
    ) {
        Text(
            label, 
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            fontWeight = FontWeight.ExtraBold,
            color = if (selected) Color.White else BrandDark,
            fontSize = 14.sp
        )
    }
}

@Composable
fun VibrantHistoryItemCard(item: HistoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        (if (item.type == "SIP") BrandPrimary else BrandSecondary).copy(alpha = 0.1f), 
                        RoundedCornerShape(14.dp)
                    ), 
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (item.type == "SIP") Icons.Default.TrendingUp else Icons.Default.Calculate, 
                    null, 
                    tint = if (item.type == "SIP") BrandPrimary else BrandSecondary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, fontWeight = FontWeight.ExtraBold, color = BrandDark, fontSize = 16.sp)
                Text("${item.date} • ${item.duration}", style = MaterialTheme.typography.bodySmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("₹${item.realValue} Real", color = PositiveGreen, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                Text("Nominal: ₹${item.nominalValue}", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
            }
        }
    }
}

data class HistoryItem(val title: String, val date: String, val duration: String, val realValue: String, val nominalValue: String, val type: String)

val historyItems = listOf(
    HistoryItem("SIP (Home Fund)", "Oct 24, 2023", "15 Years", "15.8L", "28.4L", "SIP"),
    HistoryItem("CAGR (Reliance)", "Oct 22, 2023", "5 Years", "8.5%", "14.2%", "CAGR"),
    HistoryItem("Retirement Corpus", "Oct 15, 2023", "25 Years", "4.2Cr", "12.8Cr", "SIP"),
    HistoryItem("Education Plan", "Oct 10, 2023", "10 Years", "45.0L", "82.5L", "SIP")
)
