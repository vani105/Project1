package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.project1.ui.theme.*

@Composable
fun PortfolioScreen(navController: NavController) {
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
                    VibrantChip(label = "Filters", leadingIcon = Icons.Default.FilterList)
                    VibrantChip(label = "Assets", trailingIcon = Icons.Default.KeyboardArrowDown)
                    VibrantChip(label = "Gainers", trailingIcon = Icons.Default.KeyboardArrowDown)
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("ASSET", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
                    Text("RETURNS (NOM/REAL)", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(portfolioAssets) { asset ->
                        PortfolioAssetItem(asset) {
                            navController.navigate("asset_details")
                        }
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
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
fun VibrantChip(label: String, leadingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null, trailingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null) {
    Surface(
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
