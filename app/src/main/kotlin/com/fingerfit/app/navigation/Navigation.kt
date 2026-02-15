package com.fingerfit.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fingerfit.app.screens.*

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object FingerTraining : Screen("finger_training/{difficulty}") {
        fun createRoute(difficulty: Int) = "finger_training/$difficulty"
    }
    object ArmTraining : Screen("arm_training/{difficulty}") {
        fun createRoute(difficulty: Int) = "arm_training/$difficulty"
    }
    object Results : Screen("results/{score}/{total}/{mode}") {
        fun createRoute(score: Int, total: Int, mode: String) = "results/$score/$total/$mode"
    }
    object Settings : Screen("settings")
}

@Composable
fun FingerFitNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onFingerTrainingClick = { difficulty ->
                    navController.navigate(Screen.FingerTraining.createRoute(difficulty))
                },
                onArmTrainingClick = { difficulty ->
                    navController.navigate(Screen.ArmTraining.createRoute(difficulty))
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }
        
        composable(
            route = Screen.FingerTraining.route,
            arguments = listOf(navArgument("difficulty") { type = NavType.IntType })
        ) { backStackEntry ->
            val difficulty = backStackEntry.arguments?.getInt("difficulty") ?: 1
            FingerTrainingScreen(
                difficulty = difficulty,
                onComplete = { score, total ->
                    navController.navigate(Screen.Results.createRoute(score, total, "finger")) {
                        popUpTo(Screen.Home.route)
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.ArmTraining.route,
            arguments = listOf(navArgument("difficulty") { type = NavType.IntType })
        ) { backStackEntry ->
            val difficulty = backStackEntry.arguments?.getInt("difficulty") ?: 1
            ArmTrainingScreen(
                difficulty = difficulty,
                onComplete = { score, total ->
                    navController.navigate(Screen.Results.createRoute(score, total, "arm")) {
                        popUpTo(Screen.Home.route)
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.Results.route,
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType },
                navArgument("mode") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 0
            val mode = backStackEntry.arguments?.getString("mode") ?: "finger"
            ResultsScreen(
                score = score,
                total = total,
                mode = mode,
                onHomeClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onRetryClick = {
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
