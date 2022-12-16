package com.example.note.ui.screens


import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.note.MainViewModel
import com.example.note.globalDao
import com.example.note.ui.graphs.BottomBar
import com.example.note.ui.graphs.HomeTabBar
import com.example.note.ui.graphs.Route
import com.example.note.ui.graphs.TabPage
import com.example.note.ui.theme.Purple500
import com.example.note.ui.theme.Purple700
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity),
    onClick: () -> Unit = {},
    bottomBarVisibility: Boolean = true,
    floatingButtonVisibility: Boolean = true
) {
    val context = LocalContext.current
    val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    Scaffold(
        bottomBar = {
        if(bottomBarVisibility){HomeTabBar(
            backgroundColor = Purple500,
            tabPage = TabPage.HOME,
            onTabSelected = {},
            toScreen = {},
            differentState = ""
        )} else {}},
        floatingActionButton =
        if (floatingButtonVisibility) {
            {
                FloatingActionButton(onClick = {
                    viewModel.clearModeldb()
                    onClick()
                }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        } else {
            {}
        }
    ) {
        var text by remember { mutableStateOf("") }
        val list by globalDao.getAllModelsdb().observeAsState(listOf())
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                    .weight(1f)
            ) {
                items(list.reversed()) { item ->
                    noteCard(modeldb = item, navController, onClick)
                }
            }
            /*if (bottomBarVisibility)
                BottomBar(navController = navController)*/
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun LessAboutScreen(
    navController: NavHostController = rememberNavController(),
    onClick: () -> Unit = {},
    navigateTo: String = Route.MainScreen.route,
    updateBackground:()->Unit = {},
    updateBackgroundBoolean: Boolean = false
) {
    BackdropScaffold(
        appBar = {},
        backLayerContent =
        if (navigateTo == Route.MainScreen.route) {
            {
                MainScreen(
                    bottomBarVisibility = false,
                    onClick = onClick,
                    floatingButtonVisibility = false
                )
            }
        } else {
            {
                SelectedNoteScreen(
                    onClick = onClick,
                    bottomBarVisibility = false
                )
            }
        },
        frontLayerContent = {
            FullAboutScreen(updateBackground = updateBackground,updateBackgroundBoolean = updateBackgroundBoolean)
        },
        peekHeight = 60.dp,
        headerHeight = 160.dp
    ) {

    }
}
