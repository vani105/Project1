package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.project1.data.AppViewModel
import com.example.project1.data.HistoryItem
import com.example.project1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController, viewModel: AppViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    
    val filteredItems = viewModel.historyItems.filter { item ->
        val itemType = item.type.uppercase()
        val filter = selectedFilter.uppercase()
        
        // Exact type match
        val typeMatches = when (filter) {
            "ALL" -> true
            "CAGR" -> itemType == "CAGR" && !item.title.startsWith("XIRR", ignoreCase = true)
            else -> itemType == filter
        }
        
        typeMatches && item.title.contains(searchQuery, ignoreCase = true)
    }

    AppNavigationWrapper(navController, "history") { padding ->
        Column(modifier = Modifier.fillMaxSize().background(BrandBackground).padding(padding).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(44.dp).background(BrandPrimary.copy(alpha = 0.1f), CircleShape), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.History, null, tint = BrandPrimary, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Inflatio Smart", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {}) { Icon(Icons.Default.Notifications, null, tint = BrandDark) }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search calculations...", color = TextGray.copy(alpha = 0.5f)) },
                leadingIcon = { Icon(Icons.Default.Search, null, tint = BrandPrimary) },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = DividerColor,
                    focusedBorderColor = BrandPrimary
                )
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            LazyRow(
                modifier = Modifier.fillMaxWidth(), 
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(end = 16.dp)
            ) {
                items(listOf("All", "SIP", "CAGR", "XIRR", "Lumpsum")) { filter ->
                    VibrantFilterChip(
                        selected = selectedFilter == filter, 
                        label = filter,
                        onClick = { selectedFilter = filter }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("RECENT CALCULATIONS (${filteredItems.size})", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.ExtraBold))
            Spacer(modifier = Modifier.height(12.dp))
            
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filteredItems, key = { it.id }) { item ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {
                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                viewModel.deleteHistoryItem(item)
                                true
                            } else false
                        }
                    )

                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = {
                            val color = when (dismissState.dismissDirection) {
                                SwipeToDismissBoxValue.EndToStart -> NegativeRed
                                else -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color, RoundedCornerShape(20.dp))
                                    .padding(end = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.White)
                            }
                        },
                        content = {
                            VibrantHistoryItemCard(item)
                        }
                    )
                }
                if (filteredItems.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                            Text("No history found", color = TextGray, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(40.dp)) }
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                Icon(Icons.Default.Refresh, null, tint = BrandPrimary.copy(alpha = 0.4f), modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text("Swipe left on any item to delete it from history.", color = TextGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun VibrantFilterChip(selected: Boolean, label: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = if (selected) BrandPrimary else Color.White,
        shape = RoundedCornerShape(12.dp),
        border = if (!selected) androidx.compose.foundation.BorderStroke(1.dp, DividerColor) else null,
        shadowElevation = if (selected) 4.dp else 0.dp
    ) {
        Text(
            label, 
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            fontWeight = FontWeight.ExtraBold,
            color = if (selected) Color.White else BrandDark,
            fontSize = 14.sp,
            maxLines = 1
        )
    }
}

@Composable
fun VibrantHistoryItemCard(item: HistoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        (when (item.type.uppercase()) {
                            "SIP" -> BrandPrimary
                            "CAGR" -> BrandSecondary
                            "XIRR" -> BrandPrimary
                            else -> AccentYellow
                        }).copy(alpha = 0.1f),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    when (item.type.uppercase()) {
                        "SIP" -> Icons.Default.TrendingUp
                        "CAGR" -> Icons.Default.Calculate
                        "XIRR" -> Icons.Default.AutoGraph
                        else -> Icons.Default.Timer
                    },
                    null,
                    tint = when (item.type.uppercase()) {
                        "SIP" -> BrandPrimary
                        "CAGR" -> BrandSecondary
                        "XIRR" -> BrandPrimary
                        else -> AccentYellow
                    },
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, fontWeight = FontWeight.ExtraBold, color = BrandDark, fontSize = 16.sp)
                Text("${item.date} • ${item.duration}", style = MaterialTheme.typography.bodySmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
            }
            Column(horizontalAlignment = Alignment.End) {
                val prefix = if (item.realValue.contains("%")) "" else "₹"
                Text("$prefix${item.realValue} Real", color = PositiveGreen, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                Text("Nominal: $prefix${item.nominalValue}", style = MaterialTheme.typography.labelSmall.copy(color = TextGray, fontWeight = FontWeight.Bold))
            }
        }
    }
}
