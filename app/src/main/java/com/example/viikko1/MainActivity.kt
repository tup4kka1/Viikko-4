package com.example.viikko1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.viikko1.ui.screens.HomeScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikko1.navigation.ROUTE_HOME
import com.example.viikko1.navigation.ROUTE_CALENDAR
import com.example.viikko1.viewmodel.TaskViewModel
import com.example.viikko1.ui.screens.CalendarScreen
import com.example.viikko1.navigation.ROUTE_SETTINGS
import com.example.viikko1.ui.screens.SettingsScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val taskViewModel: TaskViewModel = viewModel()

            NavHost(
                navController = navController,
                startDestination = ROUTE_HOME
            ) {
                composable(ROUTE_HOME) {
                    HomeScreen(
                        taskViewModel = taskViewModel,
                        onGoCalendar = {
                            navController.navigate(ROUTE_CALENDAR)
                        },
                        onGoSettings = {
                            navController.navigate(ROUTE_SETTINGS)
                        }
                    )

                }

                composable(ROUTE_CALENDAR) {
                    CalendarScreen(
                        taskViewModel = taskViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(ROUTE_SETTINGS) {
                    SettingsScreen(
                        onBack = { navController.popBackStack() }
                    )
                }

            }
        }

    }
}
