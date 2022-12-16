package com.example.note.ui.graphs

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.note.HelloScreenViewModel
import com.example.note.MainScreenViewModel
import com.example.note.ui.bottombar.MainBottomBar
import com.example.note.ui.screens.FullAboutScreen
import com.example.note.ui.theme.Purple500
import com.example.note.ui.theme.Purple700

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: MainScreenViewModel = viewModel(
        LocalContext.current as ComponentActivity
    )
) {
    //TODO("it is todo to me in future, make it, please")
    var saveState by remember{ mutableStateOf(Unit) }
    var tabPage by remember { mutableStateOf(TabPage.HOME) }
    val backgroundColor by animateColorAsState(if (tabPage == TabPage.HOME) Purple700 else Purple500)
    Scaffold(
        bottomBar = {
            HomeTabBar(
                differentState = viewModel.startDestination.value,
                navController = navController,
                backgroundColor = backgroundColor,
                tabPage = tabPage,
                onTabSelected = { tabPage = it },
                toScreen = {
                    //TODO("MAKE IT")
                    if (tabPage == TabPage.HOME) {
                        if (viewModel.startDestination.value == Route.FullAboutScreen.route) {
                            navController.navigate(Route.FullAboutScreen.route)
                        } else {
                            navController.navigate(Route.LessAboutScreen.route)
                            //navController.popBackStack(Route.LessAboutSelectedScreen.route, inclusive = true)
                        }
                    } else {
                        if (viewModel.startDestination.value == Route.FullAboutScreen.route) {
                            navController.navigate(Route.SelectedScreen.route)
                        } else {
                            navController.navigate(Route.LessAboutSelectedScreen.route)
                            //navController.popBackStack(Route.LessAboutScreen.route, inclusive = true)
                        }
                    }
                })
        }//BottomBar(navController,viewModel) },
    ) {
        MainNavGraph(navController = navController, LessState = {saveState = it})
    }
}

@Composable
fun BottomBar(
    navController: NavHostController, viewModel: MainScreenViewModel = viewModel(
        LocalContext.current as ComponentActivity
    )
) {
    var homeScreen = MainBottomBar.Home
    homeScreen.route =
        if (viewModel.startDestination.value == Route.FullAboutScreen.route)
            Route.MainScreen.route
        else
            Route.LessAboutScreen.route

    var selectedScreen = MainBottomBar.Selected
    selectedScreen.route =
        if (viewModel.startDestination.value == Route.FullAboutScreen.route)
            Route.SelectedScreen.route
        else
            Route.LessAboutSelectedScreen.route
    Log.e("Log", "in Bottom Bar ${viewModel.startDestination.value}")
    val screens = listOf(
        homeScreen,
        selectedScreen
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: MainBottomBar,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        })
}