package com.example.note.ui.topAppBar

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.note.MainViewModel
import com.example.note.db.Modeldb
import com.example.note.doRoom

@Composable
fun AboutTopAppBar(
    navController: NavHostController = rememberNavController(),
    onClick: () -> Unit = {},
    modeldb: Modeldb = Modeldb(),
    mainViewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity),
    checkState: Boolean = false,
    changeStateFun: () -> Unit = {},
    roomDoing: doRoom = doRoom.insert,
    titleValue: String,
    textValue: String,
    updateBackground:()->Unit = {},
    updateBackgroundBoolean: Boolean = false
) {
    val context = LocalContext.current
    //var changeState1 by remember{ mutableStateOf(changeState) }
    Column {
        TopAppBar(
            title = {},
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.primarySurface,
            navigationIcon = {
                IconButton(onClick = { onClick() }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            },
            actions = {
                IconButton(onClick = {
                    Toast.makeText(context,modeldb.selected.toString(),Toast.LENGTH_SHORT).show()
                    mainViewModel.updateNote(
                        Modeldb(
                            id = modeldb.id,
                            title = modeldb.title,
                            text = modeldb.text,
                            selected = !modeldb.selected
                        )
                    )
                    modeldb.selected = !modeldb.selected
                    if(updateBackgroundBoolean) updateBackground()
                }) {
                    Icon(Icons.Filled.Star, null)
                }
                if (checkState) {
                    IconButton(onClick = {
                        if (titleValue == "" && textValue == "") {
                            mainViewModel.deleteNote(modeldb)
                            changeStateFun()
                        } else {
                            if (roomDoing == doRoom.update) {
                                mainViewModel.updateNote(modeldb)
                                changeStateFun()
                            } else if (roomDoing == doRoom.insert) {
                                mainViewModel.insertNote(modeldb)
                                changeStateFun()
                            }
                            if(updateBackgroundBoolean) updateBackground()
                        }
                    }) {
                        Icon(Icons.Filled.Check, null)
                    }
                }
            }
        )
    }
}