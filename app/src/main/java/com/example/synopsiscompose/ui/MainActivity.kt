package com.example.synopsiscompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.synopsiscompose.Screens
import com.example.synopsiscompose.ui.composable.ThemeListScreen
import com.example.synopsiscompose.ui.composable.screen.ThesisListScreen
import com.example.synopsiscompose.ui.composable.screen.ThesisScreen
import com.example.synopsiscompose.ui.theme.SynopsisComposeTheme
import com.example.synopsiscompose.viewmodel.ThesisListViewModelFactory
import com.example.synopsiscompose.viewmodel.ThesisViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SynopsisComposeTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screens.ThemeList.name
                ) {
                    composable(Screens.ThemeList.name) {
                        ThemeListScreen(viewModel()) { themeId -> navController.navigate("${Screens.Theme.name}/$themeId") }
                    }

                    composable(
                        route = "${Screens.Theme.name}/{themeId}",
                        arguments = listOf(
                            navArgument("themeId") { type = NavType.IntType })
                    ) {
                        ThesisListScreen(
                            viewModel(
                                factory = ThesisListViewModelFactory(
                                    it.arguments?.getInt(
                                        "themeId"
                                    ) ?: 0
                                )
                            ), onUpClick = navController::popBackStack
                        ) { thesisId ->
                            navController.navigate("${Screens.Thesis.name}/$thesisId")
                        }
                    }

                    composable(
                        route = "${Screens.Thesis.name}/{thesisId}",
                        arguments = listOf(
                            navArgument("thesisId") { type = NavType.IntType })
                    ) {
                        ThesisScreen(
                            viewModel(
                                factory = ThesisViewModelFactory(
                                    it.arguments?.getInt(
                                        "thesisId"
                                    ) ?: 0
                                )
                            ), onUpClick = navController::popBackStack
                        )
                    }
                }
            }
        }
    }
}