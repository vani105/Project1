package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinish: () -> Unit, onLogin: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Inflatio Smart",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandPrimary)
                )
                TextButton(onClick = onFinish) {
                    Text("SKIP", color = TextGray, fontWeight = FontWeight.Bold)
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .size(width = if (pagerState.currentPage == index) 32.dp else 8.dp, height = 8.dp)
                                .clip(CircleShape)
                                .background(if (pagerState.currentPage == index) BrandPrimary else BrandPrimary.copy(alpha = 0.2f))
                        )
                    }
                }

                Button(
                    onClick = {
                        if (pagerState.currentPage < 2) {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        } else {
                            onFinish()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            if (pagerState.currentPage == 2) "Create Account" else "Next",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (pagerState.currentPage == 2) {
                    TextButton(onClick = onLogin) {
                        Text("Log In", color = BrandPrimary, fontWeight = FontWeight.ExtraBold)
                    }
                } else {
                    TextButton(onClick = onFinish) {
                        Text("Skip Introduction", color = TextGray)
                    }
                }
            }
        }
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(padding)
        ) { page ->
            when (page) {
                0 -> OnboardingPage1()
                1 -> OnboardingPage2()
                2 -> OnboardingPage3()
            }
        }
    }
}

@Composable
fun OnboardingPage1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(modifier = Modifier.height(320.dp).fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    OnboardingCard("Stocks", AccentCyan, Modifier.height(180.dp), Icons.AutoMirrored.Filled.TrendingUp)
                    OnboardingCard("SIPs", BrandSecondary, Modifier.height(124.dp), Icons.Filled.Schedule)
                }
                OnboardingCard("Mutual Funds", PositiveGreen, Modifier.fillMaxHeight(), Icons.Filled.AccountBalance)
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            "Track every investment you own.",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Manually add stocks, mutual funds, SIPs, or any asset in seconds.",
            style = MaterialTheme.typography.bodyLarge.copy(color = TextGray)
        )
    }
}

@Composable
fun OnboardingPage2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(Color.White, RoundedCornerShape(32.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(AccentYellow.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Timer, contentDescription = null, modifier = Modifier.size(80.dp), tint = AccentYellow)
                }
                Spacer(modifier = Modifier.height(24.dp))
                Surface(color = BrandBackground, shape = RoundedCornerShape(12.dp)) {
                    Text("CPI 2024", modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Surface(color = NegativeRed.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp)) {
                    Text("↘ -6.8% Purchasing Power", color = NegativeRed, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            "Adjusted for real-world inflation.",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center, color = BrandDark)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "We factor today's CPI to show your actual purchasing power gains, not just nominal numbers.",
            style = MaterialTheme.typography.bodyLarge.copy(color = TextGray, textAlign = TextAlign.Center)
        )
    }
}

@Composable
fun OnboardingPage3() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(Color.White, RoundedCornerShape(32.dp))
                .padding(24.dp)
        ) {
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("PORTFOLIO ANALYTICS", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandPrimary))
                    Surface(color = BrandGreen, shape = RoundedCornerShape(8.dp)) {
                        Text("LIVE DATA", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    MiniChartCard("CAGR", "+14.2%", Modifier.weight(1f), BrandPrimary)
                    MiniChartCard("XIRR", "+18.7%", Modifier.weight(1f), BrandSecondary)
                }
                Spacer(modifier = Modifier.height(24.dp))
                Box(modifier = Modifier.fillMaxWidth().height(80.dp).background(BrandPrimary.copy(alpha = 0.05f), RoundedCornerShape(12.dp)))
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            "CAGR and XIRR — both, always.",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center, color = BrandDark)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Understand time-weighted and cash-flow-weighted returns side by side for total clarity.",
            style = MaterialTheme.typography.bodyLarge.copy(color = TextGray, textAlign = TextAlign.Center)
        )
    }
}

@Composable
fun OnboardingCard(title: String, iconColor: Color, modifier: Modifier, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.size(56.dp).background(iconColor.copy(alpha = 0.1f), CircleShape), contentAlignment = Alignment.Center) {
                 Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark)
        }
    }
}

@Composable
fun MiniChartCard(title: String, value: String, modifier: Modifier, color: Color) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.05f)),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = color))
            Text(value, style = MaterialTheme.typography.titleLarge.copy(color = color, fontWeight = FontWeight.ExtraBold))
        }
    }
}
