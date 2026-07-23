package com.example.project1.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project1.data.FirebaseManager
import com.example.project1.data.AppViewModel
import com.example.project1.data.UserHolding
import com.example.project1.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Locale

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
fun HomeScreen(navController: NavController, viewModel: AppViewModel) {
    var userHoldings by remember { mutableStateOf<List<UserHolding>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        userHoldings = FirebaseManager.getHoldings()
        if (userHoldings.isEmpty()) {
            userHoldings = defaultHoldings.map { UserHolding(name = it.name, type = it.type, value = it.value, change = it.change) }
        }
        isLoading = false
    }

    val totalNominal = userHoldings.sumOf { it.value.replace(",", "").toDoubleOrNull() ?: 0.0 }
    val inflationRate = viewModel.manualInflationRate.toDoubleOrNull() ?: 6.0
    val totalReal = totalNominal / (1 + inflationRate / 100.0)

    val xirr = viewModel.lastXirrResult?.nominalXirr ?: "18.7%"
    val cagr = viewModel.lastCagrResult?.nominalCagr ?: "14.2%"

    AppNavigationWrapper(navController, "home") { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                HomeHeader(viewModel.userProfile?.fullName ?: "Invest Manager") {
                    navController.navigate("notifications")
                }
                Spacer(modifier = Modifier.height(24.dp))
                PortfolioSummaryCard(totalNominal, totalReal)
                Spacer(modifier = Modifier.height(24.dp))
                StatsRow(xirr, cagr, "${String.format(Locale.getDefault(), "%.1f", inflationRate)}%")
                Spacer(modifier = Modifier.height(24.dp))
                PortfolioGrowthCard(totalNominal, totalReal)
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader("My Holdings", onAction = {})
            }

            if (isLoading) {
                item { 
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = BrandPrimary)
                    }
                }
            } else {
                items(userHoldings) { holding ->
                    HoldingItem(holding) {
                        navController.navigate("asset_details")
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun HomeHeader(name: String, onNotificationClick: () -> Unit) {
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
                Text("Inflatio Smart", style = MaterialTheme.typography.labelSmall.copy(color = BrandPrimary, fontWeight = FontWeight.Bold))
                Text("Hello, $name", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
            }
        }
        IconButton(onClick = onNotificationClick, modifier = Modifier.background(BrandSurface, CircleShape)) {
            Icon(Icons.Default.Notifications, null, tint = BrandDark)
        }
    }
}

@Composable
fun PortfolioSummaryCard(nominal: Double, real: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BrandPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("TOTAL PORTFOLIO VALUE", style = MaterialTheme.typography.labelSmall.copy(color = Color.White.copy(alpha = 0.7f), fontWeight = FontWeight.Bold))
            Text(String.format(Locale.getDefault(), "₹%,.0f", nominal), style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold, color = Color.White))
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                Icon(Icons.AutoMirrored.Filled.TrendingUp, null, modifier = Modifier.size(16.dp), tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Inflation-Adjusted: ", style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.9f)))
                Text(String.format(Locale.getDefault(), "₹%,.0f", real), style = MaterialTheme.typography.bodySmall.copy(color = Color.White, fontWeight = FontWeight.ExtraBold))
            }
        }
    }
}

@Composable
fun StatsRow(xirr: String, cagr: String, cpi: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        StatItem("XIRR", xirr, PositiveGreen, Modifier.weight(1f))
        StatItem("CAGR", cagr, BrandSecondary, Modifier.weight(1f))
        StatItem("Today's CPI", cpi, NegativeRed, Modifier.weight(1f))
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
            Text(value, style = MaterialTheme.typography.titleLarge.copy(color = color, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp), maxLines = 1)
        }
    }
}

data class ChartPoint(val date: String, val nominal: Double, val real: Double)

@Composable
fun PortfolioGrowthCard(currentNominal: Double, currentReal: Double) {
    val chartData = remember(currentNominal) {
        listOf(
            ChartPoint("Jan", currentNominal * 0.7, currentReal * 0.75),
            ChartPoint("Mar", currentNominal * 0.78, currentReal * 0.8),
            ChartPoint("May", currentNominal * 0.85, currentReal * 0.84),
            ChartPoint("Jul", currentNominal * 0.92, currentReal * 0.9),
            ChartPoint("Sep", currentNominal, currentReal)
        )
    }
    
    var selectedPoint by remember { mutableStateOf<ChartPoint?>(null) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BrandSurface),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Portfolio Growth", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                    Text("vs. Purchasing Power", style = MaterialTheme.typography.bodySmall.copy(color = TextGray))
                }
                if (selectedPoint != null) {
                    Surface(color = BrandPrimary.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                        Text(
                            text = "${selectedPoint!!.date}: ₹${String.format(Locale.getDefault(), "%,.0f", selectedPoint!!.nominal)}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BrandPrimary
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(BrandPrimary.copy(alpha = 0.02f), RoundedCornerShape(12.dp))
                    .padding(8.dp)
            ) {
                InteractiveGrowthChart(chartData) { point ->
                    selectedPoint = point
                }
            }
            
            if (selectedPoint != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    ChartDetailItem("Nominal", "₹${String.format(Locale.getDefault(), "%,.0f", selectedPoint!!.nominal)}", BrandPrimary)
                    ChartDetailItem("Real Value", "₹${String.format(Locale.getDefault(), "%,.0f", selectedPoint!!.real)}", BrandSecondary)
                }
            }
        }
    }
}

@Composable
fun ChartDetailItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.Bold)
        Text(value, fontSize = 14.sp, color = color, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
fun InteractiveGrowthChart(data: List<ChartPoint>, onPointSelected: (ChartPoint) -> Unit) {
    val primaryColor = BrandPrimary
    val secondaryColor = BrandSecondary
    
    val maxVal = data.maxOf { it.nominal } * 1.1
    
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(data) {
                detectTapGestures { offset ->
                    val width = size.width
                    val stepX = width / (data.size - 1)
                    val index = (offset.x / stepX).toInt().coerceIn(0, data.size - 1)
                    onPointSelected(data[index])
                }
            }
    ) {
        val width = size.width
        val height = size.height
        val stepX = width / (data.size - 1)

        // Grid lines
        for (i in 0..4) {
            val y = height - (i * height / 4)
            drawLine(Color.LightGray.copy(alpha = 0.2f), Offset(0f, y), Offset(width, y), strokeWidth = 1.dp.toPx())
        }

        val nominalPath = Path()
        val realPath = Path()

        data.forEachIndexed { index, point ->
            val x = index * stepX
            val yNominal = height - (point.nominal / maxVal * height).toFloat()
            val yReal = height - (point.real / maxVal * height).toFloat()
            
            if (index == 0) {
                nominalPath.moveTo(x, yNominal)
                realPath.moveTo(x, yReal)
            } else {
                nominalPath.lineTo(x, yNominal)
                realPath.lineTo(x, yReal)
            }
            
            // Draw points
            drawCircle(primaryColor, radius = 3.dp.toPx(), center = Offset(x, yNominal))
            drawCircle(secondaryColor, radius = 3.dp.toPx(), center = Offset(x, yReal))
        }

        drawPath(nominalPath, primaryColor, style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round))
        drawPath(
            realPath, 
            secondaryColor, 
            style = Stroke(
                width = 3.dp.toPx(), 
                cap = StrokeCap.Round, 
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        )
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
fun HoldingItem(holding: UserHolding, onClick: () -> Unit) {
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

data class DefaultHolding(val name: String, val type: String, val value: String, val change: String)

val defaultHoldings = listOf(
    DefaultHolding("Reliance", "Equity • 14 Shares", "41,230", "12.4"),
    DefaultHolding("Nifty 50", "ETF • 120 Units", "2,45,100", "8.1"),
    DefaultHolding("ICICI Bluechip", "Mutual Fund • Direct", "1,12,000", "15.2"),
    DefaultHolding("Gold", "Commodity • SGB", "85,000", "4.5")
)
