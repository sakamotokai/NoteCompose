package com.example.note

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.note.datastoreRepository.PreferenceManager
import com.example.note.datastoreRepository.preferencesValue
import com.example.note.ui.graphs.MainNavGraph
import com.example.note.ui.graphs.Route
import com.example.note.ui.graphs.rootRoute
import com.example.note.ui.graphs.startRoute
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HelloScreenViewModel(application: Application):AndroidViewModel(application){
    private var _isLoading: MutableState<String> = mutableStateOf("")
    var isLoading = _isLoading
    private val _startDestination:MutableState<String> = mutableStateOf(rootRoute.MainRoute.route)
    val startDestination = _startDestination
    init {
        viewModelScope.launch{
            PreferenceManager(application.applicationContext).readWithDataStore(
                preferencesKey = PreferenceManager.PreferencesKey.crossingHelloToMain
            ).collect{item->
                if(item != "")
                    _startDestination.value = rootRoute.MainRoute.route
                else
                    _startDestination.value = rootRoute.StartRoute.route
            }
        }
        _isLoading.value = preferencesValue.firstAppStart.name
    }
}