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
fun TaxSlabScreen(onBack: () -> Unit, viewModel: AppViewModel) {
    var selectedSlab by remember { mutableIntStateOf(2) }

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Tax Configuration", fontWeight = FontWeight.ExtraBold, color = BrandDark) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = BrandDark) } },
                actions = { IconButton(onClick = {}) { Icon(Icons.Default.Settings, null, tint = BrandDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            item {
                Text("Select Your Slab", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Text("Precise tax modeling ensures your 'Real Returns' are accurate.", color = Color.Black, fontWeight = FontWeight.Bold)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White), 
                    shape = RoundedCornerShape(28.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        VibrantTaxSlabItem("0% No Tax", "Income < ₹3L (New Regime)", 0, selectedSlab == 0, Tax0Color) { selectedSlab = 0 }
                        VibrantTaxSlabItem("10% Low Bracket", "Emerging portfolios", 1, selectedSlab == 1, Tax10Color) { selectedSlab = 1 }
                        VibrantTaxSlabItem("20% Mid Bracket", "Standard professional tier", 2, selectedSlab == 2, Tax20Color) { selectedSlab = 2 }
                        VibrantTaxSlabItem("30% High Bracket", "HNI / Top tier income", 3, selectedSlab == 3, Tax30Color) { selectedSlab = 3 }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(), 
                    colors = CardDefaults.cardColors(containerColor = BrandDark),
                    shape = RoundedCornerShape(28.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(28.dp)) {
                        Text("POST-TAX PROJECTION", color = BrandPrimary, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp))
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("Gross Return", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("₹12,40,000", color = Color.White, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold))
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Tax Liability", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                val taxValue = when(selectedSlab) {
                                    0 -> 0
                                    1 -> 124000
                                    2 -> 248000
                                    else -> 372000
                                }
                                Text("-₹$taxValue", color = NegativeRed, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold))
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text("Net Real Return", color = BrandGreen, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                        val netValue = when(selectedSlab) {
                            0 -> 1240000
                            1 -> 1116000
                            2 -> 992000
                            else -> 868000
                        }
                        Text("₹$netValue", color = Color.White, style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold))
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = {
                                viewModel.syncPreferences()
                                onBack()
                            }, 
                            modifier = Modifier.fillMaxWidth().height(60.dp), 
                            colors = ButtonDefaults.buttonColors(containerColor = BrandGreen),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Apply & Sync Portfolio", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun VibrantTaxSlabItem(title: String, subtitle: String, index: Int, isSelected: Boolean, bgColor: Color, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, BrandPrimary) else androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
        color = if (isSelected) BrandPrimary.copy(alpha = 0.05f) else Color.White
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(44.dp).background(bgColor, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Percent, null, modifier = Modifier.size(20.dp), tint = BrandDark)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark)
                Text(subtitle, style = MaterialTheme.typography.bodySmall.copy(color = Color.Black, fontWeight = FontWeight.Bold))
            }
            if (isSelected) {
                Icon(Icons.Default.CheckCircle, null, tint = BrandPrimary, modifier = Modifier.size(24.dp))
            }
        }
    }
}
