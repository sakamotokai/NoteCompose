package com.example.note.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.note.MainViewModel
import com.example.note.db.Modeldb
import com.example.note.ui.graphs.Route
import com.example.note.ui.theme.NoteTheme

//карточка(сама заметка) на главном экране и экране избранного
@Composable
fun noteCard(
    modeldb: Modeldb,
    navController: NavHostController = rememberNavController(),
    onClick: () -> Unit = {},
    mainViewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity)
) {
    Surface(
        modifier = Modifier
            .height(100.dp)
            .clickable {
                mainViewModel.setModeldb(modeldb)
                onClick()
            }
            .border(color = MaterialTheme.colors.error, width = 4.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.primary,
    ) {
        Text(
            text = modeldb.text,
            modifier = Modifier
                .padding(5.dp)
                .background(color = Color.White.copy(alpha = 0.8f),),
            color = MaterialTheme.colors.onSecondary,
        )
    }
}