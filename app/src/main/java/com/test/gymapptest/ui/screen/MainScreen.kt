package com.test.gymapptest.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.test.gymapptest.ui.navigation.AppNavigationRail
import com.test.gymapptest.ui.navigation.RailDestination
import com.test.gymapptest.ui.viewmodels.SearchType
import com.test.gymapptest.ui.viewmodels.SearchViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RutinaScr(
    modifier: Modifier,
    activity: ComponentActivity,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val windowSizeClass = calculateWindowSizeClass(activity = activity)

    var showNAvigationRail = windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact


    val currentSearchQuery by searchViewModel.searchQuery.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        val type = when (currentRoute) {
            RailDestination.EJERCICIOS.route -> SearchType.EJERCICIOS
            RailDestination.RUTINAS.route -> SearchType.RUTINAS
            else -> SearchType.NONE
        }
        searchViewModel.setCurrentSearchType(type)
    }


    Scaffold(
        topBar = {
            SearchableTopAppBar(title = when (searchViewModel.currentSearchType.collectAsState().value) { // Título dinámico
                SearchType.EJERCICIOS -> "Ejercicios"
                SearchType.RUTINAS -> "Rutinas"
                else -> "GymApp"
            },
                searchQuery = currentSearchQuery,
                onSearchQueryChanged = { query -> searchViewModel.onSearchTextChange(query) },
                onSearchStarted = {  },
                onSearchConfirmed = { searchViewModel.onSearchConfirmed() },
                onCloseSearch = {
                    searchViewModel.onSearchTextChange("")
                },

                actions = {

                })
        }
    ) { innerPaddingValues ->
        Row(
            modifier = Modifier
                .padding(innerPaddingValues)
                .fillMaxSize()
        )
        {
            AppNavigationRail(
                navController,
                modifier = modifier,
                destination = RailDestination.entries
            )


            NavHost(
                navController = navController,
                startDestination = RailDestination.RUTINAS.route,
                modifier = modifier

            ) {
                composable(RailDestination.RUTINAS.route) {
                    RutinaScreen(searchViewModel = searchViewModel)
                }
                composable(RailDestination.EJERCICIOS.route) {
                    EjercicioScrenn(searchViewModel = searchViewModel)

                }
            }
        }
    }


}