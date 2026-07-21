package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1.ui.theme.*

@Composable
fun GoogleAccountSelectorScreen(onAccountSelected: () -> Unit, onCancel: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.Close, null, tint = TextGray)
                    }
                    GoogleIcon()
                    Spacer(modifier = Modifier.width(48.dp)) // To center the logo
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Choose an account",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = BrandDark
                    )
                )
                Text(
                    "to continue to Inflatio Smart",
                    color = TextGray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(googleAccounts) { account ->
                        GoogleAccountItem(account, onAccountSelected)
                    }
                    item {
                        AddAccountItem()
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "To continue, Google will share your name, email address, language preference and profile picture with Inflatio Smart.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextGray,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Privacy Policy • Terms of Service",
                    color = BrandPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun GoogleAccountItem(account: GoogleAccount, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = Color.Transparent,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(BrandPrimary.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, null, tint = BrandPrimary)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(account.name, fontWeight = FontWeight.Bold, color = BrandDark)
                Text(account.email, style = MaterialTheme.typography.bodySmall.copy(color = TextGray))
            }
        }
    }
}

@Composable
fun AddAccountItem() {
    Surface(
        onClick = {},
        color = Color.Transparent,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.PersonAddAlt1, null, tint = BrandDark)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text("Use another account", fontWeight = FontWeight.Bold, color = BrandDark)
        }
    }
}

data class GoogleAccount(val name: String, val email: String)

val googleAccounts = listOf(
    GoogleAccount("Alexander Sterling", "a.sterling@realreturns.io"),
    GoogleAccount("John Doe", "john.doe@gmail.com")
)
