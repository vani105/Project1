package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.automirrored.filled.TrendingUp
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
import com.example.project1.ui.theme.*

@Composable
fun CalculatorListScreen(navController: NavController) {
    AppNavigationWrapper(navController, "calculator") { padding ->
        Column(modifier = Modifier.fillMaxSize().background(BrandBackground).padding(padding).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                LogoIconSmall()
                Spacer(modifier = Modifier.width(16.dp))
                Text("Inflatio Smart", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {}) { Icon(Icons.Default.Notifications, null, tint = BrandDark) }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("Investment Tools", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
            Text("Precision calculators for sophisticated wealth planning.", color = TextGray)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            VibrantToolCard("CAGR Calculator", "Calculate annual growth rate over time.", Icons.AutoMirrored.Filled.TrendingUp, BrandPrimary, onClick = { navController.navigate("select_type/cagr") })
            VibrantToolCard("XIRR Calculator", "Internal rate of return for irregular cash flows.", Icons.Default.PieChart, BrandSecondary, onClick = { navController.navigate("select_type/xirr") })
            VibrantToolCard("Inflation Impact", "See how inflation erodes purchasing power.", Icons.Default.Timer, AccentYellow, onClick = { navController.navigate("inflation_impact") })
            VibrantToolCard("SIP Calculator", "Project future wealth with inflation adjustment.", Icons.Default.Update, PositiveGreen, onClick = { navController.navigate("sip_calc") })
            
            Spacer(modifier = Modifier.height(24.dp))
            
            VibrantTaxLossCard()
        }
    }
}

@Composable
fun VibrantToolCard(title: String, description: String, icon: ImageVector, iconColor: Color, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(56.dp).background(iconColor.copy(alpha = 0.1f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = iconColor, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark, fontSize = 16.sp)
                Text(description, style = MaterialTheme.typography.bodySmall.copy(color = TextGray))
            }
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.size(18.dp), tint = DividerColor)
        }
    }
}

@Composable
fun VibrantTaxLossCard() {
    Card(
        modifier = Modifier.fillMaxWidth().height(220.dp),
        colors = CardDefaults.cardColors(containerColor = BrandDark),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Tax-Loss Harvesting", color = Color.White, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold))
                Text("Optimize portfolio by offsetting capital gains with realized losses.", color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {}, 
                    colors = ButtonDefaults.buttonColors(containerColor = BrandGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Explore Optimizer", fontWeight = FontWeight.Bold)
                }
            }
            Icon(
                Icons.Filled.BarChart, 
                null, 
                tint = Color.White.copy(alpha = 0.05f), 
                modifier = Modifier.size(140.dp).align(Alignment.BottomEnd).offset(x = 20.dp, y = 20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentTypeSelectionScreen(calcType: String, onTypeSelected: (String) -> Unit, onBack: () -> Unit) {
    val title = if (calcType == "cagr") "CAGR Calculator" else "XIRR Calculator"
    
    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text(title, fontWeight = FontWeight.ExtraBold, color = BrandPrimary) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text("Select Asset Type", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
            Text("Choose the category of your investment for tailored analysis.", color = TextGray)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item { AssetTypeCard("Stocks / Equity", "Individual company shares and ETFs", Icons.AutoMirrored.Filled.ShowChart, BrandPrimary) { onTypeSelected("Stocks") } }
                item { AssetTypeCard("Mutual Funds", "Equity or Debt mutual fund schemes", Icons.Default.AccountBalance, BrandSecondary) { onTypeSelected("Mutual Funds") } }
                item { AssetTypeCard("Real Estate", "Residential or commercial properties", Icons.Default.HomeWork, BrandGreen) { onTypeSelected("Real Estate") } }
                item { AssetTypeCard("Crypto / Web3", "Digital assets and tokens", Icons.Default.CurrencyBitcoin, AccentCyan) { onTypeSelected("Crypto") } }
                item { AssetTypeCard("Fixed Income", "Fds, Bonds, or Government schemes", Icons.Default.AccountBalanceWallet, AccentYellow) { onTypeSelected("Fixed Income") } }
                item { AssetTypeCard("Other Assets", "Gold, Commodities, or Private Equity", Icons.Default.MoreHoriz, BrandDark) { onTypeSelected("Other") } }
            }
        }
    }
}

@Composable
fun AssetTypeCard(title: String, subtitle: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).background(color.copy(alpha = 0.1f), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = color, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark)
                Text(subtitle, style = MaterialTheme.typography.bodySmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
            }
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = DividerColor, modifier = Modifier.size(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CagrCalculatorScreen(assetType: String, onBack: () -> Unit, onResult: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var buyValue by remember { mutableStateOf("") }
    var sellValue by remember { mutableStateOf("") }
    var years by remember { mutableStateOf(5f) }
    var inflation by remember { mutableStateOf(6f) }

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("CAGR • $assetType", fontWeight = FontWeight.ExtraBold, color = BrandPrimary) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                actions = { Box(modifier = Modifier.padding(end = 16.dp)) { LogoIconSmall() } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("Asset Details", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        val nameLabel = when(assetType) {
                            "Stocks" -> "STOCK NAME / TICKER"
                            "Real Estate" -> "PROPERTY ADDRESS"
                            "Crypto" -> "TOKEN SYMBOL"
                            else -> "INVESTMENT NAME"
                        }
                        VibrantTextField(value = name, onValueChange = { name = it }, label = nameLabel, placeholder = "e.g. Reliance")
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Box(modifier = Modifier.weight(1f)) {
                                VibrantTextField(value = buyValue, onValueChange = { buyValue = it }, label = "BUY PRICE (₹)", placeholder = "0.00")
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                VibrantTextField(value = sellValue, onValueChange = { sellValue = it }, label = "SELL PRICE (₹)", placeholder = "0.00")
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text("Time & Inflation", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.height(16.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        VibrantSliderHeader("DURATION", "${years.toInt()} Years", BrandPrimary)
                        Slider(value = years, onValueChange = { years = it }, valueRange = 1f..30f, colors = SliderDefaults.colors(thumbColor = BrandPrimary, activeTrackColor = BrandPrimary))
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        VibrantSliderHeader("AVG. INFLATION", "$inflation%", BrandSecondary)
                        Slider(value = inflation, onValueChange = { inflation = it }, valueRange = 0f..15f, colors = SliderDefaults.colors(thumbColor = BrandSecondary, activeTrackColor = BrandSecondary))
                    }
                }
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Button(onClick = onResult, modifier = Modifier.fillMaxWidth().height(64.dp), shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(containerColor = BrandDark)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Calculate, null)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Calculate Real Return", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XirrCalculatorScreen(assetType: String, onBack: () -> Unit, onResult: () -> Unit) {
    var inflationRate by remember { mutableStateOf("6.00") }
    var capitalGainsTax by remember { mutableStateOf("12.50") }

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("XIRR • $assetType", fontWeight = FontWeight.ExtraBold, color = BrandPrimary) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                actions = { Box(modifier = Modifier.padding(end = 16.dp)) { LogoIconSmall() } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("Transaction History", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Text("Input all cash flows including dividends or rent.", color = TextGray)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp), border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Cash Flows", fontWeight = FontWeight.ExtraBold, color = BrandDark)
                            Button(onClick = {}, shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary.copy(alpha = 0.1f)), elevation = null) {
                                Icon(Icons.Default.Add, null, tint = BrandPrimary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Add", color = BrandPrimary, fontWeight = FontWeight.Bold)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        XirrFlowRow("01/01/2023", "-50,000", NegativeRed)
                        XirrFlowRow("01/01/2024", "62,500", PositiveGreen)
                        
                        if (assetType == "Real Estate") {
                            XirrFlowRow("05/06/2023", "12,000", PositiveGreen) 
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text("Advanced Adjustments", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.height(12.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        VibrantTextField(value = inflationRate, onValueChange = { inflationRate = it }, label = "Inflation (%)", placeholder = "6.00")
                        VibrantTextField(value = capitalGainsTax, onValueChange = { capitalGainsTax = it }, label = "Tax Slab (%)", placeholder = "12.50")
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(onClick = onResult, modifier = Modifier.fillMaxWidth().height(64.dp), shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(containerColor = BrandDark)) {
                    Icon(Icons.Filled.Calculate, null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Analyze Cash Flows", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CagrResultScreen(onBack: () -> Unit) {
    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Analysis Result", fontWeight = FontWeight.ExtraBold, color = BrandPrimary) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("CAGR Breakdown", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Text("See how your investment performed against inflation.", color = TextGray)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                StatResultCard("NOMINAL CAGR", "20.11%", "Compounded Growth", InfoBlueBg, BrandPrimary)
                Spacer(modifier = Modifier.height(16.dp))
                StatResultCard("REAL CAGR (NET)", "13.31%", "After Inflation", SuccessGreenBg, BrandGreen)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = BrandDark), shape = RoundedCornerShape(28.dp)) {
                    Column(modifier = Modifier.padding(28.dp)) {
                        Text("REAL VALUE (TODAY'S TERMS)", color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        Text("₹1.86L from ₹2.5L nominal", color = Color.White, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold))
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(64.dp), colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary), shape = RoundedCornerShape(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Save, null)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Save to Portfolio", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XirrResultScreen(onBack: () -> Unit) {
    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("XIRR Result", fontWeight = FontWeight.ExtraBold, color = BrandPrimary) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("Returns Composition", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                
                Spacer(modifier = Modifier.height(24.dp))

                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = BrandDark), shape = RoundedCornerShape(28.dp)) {
                    Column(modifier = Modifier.padding(28.dp)) {
                        Text("NOMINAL XIRR", color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text("25.00", color = Color.White, style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.ExtraBold))
                            Text("%", color = BrandPrimary, style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold), modifier = Modifier.padding(bottom = 8.dp, start = 4.dp))
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("Real XIRR", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("17.92%", color = BrandGreen, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Net Gain", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("₹12,500", color = BrandGreen, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("PORTFOLIO COMPOSITION", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.ExtraBold))
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(16.dp).background(DividerColor, CircleShape)) {
                            Box(modifier = Modifier.fillMaxWidth(0.7f).fillMaxHeight().background(PositiveGreen, CircleShape))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            CompositionIndicator("Principal (70%)", PositiveGreen)
                            CompositionIndicator("Profits (30%)", TextGray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
                
                Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(64.dp), colors = ButtonDefaults.buttonColors(containerColor = BrandDark), shape = RoundedCornerShape(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Share, null)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Export Result", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InflationImpactScreen(onBack: () -> Unit) {
    var principal by remember { mutableStateOf("1000000") }
    var duration by remember { mutableStateOf(10f) }
    var inflation by remember { mutableStateOf(6f) }

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Inflatio Smart", fontWeight = FontWeight.ExtraBold, color = BrandPrimary) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                actions = { IconButton(onClick = {}) { Icon(Icons.Filled.Notifications, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("Protect Your Wealth", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Text("Visualise how inflation erodes your purchasing power.", color = TextGray)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White), 
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Tune, null, tint = BrandPrimary)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Parameters", fontWeight = FontWeight.ExtraBold, color = BrandDark)
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        VibrantTextField(value = principal, onValueChange = { principal = it }, label = "PRINCIPAL AMOUNT (₹)", placeholder = "1000000")
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Duration: ${duration.toInt()} Years", fontWeight = FontWeight.Bold)
                        Slider(value = duration, onValueChange = { duration = it }, valueRange = 1f..30f, colors = SliderDefaults.colors(thumbColor = BrandPrimary))
                        Text("Expected Inflation: $inflation%", fontWeight = FontWeight.Bold)
                        Slider(value = inflation, onValueChange = { inflation = it }, valueRange = 0f..15f, colors = SliderDefaults.colors(thumbColor = BrandSecondary))
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = BrandDark)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Bookmark, null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Save to History", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                StatResultCard("FUTURE VALUE", "₹5,58,395", "Purchasing Power", InfoBlueBg, BrandPrimary)
                Spacer(modifier = Modifier.height(16.dp))
                StatResultCard("TOTAL EROSION", "-44.16%", "Value Lost", ErrorRedBg, NegativeRed)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text("Erosion Timeline", fontWeight = FontWeight.ExtraBold, color = BrandDark)
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.fillMaxWidth().height(220.dp).background(Color.White, RoundedCornerShape(20.dp))) 
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = InfoBlueBg), shape = RoundedCornerShape(16.dp)) {
                    Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Info, null, tint = BrandPrimary)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("In 10 years, a basket of goods costing ₹1L today will cost ~₹1.8L.", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = BrandPrimary)
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = SuccessGreenBg), shape = RoundedCornerShape(16.dp)) {
                   Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                       Icon(Icons.AutoMirrored.Filled.TrendingUp, null, tint = BrandGreen)
                       Spacer(modifier = Modifier.width(16.dp))
                       Text("You need at least 6.0% returns to maintain purchasing power.", color = BrandGreen, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                   }
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SipCalculatorScreen(onBack: () -> Unit) {
    var monthlyInvestment by remember { mutableStateOf("10000") }
    var expectedReturn by remember { mutableStateOf(12f) }
    var duration by remember { mutableStateOf(10f) }
    var inflation by remember { mutableStateOf(6f) }

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Inflatio Smart", fontWeight = FontWeight.ExtraBold, color = BrandPrimary) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                actions = { IconButton(onClick = {}) { Icon(Icons.Filled.Notifications, null, tint = BrandDark) } }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("SIP Calculator", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Text("Project your future wealth with inflation adjustment.", color = TextGray)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        VibrantSliderHeader("MONTHLY INVESTMENT", "₹$monthlyInvestment", BrandDark)
                        Slider(value = 10000f, onValueChange = {}, valueRange = 500f..100000f, colors = SliderDefaults.colors(thumbColor = BrandPrimary))
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        VibrantSliderHeader("EXPECTED RETURN (P.A)", "${expectedReturn.toInt()}%", BrandGreen)
                        Slider(value = expectedReturn, onValueChange = { expectedReturn = it }, valueRange = 1f..30f, colors = SliderDefaults.colors(thumbColor = BrandGreen))
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        VibrantSliderHeader("DURATION (YEARS)", "${duration.toInt()}Y", BrandSecondary)
                        Slider(value = duration, onValueChange = { duration = it }, valueRange = 1f..40f, colors = SliderDefaults.colors(thumbColor = BrandSecondary))
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        VibrantSliderHeader("INFLATION RATE (P.A)", "${inflation.toInt()}%", NegativeRed)
                        Slider(value = inflation, onValueChange = { inflation = it }, valueRange = 0f..15f, colors = SliderDefaults.colors(thumbColor = NegativeRed))
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("WEALTH PROJECTION", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = TextGray))
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(180.dp).background(BrandPrimary.copy(alpha = 0.05f), RoundedCornerShape(16.dp)))
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = BrandDark), shape = RoundedCornerShape(20.dp)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("TOTAL INVESTED", style = MaterialTheme.typography.labelSmall.copy(color = Color.White.copy(alpha = 0.6f), fontWeight = FontWeight.Bold))
                        Text("₹12,00,000", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = Color.White))
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    VibrantSummaryCard("MATURITY", "₹23,00,387", "+91.7%", PositiveGreen, Modifier.weight(1f))
                    VibrantSummaryCard("REAL VALUE", "₹15,84,212", "Inf-Adj", BrandPrimary, Modifier.weight(1f))
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = BrandDark), shape = RoundedCornerShape(20.dp)) {
                    Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Insights, null, tint = BrandGreen)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Real wealth is 31% lower than nominal due to inflation. Strategy: Invest 6% more monthly.", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun InfoValueRow(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(label, style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(4.dp))
        Surface(color = BrandBackground, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Text(value, modifier = Modifier.padding(16.dp), fontWeight = FontWeight.ExtraBold, color = BrandDark, fontSize = 16.sp)
        }
    }
}

@Composable
fun VibrantSliderHeader(label: String, value: String, color: Color) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = TextGray))
        Text(value, fontWeight = FontWeight.ExtraBold, color = color)
    }
}

@Composable
fun StatResultCard(label: String, value: String, description: String, bgColor: Color, valueColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(), 
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(label, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = valueColor.copy(alpha = 0.7f)))
            Text(value, style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold, color = valueColor))
            Text(description, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = valueColor.copy(alpha = 0.6f)))
        }
    }
}

@Composable
fun XirrFlowRow(date: String, amount: String, color: Color) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = date,
            onValueChange = {},
            modifier = Modifier.weight(1f).height(56.dp),
            shape = RoundedCornerShape(16.dp),
            trailingIcon = { Icon(Icons.Filled.CalendarMonth, null, tint = BrandPrimary, modifier = Modifier.size(18.dp)) },
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = DividerColor)
        )
        Spacer(modifier = Modifier.width(12.dp))
        OutlinedTextField(
            value = amount,
            onValueChange = {},
            modifier = Modifier.weight(1f).height(56.dp),
            shape = RoundedCornerShape(16.dp),
            prefix = { Text("₹ ", color = color, fontWeight = FontWeight.ExtraBold) },
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = DividerColor),
            textStyle = androidx.compose.ui.text.TextStyle(color = color, fontWeight = FontWeight.ExtraBold)
        )
        Spacer(modifier = Modifier.width(12.dp))
        IconButton(onClick = {}, modifier = Modifier.size(44.dp).background(BrandBackground, RoundedCornerShape(12.dp))) {
            Icon(Icons.Filled.Delete, null, tint = NegativeRed, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
fun CompositionIndicator(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(10.dp).background(color, CircleShape))
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, style = MaterialTheme.typography.labelSmall.copy(color = BrandDark, fontWeight = FontWeight.ExtraBold))
    }
}

@Composable
fun VibrantSummaryCard(label: String, value: String, subValue: String, color: Color, modifier: Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = TextGray))
            Text(value, fontWeight = FontWeight.ExtraBold, color = BrandDark, fontSize = 16.sp)
            Text(subValue, color = color, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}
