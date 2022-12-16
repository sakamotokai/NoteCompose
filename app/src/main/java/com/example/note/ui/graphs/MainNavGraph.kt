package com.example.note.ui.graphs

import android.content.SharedPreferences
import androidx.activity.ComponentActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.note.MainScreenViewModel
import com.example.note.isFirstLaunch
import com.example.note.ui.screens.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainNavGraph(navController: NavHostController,LessState:(unit:Unit)->Unit) {
    var saveState by remember{ mutableStateOf(Unit) }
    LessState(saveState)
    val viewModel: MainScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        LocalContext.current as ComponentActivity
    )
    NavHost(
        navController = navController,
        route = Route.Root.route,
        startDestination =
        if (viewModel.startDestination.value == Route.FullAboutScreen.route)
            Route.MainScreen.route
        else Route.LessAboutScreen.route
    ) {
        composable(route = Route.MainScreen.route) {
            MainScreen(
                navController,
                onClick =
                if (viewModel.startDestination.value == Route.FullAboutScreen.route) {
                    {
                        navController.navigate(Route.FullAboutScreen.route)
                    }
                } else {
                    {
                        navController.navigate(Route.LessAboutScreen.route)
                    }
                }
            )
        }

        composable(route = Route.SelectedScreen.route) {
            SelectedNoteScreen(navController,
                onClick = {
                    navController.navigate(Route.FullAboutScreen.route)
                })
        }

        composable(route = Route.FullAboutScreen.route) {
            FullAboutScreen(navController = navController, onClick = {
                navController.popBackStack(Route.FullAboutScreen.route, inclusive = true)
            })
        }

        composable(route = Route.LessAboutScreen.route) {
            saveState = LessAboutScreen(
                onClick = { navController.navigate(Route.LessAboutScreen.route) },
                navigateTo = Route.MainScreen.route
            )
        }

        composable(route = Route.LessAboutSelectedScreen.route) {
            LessAboutScreen(
                onClick = { navController.navigate(Route.LessAboutSelectedScreen.route) },
                navigateTo = Route.LessAboutSelectedScreen.route,
                updateBackground = {
                    navController.popBackStack(
                        Route.LessAboutSelectedScreen.route,
                        inclusive = true
                    )
                },
                updateBackgroundBoolean = true
            )
        }
    }
}

sealed class Route(val route: String) {
    object Root : Route("ROOT")
    object MainScreen : Route("HOME")
    object SelectedScreen : Route("SELECTED")
    object FullAboutScreen : Route("ABOUT")
    object LessAboutScreen : Route("LessAbout")
    object LessAboutSelectedScreen : Route("LessAboutSelectedScreen")
}