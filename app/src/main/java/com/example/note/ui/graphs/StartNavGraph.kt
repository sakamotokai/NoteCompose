package com.example.note.ui.graphs

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.note.ui.screens.HelloScreen
import com.example.note.ui.screens.MainScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StartNavGraph(navController: NavHostController = rememberNavController(),context: Context){
    NavHost(
        navController = navController,
        route = startRoute.Root.route,
        startDestination = startRoute.HelloScreen.route
    ) {
        composable(route = startRoute.HelloScreen.route){
            HelloScreen(context)
        }
        composable(route = startRoute.MainScreen.route){
            MainScreen()
        }
    }
}

sealed class startRoute(val route:String){
    object Root:startRoute("Root")
    object HelloScreen:startRoute("HelloScreen")
    object MainScreen:startRoute("MainScreen")
}