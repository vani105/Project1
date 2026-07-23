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
import com.example.project1.data.AppViewModel
import com.example.project1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultCurrencyScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    val currencies = listOf("Indian Rupee", "US Dollar", "Euro", "British Pound")

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Default Currency", fontWeight = FontWeight.ExtraBold, color = BrandDark) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text("Choose your preferred currency for portfolio valuation and performance tracking.", color = Color.Black, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(32.dp))
            
            currencies.forEach { currency ->
                VibrantRadioItem(
                    title = currency,
                    subtitle = when(currency) {
                        "Indian Rupee" -> "INR • Local Currency"
                        "US Dollar" -> "USD • International"
                        "Euro" -> "EUR • European Union"
                        else -> "GBP • United Kingdom"
                    },
                    isSelected = viewModel.selectedCurrency == currency,
                    onClick = { viewModel.selectedCurrency = currency }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Card(colors = CardDefaults.cardColors(containerColor = InfoBlueBg), shape = RoundedCornerShape(16.dp)) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Info, null, tint = BrandPrimary)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Changing your default currency will automatically update all portfolio valuations using current market exchange rates.", fontSize = 12.sp, color = BrandDark, fontWeight = FontWeight.Bold)
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { 
                    viewModel.syncPreferences()
                    onBack() 
                }, 
                modifier = Modifier.fillMaxWidth().height(64.dp), 
                shape = RoundedCornerShape(20.dp), 
                colors = ButtonDefaults.buttonColors(containerColor = BrandDark)
            ) {
                Text("Save Preferences", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun VibrantRadioItem(title: String, subtitle: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) BrandPrimary.copy(alpha = 0.05f) else Color.White,
        border = androidx.compose.foundation.BorderStroke(2.dp, if (isSelected) BrandPrimary else DividerColor)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).background(BrandBackground, CircleShape), contentAlignment = Alignment.Center) {
                Text(when(title) {
                    "Indian Rupee" -> "₹"
                    "US Dollar" -> "$"
                    "Euro" -> "€"
                    else -> "£"
                }, fontWeight = FontWeight.Bold, color = BrandDark)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark)
                Text(subtitle, style = MaterialTheme.typography.bodySmall.copy(color = Color.Black, fontWeight = FontWeight.Bold))
            }
            if (isSelected) {
                Icon(Icons.Default.CheckCircle, null, tint = BrandGreen)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertPreferencesScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Alert Preferences", fontWeight = FontWeight.ExtraBold, color = BrandDark) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("Configure critical financial triggers for your portfolio.", color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(24.dp))
                
                VibrantToggleCard("Price Alerts", "Get notified when assets reach target prices.", Icons.Default.Payments, viewModel.priceAlertsEnabled) { viewModel.priceAlertsEnabled = it }
                Spacer(modifier = Modifier.height(16.dp))
                VibrantToggleCard("Volatility Alerts", "Alerts for single-day movements > 3.5%.", Icons.Default.TrendingUp, viewModel.volAlertsEnabled) { viewModel.volAlertsEnabled = it }
                
                Spacer(modifier = Modifier.height(32.dp))
                Text("DELIVERY CHANNELS", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandPrimary))
                Spacer(modifier = Modifier.height(12.dp))
                
                ChannelItem("Push Notifications", "ENABLED", BrandGreen)
                ChannelItem("Email Summaries", "DAILY", BrandDark)
                ChannelItem("SMS Alerts", "CRITICAL ONLY", BrandSecondary)
                
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { 
                        viewModel.syncPreferences()
                        onBack() 
                    }, 
                    modifier = Modifier.fillMaxWidth().height(64.dp), 
                    shape = RoundedCornerShape(20.dp), 
                    colors = ButtonDefaults.buttonColors(containerColor = BrandDark)
                ) {
                    Text("Save Preferences", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun VibrantToggleCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isEnabled: Boolean, onToggle: (Boolean) -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).background(BrandPrimary.copy(alpha = 0.1f), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = BrandPrimary)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark)
                Text(subtitle, style = MaterialTheme.typography.bodySmall.copy(color = Color.Black, fontWeight = FontWeight.Bold))
            }
            Switch(checked = isEnabled, onCheckedChange = onToggle, colors = SwitchDefaults.colors(checkedTrackColor = BrandPrimary))
        }
    }
}

@Composable
fun ChannelItem(label: String, status: String, color: Color) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, fontWeight = FontWeight.Bold, color = BrandDark)
        Surface(color = color.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
            Text(status, color = color, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), fontSize = 10.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionLanguageScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Region & Language", fontWeight = FontWeight.ExtraBold, color = BrandDark) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text("Adjust currency symbols and market hours according to your location.", color = Color.Black, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(32.dp))
            
            VibrantLabelField("REGION", "Select Region", viewModel.selectedRegion, { viewModel.selectedRegion = it })
            Spacer(modifier = Modifier.height(24.dp))
            VibrantLabelField("DISPLAY LANGUAGE", "Select Language", viewModel.selectedLanguage, { viewModel.selectedLanguage = it })
            
            Spacer(modifier = Modifier.height(40.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedButton(onClick = onBack, modifier = Modifier.weight(1f).height(56.dp), shape = RoundedCornerShape(16.dp)) {
                    Text("Discard", color = BrandDark)
                }
                Button(
                    onClick = { 
                        viewModel.syncPreferences()
                        onBack() 
                    }, 
                    modifier = Modifier.weight(1f).height(56.dp), 
                    shape = RoundedCornerShape(16.dp), 
                    colors = ButtonDefaults.buttonColors(containerColor = BrandDark)
                ) {
                    Text("Apply", color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportPortfolioScreen(onBack: () -> Unit) {
    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Export Data", fontWeight = FontWeight.ExtraBold, color = BrandDark) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("Download your investment history and performance metrics.", color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(24.dp))
                
                ExportCard("CSV Spreadsheet", "Best for raw data manipulation in Excel or Google Sheets.", Icons.Default.TableChart)
                Spacer(modifier = Modifier.height(16.dp))
                ExportCard("JSON Data", "Structured data for developers and third-party integrations.", Icons.Default.Code)
                Spacer(modifier = Modifier.height(16.dp))
                ExportCard("PDF Report", "Formal investment report with high-fidelity charts.", Icons.Default.PictureAsPdf, isPrimary = true)
                
                Spacer(modifier = Modifier.height(32.dp))
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("DATA PRIVACY", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = BrandDark)
                        Text("Exports contain sensitive financial info. We recommend encrypting these files if stored on public services.", style = MaterialTheme.typography.bodySmall, color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun ExportCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isPrimary: Boolean = false) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(48.dp).background(BrandPrimary.copy(alpha = 0.1f), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = BrandPrimary)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark)
                    Text(subtitle, style = MaterialTheme.typography.bodySmall.copy(color = Color.Black, fontWeight = FontWeight.Bold))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {}, 
                modifier = Modifier.fillMaxWidth().height(48.dp), 
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (isPrimary) BrandGreen else BrandDark)
            ) {
                Text("GENERATE ${title.split(" ")[0].uppercase()}", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InflationBenchmarksScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Inflation Benchmarks", fontWeight = FontWeight.ExtraBold, color = BrandDark) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("Configure how the system calculates your real rate of return against purchasing power.", color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(32.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Benchmark Mode", fontWeight = FontWeight.Bold, color = BrandDark)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth().background(BrandBackground, RoundedCornerShape(12.dp)).padding(4.dp)) {
                            ModeButton("Auto (RBI CPI)", viewModel.benchmarkMode == "Auto (RBI CPI)", Modifier.weight(1f)) { viewModel.benchmarkMode = "Auto (RBI CPI)" }
                            ModeButton("Manual", viewModel.benchmarkMode == "Manual", Modifier.weight(1f)) { viewModel.benchmarkMode = "Manual" }
                        }
                        
                        if (viewModel.benchmarkMode == "Manual") {
                            Spacer(modifier = Modifier.height(24.dp))
                            VibrantTextField(value = viewModel.manualInflationRate, onValueChange = { viewModel.manualInflationRate = it }, label = "ANNUAL INFLATION RATE (%)", placeholder = "0.00")
                        } else {
                            Spacer(modifier = Modifier.height(24.dp))
                            Card(colors = CardDefaults.cardColors(containerColor = SuccessGreenBg), shape = RoundedCornerShape(12.dp)) {
                                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.TrendingUp, null, tint = BrandGreen)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text("Current RBI CPI Rate", style = MaterialTheme.typography.labelSmall, color = Color.Black)
                                        Text("5.09% (Feb 2024)", fontWeight = FontWeight.ExtraBold, color = BrandGreen)
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { 
                                viewModel.syncPreferences()
                                onBack() 
                            }, 
                            modifier = Modifier.fillMaxWidth().height(56.dp), 
                            shape = RoundedCornerShape(12.dp), 
                            colors = ButtonDefaults.buttonColors(containerColor = BrandDark)
                        ) {
                            Text("Save Preferences", color = Color.White)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                Card(colors = CardDefaults.cardColors(containerColor = BrandDark), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Why Inflation Matters", color = Color.White, fontWeight = FontWeight.ExtraBold)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Nominal returns are a vanity metric. If your portfolio grows by 10% but prices rise by 6%, your real wealth only increased by 4%.", color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun ModeButton(label: String, isSelected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) BrandDark else Color.Transparent,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        elevation = null
    ) {
        Text(label, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailSummariesScreen(onBack: () -> Unit) {
    var weeklyDigest by remember { mutableStateOf(true) }
    var monthlyPerf by remember { mutableStateOf(true) }
    var marketInsights by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Email Summaries", fontWeight = FontWeight.ExtraBold, color = BrandDark) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Text("Configure how and when you receive financial insights directly to your inbox.", color = Color.Black, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(32.dp))
            
            VibrantToggleCard("Weekly Digest", "Portfolio activity every Sunday at 8:00 AM.", Icons.Default.MenuBook, weeklyDigest) { weeklyDigest = it }
            Spacer(modifier = Modifier.height(16.dp))
            VibrantToggleCard("Monthly Performance", "Detailed asset allocation breakdown and tax-harvesting.", Icons.Default.PieChart, monthlyPerf) { monthlyPerf = it }
            Spacer(modifier = Modifier.height(16.dp))
            VibrantToggleCard("Market Insights", "Curated global market trends and institutional analysis.", Icons.Default.AutoGraph, marketInsights) { marketInsights = it }
            
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth().height(64.dp), shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(containerColor = BrandDark)) {
                Text("Save Preferences", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
