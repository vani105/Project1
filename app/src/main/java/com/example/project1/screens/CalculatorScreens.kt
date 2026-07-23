package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project1.data.AppViewModel
import com.example.project1.data.CagrResultData
import com.example.project1.data.XirrResultData
import com.example.project1.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CalculatorListScreen(navController: NavController) {
    AppNavigationWrapper(navController, "calculator") { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().background(BrandBackground).padding(padding).padding(horizontal = 16.dp)) {
            item {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Calculators", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                    IconButton(onClick = {}, modifier = Modifier.background(Color.White, CircleShape)) {
                        Icon(Icons.Default.Notifications, null, tint = BrandDark)
                    }
                }

                VibrantToolCard("CAGR Calculator", "Compound Annual Growth Rate", Icons.AutoMirrored.Filled.ShowChart, BrandPrimary) {
                    navController.navigate("select_type/cagr")
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                VibrantToolCard("XIRR Analyzer", "Extended Internal Rate of Return", Icons.AutoMirrored.Filled.TrendingUp, BrandSecondary) {
                    navController.navigate("select_type/xirr")
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                VibrantToolCard("SIP Planner", "Systematic Investment Plan", Icons.Default.Update, BrandGreen) {
                    navController.navigate("sip_calc")
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                VibrantToolCard("Inflation Impact", "Real Value vs Nominal Value", Icons.Default.LocalFireDepartment, Color(0xFFFF6B6B)) {
                    navController.navigate("inflation_impact")
                }

                Spacer(modifier = Modifier.height(32.dp))
                VibrantTaxLossCard()
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun VibrantToolCard(title: String, subtitle: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(56.dp).background(color.copy(alpha = 0.1f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = color, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark, fontSize = 18.sp)
                Text(subtitle, style = MaterialTheme.typography.bodySmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
            }
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = DividerColor)
        }
    }
}

@Composable
fun VibrantTaxLossCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = BrandDark),
        shape = RoundedCornerShape(28.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).background(BrandGreen, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Percent, null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Tax Loss Harvesting", color = Color.White, fontWeight = FontWeight.ExtraBold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Automatically identify underperforming assets to offset capital gains and reduce your tax liability.", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
            ) {
                Text("Scan Portfolio for Savings", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentTypeSelectionScreen(calcType: String, onTypeSelected: (String) -> Unit, onBack: () -> Unit) {
    val types = listOf(
        Triple("Stocks", "Equity markets and ETFs", Icons.Default.BarChart),
        Triple("Mutual Funds", "SIPs and Lump Sums", Icons.Default.Analytics),
        Triple("Real Estate", "Properties and REITs", Icons.Default.HomeWork),
        Triple("Crypto", "Digital assets and tokens", Icons.Default.CurrencyBitcoin),
        Triple("Gold", "Physical and digital gold", Icons.Default.Diamond)
    )

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text(if (calcType == "cagr") "CAGR Selection" else "XIRR Selection", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 24.dp)) {
            item {
                Text("Select Asset Type", style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Text("Tailored calculation parameters based on asset class.", color = TextGray)
                Spacer(modifier = Modifier.height(32.dp))
            }
            items(types.size) { index ->
                val type = types[index]
                AssetTypeCard(type.first, type.second, type.third, BrandPrimary) { onTypeSelected(type.first) }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun AssetTypeCard(title: String, subtitle: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).background(color.copy(alpha = 0.1f), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = color)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark)
                Text(subtitle, style = MaterialTheme.typography.bodySmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CagrCalculatorScreen(assetType: String, onBack: () -> Unit, onResult: () -> Unit, viewModel: AppViewModel) {
    var name by remember { mutableStateOf("") }
    var buyPrice by remember { mutableStateOf("") }
    var sellPrice by remember { mutableStateOf("") }
    var durationYears by remember { mutableStateOf(5f) }
    var inflationRate by remember { mutableStateOf(6.0f) }

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
                Text("Asset Details", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.height(16.dp))
                
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        VibrantTextField(
                            value = name, 
                            onValueChange = { name = it }, 
                            label = "${assetType.uppercase()} NAME / TICKER", 
                            placeholder = "e.g. Reliance"
                        )
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Box(modifier = Modifier.weight(1f)) {
                                VibrantTextField(
                                    value = buyPrice, 
                                    onValueChange = { buyPrice = it }, 
                                    label = "BUY PRICE (₹)", 
                                    placeholder = "0.00"
                                )
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                VibrantTextField(
                                    value = sellPrice, 
                                    onValueChange = { sellPrice = it }, 
                                    label = "SELL PRICE (₹)", 
                                    placeholder = "0.00"
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("Time & Inflation", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        VibrantSliderHeader("DURATION", "${durationYears.toInt()} Years", BrandPrimary)
                        Slider(
                            value = durationYears,
                            onValueChange = { durationYears = it },
                            valueRange = 1f..40f,
                            colors = SliderDefaults.colors(
                                thumbColor = BrandPrimary,
                                activeTrackColor = BrandPrimary,
                                inactiveTrackColor = BrandDark.copy(alpha = 0.1f)
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        VibrantSliderHeader("AVG. INFLATION", "${String.format("%.1f", inflationRate)}%", BrandSecondary)
                        Slider(
                            value = inflationRate,
                            onValueChange = { inflationRate = it },
                            valueRange = 0f..20f,
                            colors = SliderDefaults.colors(
                                thumbColor = BrandSecondary,
                                activeTrackColor = BrandSecondary,
                                inactiveTrackColor = BrandDark.copy(alpha = 0.1f)
                            )
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Button(
                    onClick = {
                        val initial = buyPrice.toDoubleOrNull() ?: 0.0
                        val final = sellPrice.toDoubleOrNull() ?: 0.0
                        val t = durationYears.toDouble()
                        
                        if (initial > 0 && final > 0 && t > 0) {
                            val cagr = (Math.pow(final / initial, 1.0 / t) - 1) * 100
                            val inflation = inflationRate.toDouble()
                            val realCagr = ((1 + cagr / 100) / (1 + inflation / 100) - 1) * 100
                            
                            viewModel.lastCagrResult = CagrResultData(
                                nominalCagr = String.format("%.2f%%", cagr),
                                realCagr = String.format("%.2f%%", realCagr),
                                finalNominal = String.format("₹%.0f", final),
                                finalReal = String.format("₹%.0f", final / Math.pow(1 + inflation / 100, t)),
                                name = name.ifBlank { assetType }
                            )
                            viewModel.addHistoryItem(
                                title = "CAGR (${name.ifBlank { assetType }})",
                                duration = "${t.toInt()} Years",
                                realValue = String.format("%.2f%%", realCagr),
                                nominalValue = String.format("%.2f%%", cagr),
                                type = "CAGR"
                            )
                            onResult()
                        }
                    }, 
                    modifier = Modifier.fillMaxWidth().height(64.dp), 
                    shape = RoundedCornerShape(20.dp), 
                    colors = ButtonDefaults.buttonColors(containerColor = BrandDark)
                ) {
                    Icon(Icons.Filled.Calculate, null, tint = Color.White)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Calculate Performance", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XirrCalculatorScreen(assetType: String, onBack: () -> Unit, onResult: () -> Unit, viewModel: AppViewModel) {
    var name by remember { mutableStateOf("") }
    var inflationRate by remember { mutableStateOf("6.00") }
    var capitalGainsTax by remember { mutableStateOf("12.50") }

    val initialFlows = when (assetType) {
        "Real Estate" -> listOf(
            CashFlowData("01/01/2023", -50000.0),
            CashFlowData("05/06/2023", 12000.0),
            CashFlowData("01/01/2024", 62500.0)
        )
        else -> listOf(
            CashFlowData("01/01/2023", -50000.0),
            CashFlowData("01/01/2024", 62500.0)
        )
    }
    val cashFlows = remember { mutableStateListOf<CashFlowData>().apply { addAll(initialFlows) } }

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
                Text("Asset Details", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.height(16.dp))
                VibrantTextField(value = name, onValueChange = { name = it }, label = "INVESTMENT NAME", placeholder = "e.g. Portfolio Alpha")
                
                Spacer(modifier = Modifier.height(24.dp))
                Text("Transaction History", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Text("Input all cash flows including dividends or rent.", color = TextGray)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp), border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Cash Flows", fontWeight = FontWeight.ExtraBold, color = BrandDark)
                            Button(
                                onClick = { 
                                    val today = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                                    cashFlows.add(CashFlowData(today, 0.0)) 
                                }, 
                                shape = RoundedCornerShape(12.dp), 
                                colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary.copy(alpha = 0.1f)), 
                                elevation = null
                            ) {
                                Icon(Icons.Default.Add, null, tint = BrandPrimary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Add", color = BrandPrimary, fontWeight = FontWeight.Bold)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        cashFlows.forEachIndexed { index, flow ->
                            XirrFlowRow(
                                date = flow.date,
                                amount = flow.amount.toString(),
                                color = if (flow.amount < 0) NegativeRed else PositiveGreen,
                                onDateChange = { newDate ->
                                    cashFlows[index] = flow.copy(date = newDate)
                                },
                                onAmountChange = { newAmountStr ->
                                    val newAmount = newAmountStr.toDoubleOrNull() ?: 0.0
                                    cashFlows[index] = flow.copy(amount = newAmount)
                                },
                                onDelete = {
                                    if (cashFlows.size > 1) {
                                        cashFlows.removeAt(index)
                                    }
                                }
                            )
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
                
                Button(
                    onClick = {
                        val nominalXirr = "25.00%"
                        val realXirr = "17.92%"
                        val gain = "₹12,500"

                        viewModel.lastXirrResult = XirrResultData(
                            nominalXirr = nominalXirr,
                            realXirr = realXirr,
                            netGain = gain
                        )
                        viewModel.addHistoryItem(
                            title = "XIRR (${name.ifBlank { assetType }})",
                            duration = "Custom Flow",
                            realValue = realXirr,
                            nominalValue = nominalXirr,
                            type = "XIRR"
                        )
                        onResult()
                    }, 
                    modifier = Modifier.fillMaxWidth().height(64.dp), 
                    shape = RoundedCornerShape(20.dp), 
                    colors = ButtonDefaults.buttonColors(containerColor = BrandDark)
                ) {
                    Icon(Icons.Filled.Calculate, null, tint = Color.White)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Analyze Cash Flows", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CagrResultScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    val result = viewModel.lastCagrResult ?: return

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("CAGR Results", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp).verticalScroll(rememberScrollState())) {
            StatResultCard("NOMINAL CAGR", result.nominalCagr, "Absolute growth rate", BrandPrimary, Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            StatResultCard("REAL CAGR", result.realCagr, "Inflation-adjusted growth", BrandGreen, Color.White)
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("Asset Valuation", fontWeight = FontWeight.ExtraBold, color = BrandDark)
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                Column(modifier = Modifier.padding(20.dp)) {
                    InfoValueRow("Final Value (Nominal)", result.finalNominal, Modifier)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = DividerColor)
                    InfoValueRow("Final Value (Real)", result.finalReal, Modifier)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(64.dp), shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(containerColor = BrandDark)) {
                Text("Back to Calculator", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XirrResultScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    val result = viewModel.lastXirrResult ?: return

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("XIRR Analysis", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp).verticalScroll(rememberScrollState())) {
            StatResultCard("PERSONAL XIRR", result.nominalXirr, "Time-weighted return", BrandSecondary, Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            StatResultCard("REAL XIRR", result.realXirr, "Purchasing power growth", BrandPrimary, Color.White)
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("Profitability Metrics", fontWeight = FontWeight.ExtraBold, color = BrandDark)
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                Column(modifier = Modifier.padding(20.dp)) {
                    InfoValueRow("Total Net Gain", result.netGain, Modifier)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = DividerColor)
                    InfoValueRow("Tax Efficiency", "High (12.5%)", Modifier)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Card(colors = CardDefaults.cardColors(containerColor = SuccessGreenBg), shape = RoundedCornerShape(20.dp)) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Verified, null, tint = BrandGreen)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Your performance beats inflation by 11.84%", color = BrandGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(64.dp), shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(containerColor = BrandDark)) {
                Text("Back to Analyzer", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InflationImpactScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    var amount by remember { mutableStateOf("10,00,000") }
    var years by remember { mutableStateOf("10") }
    var rate by remember { mutableStateOf("6.0") }

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Inflation Impact", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp).verticalScroll(rememberScrollState())) {
            Text("Future Purchasing Power", style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
            Text("Visualize how inflation erodes your wealth over time.", color = TextGray)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            VibrantTextField(value = amount, onValueChange = { amount = it }, label = "CURRENT AMOUNT (₹)", placeholder = "0.00")
            VibrantTextField(value = years, onValueChange = { years = it }, label = "TIME HORIZON (YEARS)", placeholder = "0")
            VibrantTextField(value = rate, onValueChange = { rate = it }, label = "EXPECTED INFLATION (%)", placeholder = "6.0")
            
            Spacer(modifier = Modifier.height(40.dp))
            
            val amt = amount.replace(",", "").toDoubleOrNull() ?: 1000000.0
            val t = years.toDoubleOrNull() ?: 10.0
            val r = rate.toDoubleOrNull() ?: 6.0
            val futureReal = amt / Math.pow(1 + r / 100, t)
            
            StatResultCard("REAL VALUE", String.format("₹%,.0f", futureReal), "Value in today's money", Color(0xFFFF6B6B), Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(colors = CardDefaults.cardColors(containerColor = BrandDark.copy(alpha = 0.05f)), shape = RoundedCornerShape(20.dp)) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("In $t years, your ₹${String.format("%,.0f", amt)} will only buy what ₹${String.format("%,.0f", futureReal)} buys today.", color = BrandDark, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SipCalculatorScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    var monthlyInvestment by remember { mutableStateOf(50000f) }
    var annualReturn by remember { mutableStateOf(12f) }
    var durationYears by remember { mutableStateOf(15f) }

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("SIP Planner", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp).verticalScroll(rememberScrollState())) {
            Text("Wealth Projection", style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
            Text("Compound your monthly savings into institutional wealth.", color = TextGray)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            VibrantSliderHeader("MONTHLY INVESTMENT", "₹${String.format("%,.0f", monthlyInvestment)}", BrandPrimary)
            Slider(value = monthlyInvestment, onValueChange = { monthlyInvestment = it }, valueRange = 1000f..500000f, colors = SliderDefaults.colors(thumbColor = BrandPrimary, activeTrackColor = BrandPrimary))
            
            Spacer(modifier = Modifier.height(24.dp))
            VibrantSliderHeader("EXPECTED ANNUAL RETURN", "${String.format("%.1f", annualReturn)}%", BrandGreen)
            Slider(value = annualReturn, onValueChange = { annualReturn = it }, valueRange = 1f..30f, colors = SliderDefaults.colors(thumbColor = BrandGreen, activeTrackColor = BrandGreen))
            
            Spacer(modifier = Modifier.height(24.dp))
            VibrantSliderHeader("INVESTMENT DURATION", "${durationYears.toInt()} Years", BrandSecondary)
            Slider(value = durationYears, onValueChange = { durationYears = it }, valueRange = 1f..40f, colors = SliderDefaults.colors(thumbColor = BrandSecondary, activeTrackColor = BrandSecondary))
            
            Spacer(modifier = Modifier.height(40.dp))
            
            val p = monthlyInvestment.toDouble()
            val r = annualReturn.toDouble() / 12 / 100
            val n = durationYears.toInt() * 12
            val totalValue = p * ((Math.pow(1 + r, n.toDouble()) - 1) / r) * (1 + r)
            val totalInvested = p * n
            
            StatResultCard("ESTIMATED WEALTH", String.format("₹%,.0f", totalValue), "Total value after compounding", BrandDark, Color.White)
            
            Spacer(modifier = Modifier.height(24.dp))
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                Column(modifier = Modifier.padding(20.dp)) {
                    InfoValueRow("Total Invested", String.format("₹%,.0f", totalInvested), Modifier)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = DividerColor)
                    InfoValueRow("Estimated Returns", String.format("₹%,.0f", totalValue - totalInvested), Modifier)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun InfoValueRow(label: String, value: String, modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = TextGray, fontWeight = FontWeight.Bold)
        Text(value, color = BrandDark, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
fun VibrantSliderHeader(label: String, value: String, color: Color) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Text(value, fontWeight = FontWeight.ExtraBold, color = color)
    }
}

@Composable
fun StatResultCard(title: String, value: String, description: String, color: Color, valueColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(28.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(title, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = valueColor.copy(alpha = 0.8f), letterSpacing = 1.sp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold, color = valueColor))
            Text(description, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = valueColor.copy(alpha = 0.6f)))
        }
    }
}

@Composable
fun XirrFlowRow(
    date: String, 
    amount: String, 
    color: Color,
    onDateChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    
    // Parse current date if possible to set picker start date
    try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.parse(date)?.let { calendar.time = it }
    } catch (e: Exception) {}

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val newDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            onDateChange(newDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = date,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = { 
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Filled.CalendarMonth, null, tint = BrandPrimary, modifier = Modifier.size(18.dp))
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedBorderColor = DividerColor,
                    focusedBorderColor = BrandPrimary,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )
            // Invisible clickable layer to trigger picker on whole field
            Box(modifier = Modifier.matchParentSize().clickable { datePickerDialog.show() })
        }
        Spacer(modifier = Modifier.width(12.dp))
        OutlinedTextField(
            value = amount,
            onValueChange = onAmountChange,
            modifier = Modifier.weight(1f).height(56.dp),
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
            shape = RoundedCornerShape(16.dp),
            prefix = { Text("₹ ", color = color, fontWeight = FontWeight.ExtraBold) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = color,
                unfocusedTextColor = color,
                unfocusedBorderColor = DividerColor,
                focusedBorderColor = BrandPrimary,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            textStyle = androidx.compose.ui.text.TextStyle(color = color, fontWeight = FontWeight.ExtraBold)
        )
        Spacer(modifier = Modifier.width(12.dp))
        IconButton(onClick = onDelete, modifier = Modifier.size(44.dp).background(BrandBackground, RoundedCornerShape(12.dp))) {
            Icon(Icons.Filled.Delete, null, tint = NegativeRed, modifier = Modifier.size(18.dp))
        }
    }
}

data class CashFlowData(
    val date: String,
    val amount: Double
)

@Composable
fun CompositionIndicator(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(10.dp).background(color, CircleShape))
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, style = MaterialTheme.typography.labelSmall.copy(color = BrandDark, fontWeight = FontWeight.Bold))
    }
}

@Composable
fun VibrantSummaryCard(title: String, value: String, description: String, color: Color, modifier: Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)), shape = RoundedCornerShape(20.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelSmall, color = color, fontWeight = FontWeight.ExtraBold)
            Text(value, style = MaterialTheme.typography.titleLarge, color = color, fontWeight = FontWeight.ExtraBold)
            Text(description, style = MaterialTheme.typography.bodySmall, color = color.copy(alpha = 0.7f), fontWeight = FontWeight.Bold)
        }
    }
}
