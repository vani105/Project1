package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.project1.data.FirebaseManager
import com.example.project1.data.UserHolding
import com.example.project1.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInvestmentFlow(onFinish: () -> Unit, onBack: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var step by remember { mutableIntStateOf(1) }
    val scope = rememberCoroutineScope()
    var isSaving by remember { mutableStateOf(false) }
    
    // Step 1 Data
    var assetName by remember { mutableStateOf("") }
    var tickerSymbol by remember { mutableStateOf("") }
    var assetType by remember { mutableStateOf("Equity (Stocks)") }
    
    // Step 2 Data (Purchase)
    var quantity by remember { mutableStateOf("") }
    var buyPrice by remember { mutableStateOf("") }
    var purchaseDate by remember { mutableStateOf("06/16/2023") }
    
    // Step 3 Data (Valuation)
    var currentMarketPrice by remember { mutableStateOf("2500") }
    var valuationDate by remember { mutableStateOf("06/16/2026") }
    var isSold by remember { mutableStateOf(false) }

    // Date Picker State
    var showDatePicker by remember { mutableStateOf(false) }
    var datePickerTarget by remember { mutableStateOf(0) } // 2 for purchase, 3 for valuation

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.getDefault())
                            .format(java.util.Date(millis))
                        if (datePickerTarget == 2) purchaseDate = date
                        else if (datePickerTarget == 3) valuationDate = date
                    }
                    showDatePicker = false
                }) {
                    Text("OK", color = BrandPrimary, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel", color = BrandDark)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

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

            Spacer(modifier = Modifier.height(32.dp))

            Box(modifier = Modifier.weight(1f)) {
                when (step) {
                    1 -> Step1AssetIdentity(assetName, { assetName = it }, tickerSymbol, { tickerSymbol = it }, assetType)
                    2 -> Step2PurchaseHistory(quantity, { quantity = it }, buyPrice, { buyPrice = it }, purchaseDate) {
                        datePickerTarget = 2
                        showDatePicker = true
                    }
                    3 -> Step3CurrentValuation(
                        currentMarketPrice, 
                        { currentMarketPrice = it }, 
                        valuationDate, 
                        isSold, 
                        { isSold = it }, 
                        quantity,
                        onClear = {
                            currentMarketPrice = ""
                            isSold = false
                        }
                    ) {
                        datePickerTarget = 3
                        showDatePicker = true
                    }
                    4 -> Step4Review(
                        assetName, tickerSymbol, assetType,
                        quantity, buyPrice, purchaseDate,
                        currentMarketPrice, valuationDate, isSold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { 
                    if (step < 4) {
                        step++
                    } else {
                        isSaving = true
                        scope.launch {
                            try {
                                val holding = UserHolding(
                                    name = assetName,
                                    type = assetType,
                                    value = String.format("%.2f", (quantity.toDoubleOrNull() ?: 0.0) * (currentMarketPrice.toDoubleOrNull() ?: 0.0)),
                                    change = String.format("%.1f", (((currentMarketPrice.toDoubleOrNull() ?: 0.0) - (buyPrice.toDoubleOrNull() ?: 0.0)) / (buyPrice.toDoubleOrNull() ?: 1.0) * 100.0))
                                )
                                val result = FirebaseManager.saveHolding(holding)
                                if (result.isSuccess) {
                                    android.widget.Toast.makeText(context, "Asset saved to portfolio!", android.widget.Toast.LENGTH_SHORT).show()
                                    onFinish()
                                } else {
                                    val error = result.exceptionOrNull()?.message ?: "Unknown error"
                                    android.widget.Toast.makeText(context, "Failed: $error", android.widget.Toast.LENGTH_LONG).show()
                                    isSaving = false
                                }
                            } catch (e: Exception) {
                                android.widget.Toast.makeText(context, "Error: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
                                isSaving = false
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                enabled = !isSaving,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (step == 4) BrandGreen else BrandPrimary)
            ) {
                if (isSaving) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = when(step) {
                                1 -> "Continue to History"
                                2 -> "Next: Valuation"
                                3 -> "Next: Review Details"
                                else -> "Save & Add to Portfolio"
                            }, 
                            fontSize = 18.sp, 
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(if (step == 4) Icons.Default.CheckCircle else Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
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
fun Step2PurchaseHistory(qty: String, onQtyChange: (String) -> Unit, price: String, onPriceChange: (String) -> Unit, date: String, onDateClick: () -> Unit) {
    Column {
        Text("Purchase Details", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Text("When and at what price did you acquire this asset?", color = TextGray)
        Spacer(modifier = Modifier.height(32.dp))
        VibrantLabelField("QUANTITY / UNITS", "e.g. 100", qty, onQtyChange)
        Spacer(modifier = Modifier.height(24.dp))
        VibrantLabelField("BUY PRICE PER UNIT (₹)", "e.g. 2400.00", price, onPriceChange)
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("PURCHASE DATE", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            onClick = onDateClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
            color = Color.White
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(date, fontWeight = FontWeight.Bold)
                Icon(Icons.Default.CalendarToday, null, tint = BrandPrimary, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun Step3CurrentValuation(
    price: String, 
    onPriceChange: (String) -> Unit, 
    date: String, 
    isSold: Boolean, 
    onIsSoldChange: (Boolean) -> Unit,
    quantity: String,
    onClear: () -> Unit,
    onDateClick: () -> Unit
) {
    val totalValue = (quantity.toDoubleOrNull() ?: 0.0) * (price.toDoubleOrNull() ?: 0.0)

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Current Market Valuation", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark), modifier = Modifier.weight(1f))
            TextButton(onClick = onClear) {
                Text("Clear", color = NegativeRed, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
        Text("Provide latest pricing data to calculate net returns.", color = TextGray)
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(colors = CardDefaults.cardColors(containerColor = InfoBlueBg), shape = RoundedCornerShape(20.dp)) {
            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("ASSET STATUS", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandPrimary))
                    Text("Mark as Sold?", fontWeight = FontWeight.ExtraBold, color = BrandDark)
                }
                Switch(checked = isSold, onCheckedChange = onIsSoldChange, colors = SwitchDefaults.colors(checkedTrackColor = BrandPrimary))
            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        VibrantLabelField("MARKET PRICE (PER UNIT)", "₹ 4250.50", price, onPriceChange)
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("DATE OF CURRENT PRICE", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            onClick = onDateClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
            color = Color.White
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text(date, fontWeight = FontWeight.Bold)
                Icon(Icons.Default.CalendarToday, null, tint = BrandPrimary, modifier = Modifier.size(20.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth(), 
            colors = CardDefaults.cardColors(containerColor = Color.White), 
            border = androidx.compose.foundation.BorderStroke(2.dp, BrandPrimary.copy(alpha = 0.2f)), 
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("ESTIMATED TOTAL CURRENT VALUE", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandPrimary))
                Spacer(modifier = Modifier.height(4.dp))
                Text("₹ ${String.format("%,.2f", totalValue)}", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Text("Total valuation based on ${quantity.ifBlank { "0" }} units held.", color = TextGray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun Step4Review(
    name: String, ticker: String, type: String,
    qty: String, buyPrice: String, buyDate: String,
    marketPrice: String, valDate: String, isSold: Boolean
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Text("Review Details", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
            Text("Verify your investment data before saving to portfolio.", color = TextGray)
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            ReviewSection("Asset Identity") {
                ReviewRow("Name", name)
                ReviewRow("Ticker", ticker.uppercase())
                ReviewRow("Type", type)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ReviewSection("Purchase History") {
                ReviewRow("Quantity", qty)
                ReviewRow("Buy Price", "₹ ${String.format("%,.2f", buyPrice.toDoubleOrNull() ?: 0.0)}")
                ReviewRow("Date", buyDate)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            ReviewSection("Current Valuation") {
                ReviewRow("Market Price", "₹ ${String.format("%,.2f", marketPrice.toDoubleOrNull() ?: 0.0)}")
                ReviewRow("Date", valDate)
                ReviewRow("Status", if (isSold) "Sold" else "Active")
                
                val totalValue = (qty.toDoubleOrNull() ?: 0.0) * (marketPrice.toDoubleOrNull() ?: 0.0)
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = DividerColor.copy(alpha = 0.5f))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total Value", fontWeight = FontWeight.Bold, color = BrandDark)
                    Text("₹ ${String.format("%,.2f", totalValue)}", fontWeight = FontWeight.ExtraBold, color = BrandPrimary, fontSize = 20.sp)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ReviewSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(title, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandPrimary, letterSpacing = 0.5.sp))
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun ReviewRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = TextGray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Text(value, fontWeight = FontWeight.Bold, color = BrandDark, fontSize = 14.sp)
    }
}
