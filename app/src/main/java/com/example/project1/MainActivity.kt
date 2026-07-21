package com.example.project1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project1.screens.*
import com.example.project1.ui.theme.Project1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project1Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "landing") {
        composable("landing") {
            LandingScreen(
                onGetStarted = { navController.navigate("onboarding") },
                onLogin = { navController.navigate("login") },
            )
        }
        composable("onboarding") {
            OnboardingScreen(
                onFinish = { navController.navigate("signup") },
                onLogin = { navController.navigate("login") },
            )
        }
        composable("signup") {
            SignUpScreen(
                onSignUp = { navController.navigate("home") },
                onLogin = { navController.navigate("login") },
                onGoogleLogin = { navController.navigate("google_selector") }
            )
        }
        composable("login") {
            LoginScreen(
                onLogin = { navController.navigate("home") },
                onSignUp = { navController.navigate("signup") },
                onGoogleLogin = { navController.navigate("google_selector") },
                onForgotPassword = { navController.navigate("forgot_password") }
            )
        }
        composable("forgot_password") {
            ForgotPasswordScreen(
                onSendLink = { navController.popBackStack() },
                onBackToLogin = { navController.popBackStack() }
            )
        }
        composable("google_selector") {
            GoogleAccountSelectorScreen(
                onAccountSelected = { navController.navigate("home") },
                onCancel = { navController.popBackStack() }
            )
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("portfolio") {
            PortfolioScreen(navController)
        }
        composable("calculator") {
            CalculatorListScreen(navController)
        }
        composable("history") {
            HistoryScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
        
        // Detailed screens
        composable("asset_details") {
            AssetDetailsScreen(onBack = { navController.popBackStack() })
        }
        composable("add_investment") {
            AddInvestmentFlow(
                onFinish = { navController.navigate("home") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("select_type/{calcType}") { backStackEntry ->
            val calcType = backStackEntry.arguments?.getString("calcType") ?: "cagr"
            InvestmentTypeSelectionScreen(
                calcType = calcType,
                onTypeSelected = { assetType ->
                    navController.navigate("${calcType}_calc/$assetType")
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("cagr_calc/{assetType}") { backStackEntry ->
            val assetType = backStackEntry.arguments?.getString("assetType") ?: "Stocks"
            CagrCalculatorScreen(
                assetType = assetType, 
                onBack = { navController.popBackStack() },
                onResult = { navController.navigate("cagr_result") }
            )
        }
        composable("cagr_result") {
            CagrResultScreen(onBack = { navController.popBackStack() })
        }
        composable("xirr_calc/{assetType}") { backStackEntry ->
            val assetType = backStackEntry.arguments?.getString("assetType") ?: "Stocks"
            XirrCalculatorScreen(
                assetType = assetType, 
                onBack = { navController.popBackStack() },
                onResult = { navController.navigate("xirr_result") }
            )
        }
        composable("xirr_result") {
            XirrResultScreen(onBack = { navController.popBackStack() })
        }
        composable("personal_info") {
            PersonalInfoScreen(onBack = { navController.popBackStack() })
        }
        composable("login_security") {
            LoginSecurityScreen(onBack = { navController.popBackStack() })
        }
        composable("default_currency") {
            DefaultCurrencyScreen(onBack = { navController.popBackStack() })
        }
        composable("region_language") {
            RegionLanguageScreen(onBack = { navController.popBackStack() })
        }
        composable("inflation_benchmarks") {
            InflationBenchmarksScreen(onBack = { navController.popBackStack() })
        }
        composable("tax_slabs") {
            TaxSlabScreen(onBack = { navController.popBackStack() })
        }
        composable("alert_preferences") {
            AlertPreferencesScreen(onBack = { navController.popBackStack() })
        }
        composable("email_summaries") {
            EmailSummariesScreen(onBack = { navController.popBackStack() })
        }
        composable("export_data") {
            ExportPortfolioScreen(onBack = { navController.popBackStack() })
        }
        composable("inflation_impact") {
            InflationImpactScreen(onBack = { navController.popBackStack() })
        }
        composable("sip_calc") {
            SipCalculatorScreen(onBack = { navController.popBackStack() })
        }
        composable("support") {
            SupportScreen(onBack = { navController.popBackStack() })
        }
        composable("tax_slabs") {
            TaxSlabScreen(onBack = { navController.popBackStack() })
        }
    }
}
