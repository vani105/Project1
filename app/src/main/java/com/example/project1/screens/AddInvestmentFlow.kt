package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
fun AddInvestmentFlow(onFinish: () -> Unit, onBack: () -> Unit) {
    var step by remember { mutableIntStateOf(1) }
    
    // Step 1 Data
    var assetName by remember { mutableStateOf("") }
    var tickerSymbol by remember { mutableStateOf("") }
    var assetType by remember { mutableStateOf("Equity (Stocks)") }
    
    // Step 2 Data (Purchase)
    var quantity by remember { mutableStateOf("") }
    var buyPrice by remember { mutableStateOf("") }
    var purchaseDate by remember { mutableStateOf("06/16/2023") }
    
    // Step 3 Data (Valuation)
    var currentMarketPrice by remember { mutableStateOf("4250.50") }
    var valuationDate by remember { mutableStateOf("06/16/2026") }
    var isSold by remember { mutableStateOf(false) }
    
    // Step 4 Data (Inflation)
    var useRbiCpi by remember { mutableStateOf(true) }
    var customInflationRate by remember { mutableStateOf("6.0") }

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Inflatio Smart", fontWeight = FontWeight.ExtraBold, color = BrandPrimary) },
                navigationIcon = {
                    IconButton(onClick = { if (step > 1) step-- else onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BrandDark)
                    }
                },
                actions = { Box(modifier = Modifier.padding(end = 16.dp)) { LogoIconSmall() } },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StepIndicator(1, "Asset", step > 1, step == 1)
                HorizontalDivider(modifier = Modifier.weight(1f).padding(horizontal = 8.dp), color = if (step > 1) BrandPrimary else DividerColor)
                StepIndicator(2, "Purchase", step > 2, step == 2)
                HorizontalDivider(modifier = Modifier.weight(1f).padding(horizontal = 8.dp), color = if (step > 2) BrandPrimary else DividerColor)
                StepIndicator(3, "Value", step > 3, step == 3)
                HorizontalDivider(modifier = Modifier.weight(1f).padding(horizontal = 8.dp), color = if (step > 3) BrandPrimary else DividerColor)
                StepIndicator(4, "Review", step > 4, step == 4)
            }

            Spacer(modifier = Modifier.height(40.dp))

            when (step) {
                1 -> Step1AssetIdentity(assetName, { assetName = it }, tickerSymbol, { tickerSymbol = it }, assetType)
                2 -> Step2PurchaseHistory(quantity, { quantity = it }, buyPrice, { buyPrice = it }, purchaseDate)
                3 -> Step3CurrentValuation(currentMarketPrice, { currentMarketPrice = it }, valuationDate, isSold, { isSold = it })
                4 -> Step4InflationAdjustment(useRbiCpi, { useRbiCpi = it }, customInflationRate, { customInflationRate = it })
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { if (step < 4) step++ else onFinish() },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (step == 4) BrandGreen else BrandDark)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = when(step) {
                            1 -> "Continue to History"
                            2 -> "Next: Valuation"
                            3 -> "Calculate Returns"
                            else -> "Save & Calculate"
                        }, 
                        fontSize = 18.sp, 
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(if (step == 4) Icons.Default.TrendingUp else Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun StepIndicator(number: Int, label: String, isCompleted: Boolean, isCurrent: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(if (isCurrent || isCompleted) BrandPrimary else DividerColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted && !isCurrent) {
                 Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(16.dp))
            } else {
                 Text(number.toString(), color = if (isCurrent || isCompleted) Color.White else TextGray, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = if (isCurrent || isCompleted) BrandPrimary else TextGray))
    }
}

@Composable
fun Step1AssetIdentity(name: String, onNameChange: (String) -> Unit, ticker: String, onTickerChange: (String) -> Unit, type: String) {
    Column {
        Text("Asset Identity", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Text("Identify the financial instrument you wish to track.", color = TextGray)
        Spacer(modifier = Modifier.height(32.dp))
        VibrantLabelField("ASSET NAME", "e.g. Reliance Industries", name, onNameChange)
        Spacer(modifier = Modifier.height(24.dp))
        VibrantLabelField("TICKER SYMBOL", "E.G. RELIANCE", ticker, onTickerChange)
        Spacer(modifier = Modifier.height(24.dp))
        Text("ASSET TYPE", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Spacer(modifier = Modifier.height(8.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
            Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(type, fontWeight = FontWeight.Bold)
                Icon(Icons.Default.KeyboardArrowDown, null, tint = BrandPrimary)
            }
        }
    }
}

@Composable
fun Step2PurchaseHistory(qty: String, onQtyChange: (String) -> Unit, price: String, onPriceChange: (String) -> Unit, date: String) {
    Column {
        Text("Purchase Details", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Text("When and at what price did you acquire this asset?", color = TextGray)
        Spacer(modifier = Modifier.height(32.dp))
        VibrantLabelField("QUANTITY / UNITS", "e.g. 100", qty, onQtyChange)
        Spacer(modifier = Modifier.height(24.dp))
        VibrantLabelField("BUY PRICE PER UNIT (₹)", "e.g. 2400.00", price, onPriceChange)
        Spacer(modifier = Modifier.height(24.dp))
        VibrantLabelField("PURCHASE DATE", "MM/DD/YYYY", date, {})
    }
}

@Composable
fun Step3CurrentValuation(price: String, onPriceChange: (String) -> Unit, date: String, isSold: Boolean, onIsSoldChange: (Boolean) -> Unit) {
    Column {
        Text("Current Market Valuation", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Text("Provide latest pricing data to calculate net returns.", color = TextGray)
        Spacer(modifier = Modifier.height(32.dp))
        
        Card(colors = CardDefaults.cardColors(containerColor = InfoBlueBg), shape = RoundedCornerShape(20.dp)) {
            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("ASSET STATUS", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandPrimary))
                    Text("Mark as Sold?", fontWeight = FontWeight.ExtraBold, color = BrandDark)
                }
                Switch(checked = isSold, onCheckedChange = onIsSoldChange, colors = SwitchDefaults.colors(checkedTrackColor = BrandPrimary))
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        VibrantLabelField("MARKET PRICE (PER UNIT)", "₹ 4250.50", price, onPriceChange)
        Spacer(modifier = Modifier.height(24.dp))
        VibrantLabelField("DATE OF CURRENT PRICE", "06/16/2026", date, {})
        
        Spacer(modifier = Modifier.height(24.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), border = androidx.compose.foundation.BorderStroke(2.dp, BrandPrimary.copy(alpha = 0.1f)), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("ESTIMATED TOTAL CURRENT VALUE", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = TextGray))
                Text("₹ 5,31,312.50", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Text("Based on units currently held.", color = TextGray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun Step4InflationAdjustment(useRbi: Boolean, onUseRbiChange: (Boolean) -> Unit, rate: String, onRateChange: (String) -> Unit) {
    Column {
        Text("Inflation Adjustment", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Text("Configure how your returns are adjusted for purchasing power.", color = TextGray)
        Spacer(modifier = Modifier.height(32.dp))
        
        Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(24.dp)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Use RBI CPI", fontWeight = FontWeight.ExtraBold, color = BrandDark)
                        Text("Use official Indian historical inflation average.", style = MaterialTheme.typography.bodySmall.copy(color = TextGray))
                    }
                    Switch(checked = useRbi, onCheckedChange = onUseRbiChange)
                }
                Spacer(modifier = Modifier.height(24.dp))
                VibrantTextField(value = rate, onValueChange = onRateChange, label = "Inflation Rate %", placeholder = "6.0")
                Spacer(modifier = Modifier.height(24.dp))
                VibrantLabelField("Base Year", "2024 (Current)", "2024 (Current)", {})
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Card(colors = CardDefaults.cardColors(containerColor = ErrorRedBg), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("PROJECTED INFLATION LOSS: -₹1,42,050", color = NegativeRed, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.fillMaxWidth().height(8.dp).background(DividerColor, CircleShape)) {
                    Box(modifier = Modifier.fillMaxWidth(0.6f).fillMaxHeight().background(NegativeRed, CircleShape))
                }
            }
        }
    }
}
