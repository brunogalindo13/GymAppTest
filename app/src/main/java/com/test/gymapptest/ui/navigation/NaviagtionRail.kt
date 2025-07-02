package com.test.gymapptest.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue

@Composable
fun AppNavigationRail(
    navController: NavController,
    modifier: Modifier = Modifier,
    destination: List<RailDestination> = RailDestination.entries
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationRail(modifier = modifier){
        destination.forEach {
            destination -> val isSelected = currentRoute == destination.route
            NavigationRailItem(selected = isSelected, onClick = {
                if (currentRoute != destination.route) {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }, icon = { Icon(destination.icon, contentDescription = destination.label) },
                label = { Text(destination.label) },
                alwaysShowLabel = false
            )

        }

    }

}