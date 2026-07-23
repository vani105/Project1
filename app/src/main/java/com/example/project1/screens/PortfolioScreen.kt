package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project1.data.FirebaseManager
import com.example.project1.data.UserHolding
import com.example.project1.ui.theme.*

import androidx.compose.material.icons.automirrored.filled.TrendingUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(navController: NavController) {
    var holdings by remember { mutableStateOf<List<UserHolding>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var showFilters by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("All Assets") }

    LaunchedEffect(Unit) {
        holdings = FirebaseManager.getHoldings()
        isLoading = false
    }

    val filteredHoldings = remember(holdings, selectedFilter) {
        if (selectedFilter == "All Assets") holdings
        else holdings.filter { it.type.contains(selectedFilter, ignoreCase = true) }
    }

    val sheetState = rememberModalBottomSheetState()

    if (showFilters) {
        ModalBottomSheet(
            onDismissRequest = { showFilters = false },
            sheetState = sheetState,
            containerColor = BrandBackground,
            dragHandle = { BottomSheetDefaults.DragHandle(color = BrandPrimary) }
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                Text("Filter Portfolio", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.height(24.dp))
                
                listOf("All Assets", "Equity (Stocks)", "Mutual Fund", "ETF", "Commodity").forEach { filter ->
                    FilterOption(
                        label = filter,
                        isSelected = selectedFilter == filter,
                        onClick = {
                            selectedFilter = filter
                            showFilters = false
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }

    AppNavigationWrapper(navController, "portfolio") { padding ->
        Box(modifier = Modifier.fillMaxSize().background(BrandBackground).padding(padding)) {
            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "My Portfolio", 
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark)
                )
                Text(
                    "Live tracking of your adjusted wealth.", 
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextGray)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    VibrantChip(
                        label = if (selectedFilter == "All Assets") "Filters" else selectedFilter,
                        leadingIcon = Icons.Default.FilterList,
                        onClick = { showFilters = true }
                    )
                    VibrantChip(label = "Analytics", leadingIcon = Icons.AutoMirrored.Filled.TrendingUp, onClick = { navController.navigate("performance_analytics") })
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("ASSET", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
                    Text("VALUE & CHANGE", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = BrandPrimary)
                    }
                } else if (filteredHoldings.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.List, null, modifier = Modifier.size(64.dp), tint = DividerColor)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("No investments found", color = TextGray)
                        }
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(filteredHoldings) { holding ->
                            PortfolioHoldingItem(holding) {
                                navController.navigate("asset_details")
                            }
                        }
                        item { Spacer(modifier = Modifier.height(80.dp)) }
                    }
                }
            }
            
            FloatingActionButton(
                onClick = { navController.navigate("add_investment") },
                modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp),
                containerColor = BrandPrimary,
                contentColor = Color.White,
                shape = RoundedCornerShape(20.dp),
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
            ) {
                Icon(Icons.Default.Add, null, modifier = Modifier.size(32.dp))
            }
        }
    }
}

@Composable
fun FilterOption(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) BrandPrimary.copy(alpha = 0.1f) else Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) BrandPrimary else DividerColor)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold, color = if (isSelected) BrandPrimary else BrandDark)
            if (isSelected) Icon(Icons.Default.Check, null, tint = BrandPrimary)
        }
    }
}

@Composable
fun PortfolioHoldingItem(holding: UserHolding, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(48.dp).background(BrandPrimary.copy(alpha = 0.1f), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                        Text(holding.name.take(1), fontWeight = FontWeight.ExtraBold, color = BrandPrimary, fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(holding.name, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                        Text(holding.type, style = MaterialTheme.typography.bodySmall.copy(color = TextGray))
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("₹${holding.value}", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                    Surface(
                        color = (if (holding.change.startsWith("-")) NegativeRed else PositiveGreen).copy(alpha = 0.1f), 
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "${if (holding.change.startsWith("-")) "" else "+"}${holding.change}%",
                            color = if (holding.change.startsWith("-")) NegativeRed else PositiveGreen,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VibrantChip(label: String, leadingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null, trailingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null, onClick: () -> Unit = {}) {
    Surface(
        onClick = onClick,
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (leadingIcon != null) Icon(leadingIcon, null, modifier = Modifier.size(16.dp), tint = BrandPrimary)
            Text(label, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = BrandDark)
            if (trailingIcon != null) Icon(trailingIcon, null, modifier = Modifier.size(16.dp), tint = TextGray)
        }
    }
}

@Composable
fun PortfolioAssetItem(asset: PortfolioAsset, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(asset.name, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                    Text(asset.fullName, style = MaterialTheme.typography.bodySmall.copy(color = TextGray))
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("₹${asset.totalValue}", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                    Text("${asset.qty} Qty @ ₹${asset.price}", style = MaterialTheme.typography.bodySmall.copy(color = TextGray))
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = DividerColor)
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("NOMINAL GAIN", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
                    Surface(color = PositiveGreen.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                        Text("+${asset.nominalReturn}%", color = PositiveGreen, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("REAL (INF-ADJ)", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
                    Surface(
                        color = (if (asset.realReturn > 0) PositiveGreen else NegativeRed).copy(alpha = 0.1f), 
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "${if (asset.realReturn > 0) "+" else ""}${asset.realReturn}%",
                            color = if (asset.realReturn > 0) PositiveGreen else NegativeRed,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

data class PortfolioAsset(
    val name: String,
    val fullName: String,
    val totalValue: String,
    val qty: String,
    val price: String,
    val nominalReturn: String,
    val realReturn: Double
)

val portfolioAssets = listOf(
    PortfolioAsset("RELIANCE", "Reliance Industries Ltd.", "2,65,000", "100", "2,400", "10.4", 5.3),
    PortfolioAsset("TCS", "Tata Consultancy Services", "3,45,200", "90", "3,200", "7.8", 2.9),
    PortfolioAsset("HDFCBANK", "HDFC Bank Limited", "1,58,000", "100", "1,550", "1.9", -3.2),
    PortfolioAsset("INFY", "Infosys Limited", "72,400", "50", "1,400", "3.4", -1.8)
)
