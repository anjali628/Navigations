package com.example.navigations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@Composable
fun MainNavigation() {

    val navHostController = rememberNavController()
    val viewModel:MainViewModel = viewModel()

    NavHost(
        navController = navHostController,
        startDestination = Screen.First.route
    ) {
        composable(Screen.First.route) {
            FirstScreen(navHostController, viewModel)
        }
        composable(Screen.Second.route) {
            SecondScreen(navHostController, viewModel)
        }
    }

}

sealed class Screen(val route: String) {

    object First : Screen("first")
    object Second : Screen("second")

}


@Composable
fun FirstScreen(navHostController: NavHostController,
viewModel: MainViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Button(onClick = {
            navHostController.currentBackStackEntry?.savedStateHandle?.set("name", "Anjali")
            navHostController.navigate(Screen.Second.route)
            viewModel.setData("ANJALI BINDAL")
        }) {
            Text(text = "Send Data to Second Screen")
        }
    }
}

@Composable
fun SecondScreen(navHostController: NavHostController,
viewModel: MainViewModel) {

    //navHostController.navigate(Screen.First.route)
    //navHostController.navigateUp()
val _name by viewModel.data.collectAsState()
    val name = navHostController.previousBackStackEntry?.savedStateHandle?.get<String>("name") ?: ""
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        //Text(text = "Send Data to Second Screen")
        //Text(text = "my name is $name")
        Text(text = "my name is $_name")
    }
}

class MainViewModel : ViewModel() {

    private val _data: MutableStateFlow<String> = MutableStateFlow("")
    var data = _data.asStateFlow()
        private set

    fun setData(name: String) {
        _data.value = name

    }

}