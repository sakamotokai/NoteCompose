package com.example.note.ui.graphs

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.note.HelloScreenViewModel
import com.example.note.MainViewModel

@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController(),
    context: Context,
    viewModel: HelloScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        LocalContext.current as ComponentActivity
    )
) {
    NavHost(
        navController = navController,
        route = rootRoute.RootRoute.route,
        startDestination = viewModel.startDestination.value
    ) {
        composable(route = rootRoute.MainRoute.route) {
            BottomNavGraph()
        }
        composable(route = rootRoute.StartRoute.route) {
            StartNavGraph(context = context)
        }
    }
}

sealed class rootRoute(val route: String) {
    object RootRoute : rootRoute("RootRoute")
    object MainRoute : rootRoute("MainRoute")
    object StartRoute : rootRoute("StartRoute")
}