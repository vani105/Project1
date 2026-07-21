package com.example.project1.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.project1.R
import com.example.project1.ui.theme.*

@Composable
fun GoogleIcon() {
    Image(
        painter = painterResource(id = R.drawable.ic_google_logo),
        contentDescription = "Google Logo",
        modifier = Modifier.size(24.dp)
    )
}

@Composable
fun LogoIcon(modifier: Modifier = Modifier.size(48.dp)) {
    Box(
        modifier = modifier
            .background(BrandPrimary, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(modifier = Modifier.width(4.dp).height(10.dp).background(Color.White, RoundedCornerShape(1.dp)))
            Box(modifier = Modifier.width(4.dp).height(20.dp).background(BrandGreen, RoundedCornerShape(1.dp)))
            Box(modifier = Modifier.width(4.dp).height(30.dp).background(Color.White, RoundedCornerShape(1.dp)))
            Box(modifier = Modifier.width(4.dp).height(15.dp).background(BrandGreen, RoundedCornerShape(1.dp)))
        }
    }
}

@Composable
fun LogoIconSmall(modifier: Modifier = Modifier.size(36.dp)) {
    Box(
        modifier = modifier
            .background(BrandPrimary, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            Box(modifier = Modifier.width(3.dp).height(8.dp).background(Color.White, RoundedCornerShape(0.5.dp)))
            Box(modifier = Modifier.width(3.dp).height(16.dp).background(BrandGreen, RoundedCornerShape(0.5.dp)))
            Box(modifier = Modifier.width(3.dp).height(24.dp).background(Color.White, RoundedCornerShape(0.5.dp)))
            Box(modifier = Modifier.width(3.dp).height(12.dp).background(BrandGreen, RoundedCornerShape(0.5.dp)))
        }
    }
}

@Composable
fun VibrantLabelField(label: String, placeholder: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = TextGray.copy(alpha = 0.5f)) },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = DividerColor,
                focusedBorderColor = BrandPrimary,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
    }
}

@Composable
fun VibrantTextField(
    value: String, 
    onValueChange: (String) -> Unit, 
    label: String, 
    placeholder: String, 
    isPassword: Boolean = false,
    showLabel: Boolean = true,
    leadingIcon: ImageVector? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        if (showLabel) {
            Text(label, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold, color = BrandDark))
            Spacer(modifier = Modifier.height(8.dp))
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = TextGray.copy(alpha = 0.5f)) },
            leadingIcon = if (leadingIcon != null) { { Icon(leadingIcon, contentDescription = null, tint = BrandPrimary) } } else null,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = if (isPassword) {
                {
                    val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null, tint = BrandPrimary)
                    }
                }
            } else null,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = DividerColor,
                focusedBorderColor = BrandPrimary,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
    }
}
