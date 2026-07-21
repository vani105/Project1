package com.example.project1.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1.ui.theme.*

@Composable
fun LandingScreen(onGetStarted: () -> Unit, onLogin: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BrandPrimary, BrandSecondary)
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LogoComponent()

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Inflatio Smart",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = buildAnnotatedString {
                    append("See what your money ")
                    withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
                        append("really")
                    }
                    append(" earned")
                },
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White.copy(alpha = 0.9f))
            )
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Get Started", color = BrandPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = BrandPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = onLogin) {
                Text(
                    text = buildAnnotatedString {
                        append("Already have an account? ")
                        withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.ExtraBold)) {
                            append("Log In")
                        }
                    },
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "INSTITUTIONAL GRADE ANALYSIS • SECURED",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White.copy(alpha = 0.6f),
                    letterSpacing = 2.sp
                )
            )
        }
    }
}

@Composable
fun LogoComponent() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Bar(height = 24.dp, color = Color.White)
            Bar(height = 48.dp, color = BrandGreen)
            Bar(height = 72.dp, color = Color.White)
            Bar(height = 36.dp, color = BrandGreen)
        }
    }
}

@Composable
fun Bar(height: androidx.compose.ui.unit.Dp, color: Color) {
    Box(
        modifier = Modifier
            .width(10.dp)
            .height(height)
            .background(color, RoundedCornerShape(5.dp))
    )
}
