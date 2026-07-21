package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project1.ui.theme.*

@Composable
fun AppNavigationWrapper(navController: NavController, currentRoute: String, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = BrandSurface, tonalElevation = 8.dp) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Home") },
                    selected = currentRoute == "home",
                    onClick = { if (currentRoute != "home") navController.navigate("home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BrandPrimary,
                        selectedTextColor = BrandPrimary,
                        indicatorColor = BrandPrimary.copy(alpha = 0.1f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.PieChart, null) },
                    label = { Text("Portfolio") },
                    selected = currentRoute == "portfolio",
                    onClick = { if (currentRoute != "portfolio") navController.navigate("portfolio") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BrandPrimary,
                        selectedTextColor = BrandPrimary,
                        indicatorColor = BrandPrimary.copy(alpha = 0.1f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Calculate, null) },
                    label = { Text("Calculator") },
                    selected = currentRoute == "calculator",
                    onClick = { if (currentRoute != "calculator") navController.navigate("calculator") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BrandPrimary,
                        selectedTextColor = BrandPrimary,
                        indicatorColor = BrandPrimary.copy(alpha = 0.1f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.History, null) },
                    label = { Text("History") },
                    selected = currentRoute == "history",
                    onClick = { if (currentRoute != "history") navController.navigate("history") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BrandPrimary,
                        selectedTextColor = BrandPrimary,
                        indicatorColor = BrandPrimary.copy(alpha = 0.1f)
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, null) },
                    label = { Text("Settings") },
                    selected = currentRoute == "settings",
                    onClick = { if (currentRoute != "settings") navController.navigate("settings") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = BrandPrimary,
                        selectedTextColor = BrandPrimary,
                        indicatorColor = BrandPrimary.copy(alpha = 0.1f)
                    )
                )
            }
        }
    ) { padding ->
        content(padding)
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    AppNavigationWrapper(navController, "home") { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                HomeHeader()
                Spacer(modifier = Modifier.height(24.dp))
                PortfolioSummaryCard()
                Spacer(modifier = Modifier.height(24.dp))
                StatsRow()
                Spacer(modifier = Modifier.height(24.dp))
                PortfolioGrowthCard()
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader("My Holdings", onAction = {})
            }

            items(holdings) { holding ->
                HoldingItem(holding) {
                    navController.navigate("asset_details")
                }
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LogoIcon()
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("Hello,", style = MaterialTheme.typography.labelSmall.copy(color = TextGray))
                Text("Invest Manager", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
            }
        }
        IconButton(onClick = {}, modifier = Modifier.background(BrandSurface, CircleShape)) {
            Icon(Icons.Default.Notifications, null, tint = BrandDark)
        }
    }
}

@Composable
fun PortfolioSummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BrandPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("TOTAL PORTFOLIO VALUE", style = MaterialTheme.typography.labelSmall.copy(color = Color.White.copy(alpha = 0.7f), fontWeight = FontWeight.Bold))
            Text("₹12,45,000", style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold, color = Color.White))
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Icon(Icons.AutoMirrored.Filled.TrendingUp, null, modifier = Modifier.size(16.dp), tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Inflation-Adjusted: ", style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.9f)))
                Text("₹11,80,000", style = MaterialTheme.typography.bodySmall.copy(color = Color.White, fontWeight = FontWeight.ExtraBold))
            }
        }
    }
}

@Composable
fun StatsRow() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        StatItem("XIRR", "18.7%", PositiveGreen, Modifier.weight(1f))
        StatItem("CAGR", "14.2%", BrandSecondary, Modifier.weight(1f))
        StatItem("Today's CPI", "5.1%", NegativeRed, Modifier.weight(1f))
    }
}

@Composable
fun StatItem(label: String, value: String, color: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
            Text(value, style = MaterialTheme.typography.titleLarge.copy(color = color, fontWeight = FontWeight.ExtraBold))
        }
    }
}

@Composable
fun PortfolioGrowthCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Portfolio Growth", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
            Text("vs. Purchasing Power", style = MaterialTheme.typography.bodySmall.copy(color = TextGray))
            
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxWidth().height(160.dp).background(BrandPrimary.copy(alpha = 0.05f), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Text("Chart Placeholder", color = BrandPrimary.copy(alpha = 0.4f), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, onAction: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        TextButton(onClick = onAction) {
            Text("View All", color = BrandPrimary, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun HoldingItem(holding: Holding, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).background(BrandPrimary.copy(alpha = 0.1f), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Text(holding.name.take(1), fontWeight = FontWeight.ExtraBold, color = BrandPrimary, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(holding.name, fontWeight = FontWeight.Bold, color = BrandDark)
                Text(holding.type, style = MaterialTheme.typography.bodySmall.copy(color = TextGray))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("₹${holding.value}", fontWeight = FontWeight.ExtraBold, color = BrandDark)
                Surface(color = PositiveGreen.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                    Text("+${holding.change}%", color = PositiveGreen, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

data class Holding(val name: String, val type: String, val value: String, val change: String)

val holdings = listOf(
    Holding("Reliance", "Equity • 14 Shares", "41,230", "12.4"),
    Holding("Nifty 50", "ETF • 120 Units", "2,45,100", "8.1"),
    Holding("ICICI Bluechip", "Mutual Fund • Direct", "1,12,000", "15.2"),
    Holding("Gold", "Commodity • SGB", "85,000", "4.5")
)
