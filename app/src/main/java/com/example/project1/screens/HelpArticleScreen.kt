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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpArticleScreen(topic: String, onBack: () -> Unit) {
    Scaffold(
        containerColor = BrandBackground,
        topBar = {
            TopAppBar(
                title = { Text("Article", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp)) {
            item {
                when (topic) {
                    "Getting Started" -> GettingStartedContent()
                    "Portfolio Tracking" -> PortfolioTrackingContent()
                    "Security & Privacy" -> SecurityPrivacyContent()
                    "Calculators" -> CalculatorsHelpContent()
                    else -> Text("Content coming soon...")
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun GettingStartedContent() {
    Column {
        Text("Getting Started with Inflatio Smart", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Welcome to your professional-grade investment companion. Inflatio Smart is designed to help you track wealth while accounting for the silent eroder: Inflation.", color = TextGray)
        
        Spacer(modifier = Modifier.height(32.dp))
        ArticleStep(1, "Create Your Account", "Sign up with your professional email or Google account to keep your data synced across devices.")
        ArticleStep(2, "Set Your Preferences", "Go to Settings to choose your default currency (e.g., INR) and set your local inflation benchmark.")
        ArticleStep(3, "Add Your First Asset", "Navigate to the Portfolio tab and tap the '+' button to add Stocks, Mutual Funds, or Real Estate.")
        ArticleStep(4, "Analyze Performance", "Use the Calculators tab to find your Real CAGR and XIRR, which show your true profit after inflation and taxes.")
    }
}

@Composable
fun PortfolioTrackingContent() {
    Column {
        Text("Portfolio Tracking 101", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Master the art of tracking diverse asset classes in one place.", color = TextGray)
        
        Spacer(modifier = Modifier.height(32.dp))
        SectionHeader("Asset Classes Supported")
        BulletPoint("Stocks & ETFs: Manual entry or bulk import.")
        BulletPoint("Mutual Funds: Track SIPs and Lump Sum investments.")
        BulletPoint("Real Estate: Record property value and rental income.")
        
        Spacer(modifier = Modifier.height(24.dp))
        SectionHeader("Real-Time Valuation")
        Text("The dashboard provides a live breakdown of your allocation. The 'Real Value' metric indicates what your current portfolio is worth in today's purchasing power.", fontSize = 14.sp)
    }
}

@Composable
fun SecurityPrivacyContent() {
    Column {
        Text("Security & Privacy Protocols", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Your financial data is sensitive. We treat it with institutional-grade security.", color = TextGray)
        
        Spacer(modifier = Modifier.height(32.dp))
        VibrantInfoCard(Icons.Default.Shield, "Data Encryption", "All data is encrypted using AES-256 standards before being stored in our secure Firebase database.")
        Spacer(modifier = Modifier.height(16.dp))
        VibrantInfoCard(Icons.Default.Https, "Secure Authentication", "We use Firebase Auth and Google Identity Services to ensure only you can access your portfolio.")
    }
}

@Composable
fun CalculatorsHelpContent() {
    Column {
        Text("Using Financial Calculators", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Spacer(modifier = Modifier.height(16.dp))
        
        SectionHeader("CAGR (Compound Annual Growth Rate)")
        Text("Ideal for simple 'buy and hold' investments. It shows the mean annual growth rate of an investment over a specified period of time longer than one year.")
        
        Spacer(modifier = Modifier.height(24.dp))
        SectionHeader("XIRR (Extended Internal Rate of Return)")
        Text("The gold standard for portfolios with multiple cash flows (SIPs, dividends, partial sells). It accounts for the exact timing of every transaction.")
    }
}

@Composable
fun ArticleStep(number: Int, title: String, description: String) {
    Row(modifier = Modifier.padding(vertical = 12.dp)) {
        Surface(
            color = BrandPrimary,
            shape = CircleShape,
            modifier = Modifier.size(28.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(number.toString(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark)
            Text(description, fontSize = 14.sp, color = TextGray)
        }
    }
}

@Composable
fun SectionHeader(text: String) {
    Text(text, fontWeight = FontWeight.ExtraBold, color = BrandPrimary, fontSize = 16.sp, modifier = Modifier.padding(vertical = 8.dp))
}

@Composable
fun BulletPoint(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Box(modifier = Modifier.size(6.dp).background(BrandSecondary, CircleShape))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, fontSize = 14.sp, color = BrandDark, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun VibrantInfoCard(icon: ImageVector, title: String, description: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(icon, null, tint = BrandPrimary)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, fontWeight = FontWeight.ExtraBold, color = BrandDark)
                Text(description, fontSize = 13.sp, color = TextGray)
            }
        }
    }
}
