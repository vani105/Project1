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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1.data.AppViewModel
import com.example.project1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerformanceAnalyticsScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    val totalNominal = viewModel.holdings.sumOf { it.value.replace(",", "").toDoubleOrNull() ?: 0.0 }
    val inflationRate = viewModel.manualInflationRate.toDoubleOrNull() ?: 6.0
    val erosionAmount = totalNominal * (inflationRate / 100.0)
    val realCorpus = totalNominal - erosionAmount

    val latestXirrStr = viewModel.lastXirrResult?.nominalXirr?.replace("%", "") ?: "18.72"
    val latestXirr = latestXirrStr.toDoubleOrNull() ?: 18.72
    val benchmarkXirr = 14.20
    val alpha = latestXirr - benchmarkXirr

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Performance Analytics", fontWeight = FontWeight.ExtraBold, color = BrandDark) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("Alpha Generation", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Text("Your portfolio's edge against standard benchmarks.", color = Color.Black, fontWeight = FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("Portfolio XIRR", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
                                Text(String.format("%.2f%%", latestXirr), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandPrimary))
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Nifty 50 TRI", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
                                Text(String.format("%.2f%%", benchmarkXirr), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Surface(
                            color = (if (alpha >= 0) BrandGreen else NegativeRed).copy(alpha = 0.1f), 
                            shape = RoundedCornerShape(12.dp), 
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "ALPHA: ${if (alpha >= 0) "+" else ""}${String.format("%.2f%%", alpha)} P.A.", 
                                color = if (alpha >= 0) BrandGreen else NegativeRed, 
                                modifier = Modifier.padding(16.dp), 
                                textAlign = TextAlign.Center, 
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text("Inflation Erosion Analysis", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Text("Impact of ${String.format("%.1f%%", inflationRate)} annual inflation on your wealth.", style = MaterialTheme.typography.bodySmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(16.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = BrandDark), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        AnalyticsRow("Nominal Corpus", String.format("₹%,.0f", totalNominal), Color.White)
                        AnalyticsRow("Inflation Impact (1Y)", String.format("-₹%,.0f", erosionAmount), NegativeRed)
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f), modifier = Modifier.padding(vertical = 16.dp))
                        AnalyticsRow("Real Corpus (Adjusted)", String.format("₹%,.0f", realCorpus), BrandGreen)
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Text("Asset Allocation Efficiency", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.height(16.dp))
                
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        if (viewModel.holdings.isEmpty()) {
                            Text("No holdings found. Add assets to see distribution.", color = TextGray, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                        } else {
                            val groups = viewModel.holdings.groupBy { it.type }
                            groups.forEach { (type, items) ->
                                val typeTotal = items.sumOf { it.value.replace(",", "").toDoubleOrNull() ?: 0.0 }
                                val percentage = if (totalNominal > 0) (typeTotal / totalNominal) * 100 else 0.0
                                
                                AllocationBar(type, String.format("₹%,.0f", typeTotal), percentage.toFloat())
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun AllocationBar(label: String, value: String, percentage: Float) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, fontWeight = FontWeight.Bold, color = BrandDark)
            Text(value, fontWeight = FontWeight.ExtraBold, color = BrandPrimary)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth().height(8.dp).background(DividerColor, RoundedCornerShape(4.dp))) {
            Box(modifier = Modifier.fillMaxWidth(percentage / 100f).height(8.dp).background(BrandPrimary, RoundedCornerShape(4.dp)))
        }
        Text("${String.format("%.1f%%", percentage)} of total portfolio", fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun AnalyticsRow(label: String, value: String, valueColor: Color) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Color.White.copy(alpha = 0.6f), fontWeight = FontWeight.Bold)
        Text(value, color = valueColor, fontWeight = FontWeight.ExtraBold)
    }
}
