package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetDetailsScreen(onBack: () -> Unit) {
    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Inflatio Smart", fontWeight = FontWeight.ExtraBold, color = BrandPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark)
                    }
                },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Filled.Notifications, null, tint = BrandDark) }
                    Box(modifier = Modifier.padding(end = 16.dp)) {
                        LogoIconSmall()
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Surface(color = BrandSecondary.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                    Text("STOCK • NSE", style = MaterialTheme.typography.labelSmall.copy(color = BrandSecondary, fontWeight = FontWeight.ExtraBold), modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Reliance Industries", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {}, modifier = Modifier.background(Color.White, CircleShape)) { Icon(Icons.Filled.Edit, null, modifier = Modifier.size(20.dp), tint = BrandPrimary) }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {}, modifier = Modifier.background(Color.White, CircleShape)) { Icon(Icons.Filled.Delete, null, tint = NegativeRed, modifier = Modifier.size(20.dp)) }
                }
                Text("RELIANCE", style = MaterialTheme.typography.bodyLarge.copy(color = TextGray, fontWeight = FontWeight.Bold))
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    VibrantGainCard("Absolute Gain", "₹2,50,000", PositiveGreen, Modifier.weight(1f))
                    VibrantGainCard("Real Gain", "₹1,80,000", BrandPrimary, Modifier.weight(1f))
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                VibrantStatsGridCard()
                
                Spacer(modifier = Modifier.height(24.dp))
                
                VibrantGrowthChartCard()
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text("CASH FLOW HISTORY", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.height(12.dp))
                
                VibrantCashFlowTable()
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(
                    onClick = {}, 
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(2.dp, BrandPrimary.copy(alpha = 0.2f)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text("VIEW ALL TRANSACTIONS", color = BrandPrimary, fontWeight = FontWeight.ExtraBold)
                }
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Box(
                    modifier = Modifier.fillMaxWidth().height(160.dp).background(
                        brush = Brush.horizontalGradient(listOf(BrandPrimary, BrandSecondary)),
                        shape = RoundedCornerShape(24.dp)
                    ).padding(24.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        "Reflecting institutional precision in wealth management.",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun VibrantGainCard(label: String, value: String, color: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
            Text(value, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = color))
        }
    }
}

@Composable
fun VibrantStatsGridCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BrandDark),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                VibrantStatSubItem("Real XIRR", "12.5%", BrandGreen)
                VibrantStatSubItem("Nominal XIRR", "18.2%", Color.White)
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                VibrantStatSubItem("Real CAGR", "9.8%", BrandGreen)
                VibrantStatSubItem("Nominal CAGR", "15.0%", Color.White)
            }
        }
    }
}

@Composable
fun VibrantStatSubItem(label: String, value: String, valueColor: Color) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall.copy(color = Color.White.copy(alpha = 0.6f), fontWeight = FontWeight.Bold))
        Text(
            value,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = valueColor)
        )
    }
}

@Composable
fun VibrantGrowthChartCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("GROWTH VS. INFLATION", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Row(verticalAlignment = Alignment.CenterVertically) {
                   Box(modifier = Modifier.size(10.dp).background(BrandDark, CircleShape))
                   Text(" Nominal", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BrandDark)
                   Spacer(modifier = Modifier.width(12.dp))
                   Box(modifier = Modifier.size(10.dp).background(BrandGreen, CircleShape))
                   Text(" Real", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = BrandGreen)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier.fillMaxWidth().height(180.dp).background(BrandPrimary.copy(alpha = 0.05f), RoundedCornerShape(12.dp))) 
        }
    }
}

@Composable
fun VibrantCashFlowTable() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth().background(BrandPrimary.copy(alpha = 0.05f)).padding(16.dp)) {
                Text("Date", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall.copy(color = BrandPrimary, fontWeight = FontWeight.ExtraBold))
                Text("Type", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall.copy(color = BrandPrimary, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center))
                Text("Amount", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall.copy(color = BrandPrimary, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.End))
            }
            VibrantCashFlowRow("12 Jan 2024", "BUY", "₹1,20,000", BrandPrimary)
            HorizontalDivider(color = DividerColor, modifier = Modifier.padding(horizontal = 16.dp))
            VibrantCashFlowRow("05 Oct 2023", "DIVIDEND", "₹4,200", BrandGreen)
            HorizontalDivider(color = DividerColor, modifier = Modifier.padding(horizontal = 16.dp))
            VibrantCashFlowRow("18 May 2023", "BUY", "₹80,000", BrandPrimary)
        }
    }
}

@Composable
fun VibrantCashFlowRow(date: String, type: String, amount: String, typeColor: Color) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(date, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = BrandDark)
        Surface(color = typeColor.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp), modifier = Modifier.weight(1f)) {
            Text(type, color = typeColor, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), fontSize = 10.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold)
        }
        Text(amount, modifier = Modifier.weight(1f), textAlign = TextAlign.End, fontWeight = FontWeight.ExtraBold, color = BrandDark)
    }
}
