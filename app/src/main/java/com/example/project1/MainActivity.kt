package com.example.project1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project1.data.AppViewModel
import com.example.project1.screens.*
import com.example.project1.ui.theme.Project1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Project1Theme {
                val appViewModel: AppViewModel = viewModel()
                AppNavigation(appViewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: AppViewModel) {
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
                onSignUp = { 
                    viewModel.refresh()
                    navController.navigate("home") 
                },
                onLogin = { navController.navigate("login") },
                onGoogleLogin = { navController.navigate("google_selector") }
            )
        }
        composable("login") {
            LoginScreen(
                onLogin = { 
                    viewModel.refresh()
                    navController.navigate("home") 
                },
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
                onAccountSelected = { 
                    viewModel.refresh()
                    navController.navigate("home") 
                },
                onUseAnotherAccount = { navController.navigate("signup") },
                onCancel = { navController.popBackStack() }
            )
        }
        composable("home") {
            HomeScreen(navController, viewModel)
        }
        composable("portfolio") {
            PortfolioScreen(navController)
        }
        composable("calculator") {
            CalculatorListScreen(navController)
        }
        composable("history") {
            HistoryScreen(navController, viewModel)
        }
        composable("settings") {
            SettingsScreen(navController, viewModel)
        }
        composable("notifications") {
            NotificationScreen(onBack = { navController.popBackStack() })
        }
        
        // Detailed screens
        composable("asset_details") {
            AssetDetailsScreen(onBack = { navController.popBackStack() })
        }
        composable("add_investment") {
            AddInvestmentFlow(
                onFinish = { 
                    navController.popBackStack() 
                },
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
                onResult = { navController.navigate("cagr_result") },
                viewModel = viewModel
            )
        }
        composable("cagr_result") {
            CagrResultScreen(onBack = { navController.popBackStack() }, viewModel = viewModel)
        }
        composable("xirr_calc/{assetType}") { backStackEntry ->
            val assetType = backStackEntry.arguments?.getString("assetType") ?: "Stocks"
            XirrCalculatorScreen(
                assetType = assetType, 
                onBack = { navController.popBackStack() },
                onResult = { navController.navigate("xirr_result") },
                viewModel = viewModel
            )
        }
        composable("xirr_result") {
            XirrResultScreen(onBack = { navController.popBackStack() }, viewModel = viewModel)
        }
        composable("personal_info") {
            val context = androidx.compose.ui.platform.LocalContext.current
            LaunchedEffect(Unit) {
                context.startActivity(android.content.Intent(context, com.example.project1.activities.ProfileActivity::class.java))
                navController.popBackStack()
            }
        }
        composable("login_security") {
            LoginSecurityScreen(onBack = { navController.popBackStack() }, viewModel)
        }
        composable("default_currency") {
            DefaultCurrencyScreen(onBack = { navController.popBackStack() }, viewModel)
        }
        composable("region_language") {
            RegionLanguageScreen(onBack = { navController.popBackStack() }, viewModel)
        }
        composable("inflation_benchmarks") {
            InflationBenchmarksScreen(onBack = { navController.popBackStack() }, viewModel)
        }
        composable("tax_slabs") {
            TaxSlabScreen(onBack = { navController.popBackStack() }, viewModel)
        }
        composable("alert_preferences") {
            AlertPreferencesScreen(onBack = { navController.popBackStack() }, viewModel)
        }
        composable("email_summaries") {
            EmailSummariesScreen(onBack = { navController.popBackStack() })
        }
        composable("export_data") {
            ExportPortfolioScreen(onBack = { navController.popBackStack() })
        }
        composable("inflation_impact") {
            InflationImpactScreen(onBack = { navController.popBackStack() }, viewModel = viewModel)
        }
        composable("performance_analytics") {
            PerformanceAnalyticsScreen(onBack = { navController.popBackStack() }, viewModel)
        }
        composable("sip_calc") {
            SipCalculatorScreen(onBack = { navController.popBackStack() }, viewModel = viewModel)
        }
        composable("support") {
            SupportScreen(onBack = { navController.popBackStack() })
        }
        composable("help_center") {
            HelpCenterScreen(
                onBack = { navController.popBackStack() },
                onContactSupport = { navController.navigate("support") },
                onTopicSelected = { topic ->
                    navController.navigate("help_article/$topic")
                }
            )
        }
        composable("help_article/{topic}") { backStackEntry ->
            val topic = backStackEntry.arguments?.getString("topic") ?: ""
            HelpArticleScreen(topic = topic, onBack = { navController.popBackStack() })
        }
    }
}
